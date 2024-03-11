package com.talentacademy.core.models;

import com.talentacademy.core.beans.TabSliderDetails;

import java.util.List;

/**
 * Interface class of Tab Slider Model
 */
public interface TabSliderModel {

	/**
	 * @return Header of the Tab Slider.
	 */
	public String getHeader();

	/**
	 * @return List of Tab Slider Details.
	 */
	public List<TabSliderDetails> getTabSliderDetails();
}
