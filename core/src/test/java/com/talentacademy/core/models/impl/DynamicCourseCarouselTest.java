package com.talentacademy.core.models.impl;

import com.talentacademy.core.services.ALMAuthenticationService;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.InvokeAPIService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.Constants;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class DynamicCourseCarouselTest {
	private final AemContext aemContext = new AemContext();

	private final String ROOT_PATH = "/content/talentacademy/us/en/home";

	DynamicCourseCarouselModelImpl dynamicCourseCarouselModel;

	@Mock
	CourseDetailsService courseDetailsService;

	@Mock
	ALMAuthenticationService almAuthenticationService;

	@Mock
	InvokeAPIService invokeAPIService;

	/**
	 * setUp to load before test method
	 */
	@BeforeEach
	public void setup() {
		aemContext.addModelsForClasses(DynamicCourseCarouselModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/DynamicCourseCarousel.json", ROOT_PATH);
	}

	/**
	 * Test to Fetch Course Details
	 */
	@Test
	void fetchCourseDetails() throws URISyntaxException {
		aemContext.registerService(CourseDetailsService.class, courseDetailsService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(ALMAuthenticationService.class, almAuthenticationService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(InvokeAPIService.class, invokeAPIService, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.currentResource(ROOT_PATH + "/jcr:content/root/container/carouselcontainer/dynamiccourse");
		dynamicCourseCarouselModel = aemContext.request().adaptTo(DynamicCourseCarouselModelImpl.class);

		assertNotNull(dynamicCourseCarouselModel.getCourseDetails());
		assertEquals("en-US", dynamicCourseCarouselModel.getCurrentPageLocale());
	}

	/**
	 * Test the Course Details If Number of Slides Is Empty
	 */
	@Test
	void fetchEmptyCourseDetailsIfNoOfFieldsIsEmpty() throws URISyntaxException {
		aemContext.registerService(CourseDetailsService.class, courseDetailsService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(ALMAuthenticationService.class, almAuthenticationService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(InvokeAPIService.class, invokeAPIService, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.currentResource(ROOT_PATH + "/jcr:content/root/container/carouselcontainer-2/dynamiccourse");
		dynamicCourseCarouselModel = aemContext.request().adaptTo(DynamicCourseCarouselModelImpl.class);

		assertEquals(0, dynamicCourseCarouselModel.getCourseDetails().size());
	}
	
	/**
	 * Test the Learning Pathway Course Details
	 */
	@Test
	void fetchLearningPathwayCourses() throws URISyntaxException, IOException {
		aemContext.registerService(CourseDetailsService.class, courseDetailsService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(ALMAuthenticationService.class, almAuthenticationService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(InvokeAPIService.class, invokeAPIService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.currentResource(ROOT_PATH + "/jcr:content/root/container/carouselcontainer/dynamiccourse");
		aemContext.request().addRequestParameter("loid", "learningProgram:123");
		dynamicCourseCarouselModel = aemContext.request().adaptTo(DynamicCourseCarouselModelImpl.class);
		String response = new String(Files.readAllBytes(
				Paths.get("src/test/resources/com/talentacademy/core/models/impl/LearningPathwayDetails.json")));
		when(invokeAPIService.getSingleCourseResponse(Mockito.anyString(), Mockito.anyList())).thenReturn(response);
		assertNotNull(dynamicCourseCarouselModel.getCourseDetails());
		assertEquals("learningProgram:123", dynamicCourseCarouselModel.getLoId());
	}
}
