package com.talentacademy.core.models;

import java.util.List;

public interface IconGridModel {

	/**
	 * 
	 * @return the gridType
	 */
	public String getGridType();

	/**
	 * 
	 * @return the caption
	 */
	public String getCaption();

	/**
	 * 
	 * @return the IconGridTitle
	 */
	public String getIconGridTitle();

	/**
	 * 
	 * @return the IconGridDescription
	 */
	public String getIconGridDescription();

	/**
	 * 
	 * @return the IconGridList
	 */
	public List<IconGridList> getGridIconsList();

}
