package com.talentacademy.core.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.services.RecaptchaValidationService;
import com.talentacademy.core.utils.RestApiHttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecaptchaValidationServiceImpl implements RecaptchaValidationService {
    private static final Logger log = LoggerFactory.getLogger(RecaptchaValidationServiceImpl.class);
    private static final String URI = "https://www.google.com/recaptcha/api/siteverify";

    /**
     * Main validation method exposed for 3rd party service invocations.
     *
     * @param reCaptchaSecret reCaptchaSecret key
     * @param reCaptcha       g-recaptcha-response property
     * @return validation result
     */
    @Override
    public boolean validate(String reCaptchaSecret, String reCaptcha) {
        if (StringUtils.isEmpty(reCaptcha) || StringUtils.isEmpty(reCaptchaSecret)) {
            log.error("Unable to validate: reCaptcha or currentPagePath are null");
            return false;
        }
        return validateCaptcha(reCaptchaSecret, reCaptcha);
    }

    /**
     * Method responsible for sending request to Google API.
     *
     * @param secretKey       recaptcha secret key
     * @param captchaResponse recaptcha token
     * @return validation result
     */
    private boolean validateCaptcha(String secretKey, String captchaResponse) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(ApplicationConstants.SECRET_PARAMETER, secretKey));
        params.add(new BasicNameValuePair(ApplicationConstants.RESPONSE_PARAMETER, captchaResponse));
        String response =  RestApiHttpClientUtil.doUrlEncodedPostCall(URI, params);
        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        if (jsonObject != null && jsonObject.has("success")) {
            return jsonObject.get("success").getAsBoolean();
        }
        return Boolean.FALSE;
    }
}
