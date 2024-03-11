package com.talentacademy.core.models.impl;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.beans.QnA;
import com.talentacademy.core.models.FAQAccordionModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = FAQAccordionModel.class,
        resourceType = FAQAccordionModelImpl.RESOURCE_TYPE,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FAQAccordionModelImpl implements FAQAccordionModel {

    public static final String RESOURCE_TYPE = "talentacademy/components/content/faqaccordion";

    @ValueMapValue
    private String title;

    @ChildResource
    private List<QnA> faq;

    @ValueMapValue
    private int loadMoreLimit;

    @ValueMapValue
    private String loadMoreText;

    private int totalCount;

    /**
     * @return the title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @return the list of frequently asked questions
     */
    @Override
    public List<QnA> getFaq() {
        return new ArrayList<>(faq);
    }

    /**
     * @param faq
     */
    public void setFaq(List<QnA> faq) {
        faq = new ArrayList<>(faq);
        this.faq = Collections.unmodifiableList(faq);
    }

    /**
     * @return load more items limit
     */
    @Override
    public int getLoadMoreLimit() {
        return loadMoreLimit;
    }

    /**
     * @return load more text
     */
    @Override
    public String getLoadMoreText() {
        return loadMoreText;
    }

    /**
     * @return total count of FAQ's
     */
    @Override
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
