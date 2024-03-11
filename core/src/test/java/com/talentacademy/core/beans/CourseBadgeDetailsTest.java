package com.talentacademy.core.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseBadgeDetailsTest {

    /**
     * Test the Course Badge Details
     */
    @Test
    void testGetters() {
        CourseBadgeDetails courseBadgeDetails = new CourseBadgeDetails();

        String imageUrl = "/test.jpg";
        courseBadgeDetails.setBadgeImage(imageUrl);
        assertEquals(imageUrl, courseBadgeDetails.getBadgeImage());

        String name = "badge name";
        courseBadgeDetails.setBadgeName(name);
        assertEquals(name, courseBadgeDetails.getBadgeName());

        String state = "active";
        courseBadgeDetails.setBadgeState(state);
        assertEquals(state, courseBadgeDetails.getBadgeState());
    }
}
