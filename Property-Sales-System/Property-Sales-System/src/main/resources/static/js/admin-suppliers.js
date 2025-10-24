async function loadSuppliers() {
    const response = await fetch("http://localhost:8080/suppliers");
    const suppliers = await response.json();

    const container = document.getElementById("admin-suppliers");
    container.innerHTML = "";

    suppliers.forEach(s => {
        const div = document.createElement("div");
        div.innerHTML = `<b>${s.name}</b> - ${s.contact}`;
        container.appendChild(div);
    });
}
