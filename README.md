# Task Manager

Task Manager es una aplicación web para la gestión de tareas que integra **Backend con Spring Boot** y **Frontend con Angular**. La aplicación permite registrar usuarios, autenticarse con **JWT**, y gestionar tareas asociadas a cada usuario.

---

## 🔹 Tecnologías utilizadas

**Backend (Spring Boot):**

- Java 21
- Spring Boot 3.5.11
- Spring Data JPA / Hibernate
- Spring Security con JWT
- Lombok
- PostgreSQL / H2 (para desarrollo)
- Gradle

**Frontend (Angular):**

- Angular 21
- TypeScript, HTML, CSS
- Tailwind CSS

---

## 🔹 Funcionalidades

- Registro y login de usuarios
- Autenticación y autorización con JWT
- CRUD de tareas (crear, listar, actualizar, eliminar)
- Asociación de tareas con usuarios
- Frontend interactivo en Angular
- Documentación de API disponible en Swagger

---

## 🔹 Instalación y ejecución

### Backend (Spring Boot)

1. Clonar el repositorio:

```bash
git clone https://github.com/omarloz10/task_manager.git
cd task_manager/task-manager
```

2. Modificar el archivo .env con tus variables de entorno
3. Ejecutar el comando

```bash
./gradlew bootRun
```


### Frontend (Angular 21)

1. Abrir la carpeta del frontend y ejecutar:

```bash
cd task_manager/frontend
ng serve
```
