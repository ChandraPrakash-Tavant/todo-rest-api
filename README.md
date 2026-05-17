# 📝 Todo Task Management REST API

A production-structured RESTful API built with **Java Spring Boot** and **PostgreSQL** for managing users and their tasks. This project follows industry-standard layered architecture with proper separation of concerns, relationship mapping, input validation, and global exception handling.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| Spring Boot 3 | Application framework |
| Spring Data JPA | Data access layer |
| Hibernate | ORM for database mapping |
| PostgreSQL | Relational database |
| Maven | Dependency management and build tool |
| Bean Validation | Input validation (`@Valid`) |

---

## 📁 Project Structure

```
src/main/java/com/todo/app/
│
├── controller/
│   ├── UserController.java       # Handles user-related HTTP requests
│   └── TaskController.java       # Handles task-related HTTP requests
│
├── service/
│   ├── UserService.java          # Business logic for users
│   └── TaskService.java          # Business logic for tasks
│
├── repository/
│   ├── UserRepository.java       # Database access for users
│   └── TaskRepository.java       # Database access for tasks
│
├── entity/
│   ├── Users.java                # User entity mapped to users table
│   └── Tasks.java                # Task entity mapped to tasks table
│
├── dto/
│   ├── UserRequestDTO.java       # Incoming user request body
│   └── TaskRequestDTO.java       # Incoming task request body
│
├── enums/
│   └── TaskStatus.java           # Enum: TODO, IN_PROGRESS, DONE
│
└── exception/
    ├── GlobalExceptionHandler.java    # Centralized exception handling
    ├── ErrorResponse.java             # Structured error response body
    ├── UserNotFoundException.java     # Thrown when user not found
    └── TaskNotFoundException.java     # Thrown when task not found
```

---

## ✨ Features

- ✅ Full CRUD operations for **Users** and **Tasks**
- ✅ Proper **One-to-Many** relationship between Users and Tasks using JPA
- ✅ **DTO pattern** to control and validate incoming request data
- ✅ **Global exception handling** with structured JSON error responses
- ✅ **Input validation** using Bean Validation (`@NotBlank`, `@Email`, `@Size`, `@NotNull`)
- ✅ **Custom exceptions** (`UserNotFoundException`, `TaskNotFoundException`)
- ✅ **Task status management** using Enum (`TODO`, `IN_PROGRESS`, `DONE`)
- ✅ **Filter tasks by status** using query parameters
- ✅ **Auto timestamp** on task creation using `@CreationTimestamp`
- ✅ Layered architecture (Controller → Service → Repository)
- ✅ Constructor-based dependency injection throughout

---

## 🗄️ Database Schema

### `users` table
| Column | Type | Constraints |
|---|---|---|
| id | SERIAL | PRIMARY KEY |
| first_name | VARCHAR | NOT NULL |
| last_name | VARCHAR | NOT NULL |
| email | VARCHAR | NOT NULL |

### `tasks` table
| Column | Type | Constraints |
|---|---|---|
| id | SERIAL | PRIMARY KEY |
| task_name | VARCHAR | NOT NULL |
| description | VARCHAR | |
| status | VARCHAR | NOT NULL (TODO / IN_PROGRESS / DONE) |
| user_id | INTEGER | FOREIGN KEY → users(id) |
| created_at | TIMESTAMP | Auto-set on creation |

---

## 🔗 API Endpoints

### Users

| Method | Endpoint | Description | Request Body | Response |
|---|---|---|---|---|
| `POST` | `/users` | Create a new user | `UserRequestDTO` | `201 Created` |
| `GET` | `/users/{id}` | Get user by ID | None | `200 OK` |

#### Sample Request — Create User
```json
POST /users
Content-Type: application/json

{
    "firstName": "Avijit",
    "lastName": "Nath",
    "email": "avijit.nath@gmail.com"
}
```

#### Sample Response
```json
{
    "id": 1,
    "firstName": "Avijit",
    "lastName": "Nath",
    "email": "avijit.nath@gmail.com"
}
```

---

### Tasks

| Method | Endpoint | Description | Request Body | Response |
|---|---|---|---|---|
| `POST` | `/users/{userId}/tasks` | Create task for a user | `TaskRequestDTO` | `201 Created` |
| `GET` | `/users/{userId}/tasks` | Get all tasks for a user | None | `200 OK` |
| `PUT` | `/tasks/{taskId}` | Update a task | `TaskRequestDTO` | `200 OK` |
| `DELETE` | `/tasks/{taskId}` | Delete a task | None | `200 OK` |
| `GET` | `/tasks?status=TODO` | Filter tasks by status | None | `200 OK` |

#### Sample Request — Create Task
```json
POST /users/1/tasks
Content-Type: application/json

{
    "taskName": "Buy groceries",
    "description": "Milk, eggs, and bread",
    "status": "TODO"
}
```

#### Sample Response
```json
{
    "id": 1,
    "taskName": "Buy groceries",
    "description": "Milk, eggs, and bread",
    "status": "TODO",
    "createdAt": "2026-05-17T10:30:00"
}
```

#### Sample Request — Update Task
```json
PUT /tasks/1
Content-Type: application/json

{
    "taskName": "Buy groceries",
    "description": "Milk, eggs, and bread",
    "status": "IN_PROGRESS"
}
```

#### Filter Tasks by Status
```
GET /tasks?status=TODO
GET /tasks?status=IN_PROGRESS
GET /tasks?status=DONE
```

---

## ❌ Error Responses

All errors return a consistent JSON structure:

```json
{
    "status": 404,
    "errorMessage": "User not found with id: 5",
    "timeStamp": 1234567890123
}
```

| Scenario | Status Code |
|---|---|
| User or Task not found | `404 Not Found` |
| Validation failure | `400 Bad Request` |
| Unexpected server error | `500 Internal Server Error` |

#### Sample Validation Error
```json
{
    "status": 400,
    "errorMessage": "taskName: Task name cannot be empty, email: Please provide a valid email address",
    "timeStamp": 1234567890123
}
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL 14+

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/your-username/todo-rest-api.git
cd todo-rest-api
```

**2. Create the PostgreSQL database**
```sql
CREATE DATABASE todo_db;
CREATE USER your_username WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE todo_db TO your_username;
```

**3. Configure application properties**

Copy the example file:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Fill in your credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/TodoTracker
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

**4. Build and run**
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## 🏗️ Architecture

This project follows a strict **3-layer architecture**:

```
HTTP Request
     ↓
Controller Layer    — receives request, validates input, returns response
     ↓
Service Layer       — handles all business logic
     ↓
Repository Layer    — communicates with PostgreSQL via JPA
     ↓
Database
```

**Key design decisions:**
- Controllers never talk to repositories directly
- Entities are never exposed directly as request bodies — DTOs are used instead
- All exceptions are handled in one place via `@ControllerAdvice`
- Constructor injection used throughout instead of field injection

---

## 🔮 Planned Improvements

- [ ] Spring Security + JWT authentication
- [ ] Unit and integration tests (JUnit 5 + Mockito)
- [ ] Pagination for task listing
- [ ] Database migration with Flyway
- [ ] Docker support

---

## 👤 Author

**Avijit Nath**  
[GitHub](https://github.com/Avijitnath2) · [LinkedIn](https://www.linkedin.com/in/avijitnath1/)
