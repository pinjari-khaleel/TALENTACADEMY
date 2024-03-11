package com.talentacademy.core.models.impl;

import com.talentacademy.core.beans.SkillCardItem;
import com.talentacademy.core.models.SkillCardsModel;
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
class SkillCardsModelTest {

    private final AemContext ctx = new AemContext();

    @InjectMocks
    SkillCardsModel cardsModel;

    @BeforeEach
    public void setup() throws Exception {
        ctx.addModelsForClasses(SkillCardsModelImpl.class);
        ctx.load().json("/com/talentacademy/core/models/impl/SkillCards.json", "/content");
        Resource resource = ctx.resourceResolver().getResource("/content/skillCards");
        cardsModel = resource.adaptTo(SkillCardsModel.class);
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.SkillCardsModelImpl#getTitle()}.
     */
    @Test
    void testTitle() {
        assertEquals("Our Offerings", cardsModel.getTitle());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.SkillCardsModelImpl#getDescription()}.
     */
    @Test
    void testDescription(){
        assertEquals("Unlock your potential and acquire the skills you need to secure your dream role.",cardsModel.getDescription());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.SkillCardsModelImpl#getShortTitle()}
     */
    @Test
    void testShortTitle(){
        assertEquals("short Title",cardsModel.getShortTitle());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.SkillCardsModelImpl#getFileReference()}
     */
    @Test
    void testBackgroundImage(){
        assertEquals("/content/dam/talentacademy",cardsModel.getFileReference());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.SkillCardsModelImpl#getCardVariation()}
     */
    @Test
    void testCardVariation(){
        assertEquals("fourCardsLeftAlign",cardsModel.getCardVariation());
    }

    /**
     * Test method for
     * {@link com.talentacademy.core.models.impl.SkillCardsModelImpl#getCardItems()}
     */
    @Test
    void testCardItems() {
        List<SkillCardItem> items = cardsModel.getCardItems();
        assertEquals("Language Proficiency", items.get(0).getTitle());
        assertEquals("Help learners achieve proficiency", items.get(0).getDescription());
        assertEquals("/content", items.get(0).getIcon());
    }
}
