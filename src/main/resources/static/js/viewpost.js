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

    document.getElementById("postTitle").textContent = post.title;
    document.getElementById("postContent").textContent = post.content;
    document.getElementById("postImage").src = post.image;
    document.getElementById("postCategory").textContent = post.category;
  } catch (error) {
    console.error("Error al cargar el post:", error);
    alert("Hubo un problema al cargar la publicación.");
  }
});
