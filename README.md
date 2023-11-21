# java-spring-app-template

Template app for spring boot applications

# Functionalities

A short description of api functionalities

# Diagrams

Link to diagramas in the context of this API
<https://github.com/devxp-tech-group/diagrams/tree/main/>

```
You could add images, just copying these tag with correct links
![Image Alt](https://github.com/devxp-tech-group/diagrams/blob/main/xxxxx/xxxx.png "Image Alt")
```

# Hosts

- Local: <http://localhost:8080/api>
- QA Private: <https://ms-template.priv.qa.devxp-tech.local/api>
- QA Public: <https://ms-template.pub.qa.devxp-tech.io/api>
- Live Private: <https://ms-template.priv.live.devxp-tech.local/api>
- Live Public: <https://ms-template.pub.live.devxp-tech.io/api>

# Documentation

Open API docs

- Local: <http://localhost:8080/api/swagger-ui.html>
- QA: <https://ms-template.priv.qa.devxp-tech.local/swagger-ui.html>
- Live: <https://ms-template.priv.live.devxp-tech.local/swagger-ui.html>

# Setup

- Download Intellij: <https://www.jetbrains.com/idea/download/>
- Enable plugins of Spring/SpringBoot if available
- Setup JDK (openjdk11-openj9)
- Enable "EditorConfig" in -> Preferences | Editor | Code Style
- If it's needed, setup your nexus credentials with script 'setup-nexus-credentials.sh'
- Enjoy!

# Common commands

```
Build: ./gradlew build
Docker Build: docker build -t app-template .
Run docker container: docker run -it --rm app-template
Run docker compose: docker-compose up
Run docker compose only database: docker-compose up postgres
Check code style: eclint check "src/**" (Need to install 'npm install -g eclint')
```

# USING TEMPLATE

Steps to use this template:

- Create a new repository with a template
- Rename 'apptemplate" package
- Read all config steps and apply modifications *** Don't skip any step!
- Delete unused classes, if you dont need a example
- Rename app name in application.yml -> app.name
- Uncomment code in .circleci/config.yml
- Rename docker image (ms-template) in .circleci/config.yml
- Rename ingress, IAMRole and overlay name in deploy/charts (QA/Live)
- If all build tests are OK, remove this section of readme and start coding :)

## Configuration

Initial configs to use this template, take a time to read everything!

### Log

```
All default log configs are stored in src/main/resources/logback.xml
Log json template could be edited in bootstrap repository
```

### HealthCheck

```
Enabling: automatically enabled. To add a new health-check, add a @Service that implements HealthService.java
See GoogleHealthCheckService.java for example

Disabling: Health Check cannot be disabled
```

### Security

```
Enabling: Just keep the class SecurityConfig.java, and remember to add the following code on your controller methods
@Unauthorized
@SecurityRequirement(name = "bearerAuth")

Disabling: Delete class SecurityConfig.java, and remove these fields in application.yml
    jwt-secret: ${JWT_SECRET:secret}
    jwt-expiration-time-minutes: ${JWT_EXPIRATION_TIME_MINUTES:30}
    api-user-name: ${API_USER:user}
    api-pass: ${API_PASSWORD:password}

Remember to remove @Unauthorized and @SecurityRequirement annotations in controller methods too
Remove lib     implementation 'org.springframework.boot:spring-boot-starter-security'
```

### Messaging

```
Enabling: Edit these configs to application.yaml and create your @JmsListener

aws:
  sqs:
    max-tries: ${AWS_SQS_MAX_TRIES:5}
    local-endpoint: ${AWS_SQS_ENDPOINT:http://localhost:9324}
  region: ${AWS_REGION:us-east-1}

Disabling: Remove above code in application.yml

Remove this code in .circleci/config.yml

      - run:
          name: Run SQS
          command: |
            wget https://s3-eu-west-1.amazonaws.com/softwaremill-public/elasticmq-server-0.14.6.jar
            java -Dconfig.file=elasticmq.conf -jar elasticmq-server-0.14.6.jar
          background: true

Remove sqs container in docker-compose.yaml

  sqs:
    image: s12v/elasticmq
    ports:
      - "9324:9324"
    volumes:
      - ./elasticmq.conf:/etc/elasticmq/elasticmq.conf

Delete elasticmq.conf in root folder
Remove libs:
    implementation 'org.springframework:spring-jms'
    implementation 'com.amazonaws:aws-java-sdk:1.11.898'
    implementation 'com.amazonaws:amazon-sqs-java-messaging-lib:1.0.8'
```

### Database

```
Enabling: Just edit these fields in application.yml

datasource:
  name: ${DB_NAME:db_template}
  host: ${DB_HOST:localhost:5432}
  username: ${DB_USER:root}
  password: ${DB_PASSWORD:root}


Disabling: Remove above code in application.yml, and add these exclusions to Application.java

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        LiquibaseAutoConfiguration.class
})

Delete liquibase folder and hibernate.properties in src/main/resources/
Delete database container in docker-composer.yaml
Delete the following code in .circleci/config.yml

      - image: circleci/postgres:12.0-alpine-ram
        environment:
          POSTGRES_USER: root
          POSTGRES_PASSWORD: root
          POSTGRES_DB: db_template

Remove libs:
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
```

### Cache

```
Enabling: Add the annotation @EnableCaching to Application.java
Disabling: Remove libs:
    implementation 'org.springframework.boot:spring-boot-starter-cache'
```

### Scheduling

```
Enabling: Add the annotation @EnableScheduling to Application.java
```

### Mocking (for tests or local environment)

```
Enabling: Add new mock files in mock/mappgins
You could read the docs here http://wiremock.org/docs/

Disabling: Remove the following code in docker-compose.yaml

  mock:
    image: rodolpheche/wiremock:2.26.3-alpine
    ports:
    - "5000:8080"
    - "8443:8443"
    volumes:
    - ${PWD}/mock:/home/wiremock

Delete mock folder
Remove the following code in .circleci/config.yml

      - run:
          name: Start wiremock
          command: |
            wget https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/2.26.3/wiremock-standalone-2.26.3.jar
            cp wiremock-standalone-2.26.3.jar mock
            cd mock
            java -jar wiremock-standalone-2.26.3.jar --port 5000
          background: true
```

### Testing

```
Controller tests: Look at UserControllerTest.java for examples
Good to know:
- Unit test is only needed for complex operations
- Spring initialize a fake server before all tests
- All repositories and cache are cleared before/after tests
- Its possible to mock services and other classes (see ms-cobranded for more samples)
```

### Build & Review

```
Make sure of theeses steps:
- Granted access to all devxp-tech developers in repository -> settings -> manage access -> add grou developers
- Have branch protetion rules in  repository -> settings -> branches (Require pull request views, status check before merging)
- Leave only "allow squash merging" in  repository -> settings -> options -> merge button
- Setup project in circleci https://app.circleci.com/projects/project-dashboard/github/devxp-tech-group/
- Added a SSH key in circleci project -> settings -> ssh keys
- Created a token in in circleci project -> settings -> API permissions
- Use token and correct URL in circleci badge in the beginning of this readme
- Have all environment variable configured in circleci project -> settings -> API environment variables
- Have all K8s resources created and applied in https://github.com/devxp-tech-group/ops (You could use another app as example)
- If is needed, create or ask a DBA to create RDS files in ops
- If is needed, create SQS, SNS, S3 terraform files in ops
- Create a elasticsearch index in ops
- Created ticket and ask SRE team to create a k8s namespace, and helm for QA/Live
- In case of live environemnt, create a ticket and ask sre team to encrypt live environments variablies
- Add index in graylog/kibana qa/live
- Add a new panel in Grafana for metrics and resources monitoring
- Create a ticket for personal and read-only access to databases ou AWS resources

After all theses steps:
- Test private and public ingress routes
- Test Grafana panels
- Test search for something in kibana
- Test pods access with kubelogin
- Test personal access for database and aws resources
```

# Deploy

Currently, there are two delivery models:

- Using CircleCI pipelines (legacy):
  - Configure a `Build and Deploy` step defined in `.circleci/config.yml`
  - Create following files on `deploy/chart/` folder:
    - QA values: `qa-values.yaml`
    - Live values: `live-values.yaml`
    - Secrets file: `qa-secrets.yaml`
- Using ArgoCD (new):
  - Create [ArgoCD](https://github.com/devxp-tech-group/argo/) configuration files for `service-name.yaml` (replace `service-name` with your project name):
    - QA: `clusters/eks-qa-devxp-tech-latam/apps/service-name.yaml`
    - Live: `clusters/eks-live-devxp-tech-latam/apps/service-name.yaml`
  - Create a folder with the `service-name` under the [Charts](https://github.com/devxp-tech-group/charts) project with following files:
    - Main chart file: `Chart.yaml`
    - QA values: `qa-values.yaml`
    - Live values: `values.yaml`
    - Requirements file: `requirements.yaml`
  - Add your `service-name` to filebeat config files on [OPS](https://github.com/devxp-tech-group/ops/) project:
    - QA: `accounts/devxp-tech-dev/678591175058/envs/qa/us-east-1/10-clusters/qa.k8s.devxp-tech.local/manifests/10-logging/10-filebeat.yaml`
    - Live: `accounts/gfg-live/556684128444/envs/live/us-east-1/10-clusters/live.k8s.devxp-tech.local/manifests/10-logging/10-filebeat.yaml`
