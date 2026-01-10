# GridPulse

> Real-time IoT monitoring platform for smart grid infrastructure

[![Backend Deploy](https://img.shields.io/badge/backend-railway-blueviolet)](https://railway.app)
[![Frontend Deploy](https://img.shields.io/badge/frontend-vercel-black)](https://vercel.com)
[![CI/CD](https://img.shields.io/badge/build-success-green.svg)](https://github.com/Ammari-Youssef/GridPulse/actions)

A full-stack energy grid monitoring and management application built with Angular and Spring Boot. This project demonstrates modern enterprise development practices including containerization, cloud deployment, CI/CD pipelines, and comprehensive testing strategies.

## 🚀 Tech Stack

### Frontend
- **Angular 19.2.13** - Modern web framework
- **Angular Material** - UI component library
- **Tailwind CSS** - Utility-first CSS framework
- **TypeScript** - Type-safe JavaScript
- **GraphQL** - Efficient data querying

### Backend
- **Spring Boot 3.4.5** - Java backend framework
- **PostgreSQL** - Relational database
- **Liquibase** - Database migration management
- **Spring Security + JWT** - Authentication & authorization
- **GraphQL** - API query language
- **Maven** - Build automation

### DevOps & Infrastructure
- **Docker & Docker Compose** - Containerization
- **Railway** - Backend & database hosting
- **Vercel** - Frontend hosting
- **GitHub Actions** - CI/CD pipeline
- **JUnit & Mockito** - Unit testing
- **Testcontainers** - Integration testing
- **JaCoCo** - Code coverage
- **SonarQube** - Code quality analysis

## 📋 Prerequisites

Before running this project, ensure you have:

- **Node.js** 18+ and npm
- **Java** 17+
- **Maven** 3.8+
- **Docker** and Docker Compose
- **Git**

## 🛠️ Quick Start

### Local Development (Docker)

The easiest way to run the entire stack locally:
```bash
# Clone repository
git clone https://github.com/Ammari-Youssef/GridPulse.git
cd GridPulse

# Start all services
docker-compose up --build
```

**Access points:**
- 🌐 **Frontend:** http://localhost:4200
- ⚙️ **Backend API:** http://localhost:8080
- 🗄️ **PostgreSQL:** localhost:5432

### Local Development (Native)

#### Backend
```bash
cd backend

# Install dependencies and run tests
mvn clean install

# Run the application
mvn spring-boot:run
```

Backend API: `http://localhost:8080`

#### Frontend
```bash
cd frontend

# Install dependencies
npm install

# Start development server
ng serve
```

Frontend: `http://localhost:4200`

## 🌐 Production Deployment

GridPulse is deployed using modern cloud platforms:

- **Backend + Database:** Railway (auto-deploys from `master`)
- **Frontend:** Vercel (auto-deploys from `master`)

**📘 Full deployment guide:** [docs/DEPLOYMENT.md](docs/DEPLOYMENT.md)

### Quick Deploy Overview

**Backend (Railway):**
1. Create Railway project
2. Add PostgreSQL service
3. Connect GitHub repo (`backend/` root directory)
4. Configure environment variables
5. Auto-deploys on push to `master`

**Frontend (Vercel):**
1. Import GitHub repo
2. Set root directory to `frontend/`
3. Configure `API_URL` environment variable
4. Auto-deploys on push to `master`

## 🏗️ Project Structure
```
GridPulse/
├── backend/              # Spring Boot API (Java 17, Maven)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/    # Application code
│   │   │   └── resources/
│   │   │       ├── db/changelog/  # Liquibase migrations
│   │   │       └── application.yml
│   │   └── test/        # Unit & integration tests
│   ├── Dockerfile
│   └── pom.xml
├── frontend/             # Angular 19 web application
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/
│   │   │   ├── services/
│   │   │   └── models/
│   │   └── assets/
│   ├── package.json
│   └── angular.json
├── docs/                 # Documentation
│   ├── DEPLOYMENT.md     # Deployment guide
│   └── message-payloads/ # API message schemas
├── .github/
│   └── workflows/        # GitHub Actions CI/CD
├── docker-compose.yml    # Local development stack
├── Makefile              # Build automation
└── README.md
```

## 🧪 Testing

### Backend Tests
```bash
cd backend

# Run all tests
mvn test

# Run with coverage report
mvn clean verify

# View coverage (opens in browser)
open target/site/jacoco/index.html
```

**Test types:**
- Unit tests (JUnit, Mockito)
- Integration tests (Testcontainers)
- Database migration tests (Liquibase)

### Frontend Tests
```bash
cd frontend

# Run unit tests
ng test

# Run with coverage
ng test --code-coverage

# E2E tests (if configured)
ng e2e
```

## 🔨 Building for Production

### Docker Build
```bash
# Build all services
docker-compose up --build

# Production build (optimized)
docker-compose -f docker-compose.prod.yml up --build
```

### Separate Builds

**Backend:**
```bash
cd backend
mvn clean package -DskipTests
# Output: target/gridpulse-backend-*.jar
```

**Frontend:**
```bash
cd frontend
ng build --configuration production
# Output: dist/gridpulse/
```

## 📦 Useful Commands

### Docker
```bash
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f backend

# Rebuild specific service
docker-compose up --build frontend

# Reset database
docker-compose down -v && docker-compose up --build
```

### Make Commands
```bash
# Available targets (see Makefile)
make build        # Build all services
make test         # Run all tests
make clean        # Clean build artifacts
```

## 🔍 Code Quality & CI/CD

### Continuous Integration

GitHub Actions automatically on every push:
- ✅ Runs unit tests
- ✅ Runs integration tests
- ✅ Generates coverage reports
- ✅ Performs SonarQube analysis
- ✅ Builds Docker images (with caching)
- ✅ Validates code quality gates

### SonarQube Analysis
```bash
# Run locally (requires SonarQube server)
cd backend
mvn sonar:sonar \
  -Dsonar.projectKey=GridPulse \
  -Dsonar.host.url=http://localhost:9000
```

## 🚦 Development Workflow

1. **Branch from `development`**
```bash
   git checkout development
   git pull
   git checkout -b feature/your-feature
```

2. **Make changes & test locally**
```bash
   docker-compose up
   # Verify changes work
```

3. **Run tests**
```bash
   cd backend && mvn test
   cd frontend && ng test
```

4. **Create Pull Request**
   - Target: `development` branch
   - CI/CD runs automatically
   - Request review

5. **Merge to master for production**
   - After approval, merge `development` → `master`
   - Railway & Vercel auto-deploy

## 🔐 Environment Variables

### Backend (Railway)
```bash
SPRING_PROFILES_ACTIVE=prod
DB_URL=jdbc:postgresql://...
JWT_SECRET_KEY=<base64-secret>
# See docs/DEPLOYMENT.md for full list
```

### Frontend (Vercel)
```bash
API_URL=https://your-backend.railway.app
PRODUCTION=true
```

**🔒 Never commit secrets to Git!** Use `.env` locally, Railway/Vercel UI for production.

## 🐛 Troubleshooting

### Port Conflicts
```bash
docker-compose down
# Change ports in docker-compose.yml if needed
docker-compose up
```

### Database Issues
```bash
# Reset database (WARNING: deletes data)
docker-compose down -v
docker-compose up --build
```

### Build Failures
```bash
# Clear Maven cache
cd backend
mvn clean

# Clear npm cache
cd frontend
rm -rf node_modules package-lock.json
npm install
```

### Railway Deployment Fails

Check:
1. `DB_URL` has `jdbc:` prefix
2. PostgreSQL service is named `Postgres`
3. Root directory set to `backend/`
4. All environment variables configured

See [docs/DEPLOYMENT.md](docs/DEPLOYMENT.md) for detailed troubleshooting.

## 🤝 Contributing

This is a portfolio project, but feedback and suggestions are welcome!

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📚 Documentation

- **[Deployment Guide](docs/DEPLOYMENT.md)** - Railway & Vercel setup
- **[API Documentation](docs/API.md)** *(planned)*
- **[Architecture Overview](docs/ARCHITECTURE.md)** *(planned)*

## 📄 License

This project is part of my portfolio and is available for educational purposes.

## 👤 Author

**Youssef Ammari**
- GitHub: [@Ammari-Youssef](https://github.com/Ammari-Youssef)
- Email: youssef.ammari.795@gmail.com

---

**Built with ❤️ as a demonstration of modern full-stack development practices.**

*Last Updated: January 2026*