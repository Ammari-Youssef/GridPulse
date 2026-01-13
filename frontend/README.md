# GridPulse Frontend

Angular-based web interface for the GridPulse IoT monitoring platform.

[![Angular](https://img.shields.io/badge/Angular-19-red.svg)](https://angular.io/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.7-blue.svg)](https://www.typescriptlang.org/)
[![Material](https://img.shields.io/badge/Material-19-purple.svg)](https://material.angular.io/)

---

## 🚀 Quick Start

### Prerequisites
- Node.js 18+
- npm 9+

### Installation & Run
```bash
# Install dependencies
npm install

# Start development server
ng serve

# Open browser
# http://localhost:4200
```

The application will automatically reload when you modify source files.

---

## 🛠️ Tech Stack

- **Angular 19** - Modern web framework
- **Angular Material** - Material Design components
- **TailwindCSS 4** - Utility-first styling
- **Apollo GraphQL** - API client for efficient data fetching
- **TypeScript 5.7** - Type-safe development
- **Leaflet** - Interactive map visualization
- **RxJS** - Reactive programming

---

## 📁 Project Structure
```
src/
├── app/
│   ├── core/              # Singleton services & guards
│   │   ├── guards/        # Route guards (auth, role)
│   │   ├── interceptors/  # HTTP/GraphQL interceptors
│   │   ├── services/      # Core services (auth, token)
│   │   └── init/          # App initialization logic
│   ├── features/          # Feature modules
│   │   ├── auth/          # Authentication (login)
│   │   ├── dashboard/     # Main dashboard & maps
│   │   ├── devices/       # Device management
│   │   ├── analytics/     # Analytics & charts
│   │   ├── fleets/        # Fleet management
│   │   ├── alerts/        # Alert management
│   │   └── settings/      # User settings
│   ├── shared/            # Shared components & utilities
│   │   ├── components/    # Reusable UI components
│   │   ├── directives/    # Custom directives
│   │   ├── pipes/         # Custom pipes
│   │   └── models/        # Shared interfaces/types
│   ├── layout/            # Layout components
│   │   ├── header/        # Top navigation
│   │   ├── sidebar/       # Side navigation
│   │   └── footer/        # Footer
│   └── graphql/           # GraphQL module
│       ├── queries/       # GraphQL queries
│       ├── mutations/     # GraphQL mutations
│       └── fragments/     # Reusable fragments
├── environments/          # Environment configurations
│   ├── environment.ts              # Default (dev)
│   ├── environment.development.ts  # Local development
│   └── environment.production.ts   # Production
└── assets/                # Static assets (images, icons)
```

---

## 🔨 Available Commands

| Command | Description |
|---------|-------------|
| `npm start` | Start dev server (port 4200) |
| `npm run build` | Production build |
| `npm run ci:build` | CI/CD production build |
| `npm run watch` | Build with watch mode |
| `npm test` | Run unit tests |
| `npm run ci:test` | Run tests in CI (headless) |
| `npm run lint` | Check code quality |
| `npm run ci:lint` | Lint with error reporting |

---

## 🏗️ Building

### Development Build
```bash
ng build
```
Output: `dist/gridpulse/browser/`

### Production Build
```bash
ng build --configuration production
```

**Production optimizations:**
- ✅ Ahead-of-Time (AOT) compilation
- ✅ Code minification & tree-shaking
- ✅ Environment file replacement
- ✅ CSS optimization
- ✅ Source maps disabled

---

## 🧪 Testing

### Unit Tests
```bash
# Run tests with Karma
ng test

# Run with coverage report
ng test --code-coverage

# View coverage report
open coverage/index.html
```

**Test configuration:**
- Framework: Jasmine
- Runner: Karma
- Browser: ChromeHeadless (CI), Chrome (local)

### Linting
```bash
# Check code quality
ng lint

# Auto-fix issues
ng lint --fix
```

**ESLint configuration:** `.eslintrc.json`

---

## 🎨 Code Scaffolding

### Generate Component
```bash
ng generate component features/my-feature
```

### Generate Service
```bash
ng generate service core/services/my-service
```

### Generate Module
```bash
ng generate module features/my-module
```

### Available Schematics
```bash
ng generate --help
```

---

## 🌍 Environment Configuration

### Development (`environment.development.ts`)
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/graphql'
};
```

### Production (`environment.production.ts`)
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://api.gridpulse.io/graphql'
};
```

**File replacement** is configured in `angular.json`:
- Development build → uses `environment.development.ts`
- Production build → uses `environment.production.ts`

---

## 🔐 Authentication Flow

1. User submits credentials via login form
2. `AuthService` sends GraphQL mutation to backend
3. Backend returns JWT access + refresh tokens
4. `TokenStorageService` stores tokens in memory (no localStorage)
5. `JwtInterceptor` adds token to all GraphQL requests
6. `AuthGuard` protects routes requiring authentication
7. On token expiry, refresh token is used automatically

**Demo Credentials:**
```
Email: youssef@gridpulse.io
Password: ysf@1234
Role: Admin
```

---

## 🗺️ Key Features

### Dashboard
- Real-time device overview
- Interactive Leaflet maps with clustering
- Device filtering and search
- Quick stats cards

### Device Management
- CRUD operations
- Pagination & sorting
- BMS, Meter, Inverter data display
- Device details view

### Analytics
- Historical data visualization
- Multiple chart types (line, bar, pie)
- Device comparison
- Export capabilities

### Fleet Management
- Fleet organization
- Device assignment
- Location-based grouping

### Alert System
- Real-time alerts
- Severity-based filtering
- Alert acknowledgment
- Notification center

---

## 📦 Key Dependencies

### Core
- `@angular/core` ^19.2.0
- `@angular/router` ^19.2.0
- `@angular/forms` ^19.2.0

### UI
- `@angular/material` ^19.2.19
- `tailwindcss` ^4.1.18

### Data & API
- `apollo-angular` ^13.0.0
- `@apollo/client` ^4.0.1
- `graphql` ^16

### Maps
- `leaflet` ^1.9.4
- `leaflet.markercluster` ^1.5.3

### Utilities
- `rxjs` ~7.8.0
- `tslib` ^2.3.0

---

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Kill process on port 4200
npx kill-port 4200

# Or use different port
ng serve --port 4300
```

### Module Not Found Errors
```bash
# Clear cache and reinstall
rm -rf node_modules package-lock.json
npm install
```

### GraphQL Connection Issues
1. Verify backend is running: `http://localhost:8080/graphql`
2. Check CORS settings in backend
3. Inspect browser console for errors
4. Verify `environment.ts` has correct API URL

### Build Errors
```bash
# Clear Angular cache
rm -rf .angular/cache

# Rebuild
ng build
```

---

## 🚀 Deployment

See [docs/DEPLOYMENT.md](../docs/DEPLOYMENT.md) for Vercel deployment instructions.

**Quick deploy to Vercel:**
```bash
# Install Vercel CLI
npm i -g vercel

# Deploy
vercel --prod
```

---

## 📚 Additional Resources

- [Angular Documentation](https://angular.dev)
- [Angular Material Components](https://material.angular.io/components)
- [TailwindCSS Utilities](https://tailwindcss.com/docs)
- [Apollo GraphQL Angular](https://www.apollographql.com/docs/angular)
- [Leaflet Documentation](https://leafletjs.com)

---

## 👤 Author

**Youssef Ammari**
- GitHub: [@Ammari-Youssef](https://github.com/Ammari-Youssef)

---

**Last Updated:** January 2026