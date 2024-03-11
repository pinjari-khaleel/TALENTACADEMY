package com.talentacademy.core.servlets;

import com.day.cq.wcm.foundation.forms.FormsHandlingServletHelper;
import com.day.cq.wcm.foundation.forms.FormsHelper;
import com.day.cq.wcm.foundation.forms.ValidationInfo;
import com.day.cq.wcm.webservicesupport.Configuration;
import com.day.cq.wcm.webservicesupport.ConfigurationManager;
import com.google.gson.JsonObject;
import com.talentacademy.core.beans.GoogleRecaptchaConfiguration;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.services.FormMessageService;
import com.talentacademy.core.services.RecaptchaValidationService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestDispatcherOptions;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockRequestDispatcherFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.talentacademy.core.constants.ApplicationConstants.*;
import static junit.framework.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class FormServletTest {

    private static final String REDIRECT_SUCCESSFUL_URL = "/content/talentacademy/en/ksa/overview.html";
    private static final String ROOT_PATH = "/content/talentacademy/en/ksa/formpage";

    private static final String SAMEPAGE_REDIRECT_PARAM_VALUE = "isSamePage";

    @Mock
    FormMessageService formMessageService;

    @Mock
    RecaptchaValidationService recaptchaValidationService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    FormServlet formServlet;

    private ConfigurationManager configurationManager;

    private ValidationInfo info;

    private final AemContext aemContext = new AemContext();

    /**
     * Setup method for {com.talentacademy.core.servlets.FormServlet}
     */
    @BeforeEach
    void setUp(){
        aemContext.load().json("/com/talentacademy/core/models/impl/FormServletConfig.json", ROOT_PATH);
        aemContext.request().setRequestDispatcherFactory(new MockRequestDispatcherFactory() {
            @Override
            public RequestDispatcher getRequestDispatcher(String path, RequestDispatcherOptions options) {
                return requestDispatcher;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(Resource resource, RequestDispatcherOptions options) {
                return requestDispatcher;
            }
        });

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ApplicationConstants.FORM_REDIRECT_PARAM_ATTRIBUTE_NAME,REDIRECT_SUCCESSFUL_URL);
        paramMap.put("formField1","formFieldValue1");
        paramMap.put("formField2","formFieldValue2");

        aemContext.request().setParameterMap(paramMap);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithSuccessfulResponseFromServiceAndRedirect() throws ServletException, IOException {
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/formcontainerDefault");
        aemContext.request().setResource(resource);
        when(formMessageService.sendMessageAction(any(List.class), any(Resource.class))).thenReturn(true);

        formServlet.doPost(aemContext.request(), aemContext.response());

        String isSuccessfulResponse = (String) aemContext.request().getAttribute(ApplicationConstants.NEOM_FORM_SERVLET_STATE_FLAG_PARAM);

        assertNotNull(isSuccessfulResponse);
        assertEquals("true",isSuccessfulResponse);

    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithSuccessfulResponseWithEmptyValueParamInJson() throws ServletException, IOException {
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/formcontainerDefault");
        aemContext.request().setResource(resource);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ApplicationConstants.FORM_REDIRECT_PARAM_ATTRIBUTE_NAME,REDIRECT_SUCCESSFUL_URL);
        String[] strArray = {"val1","val2"};
        paramMap.put("formField1", strArray);
        paramMap.put("formField2","formFieldValue2");
        paramMap.put(FORM_ID, NTA_REGISTER_INTEREST);
        paramMap.put(COURSE_ID, "course:123");
        paramMap.put(LEARNING_PROGRAM_ID, "learningpath:123");
        aemContext.request().setParameterMap(paramMap);

        when(formMessageService.sendMessageAction(any(List.class), any(Resource.class))).thenReturn(true);

        formServlet.doPost(aemContext.request(), aemContext.response());

        String isSuccessfulResponse = (String) aemContext.request().getAttribute(ApplicationConstants.NEOM_FORM_SERVLET_STATE_FLAG_PARAM);

        assertNotNull(isSuccessfulResponse);
        assertEquals("true",isSuccessfulResponse);

    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithSuccessfulResponseFromServiceAndForward() throws ServletException, IOException {
        setUpMockScenarios("/formcontainerDefaultNoRedirect", Boolean.TRUE);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ApplicationConstants.NEOM_FORM_SERVLET_REDIRECT_SAMEPAGE_PARAM,SAMEPAGE_REDIRECT_PARAM_VALUE);
        paramMap.put("phoneCode","+123");
        paramMap.put("phone","23456713");
        paramMap.put(FORM_ID, "nta_send_us_message");
        aemContext.request().setParameterMap(paramMap);

        formServlet.doPost(aemContext.request(), aemContext.response());

        String redirectFromResponse = aemContext.response().getHeader("Location");
        String isSamePageParam = aemContext.request().getParameter(ApplicationConstants.NEOM_FORM_SERVLET_REDIRECT_SAMEPAGE_PARAM);

        assertNull(redirectFromResponse);
        assertFalse(StringUtils.isEmpty(isSamePageParam));
        assertEquals(isSamePageParam,SAMEPAGE_REDIRECT_PARAM_VALUE);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithUnSuccessfulResponseFromService() throws ServletException, IOException {
        setUpMockScenarios("/formcontainerDefault");
        String[] mockErrorMessages = {"error message in the form"};
        when(info.getErrorMessages(null)).thenReturn(mockErrorMessages);

        try (MockedStatic<ValidationInfo> validationInfoStatic = Mockito.mockStatic(ValidationInfo.class)) {

            validationInfoStatic.when(() -> ValidationInfo.createValidationInfo(aemContext.request()))
                    .thenReturn(info);

            formServlet.doPost(aemContext.request(), aemContext.response());
        }

        String redirectFromResponse = aemContext.response().getHeader("Location");

        ValidationInfo infoFromRequest = (ValidationInfo) aemContext.request().getAttribute(ValidationInfo.class.getName());

        assertNull(redirectFromResponse);
        assertNotNull(infoFromRequest);
        String[] errorMessages = infoFromRequest.getErrorMessages(null);
        assertTrue(errorMessages.length > 0);
        assertEquals("error message in the form",errorMessages[0]);

    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithUnSuccessfulResponseFromServiceAndWithIdForm() throws ServletException, IOException {
        setUpMockScenarios("/formcontainerDefaultWithIdForm");
        String[] mockErrorMessages = {"error message in the form"};
        when(info.getErrorMessages(null)).thenReturn(mockErrorMessages);

        try (MockedStatic<ValidationInfo> validationInfoStatic = Mockito.mockStatic(ValidationInfo.class)) {

            validationInfoStatic.when(() -> ValidationInfo.createValidationInfo(aemContext.request()))
                    .thenReturn(info);

            formServlet.doPost(aemContext.request(), aemContext.response());
        }

        String redirectFromResponse = aemContext.response().getHeader("Location");

        ValidationInfo infoFromRequest = (ValidationInfo) aemContext.request().getAttribute(ValidationInfo.class.getName());
        String idForm = (String) aemContext.request().getAttribute(FormsHelper.REQ_ATTR_FORMID);
        String initForm = (String) aemContext.request().getAttribute(FormsHelper.REQ_ATTR_IS_INIT);

        assertNull(redirectFromResponse);
        assertNotNull(infoFromRequest);
        String[] errorMessages = infoFromRequest.getErrorMessages(null);
        assertTrue(errorMessages.length > 0);
        assertEquals("error message in the form",errorMessages[0]);
        assertEquals("id_form_123", idForm);
        assertEquals("true", initForm);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithUnSuccessfulResponseFromServiceWithNoParentPage() throws ServletException, IOException {
        setUpMockScenarios("/formPageNoPagePrimaryType/jcr:content/root/container");
        String[] mockErrorMessages = {"error message in the form"};
        when(info.getErrorMessages(null)).thenReturn(mockErrorMessages);

        try (MockedStatic<ValidationInfo> validationInfoStatic = Mockito.mockStatic(ValidationInfo.class)) {

            validationInfoStatic.when(() -> ValidationInfo.createValidationInfo(aemContext.request()))
                    .thenReturn(info);

            formServlet.doPost(aemContext.request(), aemContext.response());
        }

        String redirectFromResponse = aemContext.response().getHeader("Location");

        ValidationInfo infoFromRequest = (ValidationInfo) aemContext.request().getAttribute(ValidationInfo.class.getName());

        assertNull(redirectFromResponse);
        assertNotNull(infoFromRequest);
        String[] errorMessages = infoFromRequest.getErrorMessages(null);
        assertTrue(errorMessages.length > 0);
        assertEquals("error message in the form",errorMessages[0]);

    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithUnSuccessfulResponseAndEmptyProperties() throws ServletException, IOException {
        setUpMockScenarios("/formcontainerNoErrorMessageKey");

        try (MockedStatic<ValidationInfo> validationInfoStatic = Mockito.mockStatic(ValidationInfo.class)) {

            validationInfoStatic.when(() -> ValidationInfo.createValidationInfo(aemContext.request()))
                    .thenReturn(info);

            formServlet.doPost(aemContext.request(), aemContext.response());
        }

        String redirectFromResponse = aemContext.response().getHeader("Location");

        ValidationInfo infoFromRequest = (ValidationInfo) aemContext.request().getAttribute(ValidationInfo.class.getName());

        assertNull(redirectFromResponse);
        assertNotNull(infoFromRequest);
        assertNull(infoFromRequest.getErrorMessages(null));

    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithUnSuccessfulResponseAndEmptyErrorMessages() throws ServletException, IOException {
        setUpMockScenarios("/formcontainerEmptyErrorMessage");

        try (MockedStatic<ValidationInfo> validationInfoStatic = Mockito.mockStatic(ValidationInfo.class)) {

            validationInfoStatic.when(() -> ValidationInfo.createValidationInfo(aemContext.request()))
                    .thenReturn(info);

            formServlet.doPost(aemContext.request(), aemContext.response());
        }

        String redirectFromResponse = aemContext.response().getHeader("Location");

        ValidationInfo infoFromRequest = (ValidationInfo) aemContext.request().getAttribute(ValidationInfo.class.getName());

        assertNull(redirectFromResponse);
        assertNotNull(infoFromRequest);
        assertNull(infoFromRequest.getErrorMessages(null));

    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithRecaptchaEnabledAndExistingConfiguration() throws ServletException, IOException {
        setUpRecaptchaScenarios();
        when(recaptchaValidationService.validate(null,"recaptchaTokenValue")).thenReturn(true);
        when(formMessageService.sendMessageAction(any(List.class), any(Resource.class))).thenReturn(true);

        formServlet.doPost(aemContext.request(), aemContext.response());

        String isSuccessfulResponse = (String) aemContext.request().getAttribute(ApplicationConstants.NEOM_FORM_SERVLET_STATE_FLAG_PARAM);

        assertNotNull(isSuccessfulResponse);
        assertEquals("true",isSuccessfulResponse);

    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithRecaptchaEnabledAndFailedResponseFromGoogle() throws ServletException, IOException {
        setUpFailingRecaptchaScenario();
        Configuration configuration = mock(Configuration.class);
        Resource contentResource = mock(Resource.class);

        when(configurationManager.getConfiguration(anyString(),any())).thenReturn(configuration);
        when(configuration.getContentResource()).thenReturn(contentResource);
        when(contentResource.adaptTo(GoogleRecaptchaConfiguration.class)).thenReturn(new GoogleRecaptchaConfiguration());
        when(recaptchaValidationService.validate(null,"recaptchaTokenValue")).thenReturn(false);

        try (MockedStatic<ValidationInfo> validationInfoStatic = Mockito.mockStatic(ValidationInfo.class)) {

            validationInfoStatic.when(() -> ValidationInfo.createValidationInfo(aemContext.request()))
                    .thenReturn(info);

            formServlet.doPost(aemContext.request(), aemContext.response());
        }

        String redirectFromResponse = aemContext.response().getHeader("Location");

        ValidationInfo infoFromRequest = (ValidationInfo) aemContext.request().getAttribute(ValidationInfo.class.getName());

        assertNull(redirectFromResponse);
        assertNotNull(infoFromRequest);
        String[] errorMessages = infoFromRequest.getErrorMessages(null);
        assertTrue(errorMessages.length > 0);
        assertEquals("Invalid recaptcha response",errorMessages[0]);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.servlets.FormServlet#doPost(SlingHttpServletRequest, SlingHttpServletResponse)}.
     */
    @Test
    void testDoPostWithRecaptchaEnabledAndConfigurationError() throws ServletException, IOException {
        setUpFailingRecaptchaScenario();
        when(configurationManager.getConfiguration(anyString(),any())).thenThrow(new IllegalArgumentException());

        try (MockedStatic<ValidationInfo> validationInfoStatic = Mockito.mockStatic(ValidationInfo.class)) {

            validationInfoStatic.when(() -> ValidationInfo.createValidationInfo(aemContext.request()))
                    .thenReturn(info);

            formServlet.doPost(aemContext.request(), aemContext.response());
        }

        String redirectFromResponse = aemContext.response().getHeader("Location");

        ValidationInfo infoFromRequest = (ValidationInfo) aemContext.request().getAttribute(ValidationInfo.class.getName());

        assertNull(redirectFromResponse);
        assertNotNull(infoFromRequest);
        String[] errorMessages = infoFromRequest.getErrorMessages(null);
        assertTrue(errorMessages.length > 0);
        assertEquals("Invalid recaptcha response",errorMessages[0]);
    }

    private void setUpMockScenarios(String resourcePath, Boolean...serviceReturn) throws ServletException, IOException {
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + resourcePath);
        aemContext.request().setResource(resource);
        aemContext.request().setAttribute(FormsHandlingServletHelper.class.getName() + "/resource", aemContext.request().getResource());
        info = mock(ValidationInfo.class);
        when(formMessageService.sendMessageAction(any(List.class), any(Resource.class))).thenReturn((serviceReturn.length > 0) ? serviceReturn[0] : Boolean.FALSE);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                aemContext.request().setAttribute(ValidationInfo.class.getName(),info);
                return null;
            }
        }).when(requestDispatcher).forward(any(SlingHttpServletRequest.class),any(SlingHttpServletResponse.class));
    }

    private void setUpFailingRecaptchaScenario() throws ServletException, IOException {
        setUpBasicRecaptchaScenarios();
        info = mock(ValidationInfo.class);
        String[] mockErrorMessages = {"Invalid recaptcha response"};
        when(info.getErrorMessages(null)).thenReturn(mockErrorMessages);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                aemContext.request().setAttribute(ValidationInfo.class.getName(),info);
                return null;
            }
        }).when(requestDispatcher).forward(any(SlingHttpServletRequest.class),any(SlingHttpServletResponse.class));
    }


    private void setUpRecaptchaScenarios(){
        setUpBasicRecaptchaScenarios();
        Configuration configuration = mock(Configuration.class);
        Resource contentResource = mock(Resource.class);

        when(configurationManager.getConfiguration(anyString(),any())).thenReturn(configuration);
        when(configuration.getContentResource()).thenReturn(contentResource);
        when(contentResource.adaptTo(GoogleRecaptchaConfiguration.class)).thenReturn(new GoogleRecaptchaConfiguration());
    }

    private void setUpBasicRecaptchaScenarios(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/formPageWithRecaptcha/jcr:content/root/container");
        aemContext.request().setResource(resource);
        configurationManager = mock(ConfigurationManager.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ApplicationConstants.FORM_REDIRECT_PARAM_ATTRIBUTE_NAME,REDIRECT_SUCCESSFUL_URL);
        paramMap.put("g-recaptcha-response","recaptchaTokenValue");
        aemContext.request().setParameterMap(paramMap);
        aemContext.registerAdapter(ResourceResolver.class,
                ConfigurationManager.class, configurationManager);
    }

}
