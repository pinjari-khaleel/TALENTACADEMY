package com.talentacademy.core.models.impl;

import com.talentacademy.core.models.RadioButtonContainerOptionItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import java.util.Optional;

import static com.talentacademy.core.constants.ApplicationConstants.NAME_KEY;
import static com.talentacademy.core.constants.ApplicationConstants.DROPDOWN_TEXT_KEY;
import static com.talentacademy.core.constants.ApplicationConstants.DROPDOWN_VALUE_KEY;

public class RadioButtonContainerOptionItemImpl implements RadioButtonContainerOptionItem {
    private static final String PN_DISABLED = "disabled";

    private final SlingHttpServletRequest request;
    private final Resource options;
    private final ValueMap properties;

    public RadioButtonContainerOptionItemImpl(SlingHttpServletRequest request, Resource options, Resource option) {
        this.request = request;
        this.options = options;
        this.properties = option.getValueMap();
    }

    @Override
    public String getText() {
        return properties.get(DROPDOWN_TEXT_KEY, String.class);
    }

    @Override
    public boolean isSelected() {
        String optionsName = Optional.ofNullable(options.getValueMap().get(NAME_KEY)).orElse(StringUtils.EMPTY).toString();
        if(StringUtils.isEmpty(optionsName))
            return false;

        String optionsValueInRequest = this.request.getParameter(optionsName);

        return optionsValueInRequest != null && optionsValueInRequest.equals(this.getValue());
    }

    @Override
    public boolean isDisabled() {
        return properties.get(PN_DISABLED, false);
    }

    @Override
    public String getValue() {
        return properties.get(DROPDOWN_VALUE_KEY, String.class);
    }
}