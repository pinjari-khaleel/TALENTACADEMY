package com.talentacademy.core.models.impl;

import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;
import com.talentacademy.core.beans.GoogleRecaptchaConfiguration;
import com.talentacademy.core.configs.CloudServiceConfiguration;
import com.talentacademy.core.models.RecaptchaModel;
import com.talentacademy.core.services.RecaptchaValidationService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class})
public class RecaptchaModelTest {
    private static final String PARAM_G_RECAPTCHA_RESPONSE = "g-recaptcha-response";
    private final AemContext aemContext = new AemContext();
    @InjectMocks
    RecaptchaModelImpl recaptcha;

    @InjectMocks
    GoogleRecaptchaConfiguration googleRecaptchaConfiguration;

    @Mock
    ConfigurationManager configurationManager;

    @Mock
    private Configuration configuration;

    @Mock
    RecaptchaValidationService captchaValidationService;

    Resource resource;

    private static final String[] ALL_SERVICES = new String[] {
            "/etc/cloudservices/google-recaptcha/configuration",
    };

    private void registerConfigurationManager(final AemContext aemContext) {
        aemContext.registerAdapter(ResourceResolver.class,
                ConfigurationManager.class, configurationManager);
    }

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(RecaptchaModel.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/GoogleRecaptcha.json", "/etc/cloudservices");
        resource = aemContext.resourceResolver().getResource( "/etc/cloudservices/google-recaptcha/jcr:content");
        recaptcha = resource.adaptTo(RecaptchaModelImpl.class);
        aemContext.registerService(RecaptchaValidationService.class, captchaValidationService);
        registerConfigurationManager(aemContext);
    }


    private void registerConfiguration(final AemContext aemContext) {
        GoogleRecaptchaConfiguration googleRecaptchaConfiguration = new GoogleRecaptchaConfiguration();
        when(configurationManager.getConfiguration(
                CloudServiceConfiguration.GOOGLE_RECAPTCHA.getCode(), ALL_SERVICES))
                .thenReturn(configuration);

    }

    /**
     * Test method for
     * {@link RecaptchaModelImpl#getReCaptchaSecretKey()}.
     */
    @Test
    void testGetReCaptchaSecretKey() {
        assertEquals("6LeEwEopAAAAAAGeBTHfxKfvlyDRBoR7MjW3hr7L", recaptcha.getReCaptchaSecretKey());
    }

    /**
     * Test method for
     * {@link RecaptchaModelImpl#getReCaptchaSiteKey()}.
     */
    @Test
    void testGetReCaptchaSiteKey() {
        assertEquals("6LeEwEopAAAAAMgt4jJOVEifgA1sh1xL3spqnx-K", recaptcha.getReCaptchaSiteKey());
    }

}
