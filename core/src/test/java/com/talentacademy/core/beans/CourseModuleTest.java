package com.talentacademy.core.beans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseModuleTest {

	/**
	 * Test the Course modules
	 */
	@Test
	void testGetters() {
		CourseModule courseModule = new CourseModule();
		String title = "title";
		courseModule.setTitle(title);
		assertEquals(title, courseModule.getTitle());

		String subtitle = "subtitle";
		courseModule.setSubtitle(subtitle);
		assertEquals(subtitle, courseModule.getSubtitle());

		String description = "description";
		courseModule.setDescription(description);
		assertEquals(description, courseModule.getDescription());

		String locale = "locale";
		courseModule.setLocale(locale);
		assertEquals(locale, courseModule.getLocale());
	}

}
