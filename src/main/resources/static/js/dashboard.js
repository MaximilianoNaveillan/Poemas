document.addEventListener("DOMContentLoaded", async () => {
  const postsContainer = document.getElementById("postsContainer");

  // Obtener la clave (key) del localStorage
  const userKey = localStorage.getItem("key");
  const username = localStorage.getItem("username");

  if (!userKey && !username) {
    console.error("No se encontró la clave de usuario en el localStorage.");
    return; // Detener la ejecución si no hay clave
  }

  document.getElementById(
    "welcome"
  ).textContent = `Bienvenido de vuelta ${username}`;

  try {
    // Usar la clave en la URL de la API
    const response = await fetch(
      `http://localhost:8080/api/posts/user/${userKey}`
    );

    if (!response.ok) {
      throw new Error("No se pudieron cargar los posts");
    }

    const posts = await response.json();
    displayPosts(posts);
  } catch (error) {
    console.error("Error al cargar los posts:", error);
  }

  function displayPosts(posts) {
    if (posts.length === 0) {
      postsContainer.innerHTML = "<p>No hay posts disponibles.</p>";
      return;
    }

    postsContainer.innerHTML = "";

    posts.forEach((post) => {
      const postElement = document.createElement("div");
      postElement.classList.add("post");

      postElement.innerHTML = `
      <div style="cursor:pointer" class="post-container" data-id="${post.id}">
        <h3 style="width: 100%; text-align:left;">${post.title}</h3>

        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 20px;">
          <!-- Columna 1: "autor" -->
          <div style="flex: 1; text-align: left;">
            <small>Autor:</small>
          </div>

          <!-- Columna 2: Nombre del autor -->
          <div style="flex: 1; text-align: center;">
            <strong>${post.author}</strong>
          </div>

          <!-- Columna 3: Icono de lápiz para editar -->
          <div style="flex: 1; text-align: right;">
            <a href="/addpost?id=${post.id}" class="view-post-link">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 20h9"></path>
                <path d="M17.7 4.3a2.8 2.8 0 0 1 0 3.9l-10 10a2 2 0 0 1-1.4.6H5a2 2 0 0 1-2-2v-3.3a2 2 0 0 1 .6-1.4l10-10a2.8 2.8 0 0 1 3.9 0l1.4 1.4z"></path>
              </svg>
            </a>
          </div>
        </div>
      </div>
    `;

      // Agregar el evento de click a la etiqueta <a> para redireccionar
      const viewPostLink = postElement.querySelector(".view-post-link");
      viewPostLink.addEventListener("click", (event) => {
        event.stopPropagation(); // Detener la propagación del evento
        // Realizar la redirección
        window.location.href = `/addpost?id=${post.id}`;
      });

      // Agregar el evento de click al div para redireccionar
      const postContainer = postElement.querySelector(".post-container");
      postContainer.addEventListener("click", (event) => {
        event.stopPropagation(); // Detener la propagación del evento
        const postId = postContainer.dataset.id;
        // Realizar la redirección
        window.location.href = `/viewpost?id=${postId}`;
      });

      postsContainer.appendChild(postElement);
    });

    // Agregar evento para eliminar publicación
    const deleteButtons = document.querySelectorAll(".delete-post-btn");
    deleteButtons.forEach((button) => {
      button.addEventListener("click", async (e) => {
        const postId = e.target.dataset.id;

        try {
          const response = await fetch(
            `http://localhost:8080/api/posts/${postId}`,
            {
              method: "DELETE",
            }
          );

          if (response.ok) {
            e.target.closest(".post").remove(); // Elimina el post del DOM
            alert("Publicación eliminada exitosamente.");
          } else {
            alert("Hubo un error al eliminar la publicación.");
          }
        } catch (error) {
          console.error("Error al eliminar el post:", error);
          alert("Error de conexión al intentar eliminar la publicación.");
        }
      });
    });
  }
});
