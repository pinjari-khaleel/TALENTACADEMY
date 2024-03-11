package com.talentacademy.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.foundation.forms.FormsHandlingRequest;
import com.day.cq.wcm.foundation.forms.FormsHelper;
import com.day.cq.wcm.foundation.forms.ValidationInfo;
import com.google.gson.Gson;
import com.talentacademy.core.beans.GoogleRecaptchaConfiguration;
import com.talentacademy.core.configs.CloudServiceConfiguration;
import com.talentacademy.core.services.FormMessageService;
import com.talentacademy.core.services.RecaptchaValidationService;
import com.talentacademy.core.services.impl.CloudConfigServiceImpl;
import com.talentacademy.core.utils.ApplicationUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.day.cq.wcm.api.constants.NameConstants.NT_PAGE;
import static com.talentacademy.core.constants.ApplicationConstants.*;

/**
 * Form servlet for NTA forms - It has a generic way to create a general json object to be sent to the service layer
 * for further processing
 */
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes= NEOM_FORM_SERVLET_RESOURCE_TYPE,
        methods= HttpConstants.METHOD_POST,
        extensions= NEOM_FORM_SERVLET_EXTENSION,
        selectors = NEOM_FORM_SERVLET_SELECTOR
)
public class FormServlet extends SlingAllMethodsServlet {
    private static final String ERROR_MESSAGE_RESOURCE_PROPERTY_NAME = "errorMessage";
    private static final String GOOGLE_RECAPTCHA_ERROR_MESSAGE_TEXT = "Invalid recaptcha response";
    private static final String GOOGLE_RECAPTCHA_RESPONSE = "g-recaptcha-response";

    /**
     * constant to fetch the redirect property in jcr. No semicolon prefix
     */
    private static final String FORM_VALUE_MAP_REDIRECT_PARAM = "redirect";
    private static final String SAMEPAGE_REDIRECT_PARAM_VALUE = "isSamePage";
    private static final String ID_FORM_PROPERTY = "id";
    private static final String TRUE_KEY_FORM_INIT_PROPERTY = "true";

    private static final String PHONE = "phone";

    private static final Set<String> INTERNAL_PARAMETER = Set.of(
            ":formstart",
            "_charset_",
            ":redirect",
            ":cq_csrf_token",
            "phoneCode",
            PHONE,
            GOOGLE_RECAPTCHA_RESPONSE
    );

    private String email;

    @Reference
    private transient FormMessageService formMessageService;

    @Reference
    private transient RecaptchaValidationService recaptchaValidationService;

    @Override
    protected void doPost(@NotNull final SlingHttpServletRequest request, @NotNull final SlingHttpServletResponse response)
            throws ServletException, IOException {

        final String recaptchaToken = request.getParameter(GOOGLE_RECAPTCHA_RESPONSE);

        try{
            if(!StringUtils.isEmpty(recaptchaToken) && !validateRecaptcha(request,recaptchaToken)) {
                setFormErrorMessage(request,response,GOOGLE_RECAPTCHA_ERROR_MESSAGE_TEXT);
                return;
            }
        }catch(IllegalArgumentException exception) {
            setFormErrorMessage(request,response,GOOGLE_RECAPTCHA_ERROR_MESSAGE_TEXT);
            return;
        }

        List<NameValuePair> nvps = getFormDataModel(request);

        boolean registrationResult = formMessageService.sendMessageAction(nvps, request.getResource());

        ValueMap resourceProperties = request.getResource().getValueMap();

        if (registrationResult) {
            request.setAttribute(NEOM_FORM_SERVLET_STATE_FLAG_PARAM,TRUE_KEY_FORM_INIT_PROPERTY);
            request.setAttribute(NEOM_FORM_SERVLET_EMAIL_PARAM, email);
            request.setAttribute(NEOM_FORM_SERVLET_RECAPTCH_PARAM, recaptchaToken);
            if(!StringUtils.isEmpty(resourceProperties.get(FORM_VALUE_MAP_REDIRECT_PARAM,String.class))){
                String redirectPageUrl = request.getParameter(FORM_REDIRECT_PARAM_ATTRIBUTE_NAME);
                request.getRequestDispatcher(redirectPageUrl).forward(new FormsHandlingRequest(request), response);
                return;
            }

            sendRedirectWithDispatcher(request,response,Boolean.TRUE);
        } else { //scenario for displaying the general error message in same form page on top of the form section
            String formId = resourceProperties.get(ID_FORM_PROPERTY,String.class);
            if(Optional.ofNullable(formId).isPresent()) {
                request.setAttribute(FormsHelper.REQ_ATTR_FORMID,formId);
                request.setAttribute(FormsHelper.REQ_ATTR_IS_INIT,TRUE_KEY_FORM_INIT_PROPERTY);
            }

            setFormErrorMessage(request,response,StringUtils.EMPTY);
        }
    }

    /**
     * function that redirects the response using request dispatcher, no creating a new get redirect for a new page
     * @param request
     * @param response
     * @param isSamePageNoError
     * @throws ServletException
     * @throws IOException
     */
    private void sendRedirectWithDispatcher(SlingHttpServletRequest request, SlingHttpServletResponse response, Boolean isSamePageNoError) throws ServletException, IOException {
        String parentResourcePath = getParentResourcePathFromRequest(request.getResource().getParent());
        Resource rsrc = request.getResourceResolver().resolve(parentResourcePath);
        SlingHttpServletRequest formsRequest = null;

        if(Boolean.TRUE.equals(isSamePageNoError))
            request.setAttribute(NEOM_FORM_SERVLET_REDIRECT_SAMEPAGE_PARAM,SAMEPAGE_REDIRECT_PARAM_VALUE);

        formsRequest = new FormsHandlingRequest(request);
        request.getRequestDispatcher(rsrc).forward(formsRequest, response);
    }

    /**
     * Returns a json object with the params in the request
     * It does not load the hidden params that are required for the AEM forms processing
     * @param request
     * @return
     */
    private List<NameValuePair> getFormDataModel(SlingHttpServletRequest request) {

        String phoneNumber = null;

        String phoneNumberCode = null;

        String formId = null;

        Gson gson = new Gson();
        List<NameValuePair> nvps = new ArrayList<>();
        Map<String, String[]> params = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (!INTERNAL_PARAMETER.contains(entry.getKey())) {
                if(entry.getKey().equalsIgnoreCase("email")) {
                    email = entry.getValue()[0];
                }
                if(entry.getKey().equalsIgnoreCase(FORM_ID)) {
                    formId = entry.getValue()[0];
                }
                String[] v = entry.getValue();
                String o = (v.length == 1) ? v[0] : gson.toJson(v);
                if(formId != null && (formId.equalsIgnoreCase(NTA_REGISTER_INTEREST) || (!entry.getKey().equalsIgnoreCase(COURSE_ID) && !entry.getKey().equalsIgnoreCase(LEARNING_PROGRAM_ID)))) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), o));
                }
            }
            if(entry.getKey().equalsIgnoreCase(PHONE)) {
                phoneNumber = entry.getValue()[0];
            }
            if(entry.getKey().equalsIgnoreCase("phoneCode")) {
                phoneNumberCode = entry.getValue()[0];
            }
        }

        nvps.add(new BasicNameValuePair(PHONE, ApplicationUtil.createStandardPhoneNumberCode(phoneNumberCode) + phoneNumber));
        return nvps;
    }

    /**
     * Validates the recaptcha token from request
     * @param request
     * @param recaptchaToken
     * @return
     */
    private boolean validateRecaptcha(SlingHttpServletRequest request, String recaptchaToken){
        CloudConfigServiceImpl cloudConfigServiceImpl = new CloudConfigServiceImpl(request.getResource());
        GoogleRecaptchaConfiguration recaptchaConfiguration =
                cloudConfigServiceImpl.getConfiguration(CloudServiceConfiguration.GOOGLE_RECAPTCHA);

        final String recaptchaSecretKey = recaptchaConfiguration.getReCaptchaSecretKey();

        return recaptchaValidationService.validate(recaptchaSecretKey, recaptchaToken);
    }

    /**
     * creates the general error message for the form. Function called multiple times from doPost method
     * @param request
     * @param response
     * @param errorMessage
     * @throws ServletException
     * @throws IOException
     */
    private void setFormErrorMessage(SlingHttpServletRequest request, SlingHttpServletResponse response, String errorMessage) throws ServletException, IOException {
        ValidationInfo info = ValidationInfo.createValidationInfo(request);
        ValueMap resourceProperties = request.getResource().getValueMap();

        if(!StringUtils.isEmpty(errorMessage)){
            info.addErrorMessage((String) null, errorMessage);
        }else{
            String[] errorMessageArray = resourceProperties.get(ERROR_MESSAGE_RESOURCE_PROPERTY_NAME, String[].class);
            if (errorMessageArray != null && errorMessageArray.length > 0) {
                info.addErrorMessage((String) null, errorMessageArray[0]);
            }
        }

        sendRedirectWithDispatcher(request,response,Boolean.FALSE);
    }

    /**
     * returns the page resource of the form request resource
     * @param resource
     * @return
     */
    private String getParentResourcePathFromRequest(Resource resource){
        if(Optional.ofNullable(resource).isEmpty())
            return StringUtils.EMPTY;

        ValueMap resourceProperties = resource.getValueMap();

        String primaryType = resourceProperties.get(JcrConstants.JCR_PRIMARYTYPE,String.class);
        if(!StringUtils.isEmpty(primaryType) && primaryType.equals(NT_PAGE))
            return resource.getPath() + HTML_EXTENSION;
        else
            return getParentResourcePathFromRequest(resource.getParent());

    }
}
