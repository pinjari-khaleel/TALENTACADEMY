package com.talentacademy.core.models;

import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Links {

    @ValueMapValue
    private String ctaLabel;

    @ValueMapValue
    private String ctaURL;
    
    @ValueMapValue
    private String target;

    @SlingObject
    ResourceResolver resolver;

    /**
     * @return The ctaLabel
     */
	public String getCtaLabel() {
		return ctaLabel;
	}

    /**
     * @return The ctaURL
     */
	public String getCtaURL() {
        return ApplicationUtil.getValidURL(ctaURL, resolver);
	}

    /**
     * @return The target
     */
	public String getTarget() {
		return target;
	}

}
