document.getElementById("userForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const user = {
    name: document.getElementById("name").value,
    email: document.getElementById("email").value,
    role: document.getElementById("role").value,
    password: document.getElementById("password").value
  };

  await fetch("http://localhost:8080/api/users", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user)
  });

  alert("User added successfully!");
});
