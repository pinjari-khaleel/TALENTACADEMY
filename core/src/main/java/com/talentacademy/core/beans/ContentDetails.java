package com.talentacademy.core.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentDetails {

    @ValueMapValue
    private String label;

    @ValueMapValue
    private String value;

    /**
     * @return The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return The value
     */
    public String getValue() {
        return value;
    }

}
