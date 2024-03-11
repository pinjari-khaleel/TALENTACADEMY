package com.talentacademy.core.beans;

import java.util.Collections;
import java.util.List;

/**
 * Course details Bean.
 */
public class CourseDetails {
	private String id;

	private String durationHours;

	private String durationMinutes;

	private String loType;

	private String imageUrl;

	private int subLosCount;

	private List<CourseLocalizedData> courseLocalizedDataList;

	private List<CourseBadgeDetails> courseBadgesList;

	private List<String> skillsList;

	private List<String> formatsList;

	private List<CourseModule> moduleList;

	private String noOfCourses;

	private String cursorValue;

	private List<String> arabicSkills;

	public String getCursorValue() {
		return cursorValue;
	}

	public void setCursorValue(String cursorValue) {
		this.cursorValue = cursorValue;
	}

	/**
	 * @return The Course Duration in Hours
	 */
	public String getDurationHours() {
		return durationHours;
	}

	/**
	 * @return The Course Duration in Minutes
	 */
	public String getDurationMinutes() {
		return durationMinutes;
	}

	/**
	 * @return The Course ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The Course Type
	 */
	public String getLoType() {
		return loType;
	}

	/**
	 * @return The Course Image
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @return The Learning path's Sub Lo's
	 */
	public int getSubLosCount() {
		return subLosCount;
	}

	/**
	 * @return The Localized course data
	 */
	public List<CourseLocalizedData> getCourseLocalizedDataList() {
		return Collections.unmodifiableList(courseLocalizedDataList);
	}

	/**
	 * @return The list of Course Specific Badges
	 */
	public List<CourseBadgeDetails> getCourseBadgesList() {
		return Collections.unmodifiableList(courseBadgesList);
	}

	/**
	 * @return The list of Course Specific Arabic Skills
	 */
	public List<String> getArabicSkills() {
		return Collections.unmodifiableList(arabicSkills);
	}

	/**
	 * @param durationHours set the Course Duration in Hours
	 */
	public void setDurationHours(String durationHours) {
		this.durationHours = durationHours;
	}

	/**
	 * @param durationMinutes set the Course Duration in Minutes
	 */
	public void setDurationMinutes(String durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	/**
	 * @param id set the Course ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param loType set the Course Type
	 */
	public void setLoType(String loType) {
		this.loType = loType;
	}

	/**
	 * @param imageUrl set the Course Image
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @param subLosCount set the Learning Paths Duration in Hours
	 */
	public void setSubLosCount(int subLosCount) {
		this.subLosCount = subLosCount;
	}

	/**
	 * @param courseLocalizedDataList set the Course Localized Data
	 */
	public void setCourseLocalizedDataList(List<CourseLocalizedData> courseLocalizedDataList) {
		this.courseLocalizedDataList = Collections.unmodifiableList(courseLocalizedDataList);
	}

	/**
	 * @param courseBadgesList set the Course Badges List
	 */
	public void setCourseBadgesList(List<CourseBadgeDetails> courseBadgesList) {
		this.courseBadgesList = Collections.unmodifiableList(courseBadgesList);
	}

	/**
	 * @return The list of Course Specific skills
	 */
	public List<String> getSkillsList() {
		return Collections.unmodifiableList(skillsList);
	}

	/**
	 * @param skillsList set the Course skills List
	 */
	public void setSkillsList(List<String> skillsList) {
		this.skillsList = Collections.unmodifiableList(skillsList);
	}

	/**
	 * @return The list of Course formats
	 */
	public List<String> getFormatsList() {
		return Collections.unmodifiableList(formatsList);
	}

	/**
	 * @param formatsList set the Course format List
	 */
	public void setFormatsList(List<String> formatsList) {
		this.formatsList = Collections.unmodifiableList(formatsList);
	}

	/**
	 * @return The list of Course modules
	 */
	public List<CourseModule> getModuleList() {
		return Collections.unmodifiableList(moduleList);
	}

	/**
	 * @param moduleList set the Course module List
	 */
	public void setModuleList(List<CourseModule> moduleList) {
		this.moduleList = Collections.unmodifiableList(moduleList);
	}

	/**
	 * @return no of Courses
	 */
	public String getNoOfCourses() {
		return noOfCourses;
	}

	/**
	 * @param set the no of courses
	 */
	public void setNoOfCourses(String noOfCourses) {
		this.noOfCourses = noOfCourses;
	}

	/**
	 * @param arabicSkills set the Arabic Skills List
	 */
	public void setArabicSkills(List<String> arabicSkills) {
		this.arabicSkills = Collections.unmodifiableList(arabicSkills);
	}
}