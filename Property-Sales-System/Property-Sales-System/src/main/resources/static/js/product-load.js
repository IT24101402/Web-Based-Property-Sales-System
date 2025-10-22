async function loadProducts() {
    const response = await fetch("http://localhost:8080/products");
    const products = await response.json();

    const container = document.getElementById("product-list");
    container.innerHTML = "";

    products.forEach(p => {
        const div = document.createElement("div");
        div.innerHTML = `<h3>${p.name}</h3><p>${p.description}</p><p>Price: ${p.price}</p>`;
        container.appendChild(div);
    });
}

window.onload = loadProducts;
