package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.models.FormContainerPreLoadModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = {FormContainerPreLoadModel.class},
        resourceType = FormContainerPreLoadModelImpl.RT_PRE_LOAD_FORM_CONTAINER)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FormContainerPreLoadModelImpl implements FormContainerPreLoadModel {

    public static final String RT_PRE_LOAD_FORM_CONTAINER = "talentacademy/components/form/ntaformcontainer";
    @Self
    private SlingHttpServletRequest request;

    private Boolean isSameConfigurationPage;

    private Boolean isSuccessfulPageSubmission;

    @PostConstruct
    private void initModel() {
        String isSamePageAttribute = (String) request.getAttribute(ApplicationConstants.NEOM_FORM_SERVLET_REDIRECT_SAMEPAGE_PARAM);
        String isSuccessfulSubAttribute = (String) request.getAttribute(ApplicationConstants.NEOM_FORM_SERVLET_STATE_FLAG_PARAM);

        if(!StringUtils.isEmpty(isSamePageAttribute))
            isSameConfigurationPage = Boolean.TRUE;

        if(!StringUtils.isEmpty(isSuccessfulSubAttribute))
            isSuccessfulPageSubmission = Boolean.TRUE;
    }

    @Override
    public Boolean isSameConfirmationPage() {
        return isSameConfigurationPage;
    }

    @Override
    public Boolean isSuccessfulPageSubmission() {
        return isSuccessfulPageSubmission;
    }

    @Override
    public String getFormActionPath() {
        return request.getResource().getResourceResolver().map(request.getResource().getPath());
    }

    @Override
    public String getEmail() {
        return (String) request.getAttribute(ApplicationConstants.NEOM_FORM_SERVLET_EMAIL_PARAM);
    }

    @Override
    public String getRecaptchaToken() {
        return (String) request.getAttribute(ApplicationConstants.NEOM_FORM_SERVLET_RECAPTCH_PARAM);
    }
}
