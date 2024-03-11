package com.talentacademy.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FilterHelper {

	@ValueMapValue
	private String tagName;

	@ValueMapValue
	private String tagLink;

	/**
	 * @param tagName set the tag name
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @param tagLink set the tag link
	 */
	public void setTagLink(String tagLink) {
		this.tagLink = tagLink;
	}

	/**
	 * @return the tag name
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @return the tag link
	 */
	public String getTagLink() {
		return tagLink;
	}

}
