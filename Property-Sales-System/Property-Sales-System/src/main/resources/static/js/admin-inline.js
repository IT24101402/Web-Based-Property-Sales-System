document.addEventListener("DOMContentLoaded", () => {
  loadUsers();
  loadProperties();
});

// ------------------ USERS -------------------
function loadUsers() {
  fetch("http://localhost:8080/admin/users")
    .then(res => res.json())
    .then(users => {
      const tbody = document.getElementById("users-table");
      tbody.innerHTML = "";
      users.forEach(u => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td class="px-4 py-2">${u.id}</td>
          <td class="px-4 py-2" contenteditable="true" onblur="updateUser(${u.id}, 'name', this.textContent)">${u.name}</td>
          <td class="px-4 py-2" contenteditable="true" onblur="updateUser(${u.id}, 'email', this.textContent)">${u.email}</td>
          <td class="px-4 py-2" contenteditable="true" onblur="updateUser(${u.id}, 'role', this.textContent)">${u.role}</td>
          <td class="px-4 py-2">
            <button onclick="deleteUser(${u.id})" class="bg-red-500 text-white px-2 py-1 rounded hover:bg-red-600">Delete</button>
          </td>
        `;
        tbody.appendChild(tr);
      });
    });
}

function addUserRow() {
  const tbody = document.getElementById("users-table");
  const tr = document.createElement("tr");
  tr.innerHTML = `
    <td class="px-4 py-2">New</td>
    <td class="px-4 py-2" contenteditable="true"></td>
    <td class="px-4 py-2" contenteditable="true"></td>
    <td class="px-4 py-2" contenteditable="true"></td>
    <td class="px-4 py-2">
      <button onclick="saveNewUser(this)" class="bg-green-500 text-white px-2 py-1 rounded hover:bg-green-600">Save</button>
    </td>
  `;
  tbody.prepend(tr);
}

function saveNewUser(btn) {
  const row = btn.parentElement.parentElement;
  const data = {
    name: row.children[1].textContent,
    email: row.children[2].textContent,
    role: row.children[3].textContent
  };
  fetch("http://localhost:8080/admin/users", {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify(data)
  }).then(() => loadUsers());
}

function updateUser(id, field, value) {
  fetch(`http://localhost:8080/admin/users/${id}`, {
    method: "PUT",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify({ [field]: value })
  }).catch(err => console.error(err));
}

function deleteUser(id) {
  fetch(`http://localhost:8080/admin/users/${id}`, { method: "DELETE" })
    .then(() => loadUsers());
}

// ------------------ PROPERTIES -------------------
function loadProperties() {
  fetch("http://localhost:8080/admin/properties")
    .then(res => res.json())
    .then(properties => {
      const tbody = document.getElementById("properties-table");
      tbody.innerHTML = "";
      properties.forEach(p => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td class="px-4 py-2">${p.id}</td>
          <td class="px-4 py-2" contenteditable="true" onblur="updateProperty(${p.id}, 'title', this.textContent)">${p.title}</td>
          <td class="px-4 py-2" contenteditable="true" onblur="updateProperty(${p.id}, 'ownerName', this.textContent)">${p.ownerName}</td>
          <td class="px-4 py-2" contenteditable="true" onblur="updateProperty(${p.id}, 'status', this.textContent)">${p.status}</td>
          <td class="px-4 py-2">
            <button onclick="deleteProperty(${p.id})" class="bg-red-500 text-white px-2 py-1 rounded hover:bg-red-600">Delete</button>
          </td>
        `;
        tbody.appendChild(tr);
      });
    });
}

function addPropertyRow() {
  const tbody = document.getElementById("properties-table");
  const tr = document.createElement("tr");
  tr.innerHTML = `
    <td class="px-4 py-2">New</td>
    <td class="px-4 py-2" contenteditable="true"></td>
    <td class="px-4 py-2" contenteditable="true"></td>
    <td class="px-4 py-2" contenteditable="true"></td>
    <td class="px-4 py-2">
      <button onclick="saveNewProperty(this)" class="bg-green-500 text-white px-2 py-1 rounded hover:bg-green-600">Save</button>
    </td>
  `;
  tbody.prepend(tr);
}

function saveNewProperty(btn) {
  const row = btn.parentElement.parentElement;
  const data = {
    title: row.children[1].textContent,
    ownerName: row.children[2].textContent,
    status: row.children[3].textContent
  };
  fetch("http://localhost:8080/admin/properties", {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify(data)
  }).then(() => loadProperties());
}

function updateProperty(id, field, value) {
  fetch(`http://localhost:8080/admin/properties/${id}`, {
    method: "PUT",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify({ [field]: value })
  }).catch(err => console.error(err));
}

function deleteProperty(id) {
  fetch(`http://localhost:8080/admin/properties/${id}`, { method: "DELETE" })
    .then(() => loadProperties());
}
