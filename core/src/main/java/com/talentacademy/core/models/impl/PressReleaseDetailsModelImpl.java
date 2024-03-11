package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.PressReleaseDetailsModel;
import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class}, adapters = PressReleaseDetailsModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = PressReleaseDetailsModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PressReleaseDetailsModelImpl implements PressReleaseDetailsModel {

    public static final String RESOURCE_TYPE = "talentacademy/components/content/pressreleasedetails";

    @SlingObject
    ResourceResolver resolver;

    @ValueMapValue
    private String date;

    @ValueMapValue
    private String cardImage;

    @ValueMapValue
    private String cardImageAltText;

    @ValueMapValue
    private String heading;

    @ValueMapValue
    private String shortDescription;

    @ValueMapValue
    private String longDescription;

    @ValueMapValue
    private String ctaLabel;

    @ValueMapValue
    private String ctaURL;

    @ValueMapValue
    private String openInNewTab;

    /**
     * @return The date
     */
    @Override
    public String getDate() {
        return date;
    }

    /**
     * @return The heroImage
     */
    @Override
    public String getHeroImage() {
        return cardImage;
    }

    /**
     * @return The imageAlt
     */
    @Override
    public String getImageAlt() {
        return cardImageAltText;
    }

    /**
     * @return The heading
     */
    @Override
    public String getHeading() {
        return heading;
    }

    /**
     * @return The short description
     */
    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @return The long description
     */
    @Override
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * @return The CTALabel
     */
    @Override
    public String getCTALabel() {
        return ctaLabel;
    }

    /**
     * @return The CTAUrl
     */
    @Override
    public String getCTAUrl() {
        ctaURL = ApplicationUtil.getValidURL(ctaURL,resolver);
        return ctaURL;
    }

    /**
     * @return The openInNewTab
     */
    @Override
    public String getOpenInNewTab() {
        return openInNewTab;
    }
}
