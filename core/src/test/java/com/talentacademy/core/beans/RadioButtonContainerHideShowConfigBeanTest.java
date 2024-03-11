package com.talentacademy.core.beans;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class RadioButtonContainerHideShowConfigBeanTest {

    @Test
    void testGetters() {
        RadioButtonContainerHideShowConfigBean radioButtonContainerHideShowConfigBean = new RadioButtonContainerHideShowConfigBean();

        String type = "radio";
        radioButtonContainerHideShowConfigBean.setType(type);
        assertEquals(type,radioButtonContainerHideShowConfigBean.getType());

        Boolean isRadio = Boolean.TRUE;
        radioButtonContainerHideShowConfigBean.setRadio(isRadio);
        assertTrue(radioButtonContainerHideShowConfigBean.getRadio());

        String value = "value";
        radioButtonContainerHideShowConfigBean.setValue(value);
        assertEquals(value,radioButtonContainerHideShowConfigBean.getValue());
    }
}
