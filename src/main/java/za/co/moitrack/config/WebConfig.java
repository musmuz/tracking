package za.co.moitrack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by nguni52 on 2017/03/24.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200", "http://moitrack.co.za", "http://moitrack.co.za:4200")
                .allowedMethods("POST", "GET",  "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("X-Auth-Token", "Content-Type")
                .allowCredentials(false)
                .maxAge(4800);
    }
}