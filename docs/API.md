# API Documentation

## Overview
This application exposes a **GraphQL API** for backend operations.  
GraphQL provides a flexible query language and a self‑documenting schema.

---

## Access
- **GraphiQL Interface (interactive docs):**  
  [http://localhost:8080/graphiql](http://localhost:8080/graphiql)

  ![GraphiQL Interface](docs/screenshots/graphiql.jpeg)


- **Schema Files:**  
  Located in `src/main/resources/graphql/`  
  - `schema.graphqls` (root schema)  
  - Feature‑specific schemas under `auth/`, `domain/`, `user/`, etc.

- **Self‑Documentation:**  
  The GraphQL schema is introspectable, meaning tools like GraphiQL, Apollo, or Postman can automatically generate documentation.

---

## Example Query
```graphql
query GetDeviceById($id: UUID!) {
    getDeviceById(id: $id) {
       id
        name
        model
        serialNumber
        manufacturer
        status
        lastSeen
        bms {
          id
          temperature
          soc
          voltage
          soh
          cycles
          batteryChemistry
        }
        meter {
          id
          powerDispatched
          energyConsumed
          energyProduced
        }
      }
    }
```