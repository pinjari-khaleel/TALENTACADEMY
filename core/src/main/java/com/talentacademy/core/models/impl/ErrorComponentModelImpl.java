package com.talentacademy.core.models.impl;

import com.talentacademy.core.models.ErrorComponentModel;
import com.talentacademy.core.utils.ApplicationUtil;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * @author sairam Implementation class of ErrorComponentModel
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, adapters = ErrorComponentModel.class, resourceType = ErrorComponentModelImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ErrorComponentModelImpl implements ErrorComponentModel {

	protected static final String RESOURCE_TYPE = "talentacademy/components/content/error";

	@SlingObject
	ResourceResolver resolver;

	@ValueMapValue
	private String logo;

	@ValueMapValue
	private String logoAltText;

	@ValueMapValue
	private String redirectURL;

	@ValueMapValue
	private String fileReference;

	@ValueMapValue
	private String altText;

	@ValueMapValue
	private String pretitle;

	@ValueMapValue
	private String title;

	@ValueMapValue
	private String description;

	@ValueMapValue
	private String arPretitle;

	@ValueMapValue
	private String arTitle;

	@ValueMapValue
	private String arDescription;

	/**
	 * @return The logo
	 */
	@Override
	public String getLogo() {
		return logo;
	}

	/**
	 * @return The logoAltText
	 */
	@Override
	public String getLogoAltText() {
		return logoAltText;
	}

	/**
	 * @return The redirectURL
	 */
	@Override
	public String getRedirectURL() {
		return ApplicationUtil.getValidURL(redirectURL, resolver);
	}

	/**
	 * @return The fileReference
	 */
	@Override
	public String getFileReference() {
		return fileReference;
	}

	/**
	 * @return The altText
	 */
	@Override
	public String getAltText() {
		return altText;
	}

	/**
	 * @return The pretitle
	 */
	@Override
	public String getPretitle() {
		return pretitle;
	}

	/**
	 * @return The title
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * @return The description
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @return The arPretitle
	 */
	@Override
	public String getArPretitle() {
		return arPretitle;
	}

	/**
	 * @return The arTitle
	 */
	@Override
	public String getArTitle() {
		return arTitle;
	}

	/**
	 * @return The arDescription
	 */
	@Override
	public String getArDescription() {
		return arDescription;
	}

}