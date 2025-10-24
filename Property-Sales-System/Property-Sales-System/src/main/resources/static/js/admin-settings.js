document.getElementById("settings-form").addEventListener("submit", e => {
  e.preventDefault();
  const data = {
    siteName: document.getElementById("site-name").value,
    adminEmail: document.getElementById("admin-email").value
  };
  fetch("http://localhost:8080/admin/settings", {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify(data)
  }).then(() => alert("Settings saved"));
});
