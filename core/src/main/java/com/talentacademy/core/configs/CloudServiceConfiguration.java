package com.talentacademy.core.configs;

import com.talentacademy.core.beans.GoogleRecaptchaConfiguration;
import com.talentacademy.core.beans.FormConnectionConfiguration;

public enum CloudServiceConfiguration {
    /**
     * Google recaptcha configuration.
     */
    GOOGLE_RECAPTCHA("google-recaptcha", GoogleRecaptchaConfiguration.class),

    /**
     * Media Monks configuration.
     */
    FORM_COLLECTOR("form-collector", FormConnectionConfiguration.class);

    /**
     * Configuration identifier (service name).
     */
    private String code;

    /**
     * Model that the resource should be adapted to.
     */
    private Class<?> modelClass;

    /**
     * Constructor.
     * @param code code
     * @param modelClass model class
     */
    CloudServiceConfiguration(String code, Class<?> modelClass) {
        this.code = code;
        this.modelClass = modelClass;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return modelClass
     */
    public Class<?> getModelClass() {
        return modelClass;
    }
}
