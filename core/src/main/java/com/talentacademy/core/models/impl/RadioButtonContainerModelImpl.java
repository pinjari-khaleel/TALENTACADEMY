package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.drew.lang.annotations.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talentacademy.core.beans.RadioButtonContainerHideShowConfigBean;
import com.talentacademy.core.constants.ApplicationConstants;
import com.talentacademy.core.models.RadioButtonContainerModel;
import com.talentacademy.core.models.RadioButtonContainerOptionItem;
import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.*;

import javax.inject.Named;
import java.util.*;

import static com.talentacademy.core.constants.ApplicationConstants.*;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = {RadioButtonContainerModel.class},
        resourceType = RadioButtonContainerModelImpl.RT_RADIO_BUTTON_CONTAINER_MODEL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class RadioButtonContainerModelImpl implements RadioButtonContainerModel {

    public static final String RT_RADIO_BUTTON_CONTAINER_MODEL = "talentacademy/components/form/radiobuttoncontainer";

    private static final String OPTION_ITEMS_PATH = "items";
    private static final String HIDE_SHOW_OPTION_ITEMS_PATH = "hideShowItems";
    private static final String ID_PREFIX = "form-radiocontainer";
    private static final String PARSYS_RESOURCE = "wcm/foundation/components/responsivegrid/new";

    @ChildResource(injectionStrategy = InjectionStrategy.OPTIONAL) @Named(OPTION_ITEMS_PATH)
    @Nullable
    private List<Resource> itemResources;

    @ChildResource(injectionStrategy = InjectionStrategy.OPTIONAL) @Named(HIDE_SHOW_OPTION_ITEMS_PATH)
    @Nullable
    private List<Resource> hideShowItemResources;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    private String helpMessage;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    protected String id;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = JcrConstants.JCR_TITLE)
    @Nullable
    protected String title;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    protected String name;

    @ValueMapValue
    @Default(values = "")
    protected String value;

    @ScriptVariable
    private Resource resource;

    @Self
    private SlingHttpServletRequest request;

    private List<RadioButtonContainerOptionItem> optionItems;
    private List<RadioButtonContainerOptionItem> optionHideShowItems;

    private String configHideShowData;

    @Override
    public List<RadioButtonContainerOptionItem> getItems() {
        if (optionItems == null) {
            populateOptionItems();
        }
        return Collections.unmodifiableList(optionItems);
    }

    @Override
    public List<RadioButtonContainerOptionItem> getHideShowItems() {
        if (optionHideShowItems == null) {
            populateOptionHideShowItems();
        }
        return Collections.unmodifiableList(optionHideShowItems);
    }

    @Override
    public String getHelpMessage() {
        return helpMessage;
    }

    @Override
    public String getType() {
        return OPTION_FORM_TYPE_PROPERTY_RADIO;
    }

    protected String getIDPrefix() {
        return ID_PREFIX;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = getIDPrefix() + "-" + Math.abs(resource.getPath().hashCode() - 1);
        }
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getResourceTypeForDropArea(){
        return PARSYS_RESOURCE;
    }

    @Override
    public String getConfigHideShowData() {
        setHideShowConfigurationData();

        return configHideShowData;
    }

    /**
     * Creates a map string configuration for the js frontend script
     * the configuration is read from the hideShowItemResources object
     * example of the generated string: [[radiobuttonvalue,[{type:val,isRadio:true,value:val},{type:val,isRadio:false,value:val}]]]
     */
    private void setHideShowConfigurationData(){
        if(hideShowItemResources == null)
            return;

        if(hideShowItemResources.isEmpty())
            return;

        HashMap<String, List<RadioButtonContainerHideShowConfigBean>> configMap = new HashMap<>();
        for(Resource hideShowItemResource : hideShowItemResources){
            ValueMap itemProperties = hideShowItemResource.getValueMap();
            String keyValue = Optional.ofNullable(itemProperties.get(DROPDOWN_VALUE_KEY)).orElse(StringUtils.EMPTY).toString();
            String keyText = Optional.ofNullable(itemProperties.get(DROPDOWN_TEXT_KEY)).orElse(StringUtils.EMPTY).toString();

            if(StringUtils.isAnyEmpty(keyValue,keyText))
                continue;

            updateHashTableConfig(keyValue,keyText, configMap);
        }

        configHideShowData = createStringObjectFromMap(configMap);
    }

    /**
     * Updates the internal hashmap object initialized in previous function by adding new values to the list of each key in the map
     * @param keyValue the value of the radiobutton container
     * @param keyText the value of the target element to be hidden or shown
     * @param configMap the map structure
     */
    private void updateHashTableConfig(String keyValue, String keyText, HashMap<String, List<RadioButtonContainerHideShowConfigBean>> configMap){
        List<RadioButtonContainerHideShowConfigBean> objConfigList = configMap.get(keyValue);

        if(objConfigList == null){
            objConfigList = new ArrayList<>();
            objConfigList.add(buildRadioButtonContainerConfigObject(keyText));
        }else{
            objConfigList.add(buildRadioButtonContainerConfigObject(keyText));
        }

        configMap.put(keyValue,objConfigList);
    }

    /**
     * Creates the object in the list of the hashmap by using a configuration bean class. From the hideShowItemResources object,
     * the key is having the following possible structure: options_2323/radio/valueInRadio. The function will transform this into an
     * object like {type:options,isRadio:true,value:valueInRadio}
     * @param keyText from the hideShowItemResources
     * @return new configuration bean object
     */
    private RadioButtonContainerHideShowConfigBean buildRadioButtonContainerConfigObject(String keyText){
        String[] keyTextArray = keyText.split(ApplicationConstants.SLASH);
        RadioButtonContainerHideShowConfigBean objHideShowConfig = new RadioButtonContainerHideShowConfigBean();

        if(keyTextArray.length > 2){//case for radio or node title having slash in content
            objHideShowConfig.setType(ApplicationUtil.getFirstStringFromArraySplit(keyTextArray[0], UNDERSCORE_CHARACTER));
            if(keyTextArray[1].equals(ApplicationConstants.OPTION_FORM_TYPE_PROPERTY_RADIO)){
                objHideShowConfig.setRadio(Boolean.TRUE);
                objHideShowConfig.setValue(ApplicationUtil.getStringFromArrayConcatenatingChar(keyTextArray,SLASH,2));
            }else{
                objHideShowConfig.setRadio(Boolean.FALSE);
                objHideShowConfig.setValue(ApplicationUtil.getStringFromArrayConcatenatingChar(keyTextArray,SLASH,1));
            }
        }else if(keyTextArray.length == 2){//basic case when it is not radio and no slash in content
            objHideShowConfig.setRadio(Boolean.FALSE);
            objHideShowConfig.setType(ApplicationUtil.getFirstStringFromArraySplit(keyTextArray[0],UNDERSCORE_CHARACTER));
            objHideShowConfig.setValue(keyTextArray[1]);
        }

        return objHideShowConfig;
    }

    /**
     * Creates the string map object. Since it is not a json object for simplicity, this function creates the string
     * @param configMap the hashmap object created in previous functions
     * @return
     */
    private String createStringObjectFromMap(HashMap<String, List<RadioButtonContainerHideShowConfigBean>> configMap){
        if(configMap.isEmpty())
            return StringUtils.EMPTY;

        StringBuilder builder = new StringBuilder();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        Iterator<Map.Entry<String,List<RadioButtonContainerHideShowConfigBean>>> configMapIterator = configMap.entrySet().iterator();
        builder.append(OPEN_SQUARE_BRACKET);
        while(configMapIterator.hasNext()){

            Map.Entry<String,List<RadioButtonContainerHideShowConfigBean>> mapObj = configMapIterator.next();

            builder.append(OPEN_SQUARE_BRACKET);
            builder.append(DOUBLE_QUOTATION_CHARACTER);
            builder.append(mapObj.getKey());
            builder.append(DOUBLE_QUOTATION_CHARACTER);
            builder.append(COMMA_CHARACTER);
            builder.append(OPEN_SQUARE_BRACKET);
            for(RadioButtonContainerHideShowConfigBean radioContainerObj : mapObj.getValue()){
                builder.append(gson.toJson(radioContainerObj));
                builder.append(COMMA_CHARACTER);
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(CLOSE_SQUARE_BRACKET);
            builder.append(CLOSE_SQUARE_BRACKET);
            builder.append(COMMA_CHARACTER);
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(CLOSE_SQUARE_BRACKET);

        return builder.toString();
    }

    /**
     * Initialize the optionItems list and populates it by calling internal function
     */
    private void populateOptionItems() {
        this.optionItems = new ArrayList<>();
        populateOptionItemsFromLocal();
    }

    /**
     * Initialize the optionHideShowItems and populates it by calling internal function
     */
    private void populateOptionHideShowItems() {
        this.optionHideShowItems = new ArrayList<>();
        populateOptionHideShowItemsFromLocal();
    }

    /**
     * Populates the optionItems list from the items resource. Having the radio button values for the radio container component
     */
    private void populateOptionItemsFromLocal() {
        if (itemResources != null) {
            for (Resource itemResource : itemResources) {
                RadioButtonContainerOptionItem optionItem = new RadioButtonContainerOptionItemImpl(request, resource, itemResource);
                if ((optionItem.isDisabled() || StringUtils.isNotBlank(optionItem.getValue()))) {
                    optionItems.add(optionItem);
                }
            }
        }
    }

    /**
     * Populates the optionHideShowItems list from the hideShowResource. Having the hide/show configuration elements
     */
    private void populateOptionHideShowItemsFromLocal() {
        if (hideShowItemResources != null) {
            for (Resource itemResource : hideShowItemResources) {
                RadioButtonContainerOptionItem optionItem = new RadioButtonContainerOptionItemImpl(request, resource, itemResource);
                if (StringUtils.isNotBlank(optionItem.getValue())) {
                    optionHideShowItems.add(optionItem);
                }
            }
        }
    }
}
