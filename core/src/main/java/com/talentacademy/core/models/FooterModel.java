package com.talentacademy.core.models;

import java.util.List;

public interface FooterModel {

	/**
	 * @return The globalLinks
	 */
	public List<FooterGlobalLinks> getGlobalLinks();

	/**
	 * @return The socialMediaLinks
	 */
	public List<SocialMediaLinks> getSocialMediaLinks();

	/**
	 * @return The legalLinks
	 */
	public List<Links> getLegalLinks();

	/**
	 * @return The socialMediaHeader
	 */
	public String getSocialMediaHeader();

	/**
	 * @return The copyrightText
	 */
	public String getCopyrightText();

	/**
	 * @return The recaptchaText
	 */
	public String getRecaptchaText();

}
