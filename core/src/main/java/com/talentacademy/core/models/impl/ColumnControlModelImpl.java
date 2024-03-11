package com.talentacademy.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.talentacademy.core.models.ColumnControlModel;

@Model(adaptables = Resource.class, adapters = ColumnControlModel.class, 
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, 
       resourceType = ColumnControlModelImpl.RESOURCE_TYPE)
public class ColumnControlModelImpl implements ColumnControlModel {

    public static final String RESOURCE_TYPE = "talentacademy/components/content/columncontrol";

    private static final int GRID_SIZE = 12;

    @ValueMapValue
    private int columnNumbers;

    @ValueMapValue
    @Default(values = "")
    private String sectionName;

    @ValueMapValue
    private boolean isContainerFluid;

    private List<Integer> list;

    private int colCount = 1;

    /**
     * Initializes the list
     */
    @PostConstruct
    private void init() {
        list = new ArrayList<>();
        if (columnNumbers > 0) {
            colCount = GRID_SIZE / columnNumbers;
            for (int i = 0; i < columnNumbers; i++) {
                list.add(i);
            }
        }
    }

    /**
     * @return The list
     */
    @Override
    public List<Integer> getList() {
        return Collections.unmodifiableList(list);
    }

    /**
     * @return The colCount
     */
    @Override
    public int getColumnCount() {
        return colCount;
    }

    /**
     * @return The sectionName
     */
    @Override
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @return boolean value isContainerFluid
     */
    @Override
    public boolean getIsContainerFluid() {
        return isContainerFluid;
    }
    
}
