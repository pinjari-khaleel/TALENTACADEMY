package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talentacademy.core.models.SocialMediaLinks;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CourseCardModelTest {

	private final AemContext aemContext = new AemContext();

	private final String ROOT_PATH = "/content/talentacademy/us/en/test";

	CourseCardModelImpl courseCardModel;

	/**
	 * Setup method for {com.talentacademy.core.models.impl.CourseCardModelImpl}
	 */
	@BeforeEach
	public void setup() {
		aemContext.addModelsForClasses(CourseCardModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/CourseCardTest.json", ROOT_PATH);
		aemContext.currentResource(ROOT_PATH);
		courseCardModel = aemContext.currentResource().adaptTo(CourseCardModelImpl.class);
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getCarouselImage()}.
	 */
	@Test
	void testCarouselImage() {
		assertEquals("/content/dam/talentacademy/construction.jpg", courseCardModel.getCarouselImage());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getAltTextForImage()}.
	 */
	@Test
	void testAltTextForImage() {
		assertEquals("Image Alt Text", courseCardModel.getAltTextForImage());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getCarouselImageLabelText()}.
	 */
	@Test
	void testCarouselImageLabelText() {
		assertEquals("Testing Learn Path", courseCardModel.getCarouselImageLabelText());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getHeadingText()}.
	 */
	@Test
	void testHeadingText() {
		assertEquals("Testing Heading", courseCardModel.getHeadingText());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getCoursesCount()}.
	 */
	@Test
	void testCoursesCount() {
		assertEquals("14 Course", courseCardModel.getCoursesCount());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getBadgesTitle()}.
	 */
	@Test
	void testBadgesTitle() {
		assertEquals("Earn these Badges", courseCardModel.getBadgesTitle());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getPrimaryButtonLink()}.
	 */
	@Test
	void testPrimaryButtonLink() {
		assertEquals("/content/talentacademy/language-masters/en/about-us.html",
				courseCardModel.getPrimaryButtonLink());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getPrimaryButtonLabel()}.
	 */
	@Test
	void testPrimaryButtonLabel() {
		assertEquals("Primary", courseCardModel.getPrimaryButtonLabel());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getPrimaryButtonTarget()}.
	 */
	@Test
	void testPrimaryButtonTarget() {
		assertEquals("blank", courseCardModel.getPrimaryButtonTarget());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getSecondaryButtonLink()}.
	 */
	@Test
	void testSecondaryButtonLink() {
		assertEquals("/content/talentacademy/language-masters/en/about-us.html",
				courseCardModel.getSecondaryButtonLink());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getSecondaryButtonLabel()}.
	 */
	@Test
	void testSecondaryButtonLabel() {
		assertEquals("Secondary", courseCardModel.getSecondaryButtonLabel());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getSecondaryButtonTarget()}.
	 */
	@Test
	void testSecondaryButtonTarget() {
		assertEquals("blank", courseCardModel.getSecondaryButtonTarget());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getBadgesList()}.
	 */
	@Test
	void testBadgesList() {
		for (SocialMediaLinks field : courseCardModel.getBadgesList()) {
			assertEquals("/content/dam/talentacademy/icons/facebook.svg", field.getIcon());
		}
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.CourseCardModelImpl#getLearningPathwayID()}.
	 */
	@Test
	void testLearningPathwayID() {
		assertEquals("learningPathwayID:123", courseCardModel.getLearningPathwayID());
	}

}