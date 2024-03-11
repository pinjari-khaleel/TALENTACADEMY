package com.talentacademy.core.models.impl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@ExtendWith({ AemContextExtension.class})
public class RadioButtonContainerOptionItemImplTest {

    private final String ROOT_PATH = "/content/talentacademy/us/en/test";
    private final AemContext aemContext = new AemContext();

    private RadioButtonContainerOptionItemImpl radioButtonContainerOptionItem;

    /**
     * Setup method for {com.talentacademy.core.models.RadioButtonContainerOptionItemImpl}
     */
    @BeforeEach
    public void setup() {
        aemContext.addModelsForClasses(RadioButtonContainerModelImpl.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/RadioButtonContainerOptionItemImpl.json", ROOT_PATH);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerOptionItemImpl#isSelected()}.
     */
    @Test
    void isSelectedWithNullOptionNameInRequest() {
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        Resource resourceItem = resource.getChild("items/item0");

        radioButtonContainerOptionItem = new RadioButtonContainerOptionItemImpl(aemContext.request(),resource,resourceItem);

        boolean result = radioButtonContainerOptionItem.isSelected();

        assertFalse(result);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerOptionItemImpl#isSelected()}.
     */
    @Test
    void isSelectedWithNullNameKeyInParentResource() {
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerNullNameParentResource");
        Resource resourceItem = resource.getChild("items/item0");

        radioButtonContainerOptionItem = new RadioButtonContainerOptionItemImpl(aemContext.request(),resource,resourceItem);

        boolean result = radioButtonContainerOptionItem.isSelected();

        assertFalse(result);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerOptionItemImpl#isSelected()}.
     */
    @Test
    void isSelectedWithNoEqualValuesInRequest() {
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        Resource resourceItem = resource.getChild("items/item0");
        Map<String,Object> params = new HashMap<>();
        params.put("career goals","accelerate career");
        aemContext.request().setParameterMap(params);

        radioButtonContainerOptionItem = new RadioButtonContainerOptionItemImpl(aemContext.request(),resource,resourceItem);

        boolean result = radioButtonContainerOptionItem.isSelected();

        assertFalse(result);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerOptionItemImpl#isSelected()}.
     */
    @Test
    void isSelectedSuccessfully() {
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        Resource resourceItem = resource.getChild("items/item0");
        Map<String,Object> params = new HashMap<>();
        params.put("career goals","kickstart career");
        aemContext.request().setParameterMap(params);

        radioButtonContainerOptionItem = new RadioButtonContainerOptionItemImpl(aemContext.request(),resource,resourceItem);

        boolean result = radioButtonContainerOptionItem.isSelected();

        assertTrue(result);
    }
}
