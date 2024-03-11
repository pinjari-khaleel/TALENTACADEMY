package com.talentacademy.core.beans;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseDetailsTest {

    List<CourseLocalizedData> courseLocalizedDataList;

    List<CourseBadgeDetails> courseBadgesList;
    
    List<CourseModule> courseModuleList;

    List<String> arabicSkills;

    /**
     * Test the Course Details
     */
    @Test
    void testGetters() {
        CourseDetails courseDetails = new CourseDetails();

        String hours = "10";
        courseDetails.setDurationHours(hours);
        assertEquals(hours, courseDetails.getDurationHours());

        String minutes = "10";
        courseDetails.setDurationMinutes(minutes);
        assertEquals(minutes, courseDetails.getDurationMinutes());

        String id = "12233";
        courseDetails.setId(id);
        assertEquals(id, courseDetails.getId());

        String loType = "test lo";
        courseDetails.setLoType(loType);
        assertEquals(loType, courseDetails.getLoType());

        String image = "/test.jpg";
        courseDetails.setImageUrl(image);
        assertEquals(image, courseDetails.getImageUrl());

        int subLoCount = 5;
        courseDetails.setSubLosCount(subLoCount);
        assertEquals(subLoCount, courseDetails.getSubLosCount());

        courseLocalizedDataList = new ArrayList<>();
        CourseLocalizedData courseLocalizedData = new CourseLocalizedData();
        courseLocalizedDataList.add(courseLocalizedData);
        courseDetails.setCourseLocalizedDataList(courseLocalizedDataList);
        assertEquals(courseLocalizedDataList, courseDetails.getCourseLocalizedDataList());

        courseBadgesList = new ArrayList<>();
        CourseBadgeDetails courseBadgeDetails = new CourseBadgeDetails();
        courseBadgesList.add(courseBadgeDetails);
        courseDetails.setCourseBadgesList(courseBadgesList);
        assertEquals(courseBadgesList, courseDetails.getCourseBadgesList());
        
        courseModuleList = new ArrayList<>();
        CourseModule courseModule = new CourseModule();
        courseModuleList.add(courseModule);
        courseDetails.setModuleList(courseModuleList);
        assertEquals(courseModuleList, courseDetails.getModuleList());

        arabicSkills = new ArrayList<>();
        String skill = "test-skill";
        arabicSkills.add(skill);
        courseDetails.setArabicSkills(arabicSkills);
        assertEquals(arabicSkills, courseDetails.getArabicSkills());
    }
}
