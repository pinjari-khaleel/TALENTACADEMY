package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AnalyticsModelImplTest {
	private final AemContext aemContext = new AemContext();

	private AnalyticsModelImpl model;

	@Mock
	private SlingHttpServletRequest request;

	/**
	 * Test method for {@link AnalyticsModelImpl #getTitle()}.
	 */

	@Test
	void testGetTitle() {
		aemContext.addModelsForClasses(AnalyticsModelImpl.class);
		aemContext.request().setAttribute("title", "Promotional Banner");
		model = aemContext.request().adaptTo(AnalyticsModelImpl.class);
		assertEquals("promotional-banner", model.getTitle());
	}

}
