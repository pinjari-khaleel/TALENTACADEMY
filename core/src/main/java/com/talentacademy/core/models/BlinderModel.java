package com.talentacademy.core.models;

import com.talentacademy.core.beans.PageModel;
import com.talentacademy.core.models.impl.ContentBlockModelImpl;

import java.util.List;

/**
 * Represents the Blinder Model
 **/
public interface BlinderModel {

    /**
     * @return the header.
     */
    String getHeader();

    /**
     * @return the variation.
     */
    String getVariation();

    /**
     * @return the list of Blinder details.
     */
    List<ContentBlockModelImpl> getBlinderDetails();

    /**
     * @return the list of pages
     */
    List<PageModel> getPages();

}