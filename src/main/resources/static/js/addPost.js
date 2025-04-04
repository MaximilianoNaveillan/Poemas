document.addEventListener("DOMContentLoaded", () => {
  const addPostForm = document.getElementById("addPostForm");
  const postIdField = document.getElementById("postId");
  const titleField = document.getElementById("title");
  const contentField = document.getElementById("content");
  const imageField = document.getElementById("image");
  const categoryField = document.getElementById("category");
  const submitBtn = document.getElementById("submitBtn");
  const pageTitle = document.getElementById("pageTitle");
  const formHeader = document.getElementById("formHeader");

  // ✅ Función para cargar los datos del post en el formulario
  async function loadPostData(postId) {
    try {
      const response = await fetch(`http://localhost:8080/api/posts/${postId}`);
      if (!response.ok) {
        throw new Error("No se pudo obtener los datos del post.");
      }

      const post = await response.json();
      titleField.value = post.title;
      contentField.value = post.content;
      imageField.value = post.image;
      categoryField.value = post.category;
      postIdField.value = post.id;

      submitBtn.textContent = "Actualizar Post";
      pageTitle.textContent = "Editar Publicación";
      formHeader.textContent = "Editar publicación existente";
    } catch (error) {
      console.error("Error al cargar los datos del post:", error);
      alert("Error al cargar los datos del post.");
    }
  }

  // ✅ Detectar ?id en la URL y cargar datos
  const urlParams = new URLSearchParams(window.location.search);
  const postIdFromUrl = urlParams.get("id");
  if (postIdFromUrl) {
    loadPostData(postIdFromUrl);
  }

  addPostForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const title = titleField.value;
    const content = contentField.value;
    const image = imageField.value;
    const category = categoryField.value;

    const postData = {
      title: title,
      content: content,
      image: image,
      category: category,
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
        throw new Error("No se pudo agregar o editar el post.");
      }

      const createdOrUpdatedPost = await response.json();
      alert(
        `Post ${postId ? "editado" : "agregado"} exitosamente: ` +
          createdOrUpdatedPost.title
      );

      window.location.href = "/dashboard"; // Redirige al dashboard después
    } catch (error) {
      console.error("Error al agregar o editar el post:", error);
      alert("Error al agregar o editar el post.");
    }
  });
});
