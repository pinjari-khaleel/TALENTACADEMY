package com.talentacademy.core.services.impl;

import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.models.impl.RecaptchaModelImpl;
import com.talentacademy.core.utils.RestApiHttpClientUtil;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({AemContextExtension.class})
class RecaptchaValidationServiceImplTest {
    RecaptchaValidationServiceImpl service;
    private final AemContext aemContext = new AemContext();
    @InjectMocks
    RecaptchaModelImpl recaptcha;
    @BeforeEach
    void setUp() {
        service = new RecaptchaValidationServiceImpl();
        aemContext.load().json("/com/talentacademy/core/models/impl/GoogleRecaptcha.json", "/content");
        Resource resource = aemContext.resourceResolver().getResource("/content/google-recaptcha/jcr:content");
        recaptcha = resource.adaptTo(RecaptchaModelImpl.class);
    }

    /**
     * Test method for
     * {@link RecaptchaValidationServiceImpl#validate(String, String)} )}.
     */
    @Test
    void testValidateSuccessResponse() {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String arg = "{\"success\": true}";
        try (MockedStatic<RestApiHttpClientUtil> mockedRestApiHttpClientUtil = Mockito.mockStatic(RestApiHttpClientUtil.class)) {
            mockedRestApiHttpClientUtil.when(() -> RestApiHttpClientUtil.doUrlEncodedPostCall(url,
                            Arrays.asList(new BasicNameValuePair(ApplicationConstants.SECRET_PARAMETER, recaptcha.getReCaptchaSecretKey()),
                                    new BasicNameValuePair(ApplicationConstants.RESPONSE_PARAMETER, recaptcha.getReCaptchaSiteKey())
                            )))
                    .thenReturn(arg);

            assertTrue(service.validate(recaptcha.getReCaptchaSecretKey(), recaptcha.getReCaptchaSiteKey()));
        }
    }

    /**
     * Test method for
     * {@link RecaptchaValidationServiceImpl#validate(String, String)} )}.
     */
    @Test
    void testValidateWithOutSuccessInResponse() {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String arg = "{\"test\": false}";
        try (MockedStatic<RestApiHttpClientUtil> mockedRestApiHttpClientUtil = Mockito.mockStatic(RestApiHttpClientUtil.class)) {
            mockedRestApiHttpClientUtil.when(() -> RestApiHttpClientUtil.doUrlEncodedPostCall(url,
                            Arrays.asList(new BasicNameValuePair(ApplicationConstants.SECRET_PARAMETER, recaptcha.getReCaptchaSecretKey()),
                                    new BasicNameValuePair(ApplicationConstants.RESPONSE_PARAMETER, recaptcha.getReCaptchaSiteKey())
                            )))
                    .thenReturn(arg);

            assertFalse(service.validate(recaptcha.getReCaptchaSecretKey(), recaptcha.getReCaptchaSiteKey()));
        }
    }

    /**
     * Test method for
     * {@link RecaptchaValidationServiceImpl#validate(String, String)} )}.
     */
    @Test
    void testValidateEmptyRecaptcha() {
        Assert.assertNotNull(service.validate(recaptcha.getReCaptchaSecretKey(), ""));
    }
}
