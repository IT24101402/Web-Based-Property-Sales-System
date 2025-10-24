document.addEventListener("DOMContentLoaded", () => {
  fetch("http://localhost:8080/admin/maintenance")
    .then(res => res.json())
    .then(requests => {
      const tbody = document.getElementById("maintenance-table");
      tbody.innerHTML = "";
      requests.forEach(r => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td class="px-4 py-2">${r.id}</td>
          <td class="px-4 py-2">${r.propertyTitle}</td>
          <td class="px-4 py-2">${r.description}</td>
          <td class="px-4 py-2">${r.status}</td>
          <td class="px-4 py-2">
            <button onclick="markDone(${r.id})" class="bg-green-500 text-white px-2 py-1 rounded hover:bg-green-600">Done</button>
          </td>
        `;
        tbody.appendChild(tr);
      });
    });
});

function markDone(id) {
  fetch(`http://localhost:8080/admin/maintenance/${id}/done`, { method: "POST" })
    .then(() => location.reload());
}
