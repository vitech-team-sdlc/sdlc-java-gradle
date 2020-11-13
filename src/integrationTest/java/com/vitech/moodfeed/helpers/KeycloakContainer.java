package com.vitech.moodfeed.helpers;

import lombok.EqualsAndHashCode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

import java.time.Duration;

@EqualsAndHashCode(callSuper = true)
public class KeycloakContainer extends GenericContainer<KeycloakContainer> {

    private static final String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak";
    private static final String KEYCLOAK_VERSION = "11.0.2";

    private static final int KEYCLOAK_PORT = 8080;

    private static final String KEYCLOAK_ADMIN_USER = "admin";
    private static final String KEYCLOAK_ADMIN_PASSWORD = "admin";

    private String importFile;

    public KeycloakContainer() {
        this(KEYCLOAK_IMAGE + ":" + KEYCLOAK_VERSION);
    }

    public KeycloakContainer(String dockerImageName) {
        super(dockerImageName);
        setWaitStrategy(Wait
                .forHttp("/auth")
                .forPort(KEYCLOAK_PORT)
                .withStartupTimeout(Duration.ofMinutes(2))
        );
    }

    @Override
    protected void configure() {
        withCommand(
                "-c standalone.xml", // don't start infinispan cluster
                "-b 0.0.0.0", // ensure proper binding
                "-Dkeycloak.profile.feature.upload_scripts=enabled" // enable script uploads
        );

        withEnv("KEYCLOAK_USER", KEYCLOAK_ADMIN_USER);
        withEnv("KEYCLOAK_PASSWORD", KEYCLOAK_ADMIN_PASSWORD);

        if (importFile != null) {
            String importFileInContainer = "/tmp/" + importFile;
            withCopyFileToContainer(MountableFile.forClasspathResource(importFile), importFileInContainer);
            withEnv("KEYCLOAK_IMPORT", importFileInContainer);
        }
    }

    public KeycloakContainer withRealmImportFile(String importFile) {
        this.importFile = importFile;
        return self();
    }

    public String getServerUrl() {
        return String.format("http://%s:%s/auth",
                getContainerIpAddress(),
                getMappedPort(KEYCLOAK_PORT));
    }

    public String getAuthServerUrl(String realm) {
        return String.format("%s/realms/%s/protocol/openid-connect/certs", getServerUrl(), realm);
    }

}
