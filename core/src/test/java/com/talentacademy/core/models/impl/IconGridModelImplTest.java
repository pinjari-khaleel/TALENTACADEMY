package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.talentacademy.core.models.IconGridList;
import com.talentacademy.core.models.SocialMediaLinks;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
class IconGridModelImplTest {

	private final AemContext aemContext = new AemContext();

	private final String ROOT_PATH = "/content/talentacademy/us/en/test";

	IconGridModelImpl iconGridModelImpl;

	/**
	 * Setup method for {com.talentacademy.core.models.impl.IconGridModelImpl}
	 */
	@BeforeEach
	public void setup() {
		aemContext.addModelsForClasses(IconGridModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/IconGridTest.json", ROOT_PATH);
		aemContext.currentResource(ROOT_PATH);
		iconGridModelImpl = aemContext.currentResource().adaptTo(IconGridModelImpl.class);
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.IconGridModelImpl#getGridType()}.
	 */
	@Test
	void testGetCarouselType() {
		assertEquals("grid", iconGridModelImpl.getGridType());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.IconGridModelImpl#getCaption()}.
	 */
	@Test
	void testGetCaption() {
		assertEquals("Caption", iconGridModelImpl.getCaption());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.IconGridModelImpl#getIconGridTitle()}.
	 */
	@Test
	void testGetIconGridTitle() {
		assertEquals("Icon Grigd Title", iconGridModelImpl.getIconGridTitle());
	}

	/**
	 * Test method for
	 * {@getIconGridDescription com.talentacademy.core.models.impl.PeopleCarouselModelImpl#getIconGridDescription()}.
	 */
	@Test
	void testGetIconGridDescription() {
		assertEquals("Icon Grigd Desceiption", iconGridModelImpl.getIconGridDescription());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.IconGridModelImpl#getGridIconsList()}.
	 */
	@Test
	void testBadgesList() {
		for (IconGridList field : iconGridModelImpl.getGridIconsList()) {
			assertEquals("/content/dam/dummyicon.png", field.getIcon());
			assertEquals("Icon Label", field.getIconText());
			assertEquals("Alt Text", field.getAltText());
		}
	}

}
