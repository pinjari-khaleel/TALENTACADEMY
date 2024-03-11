package com.talentacademy.core.models;

import com.day.cq.wcm.api.Page;

public interface SiteMapModel {

	/**
	 * 
	 * @return the PagePath
	 */
	Page getPagePath();

	/**
	 * 
	 * @return the RootPage Path
	 */
	String getRootPage();

	/**
	 * 
	 * @return the Heading
	 */
	String getHeading();

}
