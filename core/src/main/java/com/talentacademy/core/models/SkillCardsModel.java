package com.talentacademy.core.models;

import com.talentacademy.core.beans.SkillCardItem;

import java.util.List;

/**
 * Represent Skill Cards Model
 */
public interface SkillCardsModel {
    /**
     * @return the title
     */
    String getTitle();

    /**
     * @return the description
     */
    String getDescription();

    /**
     * @return the shortTitle
     */
    String getShortTitle();

    /**
     * @return the fileReference
     */
    String getFileReference();

    /**
     * @return the cardVariation
     */
    String getCardVariation();

    /**
     * @return the list of card items
     */
    List<SkillCardItem> getCardItems();
}
