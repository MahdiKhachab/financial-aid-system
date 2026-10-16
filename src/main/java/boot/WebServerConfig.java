package boot;

import jakarta.servlet.MultipartConfigElement;

import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** web.xml settings that have no Spring Boot property. */
@Configuration
public class WebServerConfig {

	/** Former web.xml welcome-file: the application root shows the home page. */
	static final String WELCOME_FILE = "faces/views/index.xhtml";

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> legacyWebXmlSettings() {
		return factory -> factory.addContextCustomizers(context -> {
			context.addWelcomeFile(WELCOME_FILE);
			context.addMimeMapping("ico", "image/x-icon");
		});
	}

	/** WildFly's default request limit (Undertow max-post-size), applied to PrimeFaces file uploads. */
	static final long MAX_UPLOAD_BYTES = 10L * 1024 * 1024;

	/**
	 * Multipart support for the Faces servlet (p:fileUpload). JoinFaces applies this bean to the
	 * FacesServlet registration; without it uploads are silently ignored.
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		return new MultipartConfigElement("", MAX_UPLOAD_BYTES, MAX_UPLOAD_BYTES, 0);
	}

	/**
	 * Pages reference some files by plain URL (e.g. /resources/images/ajax-loader.gif), which WildFly's
	 * default servlet served. Only /resources/* is served statically, so page sources stay private.
	 */
	@Bean
	public ServletRegistrationBean<DefaultServlet> staticResourcesServlet() {
		ServletRegistrationBean<DefaultServlet> registration = new ServletRegistrationBean<>(new DefaultServlet(),
				"/resources/*");
		registration.addInitParameter("listings", "false");
		registration.setLoadOnStartup(1);
		return registration;
	}
}
