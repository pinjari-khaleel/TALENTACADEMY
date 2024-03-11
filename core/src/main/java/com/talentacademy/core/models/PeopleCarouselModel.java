package com.talentacademy.core.models;

import java.util.List;

public interface PeopleCarouselModel {

	/**
	 * 
	 * @return the caption
	 */
	public String getCaption();

	/**
	 * 
	 * @return the description
	 */
	public String getDescription();

	/**
	 * 
	 * @return People Carousel List
	 */
	public List<PeopleCarouselList> getPeopleCarouselList();

}
