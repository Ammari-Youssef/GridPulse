# GridPulse GraphQL Schema

This schema is modularized by domain to improve clarity, scalability, and onboarding for future contributors and recruiters.

## 🗂️ Structure Overview

- `schema.graphqls`: Legacy entry point
- `api/`: System-level metadata resolver (e.g. version, uptime, hello)
  - `api.graphqls`
- `auth/`: Authentication and access control
- `user/`: User entity and role modeling
- `domain/`: Core business entities, each with:
  - `types/`: GraphQL object types and history types
  - `input/`: Mutation input types
  - `enum/`: Domain-specific enums
  - `schema/`: Queries and mutations

- `interfaces/`: Shared contracts and enums
  - `auditable.graphqls`
  - `enums.graphqls`
  - `sunspec.graphqls`

## 🧠 Design Principles

- **Domain-driven separation** for maintainability and scalability
- **Shared interfaces** for auditability and cross-domain reuse
- **Resolver alignment** with schema definitions
- **Recruiter-friendly layout** for code reviews and onboarding

## ✅ Example Domains

- `bms/`, `device/`, `fleet/`, `inverter/`, `inv_common/`, `message/`, `meter/`, `security_key/`

Each domain follows the same modular pattern for consistency and extensibility.

---