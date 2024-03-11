package com.talentacademy.core.services;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import com.talentacademy.core.beans.GridListing;

public interface GridListingService {

	/**
	 *
	 * @return The list of component properties and child nodes of each career
	 *         details page
	 */
	public List<GridListing> getCareerDetailsdata(ResourceResolver resourceResolver, String rootDetailsPath);

	/**
	 *
	 * @return The list of component properties and child nodes of each course
	 *         details page
	 */
	public List<GridListing> getCourseDetailsdata(ResourceResolver resourceResolver, String rootDetailsPath);

	/**
	 *
	 * @return The list of component properties and child nodes of each press release
	 *         details page
	 */
	List<GridListing> getPressReleaseDetaildata(ResourceResolver resourceResolver, String rootDetailsPath);
}
