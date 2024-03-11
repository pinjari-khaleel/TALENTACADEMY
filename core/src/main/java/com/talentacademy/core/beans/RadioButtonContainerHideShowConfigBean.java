package com.talentacademy.core.beans;

/**
 * Bean configuration class used for generating the string object for the JS script
 */
public class RadioButtonContainerHideShowConfigBean {

    private String type;
    private Boolean isRadio;
    private String value;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRadio() {
        return isRadio;
    }

    public void setRadio(Boolean radio) {
        isRadio = radio;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
