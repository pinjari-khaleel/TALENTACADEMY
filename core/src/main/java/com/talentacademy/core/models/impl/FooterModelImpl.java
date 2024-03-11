package com.talentacademy.core.models.impl;

import com.talentacademy.core.models.FooterModel;
import com.talentacademy.core.models.Links;
import com.talentacademy.core.models.FooterGlobalLinks;
import com.talentacademy.core.models.SocialMediaLinks;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sairam Implementation class of Footer Model
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, adapters = FooterModel.class, resourceType = FooterModelImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModelImpl implements FooterModel {

	protected static final String RESOURCE_TYPE = "talentacademy/components/structure/footer";

	@ChildResource
	private List<FooterGlobalLinks> globalLinks;

	@ChildResource
	private List<SocialMediaLinks> socialMediaLinks;

	@ChildResource
	private List<Links> legalLinks;

	@ValueMapValue
	private String socialMediaHeader;

	@ValueMapValue
	private String copyrightText;

	@ValueMapValue
	private String recaptchaText;

	/**
	 * @return The globalLinks
	 */
	@Override
	public List<FooterGlobalLinks> getGlobalLinks() {
		return new ArrayList<>(globalLinks);
	}

	/**
	 * @return The socialMediaLinks
	 */
	@Override
	public List<SocialMediaLinks> getSocialMediaLinks() {
		return new ArrayList<>(socialMediaLinks);
	}

	/**
	 * @return The legalLinks
	 */
	@Override
	public List<Links> getLegalLinks() {
		return new ArrayList<>(legalLinks);
	}

	/**
	 * @return The socialMediaHeader
	 */
	@Override
	public String getSocialMediaHeader() {
		return socialMediaHeader;
	}

	/**
	 * @return The copyrightText
	 */
	@Override
	public String getCopyrightText() {
		return copyrightText;
	}

	/**
	 * @return The recaptchaText
	 */
	@Override
	public String getRecaptchaText() {
		return recaptchaText;
	}


}