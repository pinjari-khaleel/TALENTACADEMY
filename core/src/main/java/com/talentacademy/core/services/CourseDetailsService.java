package com.talentacademy.core.services;

import com.talentacademy.core.beans.CourseDetails;

import java.util.List;

/**
 * Course details service interface.
 */
public interface CourseDetailsService {

	/**
	 * List of course details.
	 *
	 * @param responseStr       ALM response as string
	 * @param isSingleCourse
	 * @param currentPageLocale
	 * @return courseDetails list
	 */
	List<CourseDetails> getCourseDetails(String responseStr, Boolean isSingleCourse, String currentPageLocale);

	/**
	 * Get Cursor ID.
	 * 
	 * @param responseStr
	 * @return
	 */
	String getCursorId(String responseStr);

}
