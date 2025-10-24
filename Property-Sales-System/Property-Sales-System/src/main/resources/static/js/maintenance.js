document.addEventListener("DOMContentLoaded", () => {
    const tbody = document.querySelector("#maintenance-table tbody");

    // Sample maintenance requests
    const requests = [
        {id: 1, property: "Luxury Villa", description: "Leaky faucet", status: "Pending"},
        {id: 2, property: "Modern Apartment", description: "Broken AC", status: "Completed"},
        {id: 3, property: "Cozy House", description: "Roof repair", status: "In Progress"}
    ];

    requests.forEach(req => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${req.id}</td>
            <td>${req.property}</td>
            <td>${req.description}</td>
            <td>${req.status}</td>
        `;
        tbody.appendChild(tr);
    });
});
