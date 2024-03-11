package com.talentacademy.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.commons.jcr.JcrConstants;
import com.talentacademy.core.utils.ApplicationUtil;
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

import static com.talentacademy.core.constants.ApplicationConstants.HTML_EXTENSION;

/**
 * Datasource servlet class that provides the radio container values
 */
@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes= "talentacademy/components/form/radiobuttoncontainer/datasource/dropdownvalues",
        methods= HttpConstants.METHOD_GET
)
public class GetRadioButtonContainerValuesServlet extends SlingSafeMethodsServlet {

    private static final String DROPDOWN_VALUE_KEY = "value";
    private static final String DROPDOWN_TEXT_KEY = "text";

    @Override
    protected void doGet(@NotNull final SlingHttpServletRequest request, @NotNull final SlingHttpServletResponse response) throws ServletException {
        ResourceResolver resourceResolver = request.getResourceResolver();

        String resourcePathFromUri = ApplicationUtil.getSecondElementFromSplitUri(request.getRequestURI());

        Resource radioButtonContainerResource = (resourcePathFromUri != null
                ? resourceResolver.getResource(resourcePathFromUri) : null);

        if(radioButtonContainerResource == null)
            return;

        List<Resource> optionResourceList = getRadioButtonContainerResourceItemsValues(radioButtonContainerResource,resourceResolver);
        DataSource ds = new SimpleDataSource(optionResourceList.iterator());
        request.setAttribute(DataSource.class.getName(), ds);
    }

    /**
     * Returns the list of values in the radio form container
     * @param radioButtonResource
     * @param resourceResolver
     * @return
     */
    private List<Resource> getRadioButtonContainerResourceItemsValues(Resource radioButtonResource, ResourceResolver resourceResolver){
        List<Resource> childrenResourcesList = new ArrayList<>();

        Iterator< Resource > children = radioButtonResource.listChildren();

        Resource itemsResource = getRadioButtonContainerItemsResource(children);

        if(itemsResource == null)
            return childrenResourcesList;

        Iterator< Resource > itemsChildrenIterator = itemsResource.listChildren();

        while(itemsChildrenIterator.hasNext()){
            Resource itemResource = itemsChildrenIterator.next();
            ValueMap itemResourceProperties = itemResource.getValueMap();

            if(itemResourceProperties.get(DROPDOWN_VALUE_KEY) != null
                    && itemResourceProperties.get(DROPDOWN_TEXT_KEY) != null ){

                ValueMap vm = new ValueMapDecorator(new HashMap<>());
                vm.put(DROPDOWN_VALUE_KEY, itemResourceProperties.get(DROPDOWN_VALUE_KEY).toString());
                vm.put(DROPDOWN_TEXT_KEY, itemResourceProperties.get(DROPDOWN_TEXT_KEY).toString());

                childrenResourcesList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, vm));
            }
        }

        return childrenResourcesList;
    }

    /**
     * returns the items resource for the given children iterator resource
     * @param resourceIterator
     * @return
     */
    private Resource getRadioButtonContainerItemsResource(Iterator<Resource> resourceIterator){

        while (resourceIterator.hasNext()) {
            Resource child = resourceIterator.next();
            if(child.getName().equals("items")){
                return child;
            }
        }

        return null;
    }

}
