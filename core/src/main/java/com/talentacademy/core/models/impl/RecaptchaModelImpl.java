package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.GoogleRecaptchaConfiguration;
import com.talentacademy.core.configs.CloudServiceConfiguration;
import com.talentacademy.core.models.RecaptchaModel;
import com.talentacademy.core.services.impl.CloudConfigServiceImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, adapters = RecaptchaModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RecaptchaModelImpl implements RecaptchaModel {

    private static final Logger log = LoggerFactory.getLogger(RecaptchaModelImpl.class);

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

    /**
     * Inject self Resource.
     */
    @SlingObject
    private transient Resource currentResource;

    @PostConstruct
    protected void init() {
        try {
            reCaptchaSiteKey = Optional.of(new CloudConfigServiceImpl(currentResource)).
                    map(services -> services.getConfiguration(CloudServiceConfiguration.GOOGLE_RECAPTCHA)).
                    map(configuration -> ((GoogleRecaptchaConfiguration) configuration).getReCaptchaSiteKey()).orElse(null);
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error("Unable to get recaptcha site key: ", e);
        }

    }

    /**
     * @return reCaptchaSiteKey
     */
    public String getReCaptchaSiteKey() {
        return reCaptchaSiteKey;
    }

    /**
     * @return reCaptchaSecretKey
     */
    public String getReCaptchaSecretKey() {
        return reCaptchaSecretKey;
    }
}
