package com.talentacademy.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.talentacademy.core.utils.ApplicationUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.talentacademy.core.constants.ApplicationConstants.*;

/**
 * Servlet datasource class to provide the children elements in the container
 */
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes= "talentacademy/components/form/radiobuttoncontainer/datasource/dropdownelements",
        methods= HttpConstants.METHOD_GET
)
public class GetRadioButtonContainerElementsServlet extends SlingSafeMethodsServlet {

    private static final String OPTIONS_FORM_RESOURCE_TYPE = "talentacademy/components/form/options";
    private static final String TEXT_FORM_RESOURCE_TYPE = "talentacademy/components/form/text";
    private static final String COLUMN_CONTAINER_RESOURCE_TYPE = "talentacademy/components/content/columncontrol";
    private static final String OPTION_FORM_TYPE_PROPERTY = "type";

    @Override
    protected void doGet(@NotNull final SlingHttpServletRequest request, @NotNull final SlingHttpServletResponse response) throws ServletException {
        ResourceResolver resourceResolver = request.getResourceResolver();

        String resourcePathFromUri = ApplicationUtil.getSecondElementFromSplitUri(request.getRequestURI());

        Resource radioButtonContainerResource = (resourcePathFromUri != null
                ? resourceResolver.getResource(resourcePathFromUri) : null);

        if(radioButtonContainerResource == null)
            return;

        List<Resource> optionResourceList = getResources(radioButtonContainerResource,resourceResolver);
        DataSource ds = new SimpleDataSource(optionResourceList.iterator());
        request.setAttribute(DataSource.class.getName(), ds);
    }

    /**
     * Returns the children elements list that are of form text or form options in the container
     * @param radioButtonResource
     * @param resourceResolver
     * @return
     */
    private List<Resource> getResources(Resource radioButtonResource, ResourceResolver resourceResolver){
        List<Resource> childrenResourcesList = new ArrayList<>();

        Iterator< Resource > children = radioButtonResource.listChildren();

        while (children.hasNext()) {
            Resource child = children.next();
            if(child.isResourceType(COLUMN_CONTAINER_RESOURCE_TYPE)){
                setColumnContainerChildren(child.getChildren(),childrenResourcesList);
            }else if ((child.isResourceType(OPTIONS_FORM_RESOURCE_TYPE) || child.isResourceType(TEXT_FORM_RESOURCE_TYPE))){
                childrenResourcesList.add(child);
            }
        }

        return obtainResourcesList(childrenResourcesList,resourceResolver);
    }

    /**
     * Adds form text/options elements that are within a column container component
     * @param columnContainerChildren
     * @param childrenResourcesList
     */
    private void setColumnContainerChildren(Iterable<Resource> columnContainerChildren, List<Resource> childrenResourcesList){
        StreamSupport.stream(columnContainerChildren.spliterator(),false)
                .map(column -> column.getChildren().spliterator())
                .flatMap(splIterator -> StreamSupport.stream(splIterator,false))
                .filter(child -> child.isResourceType(OPTIONS_FORM_RESOURCE_TYPE) || child.isResourceType(TEXT_FORM_RESOURCE_TYPE))
                .forEach(childrenResourcesList::add);
    }

    /**
     * Returns the list of form text or form options in the container as list
     * @param resources
     * @param resourceResolver
     * @return
     */
    private List<Resource> obtainResourcesList(List<Resource> resources, ResourceResolver resourceResolver){
        List<Resource> resourcesList = new ArrayList<>();

        if(resources.isEmpty())
            return resourcesList;

        populateListWithTextFormAndOptionFormNoRadioChildren(resourcesList,resources,resourceResolver);

        populateListWithOptionFormRadioChildren(resourcesList,resources,resourceResolver);

        return resourcesList;
    }

    /**
     * Populate the list with only form text or form options no radio elements in the container
     * @param resourcesList
     * @param resources
     * @param resourceResolver
     */
    private void populateListWithTextFormAndOptionFormNoRadioChildren(List<Resource> resourcesList, List<Resource> resources, ResourceResolver resourceResolver) {
        List<Resource> resourcesNoRadioOption = resources.stream().filter(r -> r.isResourceType(TEXT_FORM_RESOURCE_TYPE) ||
                        !r.getValueMap().get(OPTION_FORM_TYPE_PROPERTY).equals(OPTION_FORM_TYPE_PROPERTY_RADIO))
                .map(rt -> {
                    ValueMap vm = new ValueMapDecorator(new HashMap<>());
                    vm.put(DROPDOWN_VALUE_KEY, Optional.ofNullable(rt.getName() + SLASH + rt.getValueMap().get(JcrConstants.JCR_TITLE)).orElse(StringUtils.EMPTY));
                    vm.put(DROPDOWN_TEXT_KEY, Optional.ofNullable(rt.getValueMap().get(JcrConstants.JCR_TITLE)).orElse(StringUtils.EMPTY));
                    return vm;})
                .map(v -> new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, v))
                .collect(Collectors.toList());

        resourcesList.addAll(resourcesNoRadioOption);
    }

    /**
     * Populate the list with only form options radio elements, returns the items in the radio form
     * @param resourcesList
     * @param resources
     * @param resourceResolver
     */
    private void populateListWithOptionFormRadioChildren(List<Resource> resourcesList, List<Resource> resources, ResourceResolver resourceResolver) {
        List<Resource> optionItemResources = resources.stream().filter(r -> r.getValueMap().get(OPTION_FORM_TYPE_PROPERTY).equals(OPTION_FORM_TYPE_PROPERTY_RADIO)
                        && r.isResourceType(OPTIONS_FORM_RESOURCE_TYPE))
                .map(r -> {
                    Iterator<Resource> resourceItemChildren = r.listChildren().next().listChildren();
                    List<OptionRadioSimpleResource> items = new ArrayList<>();
                    while(resourceItemChildren.hasNext()){
                        items.add(createOptionRadioSimpleResource(r.getName(),resourceItemChildren.next()));
                    }
                    return items;})
                .flatMap(Collection::stream)
                .map(rt -> {
                    ValueMap vm = new ValueMapDecorator(new HashMap<>());
                    vm.put(DROPDOWN_VALUE_KEY, rt.resourceName);
                    vm.put(DROPDOWN_TEXT_KEY, rt.resourceValue);
                    return vm;})
                .map(v -> new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, v))
                .collect(Collectors.toList());

        resourcesList.addAll(optionItemResources);
    }

    /**
     * creates the dropdown element
     * @param parentName
     * @param element
     * @return
     */
    private OptionRadioSimpleResource createOptionRadioSimpleResource(String parentName, Resource element){
        OptionRadioSimpleResource optionRadioSimpleResource = new OptionRadioSimpleResource();

        optionRadioSimpleResource.resourceName = parentName+SLASH+OPTION_FORM_TYPE_PROPERTY_RADIO+SLASH+Optional.ofNullable(element.getValueMap().get(DROPDOWN_TEXT_KEY)).orElse(StringUtils.EMPTY);
        optionRadioSimpleResource.resourceValue = Optional.ofNullable(element.getValueMap().get(DROPDOWN_TEXT_KEY)).orElse(StringUtils.EMPTY).toString();

        return optionRadioSimpleResource;
    }

    /**
     * Internal structure for the dropdown element
     */
    private class OptionRadioSimpleResource {
        String resourceName;
        String resourceValue;
    }

}
