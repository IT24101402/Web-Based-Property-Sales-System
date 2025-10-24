async function loadBrands() {
    const response = await fetch("http://localhost:8080/brands");
    const brands = await response.json();

    const container = document.getElementById("admin-brands");
    container.innerHTML = "";

    brands.forEach(b => {
        const div = document.createElement("div");
        div.innerHTML = `<b>${b.name}</b>`;
        container.appendChild(div);
    });
}
