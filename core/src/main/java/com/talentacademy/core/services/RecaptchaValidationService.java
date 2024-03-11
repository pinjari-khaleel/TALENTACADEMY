package com.talentacademy.core.services;

public interface RecaptchaValidationService {
    /**
     * Main validation method exposed for 3rd party service invocations.
     *
     * @param reCaptcha         g-recaptcha-response property
     * @param reCaptchaSecret       reCaptchaSecret key
     * @return validation result
     */
    boolean validate(String reCaptchaSecret, String reCaptcha);
}
