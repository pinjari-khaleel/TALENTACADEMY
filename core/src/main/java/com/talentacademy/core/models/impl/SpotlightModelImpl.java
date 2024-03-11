package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.SpotlightModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, adapters = {SpotlightModel.class},
        resourceType = "talentacademy/components/content/spotlight",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SpotlightModelImpl implements SpotlightModel {

    @ValueMapValue
    public String caption;

    @ValueMapValue
    public String learningPathway;

    @ValueMapValue
    public String title;

    @ValueMapValue
    public String description;

    @ValueMapValue
    public String desktopBackgroundImage;

    @ValueMapValue
    public String mobileBackgroundImage;

    @ValueMapValue
    public String imageAltText;

    @ValueMapValue
    public String backgroundVideo;

    @ValueMapValue
    public String backgroundVideoPoster;

    
    /** 
     * @return Caption
     */
    @Override
    public String getCaption() {
        return caption;
    }

    /** 
     * @return the pathway tag label
     */
    @Override
    public String getLearningPathway() {
        return learningPathway;
    }

    /** 
     * @return Title of the component
     */
    @Override
    public String getTitle() {
        return title;
    }

    /** 
     * @return The description of the component
     */
    @Override
    public String getDescription() {
        return description;
    }

    /** 
     * @return The background image for desktop
     */
    @Override
    public String getDesktopBackgroundImage() {
        return desktopBackgroundImage;
    }

    /** 
     * @return background image for mobile
     */
    @Override
    public String getMobileBackgroundImage() {
        return mobileBackgroundImage;
    }

    /** 
     * @return The alternate text for image
     */
    @Override
    public String getImageAltText() {
        return imageAltText;
    }

    /** 
     * @return The background video
     */
    @Override
    public String getBackgroundVideo() {
        return backgroundVideo;
    }

    /** 
     * @return The background video poster image
     */
    @Override
    public String getBackgroundVideoPoster() {
        return backgroundVideoPoster;
    }
}