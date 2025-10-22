async function loadProducts() {
    const response = await fetch("http://localhost:8080/products");
    const products = await response.json();

    const container = document.getElementById("admin-products");
    container.innerHTML = "";

    products.forEach(p => {
        const div = document.createElement("div");
        div.innerHTML = `
      <b>${p.name}</b> - ${p.price} 
      <button onclick="deleteProduct(${p.id})">Delete</button>`;
        container.appendChild(div);
    });
}

async function deleteProduct(id) {
    await fetch(`http://localhost:8080/products/${id}`, { method: "DELETE" });
    loadProducts();
}
