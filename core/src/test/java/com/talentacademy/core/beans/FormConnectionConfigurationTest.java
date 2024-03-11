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
class FormConnectionConfigurationTest {
    private final AemContext aemContext = new AemContext();
    @InjectMocks
    FormConnectionConfiguration formConnectionConfiguration;

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(FormConnectionConfiguration.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/MediaMonks.json", "/content");
        Resource resource = aemContext.resourceResolver().getResource("/content/form-collector/jcr:content");
        formConnectionConfiguration = resource.adaptTo(FormConnectionConfiguration.class);
    }

    /**
     * Test method for
     * {@link FormConnectionConfiguration#getUrl()} .
     */
    @Test
    void testGetReCaptchaSecretKey() {
        assertEquals("test/forms/submission", formConnectionConfiguration.getUrl());
    }

    /**
     * Test method for
     * {@link FormConnectionConfiguration#getApiKey()} .
     */
    @Test
    void testGetReCaptchaSiteKey() {
        assertEquals("testKey", formConnectionConfiguration.getApiKey());
    }
}
