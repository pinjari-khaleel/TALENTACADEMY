package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.Constants;

import com.talentacademy.core.models.SocialMediaLinks;
import com.talentacademy.core.services.ALMAuthenticationService;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.InvokeAPIService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URISyntaxException;

/**
 * Single Card Wrapper Test Class.
 **/
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class SingleCardWrapperModelTest {

	private final AemContext aemContext = new AemContext();

	private final String ROOT_PATH = "/content/talentacademy/us/en/test";

	SingleCardWrapperModelImpl singleCardWrapperModel;

	@Mock
	CourseDetailsService courseDetailsService;

	@Mock
	ALMAuthenticationService almAuthenticationService;

	@Mock
	InvokeAPIService invokeAPIService;

	@BeforeEach
	public void setup() {
		aemContext.addModelsForClasses(SingleCardWrapperModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/SingleCardTest.json", ROOT_PATH);
		aemContext.currentResource(ROOT_PATH);
		singleCardWrapperModel = aemContext.currentResource().adaptTo(SingleCardWrapperModelImpl.class);
	}

	/**
	 * Test to Fetch Course Details
	 */
	@Test
	void fetchSingleCardWrapperDetails() throws URISyntaxException {
		aemContext.registerService(CourseDetailsService.class, courseDetailsService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(ALMAuthenticationService.class, almAuthenticationService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(InvokeAPIService.class, invokeAPIService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext
				.currentResource(ROOT_PATH + "/jcr:content/root/container/carouselcontainer/singleCardWrapperDetails");
		singleCardWrapperModel = aemContext.request().adaptTo(SingleCardWrapperModelImpl.class);

		assertNotNull(singleCardWrapperModel.getSingleCardWrapperDetails());
		assertEquals("en-US", singleCardWrapperModel.getCurrentPageLocale());
	}


	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.singleCardWrapperModelImpl#getCourseid()}.
	 */
	@Test
	void testGetCourseid() {
		assertEquals("course:123", singleCardWrapperModel.getCourseid());
	}

	/**
	 * Test method for
	 * {@link com.talentacademy.core.models.impl.singleCardWrapperModelImpl#getSingleCardVariation()}.
	 */
	@Test
	void testGetSingleCardVariation() {
		assertEquals("card Variation", singleCardWrapperModel.getSingleCardVariation());
	}

}