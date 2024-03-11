package com.talentacademy.core.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Bean class of Tab Slider Model
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TabSliderDetails {

    @ValueMapValue
    private String tabName;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String image;

    @ValueMapValue
    private String imageAltText;

    /**
     * @return Name of the Tab.
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * @return Title of the Tab.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Description of the Tab.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Image of the Tab.
     */
    public String getImage() {
        return image;
    }

    /**
     * @return Image Alt Text of the Tab.
     */
    public String getImageAltText() {
        return imageAltText;
    }
}
