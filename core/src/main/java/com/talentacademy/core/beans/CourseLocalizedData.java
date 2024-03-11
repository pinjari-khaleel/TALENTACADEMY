package com.talentacademy.core.beans;

/**
 * Course specific localized data Bean.
 */
public class CourseLocalizedData {

    String title;
    String locale;
    String richTextOverview;
    /**
     * @return The Course Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title set the Course Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The Course Locale
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
     * @return The Course RichText Overview
     */
	public String getRichTextOverview() {
		return richTextOverview;
	}
	
	/**
     * @param locale set the RichText Overview
     */
	public void setRichTextOverview(String richTextOverview) {
		this.richTextOverview = richTextOverview;
	}
}