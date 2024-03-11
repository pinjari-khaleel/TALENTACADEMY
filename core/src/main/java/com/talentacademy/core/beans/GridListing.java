package com.talentacademy.core.beans;

import java.util.List;
import java.util.Collections;

public class GridListing {

	private String sector;
	private String experienceLevel;
	private String recruitmentStatus;
	private String jobTitle;
	private List<String> badges;
	private String pagePath;
	private String[] filterTags;
	private String cardImage;
	private String courseTag;
	private String courseTitle;
	private String numberOfCourses;
	private String learningPathwayID;
	private String cardImageAltText;
	private String heading;
	private String date;
	private String shortDescription;
	private String longDescription;

	
	/**
	 * @param jobTitle set the job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	
	/**
	 * @return The Job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	
	/**
	 * @param pagePath set the page path
	 */
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	
	/**
	 * @return The page path
	 */
	public String getPagePath() {
		return pagePath;
	}

	
	/**
	 * @param sector set the sector
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	
	/**
	 * @return The sector
	 */
	public String getSector() {
		return sector;
	}

	
	/**
	 * @param experienceLevel set the experienceLevel
	 */
	public void setExperienceLevel(String experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	
	/**
	 * @return The experienceLevel
	 */
	public String getExperienceLevel() {
		return experienceLevel;
	}

	
	/**
	 * @param recruitmentStatus set the recruitmentStatus
	 */
	public void setRecruitmentStatus(String recruitmentStatus) {
		this.recruitmentStatus = recruitmentStatus;
	}

	
	/**
	 * @return The recruitmentStatus
	 */
	public String getRecruitmentStatus() {
		return recruitmentStatus;
	}

	
	/**
	 * @param badges set the list of badges
	 */
	public void setBadges(List<String> badges) {
		this.badges = Collections.unmodifiableList(badges);
	}

	
	/**
	 * @return the badge of List<String>
	 */
	public List<String> getBadges() {
		return Collections.unmodifiableList(badges);
	}

	
	/**
	 * @param filterTags set the filterTags of String[]
	 */
	public void setFilterTags(String[] filterTags) {
		this.filterTags = filterTags.clone();
	}

	
	/**
	 * @return the filterTags of String[]
	 */
	public String[] getFilterTags() {
		return filterTags.clone();
	}

	
	/**
	 * @return the cardImage
	 */
	public String getCardImage() {
		return cardImage;
	}

	
	/**
	 * @param cardImage set the course Image
	 */
	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

	/**
	 * @return the cardImageAltText
	 */
	public String getCardImageAltText() {
		return cardImageAltText;
	}

	
	/**
	 * @param cardImageAltText set the course image alt text
	 */
	public void setCardImageAltText(String cardImageAltText) {
		this.cardImageAltText = cardImageAltText;
	}

	
	/**
	 * @return the course title
	 */
	public String getCourseTitle() {
		return courseTitle;
	}

	
	/**
	 * @param courseTitle set the course title
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	
	/**
	 * @return the course tag
	 */
	public String getCourseTag() {
		return courseTag;
	}

	
	/**
	 * @param courseTag set the course tag
	 */
	public void setCourseTag(String courseTag) {
		this.courseTag = courseTag;
	}

	
	/**
	 * @return the number of courses
	 */
	public String getNumberOfCourses() {
		return numberOfCourses;
	}

	
	/**
	 * @param numberOfCourses set the number of courses
	 */
	public void setNumberOfCourses(String numberOfCourses) {
		this.numberOfCourses = numberOfCourses;
	}

	
	/**
	 * @return the Learning Pathway ID
	 */
	public String getLearningPathwayID() {
		return learningPathwayID;
	}

	
	/**
	 * @param learningPathwayID set the Learning Pathway ID
	 */
	public void setLearningPathwayID(String learningPathwayID) {
		this.learningPathwayID = learningPathwayID;
	}


	/**
	 * @param date set the created date
	 */
	public void setCreatedDate(String date) {
		this.date = date;
	}

	/**
	 * @return the created date
	 */
	public String getCreatedDate() { return date; }

	/**
	 * @param heading set the heading
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * @return the heading
	 */
	public String getHeading() { return heading; }

	/**
	 * @param shortDescription set the short description
	 *
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * @return the  short description
	 */
	public String getShortDescription() { return shortDescription; }

	/**
	 * @param longDescription set the long description
	 *
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	/**
	 * @return the  long description
	 */
	public String getLongDescription() { return longDescription; }
}


