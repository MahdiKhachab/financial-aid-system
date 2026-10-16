package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the executable JAR (replaces the WildFly WAR deployment).
 * Only the application's own bean packages are scanned; entities and utilities are not beans.
 */
@SpringBootApplication(scanBasePackages = { "boot", "org.controller", "org.facade", "org.singlon", "cfg.controller",
		"cfg.facade" })
public class ShariaFundsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShariaFundsApplication.class, args);
	}
}
