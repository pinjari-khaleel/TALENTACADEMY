package com.talentacademy.core.models;

public interface CareerCardsListingModel {

    /**
     * @return The load more limit number which is confugured in the dialog
     */
    public int getLoadMoreLimit();

    /**
     * @return The root career details page path which is confugured in the dialog
     */
    public String getRootDetailsPath();

    /**
     * @return The badge earned label to be show in the cards
     */
    public String getBadgesEarned();

    /**
     * @return The know more CTA label
     */
    public String getKnowMoreCtaLabel();

    /**
     * @return The load more CTA label
     */
    public String getLoadMoreCtaLabel();

    /**
     * @return The Apply filter label to show the button text
     */
    public String getApplyFilterLabel();

    /**
     * @return The clear filter label to show the button text
     */
    public String getClearFilterLabel();

    /**
     * @return The filters label wwhich will be visible on mobile device
     */
    public String getFiltersLabel();

}
