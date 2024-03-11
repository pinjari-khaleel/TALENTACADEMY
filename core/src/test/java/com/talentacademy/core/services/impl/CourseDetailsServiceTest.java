package com.talentacademy.core.services.impl;

import com.talentacademy.core.beans.CourseDetails;
import com.talentacademy.core.services.CourseDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CourseDetailsServiceTest {

    CourseDetailsService courseDetailsService;

    /**
     * Test to Fetch the List of Course Details
     */
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/com/talentacademy/core/models/impl/CourseDetails.json",
        "src/test/resources/com/talentacademy/core/models/impl/CourseDetailsWithOutSkills.json", "src/test/resources/com/talentacademy/core/models/impl/CourseDetailsWithOutInstances.json"})
    void fetchCourseDetails(String arg) throws IOException {
        courseDetailsService = new CourseDetailsServiceImpl();
        String response = new String(Files.readAllBytes(Paths.get(arg)));
        assertNotNull(courseDetailsService.getCourseDetails(response, false, ""));
    }
    
    /**
     * Test to Fetch the List of Course Details
     */
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/com/talentacademy/core/models/impl/SingleCourseDetails.json"})
    void fetchSingleCourseDetails(String arg) throws IOException {
        courseDetailsService = new CourseDetailsServiceImpl();
        String response = new String(Files.readAllBytes(Paths.get(arg)));
        assertNotNull(courseDetailsService.getCourseDetails(response,true, ""));
    }

    /**
	 * Test method for {@link courseDetailsService#getCursorId()}.
	 */
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/com/talentacademy/core/models/impl/SingleCourseDetails.json"})
    void fetchGetCursorId(String arg) throws IOException {
        courseDetailsService = new CourseDetailsServiceImpl();
        String response = new String(Files.readAllBytes(Paths.get(arg)));
        assertNotNull(courseDetailsService.getCursorId(response));
    }
    
    /**
     * Test to Fetch the Specific Course Details
     */
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/com/talentacademy/core/models/impl/CourseDetails.json"})
    void fetchSpecificCourseDetails(String arg) throws IOException {
        courseDetailsService = new CourseDetailsServiceImpl();
        String response = new String(Files.readAllBytes(Paths.get(arg)));
        List<CourseDetails> courseDetails = courseDetailsService.getCourseDetails(response, false, "");
        assertNotNull(courseDetails);
        assertEquals("course:1757270", courseDetails.get(0).getId());
        assertEquals("1", courseDetails.get(0).getDurationHours());
        assertEquals("course", courseDetails.get(0).getLoType());
        assertEquals(0 , courseDetails.get(0).getSubLosCount());
        assertEquals("https://media.test.jpg", courseDetails.get(0).getImageUrl());
        assertEquals("Self Development 101 from Mind Tools for Business (Emerald Works)", courseDetails.get(0).getCourseLocalizedDataList().get(0).getTitle());
    }

    /**
     * Test for the Empty Response
     */
    @Test
    void fetchEmptyCourseDetails() {
        courseDetailsService = new CourseDetailsServiceImpl();
        String response = "{}";
        assertEquals(Collections.emptyList(), courseDetailsService.getCourseDetails(response, false, ""));
        assertEquals(Collections.emptyList(), courseDetailsService.getCourseDetails(response, true, ""));
    }
}
