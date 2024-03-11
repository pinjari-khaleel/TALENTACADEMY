package com.talentacademy.core.models;

/**
 * Represents the Custom Config tags for the Page Component Neom project.
 **/
public interface PageConfigModel {

	/**
	 * @return the title of the current page .
	 */
	String getHeaderButtonLabel();

	/**
	 * @return the Header Button Label of the current page .
	 */
	String getHeaderButtonUrl();

	/**
	 * @return the Header Button Url name of the current page .
	 */
	String getLanguageDirection();

	/**
	 * @return the HideIn Sitemap of the current page .
	 */
	String getHideInSitemap();

	/**
	 * @return the id.
	 */
	String getId();

}