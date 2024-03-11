package com.talentacademy.core.servlets;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.services.GridListingService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CareerListingServletTest {

    private AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

    @Mock
    private GridListingService gridListingService;

    @InjectMocks
    private CareerCardListingServlet careerListingServlet;

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
     * Test method for
     * {@link CareerCardListingServlet#doGet()}.
     */
    @Test
    void testDoGet() throws Exception {

        request.getRequestParameter("filters");

        Field articlesFilterServiceVal = careerListingServlet.getClass().getDeclaredField("gridListingService");
        articlesFilterServiceVal.setAccessible(true);
        articlesFilterServiceVal.set(careerListingServlet, gridListingService);

        List<GridListing> detailsList = new ArrayList<>();
        GridListing componentProperties = new GridListing();
        componentProperties.setJobTitle("Travel Advisor");
        componentProperties.setSector("Tourism");
        detailsList.add(componentProperties);

        String pagePath = "/content/talentacademy/sa/en/future-careers";

        when(request.getResource()).thenReturn(resource);
        when(resource.getPath()).thenReturn(pagePath);
        when(gridListingService.getCareerDetailsdata(request.getResourceResolver(), pagePath)).thenReturn(detailsList);

        StringWriter out = new StringWriter();
        PrintWriter printWriter = new PrintWriter(out);

        when(response.getWriter()).thenReturn(printWriter);

        careerListingServlet.doGet(request, response);

        assertTrue(true);

    }

    /**
     * Test method for
     * {@link CareerCardListingServlet#getCardData()}.
     */
    @Test
    void testGetCardData() {

        request.getRequestParameter("filters");

        List<GridListing> gridListingList = new ArrayList<>();
        GridListing listing = new GridListing();
        String filters = "talent-academy:career-listing/sector/design-construction, talent-academy:career-listing/sector/tourism";
        String[] filterTags = {
                "talent-academy:career-listing/sector/design-construction, talent-academy:career-listing/sector/tourism" };

        listing.setJobTitle("Travel Advisor");
        listing.setSector("Tourism");
        listing.setExperienceLevel("1-2 years");
        listing.setRecruitmentStatus("Actively recruiting");
        listing.setPagePath("/content/talentacademy/sa/en/careers-in-neom/travel-advisor");
        listing.setFilterTags(filterTags);

        gridListingList.add(listing);

        careerListingServlet.getCardData(filters, gridListingList);

        assertTrue(true);
    }

}
