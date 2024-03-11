package com.talentacademy.core.servlets;

import com.talentacademy.core.beans.GridListing;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.services.GridListingService;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, AemContextExtension.class})
class PressReleaseServletTest {

    @Mock
    GridListingService gridListingService;

    @InjectMocks
    PressReleaseServlet pressReleaseServlet;

    @Mock
    private SlingHttpServletRequest request;

    @Mock
    private SlingHttpServletResponse response;

    @Mock
    PrintWriter printWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test method for
     * {@link PressReleaseServlet#doGet}.
     */
    @Test
    void doGet() throws ServletException, IOException {
        when(request.getParameter(ApplicationConstants.CARDS_PER_LOAD)).thenReturn("10");
        when(request.getParameter(ApplicationConstants.OFFSET)).thenReturn("0");
        when(request.getResource()).thenReturn(mock(Resource.class));
        when(request.getResource().getPath()).thenReturn("/content/press-release");
        List<GridListing> gridListing = new ArrayList<>();
        setGridListingProperties(gridListing);
        when(gridListingService.getPressReleaseDetaildata(request.getResourceResolver(), "/content/press-release")).thenReturn(gridListing);
        when(response.getWriter()).thenReturn(printWriter);
        pressReleaseServlet.doGet(request,response);
        assertEquals(gridListing, gridListingService.getPressReleaseDetaildata(request.getResourceResolver(), "/content/press-release"));
        assertNotNull(printWriter);
    }

    private void setGridListingProperties(List<GridListing> gridListing){
        GridListing gridListingBean = new GridListing();
        gridListingBean.setCreatedDate("20 September, 2023");
        gridListingBean.setShortDescription("Press release details");
        gridListingBean.setCardImageAltText("image alt");
        gridListingBean.setCardImage("image.png");
        gridListingBean.setLongDescription("Long description");
        gridListing.add(gridListingBean);

    }


}