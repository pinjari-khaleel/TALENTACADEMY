package com.talentacademy.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterGlobalLinks {

    @ValueMapValue
    private String header;

    @ChildResource
    private List<Links> links;

	/**
	 * @return The header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @return The list of links
	 */
	public List<Links> getLinks() {
		return new ArrayList<>(links);
	}
    
}
