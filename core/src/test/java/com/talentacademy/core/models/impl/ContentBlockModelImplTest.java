package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.talentacademy.core.beans.ContentDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.talentacademy.core.models.ContentBlockModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
class ContentBlockModelImplTest {

	private final AemContext aemContext = new AemContext();

	private ContentBlockModel contentBlockModel;

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(ContentBlockModel.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/ContentBlockModelImpl.json", "/component");
	}

	/**
	 * Test method for {@link ContentBlockModel#getHeader()}.
	 */
	@Test
	void testHeader() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("Overview", contentBlockModel.getHeader());
	}

	/**
	 * Test method for {@link ContentBlockModel#getDescription()}.
	 */
	@Test
	void testDescription() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("dummy description", contentBlockModel.getDescription());
	}

	/**
	 * Test method for {@link ContentBlockModel#getFileReference()}.
	 */
	@Test
	void testImage() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("/content/dam/dummy/image.png", contentBlockModel.getFileReference());
	}

	/**
	 * Test method for {@link ContentBlockModel#getAltText()}.
	 */
	@Test
	void testAltText() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("alt text for image", contentBlockModel.getAltText());
	}

	/**
	 * Test method for {@link ContentBlockModel#getContentDetails()}.
	 */
	@Test
	void testContentDetails() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		for (ContentDetails field : contentBlockModel.getContentDetails()) {
			assertEquals("Sector Focus", field.getLabel());
			assertEquals("All Sectors", field.getValue());
		}
	}

	/**
	 * Test method for {@link ContentBlockModel#getShortDescription()}.
	 */
	@Test
	void testShortDescription() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("dummy short description", contentBlockModel.getShortDescription());
	}

	/**
	 * Test method for {@link ContentBlockModel#getFindOutMoreLabel()}.
	 */
	@Test
	void testFindOutMoreLabel() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("Find out more", contentBlockModel.getFindOutMoreLabel());
	}

	/**
	 * Test method for {@link ContentBlockModel#getFindOutMoreLink()}.
	 */
	@Test
	void testFindOutMoreLink() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("/content/ta/dummy", contentBlockModel.getFindOutMoreLink());
	}

	/**
	 * Test method for {@link ContentBlockModel#getPretitle()}.
	 */
	@Test
	void testPretitle() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("Education", contentBlockModel.getPretitle());
	}

	/**
	 * Test method for {@link ContentBlockModel#getTitle()}.
	 */
	@Test
	void testTitle() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("neom talent academy", contentBlockModel.getTitle());
	}

	/**
	 * Test method for {@link ContentBlockModel#getDescriptionText()}.
	 */
	@Test
	void testDescriptionText() {
		aemContext.currentResource("/component/contentblock");
		contentBlockModel = aemContext.request().adaptTo(ContentBlockModel.class);
		assertEquals("welcome to TA academy", contentBlockModel.getDescriptionText());
	}

}