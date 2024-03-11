package com.talentacademy.core.models;

/**
 * Composition model used by RadioButtonContainerModel for handling items and hideShowItems
 */
public interface RadioButtonContainerOptionItem {

    /**
     * Text value of the item or hideShowItem
     * @return
     */
    String getText();

    /**
     * Is selected property of the item. This method validates the request params
     * when the form has a backend error to populate the radio button option is selected
     * @return
     */
    boolean isSelected() ;

    /**
     * is disabled property of the item
     * @return
     */
    boolean isDisabled();

    /**
     * value of the item or hideShowItem
     * @return
     */
    String getValue();
}
