# ShariaFunds — Spring Boot Migration Plan

Branch: `migration/spring-boot` (baseline commit `34a3527` on `master`).
Rules: small approved tasks, verify every step, no DB schema/data changes without approval,
never use the live `ShariaFunds` database for experiments, `RealEstate.war` is out of scope.

## Current stack (audited 2026-10-16)

| Area | Current |
|---|---|
| Java | source/target 1.8 (JDK 8u191, 17, 21 installed; default 21) |
| Server | WildFly 8.2.1.Final, binds `0.0.0.0:8080` |
| Java EE | javaee-api 7.0: Servlet 3.1, CDI 1.1, JSF 2.2 (Mojarra 2.2.8-jbossorg), JPA 2.1, EJB 3.2 |
| UI | PrimeFaces 7.0, PrimeFaces Extensions 7.0 (unused), OmniFaces 2.6.7 |
| JPA | EclipseLink 2.6.0 (pom) / 2.6.0-RC1 (WildFly runtime), JTA PU `ShariaFunds-persistence-unit` |
| Database | PostgreSQL 10.3 on localhost:5432, DB `ShariaFunds`, JDBC 9.4.1212 |
| Security | none (no login, roles, constraints or filters) |

## Approved target (2026-10-16)

| Component | Target |
|---|---|
| Java | 21 LTS |
| Spring Boot | 4.1.x (OSS support to 2027-07-31), executable JAR, embedded Tomcat 11 |
| JSF integration | JoinFaces 6.1.x |
| Faces / CDI | Mojarra 4.1.x, Weld 6.0.x servlet (via JoinFaces) |
| UI libs | PrimeFaces 15.0.x (jakarta), OmniFaces 5.x |
| JPA | EclipseLink 5.0.x (kept for sequence and lazy-loading semantics) |
| Transactions | Spring `JpaTransactionManager`, RESOURCE_LOCAL, HikariCP |
| JDBC | pgJDBC 42.7.x |
| PostgreSQL | 10.3, unchanged |
| Bind address | `127.0.0.1` only (single-PC use) |
| DI model | (a) JSF controllers become Spring beans; Weld only serves Faces/OmniFaces |

Exact patch versions are pinned in Task 5 (spike).

## Key preservation rules

- One `REQUIRED` transaction per facade call; controller methods stay non-atomic as today.
- No DDL generation (`eclipselink.ddl-generation=none`, `spring.jpa.generate-ddl=false`).
- Keep EclipseLink default `SEQUENCE` naming/allocation and `TABLE_PER_CLASS` mapping.
- Keep `/faces/*`, `*.jsf`, `*.faces` URLs, welcome page and all navigation outcomes.
- Keep 30-minute session timeout and `BaseApplication` seed behaviour.

## Task roadmap

| # | Task | Status |
|---|---|---|
| 0 | Create migration branch | Done |
| 1 | Commit 44 pre-migration changes on `master`, align branch | Done |
| 2 | Create MIGRATION_PLAN.md / MIGRATION_STATUS.md | Done |
| 3 | Repeatable baseline build on current stack (pin Maven plugins, JDK 8) | Done |
| 4 | Test DB `ShariaFunds_migration` (user restores copy) + reference snapshot | Next |
| 5 | Spike: Boot 4.1 + JoinFaces 6.1 + PF 15 + OmniFaces 5 + EclipseLink 5 (go/no-go) | Pending |
| 6 | Platform switch in small commits (pom, jakarta, descriptors, persistence, services, scopes, PF/OF APIs); remove WildFly | Pending |
| 7 | Full UI regression against checklist on test DB | Pending |
| 8 | Security stage (login, roles, session, logging) — separate approval | Pending |
| 9 | Cleanup (xmlworker, test.java, swallowed exceptions, XHTML namespaces) — separate approvals | Pending |

## Open findings for later approval

- iText 5 `xmlworker` (AGPL) has no code usage; recommend removal. OpenPDF is kept.
- WildFly app and management interfaces currently listen on `0.0.0.0` without login.
- SQL parameter logging writes personal data to logs.
- DB user is the `postgres` superuser; recommend a dedicated least-privilege role.
- PostgreSQL 10 is end-of-life (Nov 2022); kept as requested.
- `test.java` mixes `javax.faces.bean.ManagedBean` with CDI `ViewScoped`.
- `AbstractFacade.getSqlFromJpql` uses EclipseLink internal APIs.
