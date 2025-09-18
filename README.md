# 📝 Todo App

A full-stack to-do list application built with a **Spring Boot backend** and a modern **React frontend**. The backend provides a secure RESTful API with **JWT-based authentication** and per-user task management.

## 📁 Project Structure

```
todo-app/
├── backend/    # Java Spring Boot API (JWT-secured)
├── frontend/   # React client app
```

## 🛠️ Technologies Used

### Backend

- Java 21+
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT for authentication

### Frontend (planned)

- React
- Axios (HTTP client)
- Bootstrap or Tailwind (optional)

## 🚀 Features

- ✅ User registration & login
- ✅ JWT-based authentication & authorization
- ✅ Secure endpoints by user (multi-user support)
- ✅ Create, update, delete, and view to-do tasks
- ✅ Filter tasks by completion status or due date (e.g. today)
- ✅ Validation using `@Valid`, `@NotBlank`, `@FutureOrPresent`
- ✅ Global error handling with structured responses
- ✅ Relational database integration (MySQL)
- ✅ Layered architecture (Controller → Service → Repository)

## 📡 API Endpoints

### 🔐 Authentication

| Method | Endpoint             | Description              |
| ------ | -------------------- | ------------------------ |
| POST   | `/api/auth/register` | Register a new user      |
| POST   | `/api/auth/login`    | Log in and receive a JWT |

### 👤 User

| Method | Endpoint             | Description                    |
| ------ | -------------------- | ------------------------------ |
| GET    | `/api/users/profile` | Get current authenticated user |

### ✅ Tasks (All require `Authorization` header)

| Method | Endpoint          | Description                               |
| ------ | ----------------- | ----------------------------------------- |
| GET    | `/api/tasks`      | Get tasks for user (supports filters)     |
| GET    | `/api/tasks/{id}` | Get a specific task by ID                 |
| POST   | `/api/tasks`      | Create a new task (validated input)       |
| PUT    | `/api/tasks/{id}` | Update an existing task                   |
| DELETE | `/api/tasks/{id}` | Delete a task (only if owned by the user) |

## ▶️ Running the Backend Locally

### Clone the Repository

```bash
git clone https://github.com/your-username/todo-app.git
cd todo-app
```

## ✨ Status

✅ Backend: Completed
🚧 Frontend: In development

---
