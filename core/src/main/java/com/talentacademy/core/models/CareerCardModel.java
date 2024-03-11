package com.talentacademy.core.models;

import org.apache.sling.api.resource.Resource;

public interface CareerCardModel {


    /**
     * @return The jobTitle
     */
    public String getJobTitle();

    /**
     * @return The careerLabel
     */
    public String getCareerLabel();

    /**
     * @return The tileImage
     */
    public String getTileImage();

    /**
     * @return The altTextForImage
     */
    public String getAltTextForImage();

    /**
     * @return The recruitmentStatus
     */
    public String getRecruitmentStatus();

    /**
     * @return The jobRoleType
     */
    public String getJobRoleType();

    /**
     * @return The experienceLevel
     */
    public String getExperienceLevel();

    /**
     * @return The jobOpening
     */
    public String getJobOpening();

    /**
     * @return The expectedDemand
     */
    public String getExpectedDemand();

    /**
     * @return badgesWeRecommend
     */
    public String getBadgesWeRecommend();

    /**
     * @return The badgeList
     */
    public Resource getBadgeList();

    /**
     * @return The knowMoreCTALabel
     */
    public String getKnowMoreCTALabel();

    /**
     * @return The knowMoreCTALink
     */
    public String getKnowMoreCTALink();

    /**
     * @return The openInNewTab
     */
    public String getOpenInNewTab();
    
}
