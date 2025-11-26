# 📁 Liquibase Changelog Structure

This directory contains all Liquibase migration files used to manage database schema changes in a modular and
maintainable way.

# 📦 Folder Structure

`````
db/changelog/
├── user/
│   └── v1.0__create-user-table.xml
│   └── v1.0__add-name-of-constraint.xml
├── entity/
│   └── vX.action-entity-description.xml
│   └── vX.Y__add-name-of-consraint.xml
└── db.changelog-master.xml
``````

Each entity has its own folder containing changelogs relevant to that domain. This helps isolate changes and makes it
easier to track schema evolution.

# 🧩 Master Changelog

The `db.changelog-master.xml` file includes all entity-specific changelogs:

````
<include file="db/changelog/entity/vX.Y__create-entity-table.xml" />
<include file="db/changelog/ebtity/vX.Y__add-name-of-consraint.xml" />
````

# 🛠 Naming Conventions

- **Files**: Use descriptive syntax `vX.Y__description.xml` for file names like `v1.0__create-user-table.xml` instead of
  date-based names.

- **ChangeSet IDs:** Prefer semantic IDs `action_entity_description` like `create-user-table` over timestamp-based ones.

- **Authors:** Use your GitHub username or initials for traceability.

# 🧪 Testing & Rollback

Liquibase is configured to:

- Validate changelogs on startup

- Test rollback automatically

- Show a summary of applied changes

These settings are defined in application.properties:

````
spring.liquibase.enabled=true
spring.liquibase.test-rollback-on-update=true
spring.liquibase.show-summary=summary
````

# 🚀 Workflow Guide

## First-Time Setup

1. Copy `liquibase.properties.template` to `liquibase.properties`

````
  cp liquibase.properties.template liquibase.properties
````

2. Fill in your local DB credentials
3. Never commit the actual properties file

## 🧭 How to Add a New Migration

* Create a new XML file in the appropriate folder.
  `db/changelog/entity/v1.2__add-new-column.xml`
* Define your <changeSet> with a unique ID and author.
* Add the file to `db.changelog-master.xml` using `<include file="..."/>.`
* Run the app to apply the migration or use the Liquibase CLI to apply changes manually.

### 📜 Liquibase CLI Commands

        ./mvnw liquibase:update	                                  # Apply all pending changes
        ./mvnw liquibase:rollback -Dliquibase.rollbackCount=1	  # Rollback last change (1 changeSet)
        ./mvnw liquibase:rollback --define liquibase.rollbackCount=1  # Alternative syntax if the one above don't work
        ./mvnw liquibase:history	                                  # View applied changesets
        ./mvnw liquibase:validate	                                  # Verify changelog integrity
        ./mvnw liquibase:tag --define liquibase.tag=tag_name          # Create a tag (bookmark) at the current database state
        ./mvnw liquibase:rollback --define liquibase.tag=tag_name     # Rollback to last bookmark of the database

# 🔒 Rollback Best Practices

Always include rollback blocks:

```xml

<changeSet id="create_user_table" author="youssef">
    <createTable tableName="user">...</createTable>
    <rollback>
        <dropTable tableName="user"/>
    </rollback>
</changeSet>
```