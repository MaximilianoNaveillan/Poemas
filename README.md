# 📚 Proyecto: MiRed - Aplicación de Publicaciones

**MiRed** es una aplicación web CRUD desarrollada con Spring Boot para el backend y HTML/CSS/JavaScript puro para el frontend. Permite crear, leer, actualizar y eliminar publicaciones. Utiliza PostgreSQL como base de datos principal.

---

## 🛠️ Tecnologías Utilizadas

### Backend:

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Validación con `javax.validation`

### Frontend:

- HTML5
- CSS3
- JavaScript (ES6)

---

## 📂 Estructura del Proyecto

```
├── src/
│   ├── main/
│   │   ├── java/com/mired/
│   │   │   ├── controllers/
│   │   │   ├── models/
│   │   │   ├── services/
│   │   │   ├── repositories/
│   │   │   └── MiRedApplication.java
│   │   ├── resources/
│   │   │   ├── templates/       <- Archivos HTML Thymeleaf
│   │   │   ├── static/
│   │   │   │   ├── css/
│   │   │   │   └── js/
│   │   │   └── application.properties
├── README.md
```

## 🧪 Funcionalidades

✅ Crear una publicación  
✅ Listar todas las publicaciones  
✅ Ver detalles de una publicación  
✅ Editar una publicación  
✅ Eliminar una publicación

---

## ⚙️ Configuración del Proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/MaximilianoNaveillan/mired.git
cd mired
```

### 2. Configurar la base de datos PostgreSQL

Asegúrate de tener PostgreSQL instalado y crear una base de datos:

```
CREATE DATABASE mired_db;
```

### 3. Editar application.properties

Abre src/main/resources/application.properties y configura tu conexión:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/mired_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

### 4. Ejecutar el proyecto

Desde tu IDE o consola:

```
./mvnw spring-boot:run
```

###🚀 Acceder a la aplicación
Una vez ejecutado, puedes acceder a:

```
http://localhost:8080
```
