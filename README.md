# IoT Energy Dashboard

A full-stack IoT dashboard application for intelligent monitoring and management of energy networks.

## 🚀 Tech Stack
- **Backend:** Java 17, Spring Boot, GraphQL, PostgreSQL, Maven, Docker
- **Frontend:** Angular v19, TypeScript, Apollo Client, NgRx, SCSS
- **CI/CD:** GitHub Actions (build + test), Docker Compose for local setup

## 🧩 Features
- Real-time monitoring of device data through GraphQL API
- Energy cost optimization logic
- Device and cloud APIs for IoT integration
- JWT authentication and authorization
- Modular microservice-ready backend architecture

## ⚙️ Getting Started

### Backend
```bash

# Clone repository
git clone https://github.com/Ammari-Youssef/GridPulse.git
cd gridpulse

# Build and test
mvn clean verify

# Run application
mvn spring-boot:run
```
 - Access GraphQL Playground at http://localhost:8080/graphiql

### Frontend (coming soon)
```bash

# Navigate to the frontend directory
cd frontend

# Install dependencies & run the app
npm install
ng serve -o
```
 - Access dashboard at http://localhost:4200

### Docker (optional)
```bash

docker-compose up --build
```
 - Access backend at http://localhost:8080/graphiql

## ✅ CI/CD
* This project uses GitHub Actions for continuous integration:
* Runs mvn clean verify on each push and pull request
* Uploads test reports as artifacts
* **Future steps:** automated Docker image build and deployment
