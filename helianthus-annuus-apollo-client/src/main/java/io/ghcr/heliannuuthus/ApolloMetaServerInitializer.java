package io.ghcr.heliannuuthus;

import com.ctrip.framework.apollo.core.ApolloClientSystemConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class ApolloMetaServerInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {
    public static final int ORDER = -1;
    private static final String SCHEMA = "config";
    private static final String SPRING_APPLICATION_NAME_KEY = "spring.application.name";
    private static final String DEFAULT_META_SERVER = "http://{0}.heliannuuthus.org";
    private static final String DEFAULT_ENV = "dev";
    private static final String PROD_ENV = "prod";
    private static final String MIDDLE_LINE = "-";

    @Override
    public int getOrder() {
        return ORDER;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Optional.ofNullable(environment.getProperty(SPRING_APPLICATION_NAME_KEY))
                .ifPresent(
                        applicationName ->
                                System.setProperty(ApolloClientSystemConsts.APP_ID, applicationName));
        String env =
                Optional.of(environment.getActiveProfiles())
                        .flatMap(profiles -> Arrays.stream(profiles).findFirst())
                        .orElse(DEFAULT_ENV);
        System.setProperty("env", env);
        System.setProperty(
                ApolloClientSystemConsts.APOLLO_META,
                MessageFormat.format(
                        DEFAULT_META_SERVER, PROD_ENV.equals(env) ? SCHEMA : SCHEMA + MIDDLE_LINE + env));
    }
}
