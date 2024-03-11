package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
class CarouselContainerModelImplTest {

	private final AemContext aemContext = new AemContext();
	private AemContext context = new AemContext();
	private static final String CAROUSEL_NODE = "carouselcontailer";
	private static final String NUMBER_OF_FIELDS = "noofields";
	CarouselContainerModelImpl carouselModelImpl;

	/**
	 * setUp method for
	 * {@link CarouselContainerModelImpl}.
	 */
	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(CarouselContainerModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/CarouselContainerModelImpl.json", "/component");
		aemContext.currentResource("/component/careercarousel");
		carouselModelImpl = aemContext.request().adaptTo(CarouselContainerModelImpl.class);
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getCaption()}.
	 */
	@Test
	void testGetCaption() {
		assertEquals("Caption", carouselModelImpl.getCaption());
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getCarouselTitle()}.
	 */
	@Test
	void testGetCarouselTitle() {
		assertEquals("In-demand careers in 2025", carouselModelImpl.getCarouselTitle());
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getCarouselDescription()}.
	 */
	@Test
	void testGetCarouselDescription() {
		assertEquals("With thousands of job roles to explore in NEOM", carouselModelImpl.getCarouselDescription());
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getButtonLabel()}.
	 */
	@Test
	void testGetButtonLabel() {
		assertEquals("Explore careers in NEOM", carouselModelImpl.getButtonLabel());
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getButtonLink()}.
	 */
	@Test
	void testGetButtonLink() {
		assertEquals("/content/talentacademy/sa/en/page", carouselModelImpl.getButtonLink());
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getCarouselType()}.
	 */
	@Test
	void testGetCarouselType() {
		assertEquals("carousel", carouselModelImpl.getCarouselType());
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getNumberOfFields()}.
	 */
	@Test
	void testGetNumberOfFields() {
		assertEquals(0, carouselModelImpl.getNumberOfFields());
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getList()}.
	 */
	@Test
	void testColumns() throws Exception {
		Page page = context.create().page("/content/talentacademy");
		Resource resource = context.create().resource(page, CAROUSEL_NODE, JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY,
				"talentacademy/components/content/carouselcontainer", NUMBER_OF_FIELDS, "2");
		carouselModelImpl = resource.adaptTo(CarouselContainerModelImpl.class);
		assertEquals(2, carouselModelImpl.getList().size());
	}

	/**
	 * Test method for
	 * {@link CarouselContainerModelImpl #getOpenInNewTab()}.
	 */
	@Test
	void testGetOpenInNewTab() {
		assertEquals("true", carouselModelImpl.getOpenInNewTab());
	}

}
