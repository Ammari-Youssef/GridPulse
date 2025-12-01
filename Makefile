# ==========================================
# GRIDPULSE CLOUD - MAKEFILE
# Automating Docker, Backend, Database, Tests
# ==========================================

# ------------------------------------------
# Docker compose services
# ------------------------------------------

BACKEND_SERVICE=backend
DB_SERVICE=postgres
FRONTEND_SERVICE=frontend

# Containers names
BACKEND_CONTAINER=gridpulse-backend
DB_CONTAINER=gridpulse-postgres
FRONTEND_CONTAINER=gridpulse-frontend

# PostgreSQL volume
DB_VOLUME=pgdata

# ------------------------------------------
# Docker Compose Commands
# ------------------------------------------

up:
	docker compose up --build

up-d:
	docker compose up -d --build

down:
	docker compose down

restart:
	docker compose down
	docker compose up -d --build

logs:
	docker compose logs -f $(BACKEND_SERVICE)

ps:
	docker compose ps

# ------------------------------------------
# Environment check
# ------------------------------------------

check-env:
	@echo "🔹 Checking critical environment variables in backend container..."
	@docker exec -it $(BACKEND_CONTAINER) env | grep -E 'DB_USER|DB_PASS|SPRING_' || \
	( echo "❌ Missing critical env vars!" && exit 1 )
	@echo "✅ Environment variables OK."

# ------------------------------------------
# PostgreSQL Maintenance
# ------------------------------------------

db-reset:
	docker compose down
	docker volume rm $(DB_VOLUME)
	docker compose up -d --build
	@echo "✔ PostgreSQL database reset successfully."

db-shell:
	docker exec -it $(DB_CONTAINER) psql -U $$DB_USER -d $$DB_NAME

db-import:
	docker exec -i $(DB_CONTAINER) psql -U $$DB_USER -d $$DB_NAME < dump.sql

# ------------------------------------------
# Backend — Build & Test
# ------------------------------------------

build-app:
	cd backend && ./mvnw clean package -DskipTests

test-backend:
	cd backend && ./mvnw test

clean:
	cd backend && ./mvnw clean

# ------------------------------------------
# Backend — Inside Container
# ------------------------------------------

bash:
	docker exec -it $(BACKEND_CONTAINER) bash

env:
	docker exec -it $(BACKEND_CONTAINER) env

# ------------------------------------------
# Custom Commands (Seeding)
# ------------------------------------------

seed:
	docker exec -it $(BACKEND_CONTAINER) java -jar app.jar --seed

# ------------------------------------------
# Frontend — Angular
# ------------------------------------------

test-frontend:
	cd frontend && npm test

test-frontend-coverage:
	cd frontend && ng test --watch=false --code-coverage

open-coverage-frontend:
	@echo "Opening Angular coverage report..."
	@xdg-open frontend/coverage/index.html 2>/dev/null || \
	start frontend/coverage/index.html 2>/dev/null || \
	open frontend/coverage/index.html 2>/dev/null

# ------------------------------------------
# Docker Cleanup
# ------------------------------------------

prune:
	docker system prune -f

prune-all:
	docker system prune -a -f

# ------------------------------------------
# Help
# ------------------------------------------

help:
	@echo "AVAILABLE COMMANDS:"
	@echo "  make up                   : Start docker compose with build"
	@echo "  make up-d                 : Start in detached mode"
	@echo "  make down                 : Stop all services"
	@echo "  make restart              : Restart full stack"
	@echo "  make logs                 : Show backend logs"
	@echo "  make ps                   : List running containers"
	@echo "  make db-reset             : Reset PostgreSQL database"
	@echo "  make db-shell             : Enter PostgreSQL shell"
	@echo "  make build-app            : Build Spring Boot JAR"
	@echo "  make test-backend         : Run backend tests"
	@echo "  make bash                 : Enter backend container"
	@echo "  make env                  : Show env vars from container"
	@echo "  make seed                 : Run backend seeding"
	@echo "  make prune                : Remove unused Docker resources"
	@echo "  make prune-all            : Deep Docker clean"
