document.addEventListener("DOMContentLoaded", async () => {
  const postsContainer = document.getElementById("postsContainer");

  try {
    const response = await fetch("http://localhost:8080/api/posts/");
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
        <h3>${post.title}</h3>
        
        <p>${post.content}</p>
        <small>Categoría: ${post.category}</small>
        
        <a href="/viewpost?id=${post.id}" class="edit-post-link">ver publicación</a>
        <a href="/addpost?id=${post.id}" class="edit-post-link">Editar publicación</a>
        <button class="delete-post-btn" data-id="${post.id}">Eliminar publicación</button><br/>
      `;

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
