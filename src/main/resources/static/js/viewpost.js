document.addEventListener("DOMContentLoaded", async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const postId = urlParams.get("id");

  if (!postId) {
    alert("No se proporcionó un ID de post válido.");
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/api/posts/${postId}`);
    if (!response.ok) {
      throw new Error("No se pudo obtener el post.");
    }

    const post = await response.json();

    console.log(post);

    // Mostrar los detalles del post en la página
    document.getElementById("postTitle").textContent = post.title;
    document.getElementById("postUser").textContent =
      localStorage.getItem("username"); // Suponiendo que el nombre de usuario está en el almacenamiento local
    document.getElementById("postAuthor").textContent = post.author;
    document.getElementById("postYear").textContent = post.year;
    document.getElementById("postContent").textContent = post.content;

    const useKey = localStorage.getItem("key");

    const viewMoreLink = document.getElementById("viewMoreBtn");
    viewMoreLink.href = `http://localhost:8080/allpoems/${post.author}/user/${useKey}`;

    // Agregar evento de click para eliminar el post
    const deleteButton = document.querySelector(".delete-post-btn");
    deleteButton.addEventListener("click", async () => {
      const confirmation = confirm(
        "¿Estás seguro de que quieres eliminar esta publicación?"
      );
      if (confirmation) {
        try {
          const deleteResponse = await fetch(
            `http://localhost:8080/api/posts/${postId}`,
            {
              method: "DELETE",
            }
          );

          if (deleteResponse.ok) {
            alert("Publicación eliminada exitosamente.");
            window.location.href = "/dashboard"; // Redirigir a la página de publicaciones
          } else {
            alert("Hubo un error al eliminar la publicación.");
          }
        } catch (error) {
          console.error("Error al eliminar el post:", error);
          alert("Error al intentar eliminar la publicación.");
        }
      }
    });
  } catch (error) {
    console.error("Error al cargar el post:", error);
    alert("Hubo un problema al cargar la publicación.");
  }
});
