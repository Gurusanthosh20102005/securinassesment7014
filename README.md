# Recipe API Assessment

A RESTful API built with **Spring Boot** and **MySQL** for managing recipes. This project was developed as part of a technical assessment, covering CRUD operations, input validation, and data initialization from a JSON dataset.

---

## Tech Stack

| Layer       | Technology                        |
|-------------|-----------------------------------|
| Language    | Java 17                           |
| Framework   | Spring Boot 3.2.4                 |
| Database    | MySQL                             |
| ORM         | Spring Data JPA / Hibernate       |
| Build Tool  | Maven                             |
| Utilities   | Lombok                            |

---

## Project Structure

```
src/
└── main/
    ├── java/com/assessment/recipe/
    │   ├── RecipeApiApplication.java       # Application entry point
    │   ├── config/
    │   │   └── CorsConfig.java             # CORS configuration
    │   ├── controller/
    │   │   └── RecipeController.java       # REST API endpoints
    │   ├── initializer/
    │   │   └── DataInitializer.java        # Seeds DB from JSON on startup
    │   ├── model/
    │   │   └── Recipe.java                 # JPA entity
    │   └── repository/
    │       └── RecipeRepository.java       # Data access layer
    └── resources/
        ├── application.properties          # App configuration
        └── schema.sql                      # DB schema definition
```

---

## API Endpoints

### Base URL: `http://localhost:8080`

| Method | Endpoint          | Description                             |
|--------|-------------------|-----------------------------------------|
| `GET`  | `/recipes`        | Retrieve all recipes                    |
| `POST` | `/recipes`        | Add a new recipe                        |
| `GET`  | `/recipes/top`    | Get top N recipes (default: 5)          |
| `GET`  | `/recipes/count`  | Get total count of recipes in the DB    |

---

##  Data Model — `Recipe`

| Field         | Type              | Required | Notes                              |
|---------------|-------------------|----------|------------------------------------|
| `id`          | Integer           | Auto     | Auto-incremented primary key       |
| `title`       | String            |  Yes     |                                    |
| `cuisine`     | String            |  No      |                                    |
| `rating`      | Float             |  No      |                                    |
| `prep_time`   | Integer (minutes) |  Yes     |                                    |
| `cook_time`   | Integer (minutes) |  Yes     |                                    |
| `total_time`  | Integer (minutes) | Auto     | Calculated as `prep_time + cook_time` |
| `description` | String (TEXT)     |  No      |                                    |
| `nutrients`   | JSON              |  No      | Stored as a JSON column            |
| `serves`      | String            |  No      |                                    |

---

## Sample Requests

### POST `/recipes` — Create a Recipe

**Request Body:**
```json
{
  "title": "Spaghetti Carbonara",
  "cuisine": "Italian",
  "rating": 4.8,
  "prep_time": 10,
  "cook_time": 20,
  "description": "A classic Italian pasta dish.",
  "serves": "2"
}
```

**Success Response (`201 Created`):**
```json
{
  "id": 1,
  "title": "Spaghetti Carbonara",
  "cuisine": "Italian",
  "rating": 4.8,
  "prep_time": 10,
  "cook_time": 20,
  "total_time": 30,
  "description": "A classic Italian pasta dish.",
  "nutrients": null,
  "serves": "2"
}
```

**Validation Error Response (`400 Bad Request`):**
```json
{
  "error": "title, prep_time must be present in the request body."
}
```

---

### GET `/recipes/top?limit=3` — Top Recipes

**Response (`200 OK`):**
```json
{
  "data": [...]
}
```

---

### GET `/recipes/count` — Recipe Count

**Response (`200 OK`):**
```json
{
  "count": 42
}
```

---

## Setup & Installation

### Prerequisites

- Java 17+
- Maven 3.6+
- MySQL 8+

### 1. Clone the Repository

```bash
git clone https://github.com/Gurusanthosh20102005/securinassesment7014.git
cd securinassesment7014
```

### 2. Configure the Database

Create a MySQL database (or let the app auto-create it):

```sql
CREATE DATABASE IF NOT EXISTS securin_recipe_db;
```

Update `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/securin_recipe_db?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The application starts at **`http://localhost:8080`**.

---

##  Database Schema

```sql
CREATE TABLE IF NOT EXISTS recipes (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    cuisine     VARCHAR(255),
    rating      FLOAT,
    prep_time   INT NOT NULL,
    cook_time   INT NOT NULL,
    total_time  INT NOT NULL,
    description TEXT,
    nutrients   JSON,
    serves      VARCHAR(255)
);
```

---

## Validation Rules

The `POST /recipes` endpoint validates the request body and returns a `400 Bad Request` error listing all missing required fields:

- `title` — must not be null
- `cuisine` — must not be null
- `prep_time` — must not be null
- `cook_time` — must not be null

---

##  License

This project is for assessment purposes only.
