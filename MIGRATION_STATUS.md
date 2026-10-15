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

- Task 4 needs the user to create the `ShariaFunds_migration` copy (commands will be provided).

## Next task

Task 4 — Test database copy and reference snapshot.
