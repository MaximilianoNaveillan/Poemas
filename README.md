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
git https://github.com/MaximilianoNaveillan/Poemas.git
cd mired
```

### 2. Configurar la base de datos PostgreSQL

Asegúrate de tener PostgreSQL instalado y crear una base de datos:

```sql
-- Creación de la base de datos (si es necesario)
CREATE DATABASE mired_db;

-- Conexión a la base de datos (si ya estás conectado, ignora esta parte)
\c mired_db;

-- Tabla para almacenar la información de los usuarios
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Aquí puedes usar hash de contraseñas
    email VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla para almacenar las publicaciones (poemas)
CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para mejorar el rendimiento de las búsquedas
CREATE INDEX idx_author ON posts(author);
CREATE INDEX idx_user_id ON posts(user_id);

-- Insertar un usuario de ejemplo (opcional)
INSERT INTO users (username, password, email)
VALUES ('admin', 'hashed_password_example', 'admin@example.com');

-- Insertar un poema de ejemplo (opcional)
INSERT INTO posts (title, content, author, year, user_id)
VALUES ('Poema de prueba', 'Este es un contenido de prueba para el poema.', 'Autor Ejemplo', 2025, 1);

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

# Rutas del Proyecto _Poemas App_

Este documento describe las rutas disponibles tanto en el **frontend** como en el **backend** de la aplicación _Poemas App_.

---

## 🌐 Frontend (Thymeleaf)

### Páginas Disponibles

| Página                        | Ruta                               | Descripción                                                   |
| ----------------------------- | ---------------------------------- | ------------------------------------------------------------- |
| **Inicio**                    | `/`                                | Página de bienvenida o redirección.                           |
| **Login**                     | `/login`                           | Formulario de inicio de sesión.                               |
| **Registro**                  | `/register`                        | Formulario de registro de nuevos usuarios.                    |
| **Dashboard**                 | `/dashboard`                       | Listado de publicaciones del usuario autenticado.             |
| **Agregar Publicación**       | `/addpost`                         | Formulario para crear una nueva publicación.                  |
| **Ver Publicación**           | `/viewpost?id={postId}`            | Vista detallada de una publicación.                           |
| **Lista de Poemas por Autor** | `/allpoems/{author}/user/{userId}` | Listado de todos los poemas de un autor específico y usuario. |

---

## 🔌 Backend API REST (Spring Boot)

### Autenticación

| Método | Ruta                 | Descripción                                                      |
| ------ | -------------------- | ---------------------------------------------------------------- |
| `POST` | `/api/auth/register` | Registro de nuevos usuarios.                                     |
| `POST` | `/api/auth/login`    | Inicio de sesión de usuario. Retorna un token y datos de sesión. |

### Publicaciones (Poemas)

| Método   | Ruta                                          | Descripción                                                             |
| -------- | --------------------------------------------- | ----------------------------------------------------------------------- |
| `GET`    | `/api/posts`                                  | Obtener todas las publicaciones.                                        |
| `GET`    | `/api/posts/{id}`                             | Obtener una publicación por su ID.                                      |
| `POST`   | `/api/posts`                                  | Crear una nueva publicación.                                            |
| `DELETE` | `/api/posts/{id}`                             | Eliminar una publicación por su ID.                                     |
| `GET`    | `/api/posts/by-author/{author}/user/{userId}` | Obtener todas las publicaciones de un autor para un usuario específico. |

---

## 🗂️ Notas

- **Rutas protegidas**: Asegúrate de que el usuario esté autenticado para acceder a las rutas que requieren sesión.
- **`localStorage`**: El nombre de usuario actual se guarda en `localStorage` bajo la clave `"username"`.
- **Eliminación de publicaciones**: Requiere confirmación del usuario antes de ejecutar la eliminación.
