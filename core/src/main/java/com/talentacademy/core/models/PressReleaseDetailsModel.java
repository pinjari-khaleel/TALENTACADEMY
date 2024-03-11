package com.talentacademy.core.models;

public interface PressReleaseDetailsModel {

    /**
     * @return The date
     */
    public String getDate();

    /**
     * @return The heroImage
     */
    public String getHeroImage();

    /**
     * @return The imageAlt
     */
    public String getImageAlt();

    /**
     * @return The heading
     */
    public String getHeading();

    /**
     * @return The short description
     */
    public String getShortDescription();

    /**
     * @return The long description
     */
    public String getLongDescription();

    /**
     * @return The CTALabel
     */
    public String getCTALabel();

    /**
     * @return The CTAUrl
     */
    public String getCTAUrl();

    /**
     * @return The boolean openInNewTab
     */
    public String getOpenInNewTab();

}
