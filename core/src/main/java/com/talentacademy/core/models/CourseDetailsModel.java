package com.talentacademy.core.models;

import org.apache.sling.api.resource.Resource;

public interface CourseDetailsModel {


	/**
	 * @return The infotarget
	 */
	public String getInfotarget();

	/**
	 * @return The skillsLabel
	 */
	public String getSkillsLabel();

	/**
	 * @return The badgesLabel
	 */
	public String getBadgesLabel();

	/**
	 * @return The formatLabel
	 */
	public String getFormatLabel();

	/**
	 * @return The durationLabel
	 */
	public String getDurationLabel();

	/**
	 * @return the infoctaLabel
	 */
	public String getInfoctaLabel();

	/**
	 * @return the infoctaURL
	 */
	public String getInfoctaURL();

	/**
	 * @return The accHeader
	 */
	public String getAccHeader();

	/**
	 * @return The accreditations
	 */
	public Resource getAccreditations();

	/**
	 * @return The header
	 */
	public String getHeader();

	/**
	 * @return The learningPathwayID
	 */
	public String getLearningPathwayID();

	/**
	 * @return The overviewRichText
	 */
	public String getOverviewRichText();

	/**
	 * @return The duration
	 */
	public String getDuration();

}
