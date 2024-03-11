package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.CountryCodeOptions;
import com.talentacademy.core.models.CountryCodeSelectorModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * @author sairam Implementation class of Country Selector Model
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, adapters = CountryCodeSelectorModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CountryCodeSelectorModelImpl implements CountryCodeSelectorModel {

	@SlingObject
	private ResourceResolver resolver;

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String name;

	@ValueMapValue
	private String flagsPath;

	@ValueMapValue
	private String errorMessage;

	@ValueMapValue
	private String required;

	@ValueMapValue
	private String countryCodeOptionsPath;

	/**
	 * get list of country selector options list
	 * 
	 * @return countrySelectorList
	 */
	@Override
	public List<CountryCodeOptions> getCountrySelectorList() {
		List<CountryCodeOptions> countrySelectorList = new ArrayList<>();
		Resource resource = resolver.getResource(countryCodeOptionsPath);
		if (resource != null && resource.hasChildren()) {
			Iterator<Resource> countryList = resource.listChildren();
			while (countryList.hasNext()) {
				Resource countryRes = countryList.next();
				if (countryRes != null) {
					CountryCodeOptions countryOptions = countryRes.adaptTo(CountryCodeOptions.class);
					countryOptions.setCountryCodeLowerCase(countryOptions.getCountryCode().toLowerCase());
					countrySelectorList.add(countryOptions);
				}
			}
		}
		return countrySelectorList;
	}

	/**
	 * get title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * get flags path of dam
	 * 
	 * @return flagsPath
	 */
	public String getFlagsPath() {
		return flagsPath;
	}

	/**
	 * get error message
	 * 
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * get required value
	 * 
	 * @return required
	 */
	public String getRequired() {
		return required;
	}

	/**
	 * get name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
}