package org.emaginalabs.example.springdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * User: jaluque
 * Date: 14/05/13
 * Time: 13:29
 */

@Import({JPAContext.class})
@EnableWebMvc
@ComponentScan({"org.emaginalabs.example.springdata"})
@ImportResource({"classpath*:/META-INF/spring/example-database.xml",
		"classpath*:trace-context.xml",
		"classpath*:exampleApplicationContext-security.xml"})
public class ExampleApplicationContext extends WebMvcConfigurerAdapter {

	private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

	private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
	private static final String VIEW_RESOLVER_SUFFIX = ".jsp";


	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setViewClass(JstlView.class);
		jspViewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
		jspViewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
		return jspViewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}


	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

		properties.setLocation(new ClassPathResource("example-database.properties"));
		properties.setIgnoreResourceNotFound(false);

		return properties;
	}

}
