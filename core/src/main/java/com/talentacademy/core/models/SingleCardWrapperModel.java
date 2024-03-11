package com.talentacademy.core.models;

import java.net.URISyntaxException;

import java.util.List;

import com.talentacademy.core.beans.CourseDetails;

/**
 * Single Card Wrapper Model Interface.
 **/
public interface SingleCardWrapperModel {

	/**
	 * List of course details.
	 * 
	 * @return courseDetails list
	 */
	List<CourseDetails> getSingleCardWrapperDetails() throws URISyntaxException;

	/**
	 * @return courseid of the card.
	 */
	String getCourseid();

}