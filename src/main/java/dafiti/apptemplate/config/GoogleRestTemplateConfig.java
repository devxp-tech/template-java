package devxp-tech.apptemplate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import devxp-tech.bootstrap.config.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GoogleRestTemplateConfig {

    @Value("${app.external.google.host}")
    private String googleHost;

    @Bean
    @Qualifier("google")
    public RestTemplate googleRestTemplate(RestTemplateBuilder builder, ObjectMapper jacksonMapper) {
        return builder.messageConverters(new MappingJackson2HttpMessageConverter(jacksonMapper))
                .errorHandler(new RestTemplateErrorHandler())
                .rootUri(googleHost)
                .build();
    }
}
