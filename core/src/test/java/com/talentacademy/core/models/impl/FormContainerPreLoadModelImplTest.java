package com.talentacademy.core.models.impl;

import com.talentacademy.core.constants.ApplicationConstants;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.*;

@ExtendWith({ AemContextExtension.class})
class FormContainerPreLoadModelImplTest {

    private final String ROOT_PATH = "/content/talentacademy/us/en/test";

    private final AemContext aemContext = new AemContext();

    private FormContainerPreLoadModelImpl formContainerPreLoadModel;

    /**
     * Setup method for {com.talentacademy.core.models.FormContainerPreLoadModelImpl}
     */
    @BeforeEach
    public void setup() {
        aemContext.addModelsForClasses(FormContainerPreLoadModelImpl.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/FormContainerPreLoadModelImpl.json", ROOT_PATH);
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/basicFormContainerDefault");
        aemContext.request().setResource(resource);
    }

    /**
     * Test method for
     * {@link FormContainerPreLoadModelImpl#isSameConfirmationPage()}.
     */
    @Test
    void isSameConfirmationPageWithNoParam(){
        Map<String, Object> params = new HashMap<>();
        params.put("formKey","formValue");

        aemContext.request().setParameterMap(params);

        formContainerPreLoadModel = aemContext.request().adaptTo(FormContainerPreLoadModelImpl.class);

        Boolean isSamePage = formContainerPreLoadModel.isSameConfirmationPage();

        assertNull(isSamePage);
    }

    /**
     * Test method for
     * {@link FormContainerPreLoadModelImpl#isSameConfirmationPage()}.
     */
    @Test
    void isSameConfirmationPageWithParam(){
        aemContext.request().setAttribute(ApplicationConstants.NEOM_FORM_SERVLET_REDIRECT_SAMEPAGE_PARAM,"isSamePage");

        formContainerPreLoadModel = aemContext.request().adaptTo(FormContainerPreLoadModelImpl.class);

        Boolean isSamePage = formContainerPreLoadModel.isSameConfirmationPage();

        assertTrue(isSamePage);
    }

    /**
     * Test method for
     * {@link FormContainerPreLoadModelImpl#isSuccessfulPageSubmission()}.
     */
    @Test
    void isSuccessfulPageSubmission(){
        aemContext.request().setAttribute(ApplicationConstants.NEOM_FORM_SERVLET_STATE_FLAG_PARAM,"true");

        formContainerPreLoadModel = aemContext.request().adaptTo(FormContainerPreLoadModelImpl.class);

        Boolean isSuccessfulSubmission = formContainerPreLoadModel.isSuccessfulPageSubmission();

        assertTrue(isSuccessfulSubmission);
    }

    /**
     * Test method for
     * {@link FormContainerPreLoadModelImpl#getFormActionPath()} .
     */
    @Test
    void testGetFormActionPath(){
        formContainerPreLoadModel = aemContext.request().adaptTo(FormContainerPreLoadModelImpl.class);

        String actionFormPat = formContainerPreLoadModel.getFormActionPath();

        assertNotNull(actionFormPat);
        assertEquals(ROOT_PATH + "/basicFormContainerDefault",actionFormPat);
    }

    /**
     * Test method for
     * {@link FormContainerPreLoadModelImpl#getEmail()}.
     */
    @Test
    void fetchEmail(){
        aemContext.request().setAttribute(ApplicationConstants.NEOM_FORM_SERVLET_EMAIL_PARAM,"test@mail.com");

        formContainerPreLoadModel = aemContext.request().adaptTo(FormContainerPreLoadModelImpl.class);

        assertEquals("test@mail.com", formContainerPreLoadModel.getEmail());
    }

    /**
     * Test method for
     * {@link FormContainerPreLoadModelImpl#getEmail()}.
     */
    @Test
    void fetchRecaptchaToken(){
        aemContext.request().setAttribute(ApplicationConstants.NEOM_FORM_SERVLET_RECAPTCH_PARAM,"cvt236axk");

        formContainerPreLoadModel = aemContext.request().adaptTo(FormContainerPreLoadModelImpl.class);

        assertEquals("cvt236axk", formContainerPreLoadModel.getRecaptchaToken());
    }
}
