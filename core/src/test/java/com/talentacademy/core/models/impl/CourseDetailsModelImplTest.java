package com.talentacademy.core.models.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URISyntaxException;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.Constants;

import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.InvokeAPIService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CourseDetailsModelImplTest {

	private final AemContext aemContext = new AemContext();

	private CourseDetailsModelImpl model;
	
	@Mock
	CourseDetailsService courseDetailsService;

	@Mock
	InvokeAPIService invokeAPIService;
	
	@Mock
	private SlingHttpServletRequest request;
	
	private final String ROOT_PATH = "/content/talentacademy/us/en/test";

	/**
	 * setUp method for
	 * {@link CourseDetailsModelImpl}.
	 */
	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(CourseDetailsModelImpl.class);
		aemContext.load().json("/com/talentacademy/core/models/impl/CourseDetailsModelImpl.json", ROOT_PATH);
		aemContext.currentResource(ROOT_PATH + "/coursedetails");
		model = aemContext.request().adaptTo(CourseDetailsModelImpl.class);
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getOverviewRichText()}.
	 */
	@Test
	void testOverviewRichText() {
		assertEquals("dummy overview text", model.getOverviewRichText());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getSkillsLabel()}.
	 */
	@Test
	void testSkillsLabel() {
		assertEquals("skills", model.getSkillsLabel());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getSkillsLabel()}.
	 */
	@Test
	void testBadgesLabel() {
		assertEquals("badges", model.getBadgesLabel());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getFormatLabel()}.
	 */
	@Test
	void testFormatLabel() {
		assertEquals("format", model.getFormatLabel());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getDurationLabel()}.
	 */
	@Test
	void testDurationLabel() {
		assertEquals("duration", model.getDurationLabel());
	}
	
	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getDuration()}.
	 */
	@Test
	void testDuration() {
		assertEquals("15 MINS", model.getDuration());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getInfoctaLabel()}.
	 */
	@Test
	void testInfoctaLabel() {
		assertEquals("enroll now", model.getInfoctaLabel());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getInfoctaURL()}.
	 */
	@Test
	void testinfoctaURL() {
		assertEquals("dummy url", model.getInfoctaURL());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getInfotarget()}.
	 */
	@Test
	void testinfoTarget() {
		assertEquals("_blank", model.getInfotarget());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getAccHeader()}.
	 */
	@Test
	void testAccHeader() {
		assertEquals("dummy accheader", model.getAccHeader());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getHeader()}.
	 */
	@Test
	void testHeader() {
		assertEquals("curriculum", model.getHeader());
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getCourseDetails()}.
	 * @throws URISyntaxException 
	 */
	@Test
	void testCourseDetails() throws URISyntaxException {
		List<CourseDetails> courseDetails = model.getCourseDetails();
		assertEquals("Destination knowledge", courseDetails.get(0).getSkillsList().get(0));
		assertEquals("Instructor-led", courseDetails.get(0).getFormatsList().get(0));
	}
	
	/**
	 * Test method for {@link DynamicCourseDetailsModelImpl #getCourseDetails()}.
	 */
	@Test
	void testDynamicCourseDetails() throws URISyntaxException {

		aemContext.registerService(CourseDetailsService.class, courseDetailsService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(InvokeAPIService.class, invokeAPIService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.currentResource(ROOT_PATH + "/coursedetailsdynamic/jcr:content/root/container/coursedetailsdynamic");
		aemContext.request().setAttribute("type", "dynamic");
		aemContext.request().addRequestParameter("loid", "course:123");
		model = aemContext.request().adaptTo(CourseDetailsModelImpl.class);
		assertNotNull(model.getCourseDetails());
		assertEquals("en-US", model.getCurrentPageLocale());
		assertEquals("en", model.getCurrentPageLanguage());
		assertEquals("Duration", model.getDurationLabel());
	}
	
	/**
	 * Test method for {@link DynamicCourseDetailsModelImpl #testCoursesLabel()}.
	 */
	@Test
	void testCoursesLabel() throws URISyntaxException {

		aemContext.registerService(CourseDetailsService.class, courseDetailsService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.registerService(InvokeAPIService.class, invokeAPIService, Constants.SERVICE_RANKING,
				Integer.MAX_VALUE);
		aemContext.currentResource(ROOT_PATH + "/coursedetailsdynamic/jcr:content/root/container/coursedetailsdynamic");
		aemContext.request().setAttribute("type", "dynamic");
		aemContext.request().addRequestParameter("loid", "learningProgram:123");
		model = aemContext.request().adaptTo(CourseDetailsModelImpl.class);
		assertNotNull(model.getCourseDetails());
		assertEquals("Courses", model.getDurationLabel());
	}
	
	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getAccreditations()}.
	 */
	@Test
	void testAccrediations() {
		Resource acrediations = model.getAccreditations();
		assertNotNull(acrediations);
	}

	/**
	 * Test method for
	 * {@link CourseDetailsModelImpl #getLearningPathwayID()}.
	 */
	@Test
	void testLearningPathwayID() {
		assertEquals("learningPathwayID:123", model.getLearningPathwayID());
	}
}
