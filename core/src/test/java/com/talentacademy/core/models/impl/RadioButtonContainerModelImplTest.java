package com.talentacademy.core.models.impl;

import com.talentacademy.core.models.RadioButtonContainerOptionItem;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static junit.framework.Assert.*;

@ExtendWith({ AemContextExtension.class})
class RadioButtonContainerModelImplTest {

    private final String ROOT_PATH = "/content/talentacademy/us/en/test";
    private final AemContext aemContext = new AemContext();

    private RadioButtonContainerModelImpl radioButtonContainerModel;

    /**
     * Setup method for {com.talentacademy.core.models.RadioButtonContainerModelImpl}
     */
    @BeforeEach
    public void setup() {
        aemContext.addModelsForClasses(RadioButtonContainerModelImpl.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/RadioButtonContainerModelImpl.json", ROOT_PATH);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getItems()}.
     */
    @Test
    void testGetItemsInRadioButtonContainer(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> itemResources = radioButtonContainerModel.getItems();

        assertNotNull(itemResources);
        assertTrue(!itemResources.isEmpty());
        assertEquals(2,itemResources.size());
        assertEquals("I want to kickstart my career",itemResources.get(0).getText());
        assertEquals("kickstart career",itemResources.get(0).getValue());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getItems()}.
     */
    @Test
    void testGetItemsWithResourcesByHavingAlreadyItemListValues(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> itemResourcesEmpty = radioButtonContainerModel.getItems();

        List<RadioButtonContainerOptionItem> itemResourcesNoEmpty = radioButtonContainerModel.getItems();

        assertTrue(itemResourcesEmpty != null && itemResourcesNoEmpty != null);
        assertEquals(itemResourcesEmpty,itemResourcesNoEmpty);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getHideShowItems()}.
     */
    @Test
    void testGetHideShowItemsInRadioButtonContainer(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> itemResources = radioButtonContainerModel.getHideShowItems();

        assertNotNull(itemResources);
        assertTrue(!itemResources.isEmpty());
        assertEquals(2,itemResources.size());
        assertEquals("options/radio/High school student",itemResources.get(0).getText());
        assertEquals("kickstart career",itemResources.get(0).getValue());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getHideShowItems()}.
     */
    @Test
    void testGetHideShowItemsWithResourcesWithAlreadyHideItems(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> itemResourcesEmpty = radioButtonContainerModel.getHideShowItems();

        List<RadioButtonContainerOptionItem> itemResourcesNoEmpty = radioButtonContainerModel.getHideShowItems();

        assertTrue(itemResourcesEmpty != null && itemResourcesNoEmpty != null);
        assertEquals(itemResourcesEmpty,itemResourcesNoEmpty);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getHelpMessage()}.
     */
    @Test
    void testGetHelpMessage(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String helpMessage = radioButtonContainerModel.getHelpMessage();

        assertNotNull(helpMessage);
        assertEquals("help message text",helpMessage);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getType()}.
     */
    @Test
    void testGetTypeAlwaysEnumRadioValue(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        assertEquals("radio",radioButtonContainerModel.getType());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getId()}.
     */
    @Test
    void testGetIdWithGeneratedId(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String id = "form-radiocontainer" + "-" + Math.abs((ROOT_PATH + "/radiobuttoncontainerDefault").hashCode() - 1);

        String receivedId = radioButtonContainerModel.getId();

        assertEquals(id,receivedId);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getId()}.
     */
    @Test
    void testGetIdWithJcr(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefaultWithId");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String receivedId = radioButtonContainerModel.getId();

        assertNotNull(receivedId);
        assertEquals("radioId123",receivedId);

    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getName()}.
     */
    @Test
    void testGetName(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String name = radioButtonContainerModel.getName();

        assertNotNull(name);
        assertEquals("career goals",name);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getValue()}.
     */
    @Test
    void testGetValueNull(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        assertTrue(radioButtonContainerModel.getValue().isEmpty());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getTitle()}.
     */
    @Test
    void testGetTitle(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String title = radioButtonContainerModel.getTitle();

        assertNotNull(title);
        assertEquals("CAREER GOALS", title);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getResourceTypeForDropArea()}.
     */
    @Test
    void testGetResourceTypeForResourceArea(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String parsysResourceTypeResourceArea = radioButtonContainerModel.getResourceTypeForDropArea();

        assertNotNull(parsysResourceTypeResourceArea);
        assertEquals("wcm/foundation/components/responsivegrid/new", parsysResourceTypeResourceArea);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigHideShowData(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefault");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String expectedConfigData = "[[\"kickstart career\",[{\"type\":\"options\",\"isRadio\":true,\"value\":\"High school student\"}]],[\"accelerate career\",[{\"type\":\"options\",\"isRadio\":true,\"value\":\"College student\"}]]]";

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNotNull(configData);
        assertTrue(!configData.isEmpty());
        assertEquals(expectedConfigData,configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigNoHideShowResources(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerNoHideItems");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNull(configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getHideShowItems()}.
     */
    @Test
    void testGetHideShowItemResourcesNoHideShowResources(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerNoHideItems");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> hideShowItems = radioButtonContainerModel.getHideShowItems();

        assertNotNull(hideShowItems);
        assertTrue(hideShowItems.isEmpty());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigEmptyHideShowResources(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerEmptyHideItems");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNull(configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigHideShowDataWithTextHavingSlash(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerHideShowItemWithSlash");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String expectedConfigData = "[[\"kickstart career\",[{\"type\":\"options\",\"isRadio\":false,\"value\":\"High school student/University\"}]],[\"accelerate career\",[{\"type\":\"options\",\"isRadio\":true,\"value\":\"College student\"}]]]";

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNotNull(configData);
        assertTrue(!configData.isEmpty());
        assertEquals(expectedConfigData,configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigHideShowDataWithNoRadioNoSlash(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerHideShowItemNoRadioNoSlash");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String expectedConfigData = "[[\"kickstart career\",[{\"type\":\"text\",\"isRadio\":false,\"value\":\"Tell me more\"}]],[\"accelerate career\",[{\"type\":\"options\",\"isRadio\":true,\"value\":\"College student\"}]]]";

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNotNull(configData);
        assertTrue(!configData.isEmpty());
        assertEquals(expectedConfigData,configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigHideShowDataWithOptionsUnderscoreNoRadioNoSlash(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerHideShowItemOptionUnderscoreNoRadioNoSlash");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String expectedConfigData = "[[\"kickstart career\",[{\"type\":\"text\",\"isRadio\":false,\"value\":\"Tell me more\"}]],[\"accelerate career\",[{\"type\":\"options\",\"isRadio\":false,\"value\":\"College student\"}]]]";

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNotNull(configData);
        assertTrue(!configData.isEmpty());
        assertEquals(expectedConfigData,configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigHideShowDataWithMalFormedTextValue(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerHideShowItemOptionMalformedTextValue");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String expectedConfigData = "[[\"kickstart career\",[{}]],[\"accelerate career\",[{\"type\":\"options\",\"isRadio\":false,\"value\":\"College student\"}]]]";

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNotNull(configData);
        assertTrue(!configData.isEmpty());
        assertEquals(expectedConfigData,configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigHideShowDataWithEmptyKeysInResources(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerHideShowItemIncompleteData");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String expectedConfigData = "[[\"accelerate career\",[{\"type\":\"options\",\"isRadio\":true,\"value\":\"College student\"}]]]";

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNotNull(configData);
        assertTrue(!configData.isEmpty());
        assertEquals(expectedConfigData,configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getItems()}.
     */
    @Test
    void testGetItemsWithDisabledValues(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefaultWithDisabledItems");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> items = radioButtonContainerModel.getItems();

        assertNotNull(items);
        assertTrue(!items.isEmpty());
        assertTrue(items.get(0).isDisabled());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getItems()}.
     */
    @Test
    void testGetItemsWithEmptyValues(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerDefaultWithNoValueItem");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> items = radioButtonContainerModel.getItems();

        assertNotNull(items);
        assertTrue(!items.isEmpty());
        assertEquals(1,items.size());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigHideShowDataWithSameKeyMap(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerWithSameKeyInConfigMap");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String expectedConfigData = "[[\"kickstart career\",[{\"type\":\"options\",\"isRadio\":true,\"value\":\"High school student\"},{\"type\":\"options\",\"isRadio\":true,\"value\":\"College student\"}]]]";

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNotNull(configData);
        assertTrue(!configData.isEmpty());
        assertEquals(expectedConfigData,configData);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getConfigHideShowData()}.
     */
    @Test
    void testGetConfigHideShowEmptyData(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerHideShowItemAllIncompleteData");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        String configData = radioButtonContainerModel.getConfigHideShowData();

        assertNotNull(configData);
        assertTrue(configData.isEmpty());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.RadioButtonContainerModelImpl#getHideShowItems()}.
     */
    @Test
    void testGetHideShowListWithNoValueTextInItem(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerHideShowItemNoValue");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> hideShowResources = radioButtonContainerModel.getHideShowItems();

        assertNotNull(hideShowResources);
        assertTrue(!hideShowResources.isEmpty());
        assertEquals(1,hideShowResources.size());
    }

    @Test
    void testGetItemsWithNoItemsInJcr(){
        Resource resource = aemContext.resourceResolver().getResource(ROOT_PATH + "/radiobuttoncontainerNoItems");
        aemContext.request().setResource(resource);

        radioButtonContainerModel = aemContext.request().adaptTo(RadioButtonContainerModelImpl.class);

        List<RadioButtonContainerOptionItem> items = radioButtonContainerModel.getItems();

        assertNotNull(items);
        assertTrue(items.isEmpty());
    }


}
