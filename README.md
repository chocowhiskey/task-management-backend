# Task Management Backend

Ein RESTful Backend-Service zur Verwaltung von Tasks, entwickelt mit Spring Boot.

## 🎯 Projektziel

Dieses Projekt demonstriert:
- Clean Code und Java OOP-Prinzipien
- Spring Boot Ökosystem (Web, Data JPA)
- RESTful API Design mit DTOs
- Layered Architecture (Controller → Service → Repository)
- Exception Handling
- Wartbarer und testbarer Code

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.2.x**
- **Spring Data JPA** - Datenbankzugriff
- **H2 Database** - In-Memory Datenbank
- **Maven** - Build-Management
- **JUnit 5** - Testing (coming soon)

## 🏗️ Architektur
```
com.example.taskmanager
 ├─ controller/       # REST Endpoints
 ├─ service/          # Business Logic
 ├─ repository/       # Datenbankzugriff
 ├─ domain/           # Entities (Task, TaskStatus)
 ├─ dto/              # Data Transfer Objects
 ├─ exception/        # Custom Exceptions
 └─ config/           # Konfiguration
```

**Design Patterns:**
- **DTOs** für API-Layer (Trennung von Domain Model)
- **Repository Pattern** mit Spring Data JPA
- **Dependency Injection** via Constructor
- **Exception Handling** mit Custom Exceptions

## 📡 API Endpoints

### Basis-URL: `http://localhost:8080/api/tasks`

| Method | Endpoint | Beschreibung | Request Body |
|--------|----------|--------------|--------------|
| `POST` | `/api/tasks` | Neue Task erstellen | `{"title": "string", "description": "string"}` |
| `GET` | `/api/tasks` | Alle Tasks abrufen | - |
| `GET` | `/api/tasks?status=OPEN` | Tasks nach Status filtern | - |
| `GET` | `/api/tasks/{id}` | Einzelne Task abrufen | - |
| `PUT` | `/api/tasks/{id}` | Task aktualisieren | `{"title": "string", "description": "string", "status": "OPEN"}` |
| `DELETE` | `/api/tasks/{id}` | Task löschen | - |

### Task Status
- `OPEN` - Neu erstellt
- `IN_PROGRESS` - In Bearbeitung
- `DONE` - Abgeschlossen

### Beispiel-Requests

**Task erstellen:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Spring Boot lernen","description":"REST API implementieren"}'
```

**Alle Tasks abrufen:**
```bash
curl http://localhost:8080/api/tasks
```

**Task updaten:**
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"status":"IN_PROGRESS"}'
```

**PowerShell (Windows):**
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/tasks" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"title":"Erste Task","description":"Beschreibung"}'
```

## 🚀 Setup & Installation

### Voraussetzungen
- Java 17 oder höher
- Maven (oder nutze den mitgelieferten Maven Wrapper)

### Projekt starten

1. **Repository klonen:**
```bash
   git clone https://github.com/DEIN-USERNAME/task-management-backend.git
   cd task-management-backend
```

2. **Anwendung starten:**
```bash
   ./mvnw spring-boot:run
```
   
   Windows:
```bash
   mvnw.cmd spring-boot:run
```

3. **API testen:**
```
   http://localhost:8080/api/tasks
```

### H2 Console (Optional)

Die H2-Datenbank-Console ist verfügbar unter:
```
http://localhost:8080/h2-console
```

**Login-Daten:**
- JDBC URL: `jdbc:h2:mem:taskdb`
- Username: `sa`
- Password: _(leer lassen)_

## 🎨 Design Decisions

### Warum DTOs statt direkt Entities zurückgeben?
```java
// ❌ Schlecht: Entity direkt exponieren
@GetMapping("/{id}")
public Task getTask(@PathVariable Long id) { ... }

// ✅ Gut: DTO als API-Kontrakt
@GetMapping("/{id}")
public TaskResponse getTask(@PathVariable Long id) { ... }
```

**Vorteile:**
- API-Struktur unabhängig von Datenbank-Struktur
- Kontrolle über exponierte Felder
- Einfachere API-Versionierung
- Keine versehentliche Datenlecks (z.B. Passwörter)

### Warum Constructor Injection?
```java
// ✅ Constructor Injection (Best Practice)
public TaskService(TaskRepository repository) {
    this.repository = repository;
}
```

**Vorteile:**
- Immutable Dependencies
- Testbarkeit (Mocking einfacher)
- Explizite Abhängigkeiten
- Moderne Spring-Empfehlung

### Warum keine Setter in der Entity?
```java
// ❌ Schlecht
task.setStatus(TaskStatus.DONE);

// ✅ Gut: Business Methods zeigen Intention
task.changeStatus(TaskStatus.DONE);
```

**Vorteile:**
- Intention klar erkennbar
- Zusätzliche Logik möglich (z.B. updatedAt setzen)
- Domain-Driven Design

## 📈 Roadmap / Mögliche Erweiterungen

- [ ] Validierung mit `@Valid` und Bean Validation
- [ ] Unit & Integration Tests
- [ ] PostgreSQL statt H2
- [ ] Spring Security (Authentication)
- [ ] Pagination für GET /tasks
- [ ] Docker Container
- [ ] Swagger/OpenAPI Dokumentation
- [ ] CI/CD Pipeline (GitHub Actions)

## 📝 Projektstruktur (Übersicht)
```
taskmanager/
├── src/
│   ├── main/
│   │   ├── java/.../taskmanager/
│   │   │   ├── controller/
│   │   │   │   └── TaskController.java
│   │   │   ├── service/
│   │   │   │   └── TaskService.java
│   │   │   ├── repository/
│   │   │   │   └── TaskRepository.java
│   │   │   ├── domain/
│   │   │   │   ├── Task.java
│   │   │   │   └── TaskStatus.java
│   │   │   ├── dto/
│   │   │   │   ├── CreateTaskRequest.java
│   │   │   │   ├── UpdateTaskRequest.java
│   │   │   │   └── TaskResponse.java
│   │   │   └── exception/
│   │   │       └── TaskNotFoundException.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 👨‍💻 Autor

Entwickelt als Portfolio-Projekt zum Erlernen von Spring Boot.

---

**Status:** 🚧 In aktiver Entwicklung