async function loadOrders() {
    const response = await fetch("http://localhost:8080/orders");
    const orders = await response.json();

    const container = document.getElementById("admin-orders");
    container.innerHTML = "";

    orders.forEach(o => {
        const div = document.createElement("div");
        div.innerHTML = `Order #${o.id} - User: ${o.userId} - Total: ${o.total}`;
        container.appendChild(div);
    });
}
