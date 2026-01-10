# Deployment Guide

This guide covers deploying GridPulse to production using Railway (backend) and Vercel (frontend).

## Table of Contents

<!-- - [Frontend Deployment (Vercel)](#frontend-deployment-vercel) -->
- [Prerequisites](#prerequisites)
- [Backend Deployment (Railway)](#backend-deployment-railway)
- [Environment Variables](#environment-variables)
- [Troubleshooting](#troubleshooting)

---

## Prerequisites

- GitHub account
- Railway account ([railway.app](https://railway.app))
- Domain (optional)
<!-- - Vercel account ([vercel.com](https://vercel.com)) -->

---

## Backend Deployment (Railway)

### Step 1: Create Railway Project

1. Go to [Railway Dashboard](https://railway.app/dashboard)
2. Click **New Project**
3. Name it: `GridPulse`

### Step 2: Add PostgreSQL Database

1. Inside project → **New** → **Database** → **PostgreSQL**
2. Rename service to `Postgres` (click service name at top)
3. Note the auto-generated credentials

### Step 3: Add Backend Service

1. Same project → **New** → **GitHub Repo**
2. Select: `Ammari-Youssef/GridPulse`
3. Branch: `master`

### Step 4: Configure Backend Service

**Settings:**
- **Root Directory:** `backend`
- **Healthcheck Path:** `/actuator/health`
- **Watch Paths:** `backend/**`

**Environment Variables:**
```bash
SPRING_PROFILES_ACTIVE=prod
DB_URL=jdbc:postgresql://${{Postgres.PGHOST}}:${{Postgres.PGPORT}}/${{Postgres.PGDATABASE}}
DB_USER=${{Postgres.PGUSER}}
DB_PASS=${{Postgres.PGPASSWORD}}
DB_DRIVER=org.postgresql.Driver
DB_DIALECT=org.hibernate.dialect.PostgreSQLDialect
SPRING_SECURITY_USER_NAME=admin
SPRING_SECURITY_USER_PASSWORD=<generate-strong-password>
JWT_SECRET_KEY=<generate-with-openssl-rand-base64-64>
JWT_EXPIRATION_TIME=86400000
REFRESH_EXPIRATION_TIME=604800000
```

**Generate JWT Secret:**
```bash
openssl rand -base64 64
```

### Step 5: Deploy

Railway auto-deploys on push to `master`. Monitor deployment:
- Backend Service → **Deployments** → View Logs

**Expected logs:**
```
HikariPool-1 - Start completed.
Liquibase: Running Changeset: db/changelog/...
Started GridPulseApplication in 8.234 seconds
```

### Step 6: Get Backend URL

Railway generates a public URL:
```
https://api.gridpulse.io
```

Test health endpoint:
```bash
curl https://api.gridpulse.io/actuator/health
```

Expected response:
```json
{"status":"UP"}
```

---

<!-- ## Frontend Deployment (Vercel)

### Step 1: Import Repository

1. Go to [Vercel Dashboard](https://vercel.com/dashboard)
2. Click **Add New** → **Project**
3. Import: `Ammari-Youssef/GridPulse`

### Step 2: Configure Build Settings

**Framework Preset:** Angular

**Root Directory:** `frontend`

**Build Command:**
```bash
npm run build
```

**Output Directory:**
```
dist/gridpulse/browser
```

### Step 3: Environment Variables

Add in Vercel project settings:
```bash
API_URL=https://api.gridpulse.io
PRODUCTION=true
```

### Step 4: Deploy

Vercel auto-deploys. Your app will be live at:
```
https://gridpulse.vercel.app
```

--- -->

## Environment Variables Reference

### Backend (Railway)

| Variable | Description | Example |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `prod` |
| `DB_URL` | PostgreSQL connection string | `jdbc:postgresql://...` |
| `DB_USER` | Database username | From Railway Postgres |
| `DB_PASS` | Database password | From Railway Postgres |
| `JWT_SECRET_KEY` | JWT signing key (Base64) | Generate with OpenSSL |
| `JWT_EXPIRATION_TIME` | JWT expiry (milliseconds) | `86400000` (24h) |

<!-- ### Frontend (Vercel)

| Variable | Description | Example |
|----------|-------------|---------|
| `API_URL` | Backend API endpoint | Railway backend URL |
| `PRODUCTION` | Production mode flag | `true` |

--- -->

## Database Migrations

Liquibase runs automatically on backend startup:

1. Connects to PostgreSQL
2. Checks `databasechangelog` table
3. Runs pending changesets from `db/changelog/db.changelog-master.xml`
4. Updates `databasechangelog`

**Verify migrations:**
- Railway → Postgres Service → **Data** tab
- Check for tables: `databasechangelog`, `databasechangeloglock`, and your domain tables

---

## Troubleshooting

### Backend Deployment Failed

**Error:** `Unable to access jarfile target/*.jar`

**Fix:** Railway is using the Dockerfile correctly. This error is from old deployments. Check latest deployment logs.

---

**Error:** `Connection refused` to database

**Fix:** 
1. Verify Postgres service is named `Postgres`
2. Check `DB_URL` has `jdbc:` prefix
3. Ensure services are in same Railway project

---

**Error:** Liquibase changesets not running

**Fix:**
1. Check logs for "Liquibase: Starting Liquibase..."
2. Verify `spring.liquibase.enabled=true` in `application-prod.yml`
3. Ensure `DB_URL`, `DB_USER`, `DB_PASS` are set correctly

---

<!-- ### Frontend Deployment Failed

**Error:** `Module not found`

**Fix:**
1. Verify Root Directory is `frontend`
2. Check `package.json` exists in `frontend/`
3. Run `npm install` locally to verify dependencies

---

**Error:** API calls fail in production

**Fix:**
1. Check `API_URL` environment variable in Vercel
2. Verify CORS is enabled in Spring Boot backend
3. Test backend URL directly with curl

--- -->

## Monitoring & Logs

### Backend Logs
- Railway → Backend Service → **Deployments** → View Logs
- Filter by: `ERROR`, `WARN`, `Liquibase`

### Database Access
- Railway → Postgres Service → **Data** tab (GUI)
- Or use **Connect** → Copy psql command

<!-- ### Frontend Logs
- Vercel → Project → **Deployments** → Function Logs
- Browser DevTools → Console (client-side errors) -->

---

## Custom Domain (Optional)

### Backend (Railway)
1. Backend Service → **Settings** → **Networking**
2. **Custom Domain** → Add your domain
3. Update DNS with Railway's CNAME

<!-- ### Frontend (Vercel)
1. Vercel Project → **Settings** → **Domains**
2. Add custom domain
3. Update DNS records as shown

--- -->

## CI/CD Pipeline

GitHub Actions automatically:
1. Runs tests on PR
2. Deploys to Railway (backend) on merge to `master`
3. Deploys to Vercel (frontend) on merge to `master`

<!-- **Workflow file:** `.github/workflows/deploy.yml` -->

---

## Rollback Strategy

### Backend
1. Railway → Backend Service → **Deployments**
2. Find previous successful deployment
3. Click **⋮** → **Redeploy**

<!-- ### Frontend
1. Vercel → Project → **Deployments**
2. Find previous deployment
3. Click **⋮** → **Promote to Production**

--- -->

## Production Checklist

- [ ] Backend deployed to Railway
- [ ] PostgreSQL database created and linked
- [ ] Liquibase migrations applied successfully
- [ ] Backend health endpoint returns 200 OK
- [ ] Frontend deployed to Vercel
- [ ] Frontend can call backend API
- [ ] Environment variables configured (prod values)
- [ ] JWT secret is strong (64+ characters, base64)
- [ ] Admin password changed from default
- [ ] HTTPS enabled (Railway/Vercel handle this)
- [ ] CORS configured for frontend domain
- [ ] Monitoring/logging verified

---

## Support

For deployment issues:
- Check [Troubleshooting](#troubleshooting) section
- Review Railway<!-- /Vercel--> logs
- Open issue on [GitHub](https://github.com/Ammari-Youssef/GridPulse/issues)

**Last Updated:** January 2026