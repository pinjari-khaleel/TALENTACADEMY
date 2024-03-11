package com.talentacademy.core.models;

import com.talentacademy.core.beans.GridListing;

import java.util.List;

public interface PressReleaseModel {

    /**
     * @return The root press release details page path which is confugured in the dialog
     */
    public String getRootDetailsPath();

    /**
     * @return The know more CTA label
     */
    public String getKnowMoreCtaLabel();

    /**
     * @return The load more limit number which is confugured in the dialog
     */
    public int getLoadMoreLimit();

    /**
     * @return The load more CTA label
     */
    public String getLoadMoreCtaLabel();

    /**
     * @return The press release details
     */
    List<GridListing> getPressReleaseDetails();

}
