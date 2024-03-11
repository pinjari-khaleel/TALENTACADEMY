package com.talentacademy.core.models;

import com.adobe.cq.export.json.ContainerExporter;
import com.adobe.cq.wcm.core.components.models.form.OptionItem;
import com.adobe.cq.wcm.core.components.models.form.Options;

import java.util.List;

/**
 * Custom Radio Button Container Model
 */
public interface RadioButtonContainerModel {

    /**
     * Parsys resource type for adding new elements in the container
     * @return
     */
    String getResourceTypeForDropArea();

    /**
     * Text configuration map of the hideShow elements for the JS script
     * @return
     */
    String getConfigHideShowData();

    /**
     * List of the hideShowItems
     * @return
     */
    List<RadioButtonContainerOptionItem> getHideShowItems();

    /**
     * List of items in the radio button container component
     * @return
     */
    List<RadioButtonContainerOptionItem> getItems();

    /**
     * Help message property of the component
     * @return
     */
    String getHelpMessage();

    /* Following properties are part of the elements in a form - maintaining consistency */

    /**
     * Type is always Radio for this component
     * @return
     */
    String getType();

    /**
     * Id property of the component
     * @return
     */
    String getId();

    /**
     * Name property of the component
     * @return
     */
    String getName();

    /**
     * Value property of the component
     * @return
     */
    String getValue();

    /**
     * Title property of the component
     * @return
     */
    String getTitle();
}
