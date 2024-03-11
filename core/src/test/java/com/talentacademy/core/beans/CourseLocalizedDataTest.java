package com.talentacademy.core.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseLocalizedDataTest {

    /**
     * Test the Course Localized Data
     */
    @Test
    void testGetters() {
        CourseLocalizedData courseLocalizedData = new CourseLocalizedData();

        String title = "course title";
        courseLocalizedData.setTitle(title);
        assertEquals(title, courseLocalizedData.getTitle());

        String locale = "en-US";
        courseLocalizedData.setLocale(locale);
        assertEquals(locale, courseLocalizedData.getLocale());
        
        String OvervieRichText = "Overview";
        courseLocalizedData.setRichTextOverview(OvervieRichText);
        assertEquals(OvervieRichText, courseLocalizedData.getRichTextOverview());
    }
}
