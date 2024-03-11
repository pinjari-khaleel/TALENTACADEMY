package com.talentacademy.core.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The Interface ALMAuthenticationConfiguration.
 */
@ObjectClassDefinition(name = "ALM Configuration", description = "ALM Configuration")
public @interface ALMAuthenticationConfiguration {

    /**
     * Gets the ALM Client Id.
     *
     * @return the ALM Client Id
     */
    @AttributeDefinition(name = "ALM Client Id", description = "ALM Client Id")
    String clientId();

    /**
     * Gets the ALM Client Secret.
     *
     * @return the ALM Client Secret
     */
    @AttributeDefinition(name = "ALM Client Secret", description = "ALM Client Secret")
    String clientSecret();

    /**
     * Gets the ALM Refresh Token.
     *
     * @return the ALM Refresh Token
     */
    @AttributeDefinition(name = "ALM Refresh Token", description = "ALM Refresh Token")
    String refreshToken();

    /**
     * Gets the ALM Access Token URL.
     *
     * @return the ALM Access Token URL
     */
    @AttributeDefinition(name = "ALM Access Token URL", description = "ALM Access Token URL")
    String accessTokenUrl();

    /**
     * Gets the ALM Base URL.
     *
     * @return the ALM Base URL
     */
    @AttributeDefinition(name = "ALM Base Url", description = "ALM Base Url")
    String almBaseUrl();

    /**
     * Gets the ALM Prefix.
     *
     * @return the ALM Prefix
     */
    @AttributeDefinition(name = "ALM Prefix", description = "ALM Prefix")
    String prefix();

    /**
     * Gets the ALM Version.
     *
     * @return the ALM Version
     */
    @AttributeDefinition(name = "ALM Version", description = "ALM Version")
    String version();

    /**
     * Gets the ALM Learning Objects Api.
     *
     * @return the ALM Learning Objects Api
     */
    @AttributeDefinition(name = "ALM Learning Objects Api", description = "ALM Learning Objects Api")
    String learningObjectsApi();
}
