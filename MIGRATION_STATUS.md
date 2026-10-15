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
| 2 Notes | MIGRATION_PLAN.md, MIGRATION_STATUS.md created | — |

## Environment facts

- JDKs: 1.8.0_191, 17.0.1, 21.0.12 (default). Maven 3.8.6.
- PostgreSQL 10.3 (port 5432, used by app) and 9.6.1 (port 5433, not used by app).
- WildFly 8.2.1.Final at `C:\wildfly-8.2.1.Final` (shared with RealEstate.war — do not touch).

## Builds and tests run

- 2026-10-16, commit `34a3527`, JDK 1.8.0_191, Maven 3.8.6, `mvn -B package` (no `clean`, no pom changes):
  **BUILD SUCCESS** in 5.9 s → `target/ShariaFunds.war` (38.4 MB, 112 classes, 21 libs in `WEB-INF/lib`).
  - Unpinned default plugins used: resources 2.6, compiler 3.1, surefire 2.12.4, war 2.2.
  - Tests: none exist (surefire ran with nothing to execute).
  - WAR contains `xmlworker-5.5.13.1.jar` (AGPL) but not the iText core jar.
  - Build side effect `target/maven-archiver/pom.properties` was removed again to restore the prior working-tree state.
- Not deployed or started (WildFly untouched).

## Blockers / waiting on user

- Approval of Task 3 (baseline build; `pom.xml` plugin pinning only).
- Task 4 needs the user to create the `ShariaFunds_migration` copy (commands will be provided).

## Next task

Task 3 — Repeatable baseline build on the current stack.
