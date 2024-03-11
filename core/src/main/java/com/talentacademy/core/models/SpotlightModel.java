package com.talentacademy.core.models;

public interface SpotlightModel {

    /** 
     * @return Caption
     */
    public String getCaption();

     /** 
     * @return the pathway tag label
     */
    public String getLearningPathway();

    /** 
     * @return Title of the component
     */
    public String getTitle();

     /** 
     * @return The description of the component
     */
    public String getDescription();

     /** 
     * @return The background image for desktop
     */
    public String getDesktopBackgroundImage();

    /** 
     * @return background image for mobile
     */
    public String getMobileBackgroundImage();

    /** 
     * @return The alternate text for image
     */
    public String getImageAltText();

     /** 
     * @return The background video
     */
    public String getBackgroundVideo();

     /** 
     * @return The background video poster image
     */
    public String getBackgroundVideoPoster();
}