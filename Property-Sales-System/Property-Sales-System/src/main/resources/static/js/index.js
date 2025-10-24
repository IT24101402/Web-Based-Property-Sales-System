document.addEventListener("DOMContentLoaded", () => {
  const grid = document.getElementById("property-grid");
  const searchInput = document.getElementById("search");
  const filterCategory = document.getElementById("filter-category");
  const filterPrice = document.getElementById("filter-price");
  const filterPriceDisplay = document.getElementById("filter-price-display");
  const applyFilters = document.getElementById("apply-filters");

  let properties = [];

  function loadProperties() {
    fetch("http://localhost:8080/properties")
      .then(res => res.json())
      .then(data => {
        properties = data;
        renderProperties(properties);
      })
      .catch(err => console.error(err));
  }

  function renderProperties(list) {
    grid.innerHTML = "";
    if(list.length === 0){
      grid.innerHTML = "<p class='col-span-3 text-center text-gray-500'>No properties found</p>";
      return;
    }
    list.forEach(p => {
      const card = document.createElement("div");
      card.className = "bg-white rounded-xl shadow p-4 hover:shadow-lg transition relative";
      card.innerHTML = `
        <img src="${p.image || 'images/default-property.png'}" class="w-full h-40 object-cover rounded mb-2" alt="${p.title}">
        <h3 class="text-lg font-semibold">${p.title}</h3>
        <p class="text-gray-600">${p.category} | ${p.location}</p>
        <p class="text-green-600 font-bold mt-1">LKR ${p.price.toLocaleString()}</p>
        <button class="add-to-cart mt-2 bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
          data-id="${p.id}" data-title="${p.title}" data-price="${p.price}">Inquire</button>
      `;
      grid.appendChild(card);
    });
    bindAddToCartButtons();
  }

  function bindAddToCartButtons() {
    const buttons = document.querySelectorAll(".add-to-cart");
    buttons.forEach(btn => {
      btn.addEventListener("click", () => {
        const id = btn.dataset.id;
        const title = btn.dataset.title;
        const price = parseFloat(btn.dataset.price);
        addToCart({id, title, price, quantity:1});
      });
    });
  }

  function applyFilterLogic() {
    const term = searchInput.value.toLowerCase();
    const category = filterCategory.value;
    const maxPrice = parseFloat(filterPrice.value) || 50000000;

    const filtered = properties.filter(p => {
      const matchesTerm = p.title.toLowerCase().includes(term);
      const matchesCategory = category ? p.category === category : true;
      const matchesPrice = p.price <= maxPrice;
      return matchesTerm && matchesCategory && matchesPrice;
    });

    renderProperties(filtered);
  }

  filterPrice.addEventListener("input", () => {
    filterPriceDisplay.textContent = `Up to LKR ${parseInt(filterPrice.value).toLocaleString()}`;
  });

  applyFilters.addEventListener("click", applyFilterLogic);
  document.getElementById("search-btn").addEventListener("click", applyFilterLogic);

  loadProperties();
});
