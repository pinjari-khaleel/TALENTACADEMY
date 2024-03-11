package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.TabSliderDetails;
import com.talentacademy.core.models.TabSliderModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class of Tab Slider Model
 */
@ExtendWith({ AemContextExtension.class })
class TabSliderModelImplTest {

	private final AemContext aemContext = new AemContext();

	private TabSliderModel tabSliderModel;

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(TabSliderModel.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/TabSliderModelImpl.json", "/component");
	}

	/**
	 * Test method for
	 * {@link TabSliderModelImpl#getHeader()}.
	 */
	@Test
	void testHeader() {
		aemContext.currentResource("/component/tabslider");
		tabSliderModel = aemContext.request().adaptTo(TabSliderModel.class);
		assertEquals("Test Header", tabSliderModel.getHeader());
	}

	/**
	 * Test method for
	 * {@link TabSliderModelImpl#getTabSliderDetails()}.
	 */
	@Test
	void testTabSliderDetails() {
		aemContext.currentResource("/component/tabslider");
		tabSliderModel = aemContext.request().adaptTo(TabSliderModel.class);
		for (TabSliderDetails field : tabSliderModel.getTabSliderDetails()) {
			assertEquals("Test Tab Name", field.getTabName());
			assertEquals("Test Title", field.getTitle());
			assertEquals("Test Description", field.getDescription());
			assertEquals("/content/dam/test.jpg",field.getImage());
			assertEquals("Test Image", field.getImageAltText());
		}
	}
}
