package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.ConfirmationMessageModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, adapters = ConfirmationMessageModel.class,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, 
       resourceType = ConfirmationMessageModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
	extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ConfirmationMessageModelImpl implements ConfirmationMessageModel {

    public static final String RESOURCE_TYPE = "talentacademy/components/content/confirmationmessage";

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    /**
     * @return the title.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @return the description.
     */
    @Override
    public String getDescription() {
        return description;
    }
}
