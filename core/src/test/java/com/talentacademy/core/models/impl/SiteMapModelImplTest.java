package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talentacademy.core.models.SiteMapModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SiteMapModelImplTest {
	
	private final AemContext ctx = new AemContext(ResourceResolverType.JCR_MOCK);
	
	private SiteMapModel siteMapModel;

	@BeforeEach
	public void setUp() throws Exception {
		ctx.addModelsForClasses(SiteMapModelImpl.class);
		ctx.load().json("/com/talentacademy/core/models/impl/SiteMapModelTest.json",
				"/content/talentacademy/language-masters/en");
		ctx.currentResource("/content/talentacademy/language-masters/en/jcr:content/root/responsivegrid/sitemap");
		siteMapModel = ctx.request().adaptTo(SiteMapModel.class);
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.siteMapModel#getHeading()}.
	 */
	@Test
	void testGetHeading() {
		final String expectedText = "Sitemap";
		String actualText = siteMapModel.getHeading();
		assertNotNull(actualText);
		assertEquals(expectedText, actualText);
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.siteMapModel#getPagePath()}.
	 */
	@Test
	void testGetPagePath() {
		assertNotNull(siteMapModel.getPagePath());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.siteMapModel#getRootPage()}.
	 */
	@Test
	void testGetRootPage() {
		final String expectedText = "/content/talentacademy/language-masters/en";
		String actualText = siteMapModel.getRootPage();
		assertNotNull(actualText);
		assertEquals(expectedText, actualText);
	}
}
