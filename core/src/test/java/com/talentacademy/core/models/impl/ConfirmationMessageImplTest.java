package com.talentacademy.core.models.impl;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class ConfirmationMessageImplTest {

    @InjectMocks
    ConfirmationMessageModelImpl confirmationMessageModel;

    private Page page;
    private Resource resource;
    private final AemContext context = new AemContext();
    private static final String NODE = "columncontrol";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";


    /**
     * setUp method for
     * {@link ConfirmationMessageModelImpl}.
     */
    @BeforeEach
    public void setup() throws Exception {
        context.addModelsForClasses(ConfirmationMessageModelImpl.class);
        page = context.create().page("/content/talentacademy");
    }

    /**
     * Test method for
     * {@link ConfirmationMessageModelImpl #getTitle()}.
     */
    @Test
    void testGetTitle() {
        resource = context.create().resource(page, NODE,
                JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, ConfirmationMessageModelImpl.RESOURCE_TYPE,
                TITLE, "Title");
        confirmationMessageModel = resource.adaptTo(ConfirmationMessageModelImpl.class);
        assertEquals("Title", confirmationMessageModel.getTitle());
    }

    /**
     * Test method for
     * {@link ConfirmationMessageModelImpl #getDescription()}.
     */
    @Test
    void testGetDescription() {
        resource = context.create().resource(page, NODE,
                JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, ConfirmationMessageModelImpl.RESOURCE_TYPE,
                DESCRIPTION, "Description");
        confirmationMessageModel = resource.adaptTo(ConfirmationMessageModelImpl.class);
        assertEquals("Description", confirmationMessageModel.getDescription());

    }
}
