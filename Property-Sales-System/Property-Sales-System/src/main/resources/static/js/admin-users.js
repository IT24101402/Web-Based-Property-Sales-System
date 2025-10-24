const userForm = document.getElementById("user-form");
const usersTable = document.getElementById("users-table");

function loadUsers() {
  fetch("http://localhost:8080/users")
    .then(res => res.json())
    .then(users => {
      usersTable.innerHTML = "";
      users.forEach(u => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td class="px-4 py-2">${u.id}</td>
          <td class="px-4 py-2">${u.name}</td>
          <td class="px-4 py-2">${u.email}</td>
          <td class="px-4 py-2">${u.role}</td>
          <td class="px-4 py-2">
            <button onclick="editUser(${u.id})" class="bg-yellow-400 px-2 py-1 rounded">Edit</button>
            <button onclick="deleteUser(${u.id})" class="bg-red-500 px-2 py-1 rounded">Delete</button>
          </td>
        `;
        usersTable.appendChild(row);
      });
    });
}

// Add or Update User
userForm.addEventListener("submit", e => {
  e.preventDefault();
  const id = document.getElementById("user-id").value;
  const user = {
    name: document.getElementById("user-name").value,
    email: document.getElementById("user-email").value,
    role: document.getElementById("user-role").value
  };

  const url = id ? `http://localhost:8080/users/${id}` : "http://localhost:8080/users";
  const method = id ? "PUT" : "POST";

  fetch(url, {
    method: method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user)
  })
  .then(() => {
    loadUsers();
    userForm.reset();
    document.getElementById("user-id").value = "";
  });
});

// Delete User
function deleteUser(id) {
  fetch(`http://localhost:8080/users/${id}`, { method: "DELETE" })
    .then(() => loadUsers());
}

// Edit User
function editUser(id) {
  fetch(`http://localhost:8080/users/${id}`)
    .then(res => res.json())
    .then(u => {
      document.getElementById("user-id").value = u.id;
      document.getElementById("user-name").value = u.name;
      document.getElementById("user-email").value = u.email;
      document.getElementById("user-role").value = u.role;
    });
}

// Load users on page load
loadUsers();
