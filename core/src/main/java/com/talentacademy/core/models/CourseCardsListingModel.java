package com.talentacademy.core.models;

import java.net.URISyntaxException;
import java.util.List;

import com.talentacademy.core.beans.CourseDetails;

public interface CourseCardsListingModel {

    public String getTitle();

    public String getDescription();

    public String getListingType();

    public String getCatalogId();

    public String getRootDetailsPath();

    public int getLoadMoreLimit();

    public String getBadgesEarned();

    public String getLoadMoreCtaLabel();

    public String getPrimaryButtonLabel();

    public String getPrimaryButtonLink();

    public String getPrimaryButtonTarget();

    public String getSecondaryButtonLabel();

    public String getSecondaryButtonLink();

    public String getSecondaryButtonTarget();
    
    public List<CourseDetails> getCourseDetails() throws URISyntaxException;
    
    public String getCursorIdValue();
    
}