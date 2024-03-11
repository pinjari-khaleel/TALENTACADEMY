package com.talentacademy.core.beans;

import java.util.Objects;

public class CourseModule {
	private String locale;

	private String title;

	private String subtitle;
	
	private String format;
	
	private String durationHours;
	
	private String durationMinutes;

	private String description;

	/**
	 * @return locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale set the Locale
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param subtitle set the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param title set the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param subtitle set the subtitle
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * @param description set the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(String durationHours) {
		this.durationHours = durationHours;
	}

	public String getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(String durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @param obj ovveride the equals method
	 */
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		CourseModule courseModule = (CourseModule) obj;
		return title.equals(courseModule.title) && locale.equals(courseModule.locale);
	}

	/**
	 * @return hashcode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(locale, title);
	}
}
