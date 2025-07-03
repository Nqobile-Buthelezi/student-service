# Student Service

A Spring Boot microservice for managing student data as part of a personal academic study system. This service provides RESTful APIs for CRUD operations on student records, using an in-memory H2 database for development and testing.

## Features
- Create, read, update, and delete student records
- Input validation and error handling
- H2 in-memory database for easy setup
- RESTful API endpoints

## Technologies Used
- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven

### Setup
1. Clone this repository (replace `<your-github-username>` and `<repo-name>` after creating your GitHub repo):
   ```bash
   git clone https://github.com/<your-github-username>/<repo-name>.git
   cd <repo-name>
   ```
2. Build the project:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The service will be available at `http://localhost:4000`.

### H2 Database Console
- Access the H2 console at: [http://localhost:4000/h2-console](http://localhost:4000/h2-console)
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `admin_viewer`
- Password: `password`

## API Endpoints
- `GET /students` - List all students
- `GET /students/{id}` - Get a student by ID
- `POST /students` - Create a new student
- `PUT /students/{id}` - Update a student
- `DELETE /students/{id}` - Delete a student

## Running Tests
```bash
./mvnw test
```

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a pull request

## License
This project is licensed under the MIT License.

---
*Update the repository URL in this README after creating your GitHub repository.*

