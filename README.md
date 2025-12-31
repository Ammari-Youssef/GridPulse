# GridPulse

A full-stack energy grid monitoring and management application built with Angular and Spring Boot. This project demonstrates modern enterprise development practices including containerization, CI/CD pipelines, and comprehensive testing strategies.

## 🚀 Tech Stack

### Frontend
- **Angular 19.2.13** - Modern web framework (Legacy-Friendly Architecture)
- **Angular Material** - UI component library
- **Tailwind CSS** - Utility-first CSS framework
- **TypeScript** - Type-safe JavaScript

### Backend
- **Spring Boot** - Java backend framework
- **PostgreSQL** - Relational database
- **Liquibase** - Database migration management
- **Maven** - Build automation and dependency management

### DevOps & Testing
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **GitHub Actions** - CI/CD pipeline
- **JUnit & Mockito** - Unit testing
- **Testcontainers** - Integration testing with containerized dependencies
- **JaCoCo** - Code coverage reporting
- **SonarQube** - Code quality and security analysis

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- **Node.js** 18+ and npm
- **Java** 17+
- **Maven** 3.8+
- **Docker** and Docker Compose
- **Git**

## 🛠️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Ammari-Youssef/GridPulse.git
cd GridPulse
```

### 2. Running with Docker (Recommended)

The easiest way to run the entire application stack:

```bash
docker-compose up --build
```

This will start:
- Frontend (Angular) on `http://localhost:4200`
- Backend (Spring Boot) on `http://localhost:8080`
- PostgreSQL database on `localhost:5432`

### 3. Running Locally (Development)

#### Backend Setup

```bash
cd backend

# Install dependencies and run tests
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend API will be available at `http://localhost:8080`

#### Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Start development server
ng serve
```

The frontend will be available at `http://localhost:4200`

## 🏗️ Project Structure

```
GridPulse/
├── backend/              # Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   └── test/
│   └── pom.xml
├── frontend/             # Angular application
│   ├── src/
│   │   ├── app/
│   │   └── assets/
│   └── package.json
├── docs/                 # Documentation
│   └── message-payloads/ # API message schemas
├── .github/
│   └── workflows/        # GitHub Actions CI/CD
├── docker-compose.yml
├── Makefile              # Build automation scripts
└── README.md
```

## 🧪 Testing

### Backend Tests

```bash
cd backend

# Run all tests
mvn test

# Run tests with coverage report
mvn clean verify

# View JaCoCo coverage report
open target/site/jacoco/index.html
```

### Frontend Tests

```bash
cd frontend

# Run unit tests
ng test

# Run tests with coverage
ng test --code-coverage
```

## 🔨 Building for Production

### Build Everything with Docker

```bash
docker-compose -f docker-compose.prod.yml up --build
```

### Build Separately

**Backend:**
```bash
cd backend
mvn clean package -DskipTests
```

**Frontend:**
```bash
cd frontend
ng build --configuration production
```

## 📦 Docker Commands

```bash
# Build and start all services
docker-compose up --build

# Stop all services
docker-compose down

# View logs
docker-compose logs -f

# Rebuild specific service
docker-compose up --build frontend
```

## 🔍 Code Quality

This project uses SonarQube for continuous code quality inspection. The CI/CD pipeline automatically runs quality checks on every push.

```bash
# Run SonarQube analysis locally (requires SonarQube server)
mvn sonar:sonar
```

## 🚦 CI/CD Pipeline

GitHub Actions automatically:
- Runs all tests (unit and integration)
- Generates code coverage reports
- Performs SonarQube analysis
- Builds Docker images with caching
- Validates code quality

## 🤝 Contributing

This is a portfolio project, but suggestions and feedback are welcome!

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 Development Workflow

1. Create a new branch from `development`
2. Make your changes
3. Ensure tests pass locally
4. Create a PR to merge into `development`
5. After review, merge to `master` for production

## 🐛 Troubleshooting

### Docker Issues

If you encounter port conflicts:
```bash
docker-compose down
docker-compose up
```

### Database Issues

To reset the database:
```bash
docker-compose down -v
docker-compose up --build
```

## 📄 License

This project is part of my portfolio and is available for educational purposes.

## 👤 Author

**Youssef Ammari**

- GitHub: [@Ammari-Youssef](https://github.com/Ammari-Youssef)

---

**Note:** This is a portfolio project demonstrating full-stack development capabilities with modern DevOps practices.
