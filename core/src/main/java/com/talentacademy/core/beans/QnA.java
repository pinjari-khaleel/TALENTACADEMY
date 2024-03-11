package com.talentacademy.core.beans;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QnA {

    @ValueMapValue
    private String question;

    @ValueMapValue
    private String answer;

    /**
     * @return The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return The answer
     */
    public String getAnswer() {
        return answer;
    }
}
