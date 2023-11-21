package devxp-tech.apptemplate.config;

import devxp-tech.bootstrap.security.AbstractSecurityConfig;
import devxp-tech.bootstrap.security.AuthenticationService;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Collections;
import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends AbstractSecurityConfig {

    public SecurityConfig(AuthenticationService authenticationService) {
        super(authenticationService);
    }

    @Override
    protected List<String> publicRoutes() {
        return Collections.emptyList();
    }
}
