package com.talentacademy.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
class DynamicMediaModelTest {

	private final AemContext ctx = new AemContext();

	private DynamicMediaModel model;

	@Mock
	MockSlingHttpServletRequest request;

	@BeforeEach
	public void setUp() throws Exception {
		ctx.addModelsForClasses(DynamicMediaModel.class);
		ctx.load().json("/com/talentacademy/core/models/impl/DynamicMediaModel.json", "/content");
	}

	/**
	 * Test method for {@link DynamicMediaModel #testImage()}.
	 */
	@Test
	void testImage() {
		ctx.currentResource("/content/asset1");
		ctx.request().setAttribute("assetPath", "/content/asset1");
		ctx.request().setAttribute("desktopWidth", "750");
		ctx.request().setAttribute("mobileWidth", "320");
		model = ctx.request().adaptTo(DynamicMediaModel.class);
		assertEquals("https://example.scene7.com/is/image/example/007_GT_BROWN_157-11?wid=750&hei=1333",
				model.getDesktopImage());
		assertEquals("https://example.scene7.com/is/image/example/007_GT_BROWN_157-11?wid=320&hei=568",
				model.getMobileImage());
	}

	/**
	 * Test method for {@link DynamicMediaModel #testSrc()}.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "/content/asset1", "/content/asset2", "/content/asset3", "/content/asset4",
			"/content/asset6", "/content/asset7", "/content/asset8", "/content/asset9" })
	void testSrc(String arg) {
		ctx.currentResource("/content/asset1");
		ctx.request().setAttribute("assetPath", arg);
		model = ctx.request().adaptTo(DynamicMediaModel.class);
		assertNotNull(model.getSrc());
	}

}
