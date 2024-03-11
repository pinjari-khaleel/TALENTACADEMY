package com.talentacademy.core.models;

import org.apache.sling.api.resource.Resource;

public interface CareerDetailsModel {

    /**
     * @return The job title
     */
    public String getJobTitle();

    /**
     * @return The recruitment status
     */
    public String getRecruitmentStatus();

    /**
     * @return The multifield of job role description
     */
    public Resource getRoleDetails();

    /**
     * @return The expected demand growth label
     */
    public String getDemandGrowthLabel();

    /**
     * @return The expected demand growth value
     */
    public String getDemandGrowthValue();

    /**
     * @return The salary range label
     */
    public String getSalaryRangeLabel();

    /**
     * @return The salary range value
     */
    public String getSalaryRangeValue();

    /**
     * @return The potential employer label
     */
    public String getPotentialEmployersLabel();

    /**
     * @return The list of employer icons label
     */
    public Resource getEmployerIcons();

    /**
     * @return The register interest CTA label
     */
    public String getRegisterInterestCtaLabel();

    /**
     * @return The register interest CTA link
     */
    public String getRegisterInterestCtaLink();

    /**
     * @return The open in new tab
     */
    public String getOpenInNewTab();

}
