package com.talentacademy.core.models;

import java.util.List;

/**
 * Represents the Course Card Config for the Course Card Component Neom project.
 **/
public interface CourseCardModel {

	/**
	 * @return The carouselImage
	 */
	public String getCarouselImage();

	/**
	 * @return The altTextForImage
	 */
	public String getAltTextForImage();

	/**
	 * @return The carouselImageLabelText
	 */
	public String getCarouselImageLabelText();

	/**
	 * @return The headingText
	 */
	public String getHeadingText();

	/**
	 * @return The coursesCount
	 */
	public String getCoursesCount();

	/**
	 * @return The badgesTitle
	 */
	public String getBadgesTitle();

	/**
	 * @return The badgesList
	 */
	public List<SocialMediaLinks> getBadgesList();

	/**
	 * @return The primaryButtonLink
	 */
	public String getPrimaryButtonLink();

	/**
	 * @return The primaryButtonLabel
	 */
	public String getPrimaryButtonLabel();

	/**
	 * @return The primaryButtonTarget
	 */
	public String getPrimaryButtonTarget();

	/**
	 * @return The secondaryButtonLink
	 */
	public String getSecondaryButtonLink();

	/**
	 * @return The secondaryButtonLabel
	 */
	public String getSecondaryButtonLabel();

	/**
	 * @return The secondaryButtonTarget
	 */
	public String getSecondaryButtonTarget();

	/**
	 * @return The learningPathwayID
	 */
	public String getLearningPathwayID();
	
}