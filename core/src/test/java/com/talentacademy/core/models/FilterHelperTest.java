package com.talentacademy.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
class FilterHelperTest {

    private FilterHelper filterHelper = new FilterHelper();

    private final AemContext aemContext = new AemContext();

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(FilterHelper.class);

    }

    /**
     * Test method for
     * {@link FilterHelper#getTagName()}.
     */
    @Test
    void testGetTagName() {
        assertEquals(filterHelper.getTagName(), filterHelper.getTagName());
    }

    /**
     * Test method for
     * {@link FilterHelper#getTagLink()}.
     */
    @Test
    void testGetTagLink() {
        assertEquals(filterHelper.getTagLink(), filterHelper.getTagLink());
    }

    /**
     * Test method for
     * {@link FilterHelper#setTagLink()}.
     */
    @Test
    void testSetTagLink() {
        filterHelper.setTagLink("talent-academy:career-listing/sector/design-construction");
        assertEquals("talent-academy:career-listing/sector/design-construction", filterHelper.getTagLink());
    }

    /**
     * Test method for
     * {@link FilterHelper#setTagName()}.
     */
    @Test
    void testSetTagName() {
        filterHelper.setTagName("Sector");
        assertEquals("Sector", filterHelper.getTagName());
    }
}
