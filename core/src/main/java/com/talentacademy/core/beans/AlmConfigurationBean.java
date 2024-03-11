package com.talentacademy.core.beans;

/**
 * Bean class with all ALM properties from config file
 */
public class AlmConfigurationBean {

    private String clientId;

    private String clientSecret;

    private String almBaseUrl;

    private String refreshToken;

    private String accessTokenUrl;

    private String prefix;

    private String version;

    private String learningObjectsApi;

    /**
     * @return The ALM Client ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId set the ALM Client ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return The ALM Client Secret ID
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * @param clientSecret set the ALM Client Secret ID
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * @return The ALM Base URL
     */
    public String getAlmBaseUrl() {
        return almBaseUrl;
    }

    /**
     * @param almBaseUrl set the ALM Base URL
     */
    public void setAlmBaseUrl(String almBaseUrl) {
        this.almBaseUrl = almBaseUrl;
    }

    /**
     * @return The ALM Refresh Token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken set the ALM Refresh Token
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return The ALM Access Token URL
     */
    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    /**
     * @param accessTokenUrl set the ALM Access Token URL
     */
    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    /**
     * @return The ALM Prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix set the ALM Prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return The ALM Version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version set the ALM Version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return The ALM Learning Objects API
     */
    public String getLearningObjectsApi() {
        return learningObjectsApi;
    }

    /**
     * @param learningObjectsApi set the ALM Learning Objects API
     */
    public void setLearningObjectsApi(String learningObjectsApi) {
        this.learningObjectsApi = learningObjectsApi;
    }
}