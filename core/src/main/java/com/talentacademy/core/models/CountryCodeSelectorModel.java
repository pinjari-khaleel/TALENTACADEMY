package com.talentacademy.core.models;

import java.util.List;

import com.talentacademy.core.beans.CountryCodeOptions;

public interface CountryCodeSelectorModel {

	/**
	 * @return The country selector list
	 */
	public List<CountryCodeOptions> getCountrySelectorList();

}
