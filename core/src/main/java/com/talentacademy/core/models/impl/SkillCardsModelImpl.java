package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.beans.SkillCardItem;
import com.talentacademy.core.models.SkillCardsModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = SkillCardsModel.class,
        resourceType = SkillCardsModelImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SkillCardsModelImpl implements SkillCardsModel {
    public static final String RESOURCE_TYPE = "talentacademy/components/content/skillcards";

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    @Inject
    private String shortTitle;

    @Inject
    private String fileReference;

    @Inject
    private String cardVariation;

    @ChildResource
    private List<SkillCardItem> cardItems;

    /**
     * @return the title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @return the description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return the shortTitle
     */
    @Override
    public String getShortTitle() {
        return shortTitle;
    }

    /**
     * @return the fileReference
     */
    @Override
    public String getFileReference() {
        return fileReference;
    }

    /**
     * @return the cardVariation
     */
    @Override
    public String getCardVariation() {
        return cardVariation;
    }

    /**
     * @return the list of card items
     */
    @Override
    public List<SkillCardItem> getCardItems() {
        return new ArrayList<>(cardItems);
    }
}
