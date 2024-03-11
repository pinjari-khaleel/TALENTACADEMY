package com.talentacademy.core.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Optional;

@Model(adaptables = Resource.class)
public class GoogleRecaptchaConfiguration {
    /**
     * reCaptchaSiteKey.
     */
    @ValueMapValue
    private String reCaptchaSiteKey;

    /**
     * reCaptchaSecreteKey.
     */
    @ValueMapValue
    private String reCaptchaSecretKey;

    public String getReCaptchaSiteKey() {
        return reCaptchaSiteKey;
    }

    public String getReCaptchaSecretKey() {
        return reCaptchaSecretKey;
    }
}
