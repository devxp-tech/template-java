package devxp-tech.apptemplate.service;

import devxp-tech.apptemplate.model.GoogleHealthRES;
import devxp-tech.bootstrap.healthcheck.HealthService;
import devxp-tech.bootstrap.healthcheck.model.HealthStatus;
import devxp-tech.bootstrap.healthcheck.model.HealthStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class GoogleHealthCheckService implements HealthService {

    private static final Logger log = LoggerFactory.getLogger(GoogleHealthCheckService.class);
    private static final String SERVICE_NAME = "google";
    private final RestTemplate restTemplate;

    @Autowired
    public GoogleHealthCheckService(@Qualifier("google") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HealthStatus ok() {
        return new HealthStatus(SERVICE_NAME, HealthStatusEnum.OK);
    }

    private HealthStatus nok() {
        return new HealthStatus(SERVICE_NAME, HealthStatusEnum.UNAVAILABLE);
    }

    @Override
    public HealthStatus execute() {
        try {
            GoogleHealthRES response = restTemplate.getForObject("/health-check", GoogleHealthRES.class);

            return Optional.ofNullable(response)
                    .map(GoogleHealthRES::getStatus)
                    .map(st -> st.equals("OK") ? ok() : nok())
                    .orElse(nok());
        } catch (Exception e) {
            log.error("error while executing google health check", e);
            return nok().addDetail("fetch_error", e.getMessage());
        }
    }
}
