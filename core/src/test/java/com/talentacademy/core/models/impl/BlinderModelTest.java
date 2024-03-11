package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.PageModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class})
class BlinderModelTest {

    private final AemContext aemContext = new AemContext();

    private final int INDEX_ZERO = 0;

    private final int INDEX_ONE= 1;

    @InjectMocks
    BlinderModelImpl model;

    /**
     * setUp method for
     * {@link BlinderModelImpl}.
     */
    @BeforeEach
    public void setup() {
        aemContext.addModelsForClasses(BlinderModelImpl.class);
        aemContext.load().json("/com/talentacademy/core/models/impl/BlinderTest.json", "/content");
    }


    /**
     * Test method for
     * {@link BlinderModelImpl #getBlinderDetails(), #getVariation()}.
     */
    @Test
    void TestStatic() {
        Resource resource = aemContext.resourceResolver().getResource("/content/staticblinder");
        model = resource.adaptTo(BlinderModelImpl.class);
        assertEquals("test Title", model.getHeader());
        assertEquals("static", model.getVariation());
        assertEquals("image 1", model.getBlinderDetails().get(INDEX_ZERO).getAltText());
        assertEquals("image 2", model.getBlinderDetails().get(INDEX_ONE).getAltText());
        assertEquals("/content/dam/test1.jpg", model.getBlinderDetails().get(INDEX_ZERO).getFileReference());
        assertEquals("/content/dam/test2.jpg", model.getBlinderDetails().get(INDEX_ONE).getFileReference());
        assertEquals("sample text 1", model.getBlinderDetails().get(INDEX_ZERO).getHeader());
        assertEquals("sample text 2", model.getBlinderDetails().get(INDEX_ONE).getHeader());
        assertEquals("sample desc 1", model.getBlinderDetails().get(INDEX_ZERO).getDescription());
        assertEquals("sample desc 2", model.getBlinderDetails().get(INDEX_ONE).getDescription());

    }

    /**
     * Test method for
     * {@link BlinderModelImpl #getBlinderDetails()}.
     */
    @Test
    void TestDynamic() {
        ResourceResolver resolver = aemContext.resourceResolver();
        Resource resource = resolver.getResource("/content/dynamicblinder");
        model = resource.adaptTo(BlinderModelImpl.class);
        List<PageModel> pageModels = new ArrayList<>();
        PageModel pageModel = new PageModel();
        pageModel.setPagePath("/content/language-academy");
        pageModels.add(pageModel);
        assertEquals("/content/language-academy", model.getPages().get(0).getPagePath());
        assertEquals("language academy", model.getBlinderDetails().get(INDEX_ZERO).getAltText());
        assertEquals("/content/dam/talentacademy/language academy.jpg", model.getBlinderDetails().get(INDEX_ZERO).getFileReference());
        assertEquals("LANGUAGE ACADEMY", model.getBlinderDetails().get(INDEX_ZERO).getHeader());
        assertEquals("language academy detail page short description", model.getBlinderDetails().get(INDEX_ZERO).getDescription());
    }

    /**
     * Test method for
     * {@link BlinderModelImpl #getVariation()}.
     */
    @Test
    void testDynamicPagesNull() {
    	ResourceResolver resolver = aemContext.resourceResolver();
        Resource resource = resolver.getResource("/content/dynamicblinderpages");
        model = resource.adaptTo(BlinderModelImpl.class);
        assertEquals("dynamic", model.getVariation());
    }

    /**
     * Test method for
     * {@link BlinderModelImpl #getVariation()}.
     */
    @Test
    void testDynamicPagePathNull() {
    	ResourceResolver resolver = aemContext.resourceResolver();
        Resource resource = resolver.getResource("/content/dynamicblinderpagepath");
        model = resource.adaptTo(BlinderModelImpl.class);
        assertEquals("dynamic", model.getVariation());
    }

    /**
     * Test method for
     * {@link BlinderModelImpl #getPages()}.
     */
    @Test
    void testDynamicPageIncorrectPath() {
    	ResourceResolver resolver = aemContext.resourceResolver();
        Resource resource = resolver.getResource("/content/dynamicblinderincorrectpath");
        model = resource.adaptTo(BlinderModelImpl.class);
        assertEquals("/content/language-acad", model.getPages().get(0).getPagePath());
    }
}