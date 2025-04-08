document.addEventListener("DOMContentLoaded", () => {
  // Elimina el 'username' del localStorage al cargar la página
  localStorage.removeItem("username");
  localStorage.removeItem("key");

  const form = document.querySelector("form"); // Asegúrate de seleccionar el formulario correctamente
  const alertBox = document.createElement("div");
  alertBox.classList.add("custom-alert");
  document.body.appendChild(alertBox);

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = form.email.value;
    const password = form.password.value;

    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();

      if (!response.ok) {
        console.error("Error en el login:", data);
        showAlert(data.message || "Ocurrió un error ❌");
      } else {
        // Si la respuesta es exitosa
        // Guarda el nombre del usuario y otros datos en LocalStorage
        localStorage.setItem("username", data.user.firstname);
        localStorage.setItem("key", data.user.id);
        showAlert("Login exitoso ✔️");

        setTimeout(() => {
          window.location.href = "/dashboard";
        }, 2000);
      }
    } catch (error) {
      console.error("Error de conexión:", error);
      showAlert("No se pudo conectar con el servidor 😓");
    }
  });

  function showAlert(message) {
    alertBox.textContent = message;
    alertBox.classList.add("visible");

    setTimeout(() => {
      alertBox.classList.remove("visible");
    }, 4000);
  }
});
