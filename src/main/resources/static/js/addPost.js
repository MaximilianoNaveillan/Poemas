document.addEventListener("DOMContentLoaded", () => {
  const addPostForm = document.getElementById("addPostForm");
  const postIdField = document.getElementById("postId");
  const titleField = document.getElementById("title");
  const contentField = document.getElementById("content");
  const authorField = document.getElementById("author");
  const yearField = document.getElementById("year");
  const submitBtn = document.getElementById("submitBtn");
  const pageTitle = document.getElementById("pageTitle");
  const formHeader = document.getElementById("formHeader");

  // Obtener la clave (key) del localStorage
  const userKey = localStorage.getItem("key");

  if (!userKey) {
    console.error("No se encontró la clave de usuario en el localStorage.");
    return; // Detener la ejecución si no hay clave
  }

  // Función para cargar los datos del post en el formulario
  async function loadPostData(postId) {
    try {
      const response = await fetch(`http://localhost:8080/api/posts/${postId}`);
      if (!response.ok) {
        throw new Error("No se pudo obtener los datos del post.");
      }

      const post = await response.json();
      titleField.value = post.title;
      contentField.value = post.content;
      authorField.value = post.author;
      yearField.value = post.year;
      postIdField.value = post.id;

      submitBtn.textContent = "Actualizar Poema";
      pageTitle.textContent = "Editar Poema";
      formHeader.textContent = "Editar publicación existente";
    } catch (error) {
      console.error("Error al cargar los datos del post:", error);
      alert("Error al cargar los datos del post.");
    }
  }

  // Detectar ?id en la URL y cargar datos
  const urlParams = new URLSearchParams(window.location.search);
  const postIdFromUrl = urlParams.get("id");
  if (postIdFromUrl) {
    loadPostData(postIdFromUrl);
  }

  addPostForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const year = parseInt(yearField.value);
    if (year < 1900 || year > 2100) {
      alert("Por favor ingrese un año válido (entre 1900 y 2100)");
      return;
    }

    const postData = {
      title: titleField.value,
      content: contentField.value,
      author: authorField.value,
      year: year,
      userId: userKey,
    };

    const postId = postIdField.value;

    try {
      let response;

      if (postId) {
        response = await fetch(`http://localhost:8080/api/posts/${postId}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(postData),
        });
      } else {
        response = await fetch("http://localhost:8080/api/posts/create", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(postData),
        });
      }

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Error en la operación");
      }

      alert(`Post ${postId ? "editado" : "agregado"} exitosamente`);
      window.location.href = "/dashboard";
    } catch (error) {
      console.error("Error:", error);
      alert(error.message || "Error al procesar la solicitud");
    }
  });
});
