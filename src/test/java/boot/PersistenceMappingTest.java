package boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.metamodel.EntityType;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sequencing.Sequence;
import org.eclipse.persistence.sessions.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Checks the EclipseLink mappings against the migration test database (read-only).
 * Runs only when DB_PASSWORD is set; never point DB_URL at the live database.
 */
class PersistenceMappingTest {

	private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/ShariaFunds_migration";

	private static EntityManagerFactory emf;

	@BeforeAll
	static void createEntityManagerFactory() {
		String password = System.getenv("DB_PASSWORD");
		assumeTrue(password != null && !password.isEmpty(), "DB_PASSWORD not set: skipping database test");
		String url = System.getenv().getOrDefault("DB_URL", DEFAULT_URL);
		assumeTrue(url.contains("ShariaFunds_migration"), "Only runs against the migration test database");

		Map<String, Object> props = new HashMap<>();
		props.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
		props.put("jakarta.persistence.jdbc.url", url);
		props.put("jakarta.persistence.jdbc.user", System.getenv().getOrDefault("DB_USERNAME", "postgres"));
		props.put("jakarta.persistence.jdbc.password", password);
		props.put("eclipselink.logging.level", "WARNING");
		emf = Persistence.createEntityManagerFactory(PersistenceConfig.PERSISTENCE_UNIT, props);
	}

	@AfterAll
	static void close() {
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	void everyEntityCanBeRead() {
		EntityManager em = emf.createEntityManager();
		try {
			assertFalse(em.getMetamodel().getEntities().isEmpty());
			for (EntityType<?> entity : em.getMetamodel().getEntities()) {
				em.createQuery("SELECT e FROM " + entity.getName() + " e").setMaxResults(5).getResultList();
			}
		} finally {
			em.close();
		}
	}

	@Test
	void namedQueriesAreParsed() {
		EntityManager em = emf.createEntityManager();
		try {
			Session session = em.unwrap(JpaEntityManager.class).getServerSession();
			assertFalse(session.getQueries().isEmpty(), "named queries were not deployed");
		} finally {
			em.close();
		}
	}

	@Test
	void identifiersStillUseTheSharedEclipseLinkSequence() {
		EntityManager em = emf.createEntityManager();
		try {
			Session session = em.unwrap(JpaEntityManager.class).getServerSession();
			for (ClassDescriptor descriptor : session.getDescriptors().values()) {
				if (descriptor.isAggregateDescriptor() || !descriptor.usesSequenceNumbers()) {
					continue;
				}
				Sequence sequence = descriptor.getSequence();
				assertEquals("SEQ_GEN_SEQUENCE", sequence.getName().toUpperCase(), descriptor.getJavaClassName());
				assertEquals(50, sequence.getPreallocationSize(), descriptor.getJavaClassName());
			}
		} finally {
			em.close();
		}
	}

	@Test
	void schemaGenerationIsDisabled() {
		Object ddl = emf.getProperties().get("eclipselink.ddl-generation");
		assertTrue(ddl == null || "none".equals(ddl), "eclipselink.ddl-generation must stay 'none'");
	}
}
