package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.QnA;
import com.talentacademy.core.models.FAQAccordionModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
class FAQAccordionModelImplTest {

    private final AemContext ctx = new AemContext();

    @InjectMocks
    FAQAccordionModelImpl FAQAccordionModel;

    /**
     * setUp method for
     * {@link FAQAccordionModel}.
     */
    @BeforeEach
    public void setup() throws Exception {
        ctx.addModelsForClasses(FAQAccordionModelImpl.class);
        ctx.load().json("/com/talentacademy/core/models/impl/faqAccordion.json", "/content");
        Resource resource = ctx.resourceResolver().getResource("/content/faqaccordion");
        FAQAccordionModel = resource.adaptTo(FAQAccordionModelImpl.class);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.FAQAccordionModelImpl#getTitle()}.
     */
    @Test
    void testTitle() {
        assertEquals("Frequently Asked Questions", FAQAccordionModel.getTitle());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.FAQAccordionModelImpl#getFaq()}
     */
    @Test
    void testFaq() {
        List<QnA> faq = FAQAccordionModel.getFaq();
        assertEquals("Which courses are right for me?", faq.get(0).getQuestion());
        assertEquals("Following courses are right for you", faq.get(0).getAnswer());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.FAQAccordionModelImpl#getLoadMoreText()}
     */
    @Test
    void testLoadMoreText() {
        assertEquals("Load More", FAQAccordionModel.getLoadMoreText());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.FAQAccordionModelImpl#getLoadMoreLimit()}
     */
    @Test
    void testLoadMoreLimit() {
        assertEquals(2, FAQAccordionModel.getLoadMoreLimit());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.FAQAccordionModelImpl#getTotalCount()}
     */
    @Test
    void testGetTotalCount(){
        FAQAccordionModel.setTotalCount(4);
        assertEquals(4,FAQAccordionModel.getTotalCount());
    }
}