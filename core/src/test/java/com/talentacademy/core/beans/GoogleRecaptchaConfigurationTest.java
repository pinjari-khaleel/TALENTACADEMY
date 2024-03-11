package com.talentacademy.core.beans;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ AemContextExtension.class})
public class GoogleRecaptchaConfigurationTest {
    private final AemContext aemContext = new AemContext();
    @InjectMocks
    GoogleRecaptchaConfiguration recaptchaConfiguration;

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(GoogleRecaptchaConfiguration.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/GoogleRecaptcha.json", "/content");
        Resource resource = aemContext.resourceResolver().getResource("/content/google-recaptcha/jcr:content");
        recaptchaConfiguration = resource.adaptTo(GoogleRecaptchaConfiguration.class);
    }

    /**
     * Test method for
     * {@link GoogleRecaptchaConfiguration#getReCaptchaSecretKey()}.
     */
    @Test
    void testGetReCaptchaSecretKey() {
        assertEquals("6LeEwEopAAAAAAGeBTHfxKfvlyDRBoR7MjW3hr7L", recaptchaConfiguration.getReCaptchaSecretKey());
    }

    /**
     * Test method for
     * {@link GoogleRecaptchaConfiguration#getReCaptchaSiteKey()}.
     */
    @Test
    void testGetReCaptchaSiteKey() {
        assertEquals("6LeEwEopAAAAAMgt4jJOVEifgA1sh1xL3spqnx-K", recaptchaConfiguration.getReCaptchaSiteKey());
    }
}
