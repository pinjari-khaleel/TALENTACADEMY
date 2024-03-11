package com.talentacademy.core.models;

import java.util.List;

public interface CarouselContainerModel {

    /**
     * @return The caption
     */
    public String getCaption();

    /**
     * @return The carouselTitle
     */
    public String getCarouselTitle();

    /**
     * @return The carouselDescription
     */
    public String getCarouselDescription();

    /**
     * @return The buttonLabel
     */
    public String getButtonLabel();

    /**
     * @return The buttonLink
     */
    public String getButtonLink();

    /**
     * @return The list
     */
    public List<Integer> getList();

    /**
     * @return The carouselType
     */
    public String getCarouselType();

    /**
     * @return The noofields
     */
    public int getNumberOfFields();

    /**
     * @return The openInNewTab
     */
    public String getOpenInNewTab();

}
