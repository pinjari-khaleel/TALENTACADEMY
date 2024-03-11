package com.talentacademy.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.talentacademy.core.models.CareerDetailsModel;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, adapters = CareerDetailsModel.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = CareerDetailsModelImpl.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CareerDetailsModelImpl implements CareerDetailsModel {

    public static final String RESOURCE_TYPE = "talentacademy/components/content/careerdetails";

    @ChildResource
    private Resource roleDetails;

    @ChildResource
    private Resource employerIcons;

    @ValueMapValue
    private String jobTitle;

    @ValueMapValue
    private String demandGrowthLabel;

    @ValueMapValue
    private String demandGrowthValue;

    @ValueMapValue
    private String salaryRangeLabel;

    @ValueMapValue
    private String salaryRangeValue;

    @ValueMapValue
    private String potentialEmployersLabel;

    @ValueMapValue
    private String registerInterestCtaLabel;

    @ValueMapValue
    private String registerInterestCtaLink;

    @ValueMapValue
    private String openInNewTab;

    @ValueMapValue
    private String recruitmentStatus;

    /**
     * @return The job title
     */
    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @return The recruitment status
     */
    @Override
    public String getRecruitmentStatus() {
        return recruitmentStatus;
    }

    /**
     * @return The multifield of job role description
     */
    @Override
    public Resource getRoleDetails() {
        return roleDetails;
    }

    /**
     * @return The expected demand growth label
     */
    @Override
    public String getDemandGrowthLabel() {
        return demandGrowthLabel;
    }

    /**
     * @return The expected demand growth value
     */
    @Override
    public String getDemandGrowthValue() {
        return demandGrowthValue;
    }

    /**
     * @return The salary range label
     */
    @Override
    public String getSalaryRangeLabel() {
        return salaryRangeLabel;
    }

    /**
     * @return The salary range value
     */
    @Override
    public String getSalaryRangeValue() {
        return salaryRangeValue;
    }

    /**
     * @return The potential employer label
     */
    @Override
    public String getPotentialEmployersLabel() {
        return potentialEmployersLabel;
    }

    /**
     * @return The list of employer icons label
     */
    @Override
    public Resource getEmployerIcons() {
        return employerIcons;
    }

    /**
     * @return The register interest CTA label
     */
    @Override
    public String getRegisterInterestCtaLabel() {
        return registerInterestCtaLabel;
    }

    /**
     * @return The register interest CTA link
     */
    @Override
    public String getRegisterInterestCtaLink() {
        return registerInterestCtaLink;
    }

    /**
     * @return The open in new tab
     */
    @Override
    public String getOpenInNewTab() {
        return openInNewTab;
    }

}
