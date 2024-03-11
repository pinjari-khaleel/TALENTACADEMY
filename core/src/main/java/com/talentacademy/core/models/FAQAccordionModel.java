package com.talentacademy.core.models;

import com.talentacademy.core.beans.QnA;

import java.util.List;

/**
 * FAQ Accordion Model
 **/
public interface FAQAccordionModel {

    /**
     * @return title
     */
    String getTitle();

    /**
     * @return frequently asked questions list object.
     */
    List<QnA> getFaq();

    /**
     * @return Load more items limit.
     */
    int getLoadMoreLimit();

    /**
     * @return load more text
     */
    String getLoadMoreText();

    /**
     * @return total count of Faqs
     */
    int getTotalCount();
}
