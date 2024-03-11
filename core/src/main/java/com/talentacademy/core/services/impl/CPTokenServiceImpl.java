package com.talentacademy.core.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.talentacademy.core.beans.AlmConfigurationBean;
import com.talentacademy.core.services.ALMAuthenticationService;
import com.talentacademy.core.services.CPTokenService;
import com.talentacademy.core.utils.RestApiHttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.talentacademy.core.constants.ApplicationConstants.CLIENT_ID;
import static com.talentacademy.core.constants.ApplicationConstants.CLIENT_SECRET;
import static com.talentacademy.core.constants.ApplicationConstants.REFRESH_TOKEN;
import static com.talentacademy.core.constants.ApplicationConstants.ACCESS_TOKEN;

@Component(service = CPTokenService.class, immediate = true)
public class CPTokenServiceImpl implements CPTokenService {

    @Reference
    private ALMAuthenticationService almAuthenticationService;

    /** The expiresTime. */
    private Date expiresTime;

    /** The accessToken. */
    private String accessToken;

    @Activate
    public void activate() {
        generateAccessToken();
    }


    /**
     * Calls the Access Token API
     *
     */
    private void generateAccessToken() {
        AlmConfigurationBean almConfigurationBean = almAuthenticationService.getAlmConfigurationBean();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(CLIENT_ID, almConfigurationBean.getClientId()));
        params.add(new BasicNameValuePair(CLIENT_SECRET, almConfigurationBean.getClientSecret()));
        params.add(new BasicNameValuePair(REFRESH_TOKEN, almConfigurationBean.getRefreshToken()));
        String tokenResponse = RestApiHttpClientUtil.doUrlEncodedPostCall(almConfigurationBean.getAccessTokenUrl(), params);

        JsonObject jsonObject = new Gson().fromJson(tokenResponse, JsonObject.class);
        extractAccessTokenAndExpireTime(jsonObject);
    }

    /**
     * Extract the access token and expire time.
     *
     * @param jsonObject
     */
    private void extractAccessTokenAndExpireTime(JsonObject jsonObject) {
        if (jsonObject != null && jsonObject.has("expires_in")) {
            int expiresIn = jsonObject.get("expires_in").getAsInt();
            Calendar currentTime = Calendar.getInstance();
            currentTime.add(Calendar.SECOND, expiresIn - 60);
            expiresTime = currentTime.getTime();
        }
        if (jsonObject != null && jsonObject.has(ACCESS_TOKEN)) {
            setAccessToken(jsonObject.get(ACCESS_TOKEN).getAsString());
        }
    }

    /**
     * returns the Valid Access Token from the Initial Generated Token
     *
     * @param accessToken ALM Access Token
     * @return Access Token
     */
    @Override
    public String getAccessTokenFromCode(String accessToken) {
        if (StringUtils.isEmpty(accessToken)
                || (expiresTime != null && Calendar.getInstance().getTime().after(expiresTime))
                || expiresTime == null) {
            generateAccessToken();
            accessToken = getAccessToken();
        }
        return accessToken;
    }

    /**
     * Gets the Access Token.
     *
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the Access Token.
     *
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
