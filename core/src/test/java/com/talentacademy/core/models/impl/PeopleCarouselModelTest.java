package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.talentacademy.core.models.PeopleCarouselList;
import com.talentacademy.core.models.SocialMediaLinks;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PeopleCarouselModelTest {

	private final AemContext aemContext = new AemContext();

	private final String ROOT_PATH = "/content/talentacademy/us/en/test";

	PeopleCarouselModelImpl peopleCarouselModel;

	/**
	 * Setup method for {com.talentacademy.core.models.impl.PeopleCarouselModelImpl}
	 */
	@BeforeEach
	public void setup() {
		aemContext.addModelsForClasses(PeopleCarouselModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/PeopleCarouselTest.json", ROOT_PATH);
		aemContext.currentResource(ROOT_PATH);
		peopleCarouselModel = aemContext.currentResource().adaptTo(PeopleCarouselModelImpl.class);
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.PeopleCarouselModelImpl#getCaption()}.
	 */
	@Test
	void testGetCaption() {
		assertEquals("Caption", peopleCarouselModel.getCaption());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.PeopleCarouselModelImpl#getDescription()}.
	 */
	@Test
	void testGetCarouselDescription() {
		assertEquals("Description", peopleCarouselModel.getDescription());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.PeopleCarouselModelImpl#getPeopleCarouselList()}.
	 */
	@Test
	void testGetPeopleCarouselList() {
		for (PeopleCarouselList field : peopleCarouselModel.getPeopleCarouselList()) {
			assertEquals("Alt Text", field.getAltText());
			assertEquals("Test Description", field.getDescription());
			assertEquals("/content/dam/dummyicon.png", field.getImage());
			assertEquals("Neom", field.getName());
			assertEquals("Director", field.getRole());
		}
	}

}