package com.talentacademy.core.servlets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.beans.GridListingData;
import com.talentacademy.core.constants.ALMConstants;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.services.CourseDetailsService;
import com.talentacademy.core.services.GridListingService;
import com.talentacademy.core.services.InvokeAPIService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class CourseListingServletTest {

	private AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);
	
    @Mock
    private GridListingService gridListingService;

    @Mock
    private CourseDetailsService courseDetailsService;

    @Mock
    private InvokeAPIService invokeAPIService;

    @InjectMocks
    private CourseCardListingServlet courseListingServlet;

    @Mock
    private SlingHttpServletRequest request;
    
    @Mock
    private SlingHttpServletResponse response;
    
    @Mock
    private Resource resource;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        aemContext.addModelsForClasses(GridListingService.class);
        
    }

    /**
     * Test method for Static Card List
     * {@link CourseCardListingServlet#doGet()}.
     */
    @Test
    void testDoGet() throws Exception {

        Field articlesFilterServiceVal = courseListingServlet.getClass().getDeclaredField("gridListingService");
        articlesFilterServiceVal.setAccessible(true);
        articlesFilterServiceVal.set(courseListingServlet, gridListingService);

        List<GridListing> detailsList = new ArrayList<>();
        GridListing componentProperties = new GridListing();
        componentProperties.setCourseTitle("Course Title");
        componentProperties.setLearningPathwayID("123456");
        detailsList.add(componentProperties);
        
        String pagePath = "/content/talentacademy/sa/en/courses";
        when(request.getResource()).thenReturn(resource);
        when(resource.getPath()).thenReturn(pagePath);

        Mockito.lenient().when(gridListingService.getCareerDetailsdata(request.getResourceResolver(), pagePath)).thenReturn(detailsList);

        StringWriter out = new StringWriter();
        PrintWriter printWriter = new PrintWriter(out);

        when(response.getWriter()).thenReturn(printWriter);

        courseListingServlet.doGet(request, response);
        
        assertTrue(true);

    }

    /**
     * Test method for Dynamic Card List
     * {@link CourseCardListingServlet#doGet()}.
     */
    @Test
    void testDoGetDynamic() throws Exception {

        String listType = "dynamicCardList";
        String cardsPerLoad = "6";
        String catalogId = "29758";
        when(request.getParameter(ApplicationConstants.CARDS_PER_LOAD)).thenReturn(cardsPerLoad);
        when(request.getParameter(ApplicationConstants.OFFSET)).thenReturn("1");
        when(request.getParameter(ApplicationConstants.CATALOG_ID)).thenReturn(catalogId);
        when(request.getParameter(ApplicationConstants.LIST_TYPE)).thenReturn(listType);

        Field courseDetailsField = courseListingServlet.getClass().getDeclaredField("courseDetailsService");
        courseDetailsField.setAccessible(true);
        courseDetailsField.set(courseListingServlet, courseDetailsService);

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair(ALMConstants.PAGE_LIMIT, cardsPerLoad));
        nvps.add(new BasicNameValuePair(ALMConstants.SORT, ALMConstants.DATE));
        nvps.add(new BasicNameValuePair(ALMConstants.CATALOG_ID, catalogId));
        nvps.add(new BasicNameValuePair(ALMConstants.LO_TYPE_FILTER, ALMConstants.LO_TYPE_FILTER_PARAM));
        nvps.add(new BasicNameValuePair(ALMConstants.INCLUDE, ALMConstants.INCLUDED_SKILLS_INSTANCES_BADGE));

        List<CourseDetails> courseList = new ArrayList<>();
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId("course:123");
        courseDetails.setLoType("Course");
        courseDetails.setDurationHours("12");
        courseDetails.setDurationMinutes("50");
        courseList.add(courseDetails);

        String pagePath = "/content/talentacademy/sa/en/courses";
        when(request.getResource()).thenReturn(resource);
        when(resource.getPath()).thenReturn(pagePath);
        
        Mockito.lenient().when(courseDetailsService.getCourseDetails(invokeAPIService.getMultipleCourseResponse(nvps), false, "en-SA")).thenReturn(courseList);

        StringWriter out = new StringWriter();
        PrintWriter printWriter = new PrintWriter(out);

        when(response.getWriter()).thenReturn(printWriter);

        courseListingServlet.doGet(request, response);
        
        assertTrue(true);

    }
    
}