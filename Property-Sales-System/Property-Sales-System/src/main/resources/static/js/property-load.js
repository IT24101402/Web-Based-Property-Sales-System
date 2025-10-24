document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/properties") // your backend endpoint
        .then(res => res.json())
        .then(properties => {
            const grid = document.getElementById("property-grid");
            grid.innerHTML = "";

            properties.forEach(p => {
                const card = document.createElement("div");
                card.className = "bg-white rounded-lg shadow hover:shadow-lg overflow-hidden transition";
                card.innerHTML = `
          <img src="${p.image || 'images/sample-property.jpg'}" alt="${p.name}" class="w-full h-48 object-cover">
          <div class="p-4">
            <h3 class="text-lg font-semibold text-gray-800">${p.name}</h3>
            <p class="text-sm text-gray-600">${p.type} | ${p.location}</p>
            <p class="mt-2 font-bold text-green-600">LKR ${p.price.toLocaleString()}</p>
            <a href="property-detail.html?id=${p.id}" class="mt-3 block bg-green-600 hover:bg-green-700 text-white text-center py-2 rounded">View Details</a>
          </div>
        `;
                grid.appendChild(card);
            });
        })
        .catch(err => console.error("Failed to load properties:", err));
});
