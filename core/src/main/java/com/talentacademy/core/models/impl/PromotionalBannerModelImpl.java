package com.talentacademy.core.models.impl;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.PromotionalBannerModel;

@Model(adaptables = Resource.class, adapters = PromotionalBannerModel.class, 
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, 
       resourceType = PromotionalBannerModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
	extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PromotionalBannerModelImpl implements PromotionalBannerModel {

    public static final String RESOURCE_TYPE = "talentacademy/components/content/promotionalbanner";

    @ValueMapValue
    private String sectionTitle;

    @ValueMapValue
    private String sectionDescription;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String altTextForImage;

    /**
     * @return the sectionTitle
     */
    @Override
    public String getSectionTitle() {
        return sectionTitle;
    }

    /**
     * @return the sectionDescription
     */
     @Override
    public String getSectionDescription() {
        return sectionDescription;
    }

    /**
     * @return the fileReference
     */
    @Override
    public String getFileReference() {
        return fileReference;
    }

    /**
     * @return the altTextForImage
     */
    @Override
    public String getAltTextForImage() {
        return altTextForImage;
    }
}
