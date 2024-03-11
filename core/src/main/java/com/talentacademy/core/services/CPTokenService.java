package com.talentacademy.core.services;

/**
 * A service to fetch the access token from ALM
 */
public interface CPTokenService {

    /**
     * returns the Valid Access Token from the Initial Generated Token
     *
     * @param code ALM Access Token
     * @return Access Token
     */
    String getAccessTokenFromCode(String code);

    /**
     * returns the Valid Access Token
     *
     * @return Access Token
     */
    String getAccessToken();
}