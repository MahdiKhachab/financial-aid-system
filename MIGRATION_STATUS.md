# ShariaFunds — Migration Status

Last updated: 2026-10-16

## Git state

- Branch: `migration/spring-boot` = `master` = `34a3527` ("Pre-migration baseline: commit existing local work").
- `34a3527` contains exactly the 44 previously uncommitted modified files (62 insertions, 83 deletions); parent `b18330e` (`origin/master`).
- Still uncommitted, intentionally left alone:
  - 4 deleted tracked build files under `target/` (m2e-wtp and maven-archiver metadata).
  - Untracked `.settings/org.eclipse.jdt.apt.core.prefs`.
- Nothing pushed to `origin`.

## Completed

| Task | Result | Verification |
|---|---|---|
| 0 Branch | `migration/spring-boot` created | Working tree fingerprint identical before/after |
| 1 Baseline commit | 44 files committed on `master`; branch moved to it | 44 `M` entries staged, 0 others; both branches at `34a3527` |
| 2 Notes | MIGRATION_PLAN.md, MIGRATION_STATUS.md created | Committed `50b408a` |
| 3 Repeatable build | `pom.xml`: pinned clean 2.5, resources 2.6, compiler 3.1, surefire 2.12.4, war 2.2, install 2.4, deploy 2.7 (the previous defaults); enforcer 3.4.1 requires JDK [1.8,1.9) and Maven ≥3.8 | See builds below |

| 4 Test DB + snapshot | `ShariaFunds_migration` created on PG 10.3 (UTF8, `Arabic_Saudi Arabia.1256`) from `pg_dump -Fc` of `ShariaFunds`; schema saved to `migration/schema-baseline.sql` (no data) | Schema dumps of live and copy identical; row counts identical in all 27 tables |

## Database reference snapshot (2026-10-16, `ShariaFunds_migration`)

- 27 tables in `public`, 228 columns, 27 PKs, 32 FKs, 27 indexes; no views, functions or triggers; no unique/check constraints.
- One shared sequence `seq_gen_sequence` (start 50, increment 50, last_value 600) feeds **all** entity IDs
  (EclipseLink default `SEQUENCE` with preallocation 50). Highest existing id = 503 (`service`).
  **Target config must keep sequence name `SEQ_GEN_SEQUENCE` and allocationSize 50.**
- Row counts: attachment 2, cfg_company 2, cfg_healthystatus 1, cfg_inssurance 1, cfg_poorpercentage 1,
  cfg_religionreference 2, cfg_submission 3, cfg_submissiondetails 6, deadneed 0, deadperson 0, deserved 3,
  mediator 2, needdetails 0, provideaid 0, provideaidcompany 1, provideaiddesrvedperson 0, provideaidrabet 0,
  purchase_voucher 3, purchase_voucher_coupon 5, purchase_voucher_desrved 5, receive_money 5, service 2,
  sponser 2, stores 1, subbmission_exchnage 1, worker 1, worker_service 1.
- The dump file (contains personal data) was deleted after restore; only the schema is kept.

## Environment facts

- JDKs: 1.8.0_191, 17.0.1, 21.0.12 (default). Maven 3.8.6.
- PostgreSQL 10.3 (port 5432, used by app; confirmed via `select version()`) and 9.6.1 (port 5433, not used by app).
- Databases on 5432: live `ShariaFunds` (8.9 MB, never used for migration work) and test copy `ShariaFunds_migration`.
- DB credentials are never stored in the repo; the `postgres` superuser password is weak and should be changed
  once the app has its own least-privilege DB user (security stage).
- WildFly 8.2.1.Final at `C:\wildfly-8.2.1.Final` (shared with RealEstate.war — do not touch).

## Builds and tests run

- 2026-10-16, commit `34a3527`, JDK 1.8.0_191, Maven 3.8.6, `mvn -B package` (no `clean`, no pom changes):
  **BUILD SUCCESS** in 5.9 s → `target/ShariaFunds.war` (38.4 MB, 112 classes, 21 libs in `WEB-INF/lib`).
  - Unpinned default plugins used: resources 2.6, compiler 3.1, surefire 2.12.4, war 2.2.
  - Tests: none exist (surefire ran with nothing to execute).
  - WAR contains `xmlworker-5.5.13.1.jar` (AGPL) but not the iText core jar.
  - Build side effect `target/maven-archiver/pom.properties` was removed again to restore the prior working-tree state.
- Not deployed or started (WildFly untouched).
- 2026-10-16, Task 3, JDK 1.8.0_191, `mvn -B clean package`: **BUILD SUCCESS** (11.3 s), enforcer rules passed.
  - Versus the first baseline WAR: same 402 files, all 21 libs and all web resources identical; all 112 classes differ
    because the first baseline reused stale `target/classes` from a newer compiler (enums contained the Java 15+
    synthetic `$values()`); the clean build is the first true JDK 8 `javac` build. No signature changes otherwise.
  - Second clean build: byte-identical to the first except the timestamp in `META-INF/maven/.../pom.properties` → repeatable.
  - `mvn validate` under default JDK 21 fails at the enforcer as intended ("JDK 21.0.12 is not in the allowed range [1.8,1.9)").
- Build command: `JAVA_HOME="C:\Program Files\Java\jdk1.8.0_191"` then `mvn -B clean package`.

## Known issue

- `target/` build metadata is tracked in Git. Every build regenerates `target/maven-archiver/pom.properties`
  (removed again after each build to keep the prior working-tree state). Recommend `git rm --cached -r target`
  in a separate approved step.

## Blockers / waiting on user

- Approval to untrack `target/` in Git.
- Approval of Task 5 (spike).

## Next task

Task 5 — Spike: Spring Boot 4.1 + JoinFaces 6.1 + PrimeFaces 15 + OmniFaces 5 + EclipseLink 5 against
`ShariaFunds_migration` (go/no-go). Isolated in `spike/`, application code untouched.
