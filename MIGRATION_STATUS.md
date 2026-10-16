# ShariaFunds — Migration Status

Last updated: 2026-10-16

## Git state

- Branch `migration/spring-boot`, branched from `master` at `34a3527` (pre-migration baseline: the 44 local changes).
  `master` is untouched by the migration and is the rollback point. Nothing pushed to `origin`.
- Left uncommitted on purpose (build output tracked in Git, see Known issues):
  `target/` metadata files and `target/classes/META-INF/persistence.xml`; untracked `.settings/org.eclipse.jdt.apt.core.prefs`.

## Completed

| Task | Result | Verification |
|---|---|---|
| 0 Branch | `migration/spring-boot` created | Working tree identical before/after |
| 1 Baseline commit | 44 files committed on `master` (`34a3527`) | 44 `M` staged, 0 others |
| 2 Notes | MIGRATION_PLAN.md, MIGRATION_STATUS.md | — |
| 3 Repeatable WildFly build | Plugins pinned, enforcer JDK 8 | Two clean builds identical |
| 4 Test DB + snapshot | `ShariaFunds_migration` on PG 10.3 + `migration/schema-baseline.sql` | Schema and row counts identical to live |
| 5 Spike | Skipped: user chose the full switch directly (2026-10-16); validated by Task 6/7 testing instead | — |
| 6 Platform switch | Spring Boot 4.1.1 executable JAR on embedded Tomcat 11; WildFly no longer needed | See below |
| 7 Regression (first pass) | All pages render; CRUD, upload, download, Excel export verified in a browser | See below |

## Task 6 — what changed

- **Versions**: Java 21, Spring Boot 4.1.1, JoinFaces 6.1.1 (Mojarra 4.1.12, Weld 6.0.4, PrimeFaces 15.0.17,
  PrimeFaces Extensions 15.0.17, OmniFaces 5.5.1), EclipseLink 5.0.2, PostgreSQL JDBC (Boot-managed), Apache POI 5.4.0.
  Removed: `javaee-api`, `resources-ckeditor` (unused, no PF 15 release), WildFly-provided EclipseLink.
- **Java**: `javax.*` → `jakarta.*` (477 imports). EJB → Spring: 23 `@Stateless` → `@Service @Transactional`
  (abstract facade `@Transactional`), `@EJB` → `@Inject`, `@Singleton @Startup BaseApplication` → `@Component`.
  `test.java`: `@ManagedBean` → `@Named("test") @RequestScoped` (its effective scope before).
  PrimeFaces upload API (`model.file.UploadedFile`, `getContent()`); 28 `catch (IOException)` around OmniFaces
  `Faces.redirect/sendFile` → `catch (UncheckedIOException)` (OmniFaces 5 no longer declares IOException).
  Three facades use `@org.springframework.stereotype.Service` because the app has an entity named `Service`.
- **New `boot` package**: `ShariaFundsApplication` (scans only app bean packages), `PersistenceConfig`
  (EclipseLink unit from persistence.xml + Spring DataSource + `JpaTransactionManager`, no open-EM-in-view),
  `WebServerConfig` (welcome file, ico mime type, multipart 10 MB for the Faces servlet, static `/resources/*` only).
- **Config**: `application.properties` (127.0.0.1:8080, context `/ShariaFunds`, 30 min session, Faces mappings
  `/faces/*,*.jsf,*.faces`, datetime-converter time zone, DB from `DB_URL`/`DB_USERNAME`/`DB_PASSWORD`, default DB
  `ShariaFunds_migration`). `persistence.xml` → Jakarta 3.0, RESOURCE_LOCAL, `ddl-generation=none`, `weaving=false`.
  `faces-config.xml` moved to `META-INF/` (Faces 4.0 header, content unchanged). `META-INF/beans.xml`
  `bean-discovery-mode="none"`. Deleted `web.xml`, `WEB-INF/beans.xml`, `jboss-deployment-structure.xml`.
  `resources/Bundle.properties` now packaged (it was missing from every Maven-built WAR before).
- **XHTML** (1:1 replacements): `#{p:component('x')}` → `@root:@id(x)` (14), `javax.faces.Boolean/DateTime` →
  `jakarta.faces.*` (13), `selectManyCheckbox layout="grid"` → `responsive`, `fileUploadListener` → `listener` (3),
  `p:column selectionMode` → table `selectionMode` + column `selectionBox`, `xmlns:of` declared in 2 snippets,
  3 resource `library` names with `/` split into library + name. jQuery Migrate 4.0.2 (MIT) added after PrimeFaces'
  jQuery 4 for jQuery-3-era plugins (Bootstrap 4.3, select2, datepickers, slimscroll).

## Builds and tests run (Task 6/7)

- `mvn -B clean package` with JDK 21: **BUILD SUCCESS** → `target/ShariaFunds.jar` (78.7 MB).
- `mvn -B test`: without DB credentials the DB test skips (0 run); with test-DB credentials **4/4 pass**
  (`PersistenceMappingTest`: every entity readable, named queries deployed, all IDs use `SEQ_GEN_SEQUENCE`/50,
  DDL generation off).
- App start (`java -jar`, test DB): started in 15–25 s, listening on **127.0.0.1:8080 only**; seed query ran.
- HTTP sweep of all 58 view files: **56 × 200**; 2 × 500 are include fragments (`report-by-name/company.xhtml`)
  that are not standalone pages — their parent `mediator-report.xhtml` returns 200.
- Browser checks (Chrome, test DB): home + deserved list show DB data, Arabic RTL layout intact, sidebar menus work;
  Services page add/save (INSERT, id 601 from shared sequence) and delete (DELETE incl. `worker_service`);
  beneficiary card edit/save (UPDATE of changed column only, `@PreUpdate` lastupdate); mediator card file upload +
  save (attachment stored byte-exact, date saved without time-zone shift); attachment download (PDF 170 490 bytes
  intact); purchase voucher Excel export (valid .xls, Arabic file name, post-processor ran). No server errors.
- Static exposure: `/resources/images/...` 200; `/resources/` listing, raw `.xhtml`, `WEB-INF`, `META-INF` → 404.
- Afterwards `ShariaFunds_migration` was recreated from live (schema + row counts identical) to remove test writes.
  The live database was only read (pg_dump).

## Known issues / differences (not fixed, need a decision)

- PrimeFaces theme: PF 7 default "aristo" no longer exists; PF 15 default "saga-blue" is used — PrimeFaces widgets
  look somewhat different (app's own Bootstrap template unchanged).
- Browser console: Bootstrap 4.3.1 prints "requires jQuery < 4" after registering all its plugins (cosmetic).
- Pre-existing: template references `js/jquery.min.js`, which is not in the repo (`RES_NOT_FOUND`, one server
  ERROR log line per page view). Pre-existing no-op attributes: `p:tooltip atPosition` (38 files),
  `p:selectOneMenu allowClear` (1) — ignored by PF 7 as well.
- Uploads limited to 10 MB per request (WildFly/Undertow default).
- `target/` build output is tracked in Git; builds modify tracked files. Recommend `git rm --cached -r target`.
- Eclipse project settings (`.project`, facets) still describe a WildFly dynamic web project; re-import as a
  Maven project to work in Eclipse.
- Spring warns once at startup about JoinFaces' optional Hibernate integration (not on classpath) — harmless.

## How to run

```
set DB_USERNAME=postgres
set DB_PASSWORD=...            (never commit it)
mvn -B clean package
java -jar target\ShariaFunds.jar   → http://127.0.0.1:8080/ShariaFunds/
```
`DB_URL` defaults to the test copy; pointing it at the live `ShariaFunds` database is the cut-over step.

## Blockers / waiting on user

- Approval to untrack `target/` in Git; theme choice; cut-over plan to the live database.

## Next task

Task 7 (continued) — user acceptance on the test copy of the business flows (aid distribution, vouchers,
currency exchange, reports), then Task 8 (security).
