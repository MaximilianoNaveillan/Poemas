document.addEventListener("DOMContentLoaded", () => {
  // Verifica si ya existe un 'username' en el localStorage
  const username = localStorage.getItem("username");

  if (username) {
    // Si el 'username' está en el localStorage, redirige al dashboard
    window.location.href = "/dashboard";
  }

  const form = document.getElementById("registerForm"); // Aquí estamos obteniendo el formulario correctamente
  const alertBox = document.createElement("div");
  alertBox.classList.add("custom-alert");
  document.body.appendChild(alertBox);

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    // Recuperar los valores del formulario
    const firstname = form.firstname.value; // Asegúrate de que el campo 'firstname' esté presente en el HTML
    const lastname = form.lastname.value; // Asegúrate de que el campo 'lastname' esté presente en el HTML
    const email = form.email.value;
    const password = form.password.value;

    if (!firstname || !lastname || !email || !password) {
      showAlert("Todos los campos son obligatorios ❌");
      return; // Salir de la función si algún campo está vacío
    }

    try {
      // Realizar la solicitud fetch al backend
      const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ firstname, lastname, email, password }),
      });

      // Parsear la respuesta del backend
      const data = await response.json();

      if (!response.ok) {
        // Si la respuesta no es exitosa, mostrar los errores del backend
        console.error("Error en el registro:", data);
        const errorMessage =
          data.password ||
          data.firstname ||
          data.lastname ||
          data.email ||
          "Ocurrió un error ❌";
        showAlert(errorMessage);
      } else {
        // Si la respuesta es exitosa, mostrar el mensaje de éxito
        localStorage.setItem("username", firstname);
        showAlert("Registro exitoso ✔️");
        setTimeout(() => {
          window.location.href = "/dashboard"; // Redirigir después de 2 segundos
        }, 2000);
      }
    } catch (error) {
      // Si ocurre un error de conexión, mostrar el mensaje correspondiente
      console.error("Error de conexión:", error);
      showAlert("No se pudo conectar con el servidor 😓");
    }
  });

  function showAlert(message) {
    alertBox.textContent = message;
    alertBox.classList.add("visible");

    setTimeout(() => {
      alertBox.classList.remove("visible");
    }, 4000); // El mensaje se oculta después de 4 segundos
  }
});
