package com.talentacademy.core.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class FormConnectionConfiguration {
    /**
     * formConnectionUrl.
     */
    @ValueMapValue
    private String url;

    /**
     * formConnectionApiKey.
     */
    @ValueMapValue
    private String apiKey;

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }
}
