package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.sling.api.resource.Resource;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CareerCardModelImplTest {

	private CareerCardModelImpl careerCardModelImpl;

	private final AemContext aemContext = new AemContext();

	/**
	 * setUp method for
	 * {@link CareerCardModelImpl}.
	 */
	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(CareerCardModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/CareerCardModelImpl.json", "/component");
		aemContext.currentResource("/component/careercard");
		careerCardModelImpl = aemContext.request().adaptTo(CareerCardModelImpl.class);

	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getJobTitle()}.
	 */
	@Test
	void testGetJobTitle() {
		assertEquals("Travel Advisor", careerCardModelImpl.getJobTitle());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getCareerLabel()}.
	 */
	@Test
	void testGetCareerLabel() {
		assertEquals("Learning Pathway", careerCardModelImpl.getCareerLabel());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getTileImage()}.
	 */
	@Test
	void testGetTileImage() {
		assertEquals("/content/dam/talentacademy/tileimage.jpg", careerCardModelImpl.getTileImage());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getAltTextForImage()}.
	 */
	@Test
	void testGetAltTextForImage() {
		assertEquals("Alt text for Image", careerCardModelImpl.getAltTextForImage());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getRecruitmentStatus()}.
	 */
	@Test
	void testGetRecruitmentStatus() {
		assertEquals("Actively Recruiting", careerCardModelImpl.getRecruitmentStatus());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getJobRoleType()}.
	 */
	@Test
	void testGetJobRoleType() {
		assertEquals("Tourism", careerCardModelImpl.getJobRoleType());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getExperienceLevel()}.
	 */
	@Test
	void testGetExperienceLevel() {
		assertEquals("3-4 years EXPERIENCE", careerCardModelImpl.getExperienceLevel());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getJobOpening()}.
	 */
	@Test
	void testGetJobOpening() {
		assertEquals("1100", careerCardModelImpl.getJobOpening());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getExpectedDemand()}.
	 */
	@Test
	void testGetExpectedDemand() {
		assertEquals("Expected demand", careerCardModelImpl.getExpectedDemand());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getBadgesWeRecommend()}.
	 */
	@Test
	void testGetBadgesWeRecommend() {
		assertEquals("Badges we recommend", careerCardModelImpl.getBadgesWeRecommend());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getBadgeList()}.
	 */
	@Test
	void testGetBadgeList() {
		Resource res = aemContext.currentResource("/component/careercard/badgeList");
		careerCardModelImpl.getBadgeList();
		assertNotNull(res);
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getKnowMoreCTALabel()}.
	 */
	@Test
	void testGetKnowMoreCTALabel() {
		assertEquals("Know more", careerCardModelImpl.getKnowMoreCTALabel());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getKnowMoreCTALink()}.
	 */
	@Test
	void testGetKnowMoreCTALink() {
		assertEquals("/content/talentacademy/sa/en/career-in-neom", careerCardModelImpl.getKnowMoreCTALink());
	}

	/**
	 * Test method for
	 * {@link CareerCardModelImpl #getOpenInNewTab()}.
	 */
	@Test
	void testGetOpenInNewTab() {
		assertEquals("true", careerCardModelImpl.getOpenInNewTab());
	}

}
