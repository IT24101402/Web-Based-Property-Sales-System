async function loadCategories() {
    const response = await fetch("http://localhost:8080/categories");
    const categories = await response.json();

    const container = document.getElementById("admin-categories");
    container.innerHTML = "";

    categories.forEach(c => {
        const div = document.createElement("div");
        div.innerHTML = `<b>${c.name}</b>`;
        container.appendChild(div);
    });
}
