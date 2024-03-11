package com.talentacademy.core.models;

import com.talentacademy.core.beans.CourseDetails;


import java.net.URISyntaxException;
import java.util.List;

/**
 * Dynamic Course Carousel interface.
 */
public interface DynamicCourseCarouselModel {

	/**
	 * List of course details.
	 * 
	 * @return courseDetails list
	 */

	List<CourseDetails> getCourseDetails() throws URISyntaxException;

}
