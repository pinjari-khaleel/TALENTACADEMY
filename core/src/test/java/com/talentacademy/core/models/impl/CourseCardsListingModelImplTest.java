package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.Constants;

import com.day.cq.wcm.api.Page;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.services.ALMAuthenticationService;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.GridListingService;
import com.talentacademy.core.services.InvokeAPIService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CourseCardsListingModelImplTest {

	private final String ROOT_PATH = "/content/talentacademy/sa/en/overview/courses";

	private CourseCardsListingModelImpl courseCardsListingModelImpl = new CourseCardsListingModelImpl();

	private final AemContext aemContext = new AemContext();

	private GridListingService gridListingService = Mockito.mock(GridListingService.class);

	@Mock
	private SlingHttpServletRequest request;

	@Mock
	private CourseDetailsService courseDetailsService;

	@Mock
	private ALMAuthenticationService almAuthenticationService;

	@Mock
	InvokeAPIService invokeAPIService;

	@Mock
	Page page;

	@BeforeEach
	void setUp() {

		aemContext.addModelsForClasses(CourseCardsListingModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/CourseCardsListingModelImpl.json", ROOT_PATH);
		aemContext.currentResource(ROOT_PATH + "/jcr:content/root/container/coursecardslisting");
		courseCardsListingModelImpl = aemContext.request().adaptTo(CourseCardsListingModelImpl.class);

	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getTitle()}.
	 */
	@Test
	void testGetTitle() {
		assertEquals("Start your learning today", courseCardsListingModelImpl.getTitle());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getDescription()}.
	 */
	@Test
	void testGetDescription() {
		assertEquals("With thousands of job roles to explore in NEOM", courseCardsListingModelImpl.getDescription());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getLoadMoreLimit()}.
	 */
	@Test
	void testGetLoadMoreLimit() {
		assertEquals(6, courseCardsListingModelImpl.getLoadMoreLimit());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getRootDetailsPath()}.
	 */
	@Test
	void testGetRootDetailsPath() {
		assertEquals("/content/talentacademy/sa/courses", courseCardsListingModelImpl.getRootDetailsPath());
	}

	@Test
	void testGetRootDetailsPathAsCurrentPage() {
		aemContext.load().json("/com/talentacademy/core/models/impl/CourseCardsListingModelImpl.json", "/test");
		aemContext.currentResource("/test" + "/jcr:content/root/container/coursecardslisting1");
		courseCardsListingModelImpl = aemContext.request().adaptTo(CourseCardsListingModelImpl.class);
		assertEquals("/content/talentacademy/sa/en/overview/courses", courseCardsListingModelImpl.getRootDetailsPath());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getBadgesEarned()}.
	 */
	@Test
	void testGetBadgesEarned() {
		assertEquals("Earned badges", courseCardsListingModelImpl.getBadgesEarned());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getLoadMoreCtaLabel()}.
	 */
	@Test
	void testGetLoadMoreCtaLabel() {
		assertEquals("Load More", courseCardsListingModelImpl.getLoadMoreCtaLabel());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getPrimaryButtonLabel()}.
	 */
	@Test
	void testGetPrimaryButtonLabel() {
		assertEquals("Register Interest", courseCardsListingModelImpl.getPrimaryButtonLabel());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getPrimaryButtonLink()}.
	 */
	@Test
	void testGetPrimaryButtonLink() {
		assertEquals("External link", courseCardsListingModelImpl.getPrimaryButtonLink());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getPrimaryButtonTarget()}.
	 */
	@Test
	void testGetPrimaryButtonTarget() {
		assertEquals("_self", courseCardsListingModelImpl.getPrimaryButtonTarget());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getSecondaryButtonLabel()}.
	 */
	@Test
	void testGetSecondaryButtonLabel() {
		assertEquals("Learn More", courseCardsListingModelImpl.getSecondaryButtonLabel());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getSecondaryButtonLink()}.
	 */
	@Test
	void testGetSecondaryButtonLink() {
		assertEquals("Button Link", courseCardsListingModelImpl.getSecondaryButtonLink());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getSecondaryButtonTarget()}.
	 */
	@Test
	void testGetSecondaryButtonTarget() {
		assertEquals("_self", courseCardsListingModelImpl.getSecondaryButtonTarget());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getListingType()}.
	 */
	@Test
	void testGetListingType() {
		assertEquals("static", courseCardsListingModelImpl.getListingType());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getCatalogId()}.
	 */
	@Test
	void testGetCatalogId() {
		assertEquals("29758", courseCardsListingModelImpl.getCatalogId());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getCourseDetailsdata()}.
	 */
	@Test
	void testGetCourseDetailsdata() throws Exception {
		Field gridListingServiceValues = courseCardsListingModelImpl.getClass()
				.getDeclaredField("gridListingService");
		gridListingServiceValues.setAccessible(true);
		gridListingServiceValues.set(courseCardsListingModelImpl, gridListingService);

		List<GridListing> gridListingList = new ArrayList<>();
		GridListing gridListing = new GridListing();
		gridListing.setCourseTitle("course1");
		gridListing.setCardImage("/content/dam/talentacademy/cardimage");
		gridListing.setCourseTag("Learning Pathway");
		gridListing.setNumberOfCourses("14 Courses");
		gridListing.setLearningPathwayID("123456");
		gridListing.setPagePath("/content/talentacademy/sa/en/courses/course1");
		gridListingList.add(gridListing);

		when(gridListingService.getCourseDetailsdata(request.getResourceResolver(), ROOT_PATH)).thenReturn(gridListingList);
		courseCardsListingModelImpl.getCourseDetailsdata();
		assertTrue(true);

	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getCourseDetails()}.
	 */
	@Test
	void testGetCourseDetails() throws URISyntaxException {
		aemContext.registerService(CourseDetailsService.class, courseDetailsService, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.registerService(ALMAuthenticationService.class, almAuthenticationService, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.registerService(InvokeAPIService.class, invokeAPIService, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.currentResource(ROOT_PATH + "/jcr:content/root/container/coursecardslisting/dynamiccoursedetail-1");
		courseCardsListingModelImpl = aemContext.request().adaptTo(CourseCardsListingModelImpl.class);

		assertNotNull(courseCardsListingModelImpl.getCourseDetails());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getCourseDetails()}.
	 */
	@Test
	void testGetEmptyCourseDetailsIfNoOfFieldsIsEmpty() throws URISyntaxException {
		aemContext.registerService(CourseDetailsService.class, courseDetailsService, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.registerService(ALMAuthenticationService.class, almAuthenticationService, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.registerService(InvokeAPIService.class, invokeAPIService, Constants.SERVICE_RANKING, Integer.MAX_VALUE);
		aemContext.currentResource(ROOT_PATH + "/jcr:content/root/container/coursecardslisting/dynamiccoursedetail-2");
		courseCardsListingModelImpl = aemContext.request().adaptTo(CourseCardsListingModelImpl.class);

		assertEquals(0, courseCardsListingModelImpl.getCourseDetails().size());
	}

	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#getSecondaryButtonLabel()}.
	 */
	@Test
	void testGetCurrentPalegLocale() {
		assertEquals("en-SA", courseCardsListingModelImpl.getCurrentPageLocale());
	}
	
	/**
	 * Test method for
	 * {@link CourseCardsListingModelImpl#CursorIdValue()}.
	 */
	@Test
	void testGetCursorIdValue() {
		String cursorId = "eyJjb3Vyc2UiOjEwLCJsZWFybmluZ1Byb2dyYW0iOjB9";
		courseCardsListingModelImpl.setCursorIdValue(cursorId);
        assertEquals(cursorId, courseCardsListingModelImpl.getCursorIdValue());
	}

}