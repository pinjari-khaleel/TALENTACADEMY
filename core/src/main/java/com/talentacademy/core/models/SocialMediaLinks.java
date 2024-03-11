package com.talentacademy.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialMediaLinks {

    @ValueMapValue
    private String icon;
    
    @ValueMapValue
    private String link;
    
    @ValueMapValue
    private String altText;
    
    @ValueMapValue
    private String label;

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return the altText
	 */
	public String getAltText() {
		return altText;
	}
	
	/**
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

}
