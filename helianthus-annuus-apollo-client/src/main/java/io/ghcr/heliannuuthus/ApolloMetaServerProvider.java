package io.ghcr.heliannuuthus;

import com.ctrip.framework.apollo.core.enums.Env;
import com.ctrip.framework.apollo.core.spi.MetaServerProvider;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.text.MessageFormat;

public class ApolloMetaServerProvider implements MetaServerProvider, EnvironmentAware {
    public static final int ORDER = -1;

    private final String metaServerAddress;
    private Environment ENV = null;

    private static final String DEFAULT_META_SERVER = "http://{0}.heliannuuthus.com";

    public ApolloMetaServerProvider() {
        metaServerAddress = initMetaServerAddress();
    }

    private String initMetaServerAddress() {
        String schema = "apollo";
        String[] activeProfiles = ENV.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return MessageFormat.format(DEFAULT_META_SERVER, "prod".equals(activeProfiles[0]) ? schema + "-" + activeProfiles[0] : schema);
        }
        return MessageFormat.format(DEFAULT_META_SERVER, "dev");
        //for default meta server provider, we don't care the actual environment
    }

    @Override
    public String getMetaServerAddress(Env targetEnv) {
        return metaServerAddress;
    }

    @Override
    public int getOrder() {
        return ORDER;
    }


    @Override
    public void setEnvironment(Environment environment) {
        ENV = environment;
    }
}