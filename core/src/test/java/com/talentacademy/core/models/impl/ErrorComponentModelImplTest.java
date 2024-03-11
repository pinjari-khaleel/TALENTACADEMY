package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.talentacademy.core.models.ErrorComponentModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
class ErrorComponentModelImplTest {

	private final AemContext aemContext = new AemContext();

	private ErrorComponentModel errorComponentModel;

	/**
	 * setUp method for
	 * {@link ErrorComponentModel}.
	 */
	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(ErrorComponentModel.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/ErrorComponentModelImpl.json", "/component");
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getPretitle()}.
	 */
	@Test
	void testPreTitle() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("oops", errorComponentModel.getPretitle());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getTitle()}.
	 */
	@Test
	void testTitle() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("Something went wrong", errorComponentModel.getTitle());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getDescription()}.
	 */
	@Test
	void testDescription() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("dummy description", errorComponentModel.getDescription());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getArPretitle()}.
	 */
	@Test
	void testArPreTitle() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("dummy ar pretitle", errorComponentModel.getArPretitle());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getArTitle()}.
	 */
	@Test
	void testArTitle() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("dummy ar title", errorComponentModel.getArTitle());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getArDescription()}.
	 */
	@Test
	void testArDescription() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("dummy ar description", errorComponentModel.getArDescription());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getLogo()}.
	 */
	@Test
	void testLogo() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("/content/dam/dummy/logo.png", errorComponentModel.getLogo());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getLogoAltText()}.
	 */
	@Test
	void testLogoAltText() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("alt text for logo", errorComponentModel.getLogoAltText());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getRedirectURL()}.
	 */
	@Test
	void testRedirectURL() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("/content/ta/redirect", errorComponentModel.getRedirectURL());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getFileReference()}.
	 */
	@Test
	void testBgImage() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("/content/dam/dummy/image.png", errorComponentModel.getFileReference());
	}

	/**
	 * Test method for
	 * {@link ErrorComponentModel #getAltText()}.
	 */
	@Test
	void testAltText() {
		aemContext.currentResource("/component/error");
		errorComponentModel = aemContext.request().adaptTo(ErrorComponentModel.class);
		assertEquals("alt text for image", errorComponentModel.getAltText());
	}

}
