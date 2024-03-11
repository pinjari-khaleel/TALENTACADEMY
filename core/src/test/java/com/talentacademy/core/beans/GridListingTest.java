package com.talentacademy.core.beans;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class GridListingTest {

    private GridListing gridListing = new GridListing();

    private final AemContext aemContext = new AemContext();
    private List<String> badgeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(GridListing.class);

    }

    /**
     * Test method for
     * {@link GridListing#getJobTitle()}.
     */
    @Test
    void testGetJobTitle() {
        assertEquals(gridListing.getJobTitle(), gridListing.getJobTitle());
    }

    /**
     * Test method for
     * {@link GridListing#getSector()}.
     */
    @Test
    void testGetJobRoleType() {
        assertEquals(gridListing.getSector(), gridListing.getSector());
    }

    /**
     * Test method for
     * {@link GridListing#getExperienceLevel()}.
     */
    @Test
    void testGetExperienceLevel() {
        assertEquals(gridListing.getExperienceLevel(), gridListing.getExperienceLevel());
    }

    /**
     * Test method for
     * {@link GridListing#getRecruitmentStatus()}.
     */
    @Test
    void testGetRecruitmentStatus() {
        assertEquals(gridListing.getRecruitmentStatus(), gridListing.getRecruitmentStatus());
    }

    /**
     * Test method for
     * {@link GridListing#getPagePath()}.
     */
    @Test
    void testGetPagePath() {
        assertEquals(gridListing.getPagePath(), gridListing.getPagePath());
    }

    /**
     * Test method for
     * {@link GridListing#getJobTitle()}.
     */
    @Test
    void testSetJobTitle() {
        gridListing.setJobTitle("Travel Advisor");
        assertEquals("Travel Advisor", gridListing.getJobTitle());
    }

    /**
     * Test method for
     * {@link GridListing#getSector()}.
     */
    @Test
    void testSetSector() {
        gridListing.setSector("Tourism");
        assertEquals("Tourism", gridListing.getSector());
    }

    /**
     * Test method for
     * {@link GridListing#setExperienceLevel()}.
     */
    @Test
    void testSetExperienceLevel() {
        gridListing.setExperienceLevel("1-2 years");
        assertEquals("1-2 years", gridListing.getExperienceLevel());
    }

    /**
     * Test method for
     * {@link GridListing#setRecruitmentStatus()}.
     */
    @Test
    void testSetRecruitmentStatus() {
        gridListing.setRecruitmentStatus("Actively recruiting");
        assertEquals("Actively recruiting", gridListing.getRecruitmentStatus());
    }

    /**
     * Test method for
     * {@link GridListing#setPagePath()}.
     */
    @Test
    void testSetPagePath() {
        gridListing.setPagePath("/content/talentacademy/sa/en/careers-in-neom/travel-advisor");
        assertEquals("/content/talentacademy/sa/en/careers-in-neom/travel-advisor", gridListing.getPagePath());
    }

    /**
     * Test method for
     * {@link GridListing#setBadges()}.
     */
    @Test
    void testSetBadges() {
        badgeList.add("/content/dam/talentacademy/badges/badge-1.svg");
        gridListing.setBadges(badgeList);
        assertEquals(badgeList, gridListing.getBadges());
    }

    /**
     * Test method for
     * {@link GridListing#getBadges()}.
     */
    @Test
    void testGetBadges() {
        gridListing.setBadges(badgeList);
        assertEquals(gridListing.getBadges(), gridListing.getBadges());
    }

    /**
     * Test method for
     * {@link GridListing#setFilterTags()}.
     */
    @Test
    void testSetFilterTags() {
        gridListing.setFilterTags(new String[] { "talent-academy:career-listing/sector/design-construction" });

        String[] filterTag = gridListing.getFilterTags();
        assertEquals(1, filterTag.length);
        assertEquals("talent-academy:career-listing/sector/design-construction", filterTag[0]);
    }

    /**
     * Test method for
     * {@link GridListing#setCardImage()}.
     */
    @Test
    void testSetCardImage() {
        gridListing.setCardImage("/content/dam/talentacademy/cardimage");
        assertEquals("/content/dam/talentacademy/cardimage", gridListing.getCardImage());
    }

    /**
     * Test method for
     * {@link GridListing#getCardImage()}.
     */
    @Test
    void testgetCardImage() {
        assertEquals(gridListing.getCardImage(), gridListing.getCardImage());
    }

    /**
     * Test method for
     * {@link GridListing#setCardImageAltText()}.
     */
    @Test
    void testSetCardImageAltText() {
        gridListing.setCardImageAltText("Card Image alt text");
        assertEquals("Card Image alt text", gridListing.getCardImageAltText());
    }

    /**
     * Test method for
     * {@link GridListing#getCardImageAltText()}.
     */
    @Test
    void testgetCardImageAltText() {
        assertEquals(gridListing.getCardImageAltText(), gridListing.getCardImageAltText());
    }

    /**
     * Test method for
     * {@link GridListing#setCourseTag()}.
     */
    @Test
    void testSetCourseTag() {
        gridListing.setCourseTag("Learning Pathway");
        assertEquals("Learning Pathway", gridListing.getCourseTag());
    }

    /**
     * Test method for
     * {@link GridListing#getCourseTag()}.
     */
    @Test
    void testgetCourseTag() {
        assertEquals(gridListing.getCourseTag(), gridListing.getCourseTag());
    }

    /**
     * Test method for
     * {@link GridListing#setCourseTitle()}.
     */
    @Test
    void testSetCourseTitle() {
        gridListing.setCourseTitle("Course Title");
        assertEquals("Course Title", gridListing.getCourseTitle());
    }

    /**
     * Test method for
     * {@link GridListing#getCourseTitle()}.
     */
    @Test
    void testgetCourseTitle() {
        assertEquals(gridListing.getCourseTitle(), gridListing.getCourseTitle());
    }

    /**
     * Test method for
     * {@link GridListing#setNumberOfCourses()}.
     */
    @Test
    void testSetNumberOfCourses() {
        gridListing.setNumberOfCourses("14 Courses");
        assertEquals("14 Courses", gridListing.getNumberOfCourses());
    }

    /**
     * Test method for
     * {@link GridListing#getNumberOfCourses()}.
     */
    @Test
    void testgetNumberOfCourses() {
        assertEquals(gridListing.getNumberOfCourses(), gridListing.getNumberOfCourses());
    }

    /**
     * Test method for
     * {@link GridListing#learningPathwayID()}.
     */
    @Test
    void testLearningPathwayID() {
        gridListing.setLearningPathwayID("123456");
        assertEquals("123456", gridListing.getLearningPathwayID());
    }

    /**
     * Test method for
     * {@link GridListing#getLearningPathwayID()}.
     */
    @Test
    void testgetLearningPathwayID() {
        assertEquals(gridListing.getLearningPathwayID(), gridListing.getLearningPathwayID());
    }

    /**
     * Test method for
     * {@link GridListing#setLongDescription} ()}.
     */
    @Test
    void testsetLongDescription() {
        gridListing.setLongDescription("Long description");
        assertEquals("Long description", gridListing.getLongDescription());
    }

    /**
     * Test method for
     * {@link GridListing#getLongDescription} ()}.
     */
    @Test
    void testgetLongDescription() {
        gridListing.setLongDescription("Long description");
        assertEquals("Long description", gridListing.getLongDescription());
    }

    /**
     * Test method for
     * {@link GridListing#setCreatedDate} ()}.
     */
    @Test
    void testSetCreatedDate() {
        gridListing.setCreatedDate("September 20, 2023");
        assertEquals("September 20, 2023", gridListing.getCreatedDate());
    }

    /**
     * Test method for
     * {@link GridListing#getCreatedDate} ()}.
     */
    @Test
    void testGetCreatedDate() {
        gridListing.setCreatedDate("September 20, 2023");
        assertEquals("September 20, 2023", gridListing.getCreatedDate());
    }

    /**
     * Test method for
     * {@link GridListing#setHeading(String)}.
     */
    @Test
    void testSetHeading() {
        gridListing.setHeading("heading");
        assertEquals("heading", gridListing.getHeading());
    }

    /**
     * Test method for
     * {@link GridListing#getHeading}.
     */
    @Test
    void testGetHeading() {
        gridListing.setHeading("heading");
        assertEquals("heading", gridListing.getHeading());
    }

    /**
     * Test method for
     * {@link GridListing#setShortDescription(String)}.
     */
    @Test
    void testSetShortDescription() {
        gridListing.setShortDescription("short description");
        assertEquals("short description", gridListing.getShortDescription());
    }

    /**
     * Test method for
     * {@link GridListing#getShortDescription}.
     */
    @Test
    void testGetShortDescription() {
        gridListing.setShortDescription("short description");
        assertEquals("short description", gridListing.getShortDescription());
    }

}
