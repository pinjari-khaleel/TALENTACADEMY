package com.talentacademy.core.models;

import java.util.List;

public interface ColumnControlModel {

    /**
     * @return The list
     */
    public List<Integer> getList();

    /**
     * @return The columnCount
     */
    public int getColumnCount();

    /**
     * @return The sectionName
     */
    public String getSectionName();

    /**
     * @return boolean value isContainerFluid
     */
    public boolean getIsContainerFluid();

}
