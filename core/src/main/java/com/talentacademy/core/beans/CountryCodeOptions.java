package com.talentacademy.core.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CountryCodeOptions {
	@ValueMapValue
	private String text;

	@ValueMapValue
	private String value;

	@ValueMapValue
	private String countryCode;

	@ValueMapValue
	private String selected;

	private String countryCodeLowerCase;

	/**
	 * get country text
	 * 
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * get country value
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * get country code
	 * 
	 * @return countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * get country selected
	 * 
	 * @return selected
	 */
	public String getSelected() {
		return selected;
	}

	/**
	 * set country code lower case value
	 * 
	 * @param countryCodeLowerCase
	 */
	public void setCountryCodeLowerCase(String countryCodeLowerCase) {
		this.countryCodeLowerCase = countryCodeLowerCase;
	}

	/**
	 * get country code lower case value
	 * 
	 * @return countryCodeLowerCase
	 */
	public String getCountryCodeLowerCase() {
		return countryCodeLowerCase;
	}

}
