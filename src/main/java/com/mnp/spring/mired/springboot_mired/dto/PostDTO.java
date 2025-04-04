package com.mnp.spring.mired.springboot_mired.dto;

import jakarta.validation.constraints.NotBlank;

public class PostDTO {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El contenido es obligatorio")
    private String content;

    @NotBlank(message = "La imagen es obligatoria")
    private String image;  // Nuevo campo para la imagen

    @NotBlank(message = "La categoría es obligatoria")
    private String category;  // Nuevo campo para la categoría

    // Getters y Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
