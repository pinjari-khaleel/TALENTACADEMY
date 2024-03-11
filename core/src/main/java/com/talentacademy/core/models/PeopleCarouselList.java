package com.talentacademy.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PeopleCarouselList {

	@ValueMapValue
	private String image;

	@ValueMapValue
	private String altText;

	@ValueMapValue
	private String name;

	@ValueMapValue
	private String role;

	@ValueMapValue
	private String description;

	/**
	 * 
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 
	 * @return the altText
	 */
	public String getAltText() {
		return altText;
	}

	/**
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
