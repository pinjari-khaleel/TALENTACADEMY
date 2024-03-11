package com.talentacademy.core.services.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.sling.api.resource.Resource;
import org.mockito.InjectMocks;
import org.apache.sling.testing.mock.sling.ResourceResolverType;

import com.day.cq.wcm.api.Page;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CareerListingServiceImplTest {

    @InjectMocks
    GridListingServiceImpl careerListingService;

    AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

    private static final String ROOT_DETAILS_PAGE = "/content/talentacademy/sa/en/detailpage";
	private static final String DETAILS_PAGE_PATH = "/content/talentacademy/sa/en/detailpage/travel-advisor";
    private static final String CAREER_DETAILS_COMPONENT = "/jcr:content/root/container/careerdetails";
    private static final String COURSE_DETAILS_COMPONENT = "/jcr:content/root/container/coursedetails";

    Page page;

    @BeforeEach
    void setup() {
        page = aemContext.create().page(ROOT_DETAILS_PAGE);
        aemContext.create().page(DETAILS_PAGE_PATH);
        aemContext.load().json("/com/talentacademy/core/models/impl/CareerDetailsModelImpl.json", "/component");
    
    }

    /**
     * Test method for
     * {@link GridListingServiceImpl#getCareerDetailsdata()}.
     * {@link GridListingServiceImpl#getCareerDetailsProperties()}.
     * {@link GridListingServiceImpl#getCourseDetailsdata()}.
     * {@link GridListingServiceImpl#getCourseDetailsProperties()}.
     * {@link GridListingServiceImpl#getBadges()}.
     * 
     */
    @Test
    void testGetDetailsData() {

        Resource resource = aemContext.currentResource("/component/careerdetails");
        
        careerListingService.getCareerDetailsdata(aemContext.resourceResolver(), ROOT_DETAILS_PAGE);
        careerListingService.getCareerDetailsProperties(resource, ROOT_DETAILS_PAGE + CAREER_DETAILS_COMPONENT, page, aemContext.resourceResolver(), ROOT_DETAILS_PAGE + CAREER_DETAILS_COMPONENT);
        careerListingService.getBadges(resource);
        careerListingService.getCourseDetailsdata(aemContext.resourceResolver(), ROOT_DETAILS_PAGE);
        careerListingService.getCourseDetailsProperties(resource, ROOT_DETAILS_PAGE + COURSE_DETAILS_COMPONENT, page, aemContext.resourceResolver(), ROOT_DETAILS_PAGE + CAREER_DETAILS_COMPONENT);

        assertTrue(true);

    }

}
