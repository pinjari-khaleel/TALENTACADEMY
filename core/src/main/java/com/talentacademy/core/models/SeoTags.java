
package com.talentacademy.core.models;

/**
 * Represents the SEO tags for the  Page Component Neom project.
 **/
public interface SeoTags {

	/**
	 * @return the title of the  current page .
	 */
	String getTitle();

	/**
	 * @return the description of the  current page .
	 */
	String getDescription();

	/**
	 * @return the template name of the  current page .
	 */
	String getTemplateName();

	/**
	 * @return the robot tags from the current page properties.
	 */
	String[] getRobotTags();

	/**
	 * @return the twitter card type from the current page properties.
	 */
	String getTwitterCard();

	/**
	 * @return the twitter description from the current page properties.
	 */
	String getTwitterDescription();

	/**
	 * @return the twitter image path from the current page properties.
	 */
	String getTwitterImage();

	/**
	 * @return the twitter image alt text from the current page properties.
	 */
	String getTwitterImageAltText();

	/**
	 * @return the site name from the current page properties.
	 */
	String getTwitterSiteName();

	/**
	 * @return the twitter title from the current page properties.
	 */
	String getTwitterTitle();

	/**
	 * @return the canonical url from the current page properties.
	 */
	String getCanonicalUrl();

	/**
	 * @return the og title from the current page properties.
	 */
	String getOgTitle();

	/**
	 * @return the og description from the current page properties.
	 */
	String getOgDescription();

	/**
	 * @return the og image path from the current page properties.
	 */
	String getOgImage();

	/**
	 * @return the og image alt text from the current page properties.
	 */
	String getOgImageAltText();

	/**
	 * @return the og url from the current page properties.
	 */
	String getOgUrl();

	/**
	 * @return the og type from the current page properties.
	 */
	String getOgType();

	/**
	 * @return the og locale from the current page properties.
	 */
	String getOgLocale();

	/**
	 * @return the og site name from the current page properties.
	 */
	String getOgSiteName();

}