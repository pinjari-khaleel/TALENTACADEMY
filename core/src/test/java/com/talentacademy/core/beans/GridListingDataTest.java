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
class GridListingDataTest {

    private GridListingData gridListingData = new GridListingData();

    private final AemContext aemContext = new AemContext();

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(GridListingData.class);

    }

    /**
     * Test method for
     * {@link GridListingData#setTotalCards()}.
     */
    @Test
    void testSetTotalCards() {
        gridListingData.setTotalCards("50");
        assertEquals("50", gridListingData.getTotalCards());
    }

    /**
     * Test method for
     * {@link GridListingData#getTotalCards()}.
     */
    @Test
    void testGetTotalCards() {
        assertEquals(gridListingData.getTotalCards(), gridListingData.getTotalCards());
    }

    /**
     * Test method for
     * {@link GridListingData#setOffsetValue()}.
     */
    @Test
    void testSetOffsetValue() {
        gridListingData.setOffsetValue("3");
        assertEquals("3", gridListingData.getOffsetValue());
    }

    /**
     * Test method for
     * {@link GridListingData#setOffsetValue()}.
     */
    @Test
    void testGetOffsetValue() {
        assertEquals(gridListingData.getOffsetValue(), gridListingData.getOffsetValue());
    }

    /**
     * Test method for
     * {@link GridListingData#getCardList()}.
     * {@link GridListingData#setCardList()}.
     */
    @Test
    void testCardList() {
        List<GridListing> gridListingList = new ArrayList<>();
        GridListing listing = new GridListing();
        String[] filterTags = {
                "talent-academy:career-listing/sector/design-construction, talent-academy:career-listing/sector/tourism" };

        listing.setJobTitle("Travel Advisor");
        listing.setSector("Tourism");
        listing.setExperienceLevel("1-2 years");
        listing.setRecruitmentStatus("Actively recruiting");
        listing.setPagePath("/content/talentacademy/sa/en/careers-in-neom/travel-advisor");
        listing.setFilterTags(filterTags);

        gridListingList.add(listing);
        gridListingData.setCardList(gridListingList);
        assertEquals(gridListingList, gridListingData.getCardList());
    }

    /**
     * Test method for
     * {@link GridListingData#getDynamicCardList()}.
     * {@link GridListingData#setDynamicCardList()}.
     */
    @Test
    void testDynamicCardList() {
        List<CourseDetails> gridListingList = new ArrayList<>();
        CourseDetails listing = new CourseDetails();

        listing.setId("course:123");
        listing.setLoType("Course");
        listing.setDurationHours("12");
        listing.setDurationMinutes("50");

        gridListingList.add(listing);
        gridListingData.setDynamicCardList(gridListingList);
        assertEquals(gridListingList, gridListingData.getDynamicCardList());
    }

     /**
     * Test method for
     * {@link GridListingData#setCursorIdValue()}.
     * {@link GridListingData#getCursorIdValue()}.
     */
    @Test
    void testCursorId() {
        String cursorId = "eyJjb3Vyc2UiOjEwLCJsZWFybmluZ1Byb2dyYW0iOjB9";
        gridListingData.setCursorIdValue(cursorId);
        assertEquals(cursorId, gridListingData.getCursorIdValue());
    }


}