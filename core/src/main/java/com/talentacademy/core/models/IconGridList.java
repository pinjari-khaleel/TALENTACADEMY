package com.talentacademy.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class IconGridList {

	@ValueMapValue
	private String icon;

	@ValueMapValue
	private String iconText;

	@ValueMapValue
	private String altText;

	/**
	 * 
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 
	 * @return the iconText
	 */
	public String getIconText() {
		return iconText;
	}

	/**
	 * 
	 * @return the altText
	 */
	public String getAltText() {
		return altText;
	}

}
