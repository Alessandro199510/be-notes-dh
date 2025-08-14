# Notes API Documentation 

# 📄 Project Summary

This project is the backend for an application to create notes with tags by user, developed using Spring Boot 3.5.5.
It exposes a RESTful API to manage.
- Notes: Create, Read, Update, Delete (CRUD) operations for notes.
- Tags: Create, Read, Update, Delete (CRUD) operations for tags.
- Users: Register and Login.

## 🛠️ Technologies Used
- Java 21+
- Spring Boot 3.5.5
- Maven
- Spring Data JPA
- Hibernate
- MySQL Database:
- OpenAPI 3.1 (Swagger)

## 🌐 API Documentation
The API documentation is available at after running the application. You can access it via the following links:
- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [OpenAPI JSON](http://localhost:8080/v3/api-docs)
- [OpenAPI YAML](http://localhost:8080/v3/api-docs.yaml)

## 🔧 Configuration
### Prerequisites
Make sure you have the following components installed:
- Java Development Kit (JDK) version 21 or higher.
- Maven.
- A configured database instance in MySQL.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Alessandro199510/be-notes-dh.git
   ```
2. Navigate to the project directory:
   ```bash
   cd notes-api
   ```
3. Configure the database connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
    ```
4. Build the project using Maven:
    ```bash
    mvn clean install
    ```
6. Run the application:
   ```bash
    mvn spring-boot:run
    ```
7. The application will start on `http://localhost:8080`.