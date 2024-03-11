
(function() {
    "use strict";

    var COLUMN_CONTAINER_CLASS = "columncontrol";
    var TEXT_CLASSNAME_COLUMNCONTAINER = "text";
    var OPTIONS_CLASSNAME_COLUMNCONTAINER = "options";

    var selectors = {
        self: '[data-cmp-is="formRadioButtonContainer"]',
        data: 'data-cmp-data-layer'
    };

    var EDIT_MODE_COOKIE_NAME = 'cq-editor-layer.page';

    function FormRadioButtonContainer(config) {

        var editCookie = document.cookie.match('(^|;)\\s*' + EDIT_MODE_COOKIE_NAME + '\\s*=\\s*([^;]+)')?.pop() || '' ; //get edit mode cookie name

        if(editCookie === "Edit")
            return;

        if (config && config.element) { // creating object when is preview or published views
            config.element.removeAttribute("data-cmp-is");

            var configAttr = config.element.getAttribute(selectors.data);

            if(configAttr == null)
                return;

            config.element.addEventListener("change", this._handleRadioButtonChange.bind(this));

            this._childrenElements = {};
            this._childrenElements = config.element.closest('div').children;//Loading into the options object all text forms in the document
            
            this._configurationMap = new Map(JSON.parse(configAttr));
            
            config.element.removeAttribute(selectors.data);

            hideAllChildrenElements(this._childrenElements);

            var isRadioChecked = getCheckedElement(this._childrenElements[0]);
            if(isRadioChecked){
                performShowAndHideValidation(this._childrenElements,isRadioChecked,this._configurationMap);
            }
            const radioButtons = document.querySelector("#new_form")?.querySelectorAll('input[type="radio"]');
            radioButtons.forEach(radio => {
                radio.addEventListener("change", hideEmployeeId);
            });
        }
    }

    //class method loading the change event in the radio button container
    FormRadioButtonContainer.prototype._handleRadioButtonChange = function(event){
        var item = event.target.value;

        hideAllChildrenElements(this._childrenElements); //hiding all elements as new event is triggered
        
        performShowAndHideValidation(this._childrenElements,item,this._configurationMap);

        hideEmployeeId();
    };

    /**
     * Function to hide the employee id field based on the selected option
     */
    function hideEmployeeId() {
        const empIdBlock = document.getElementsByTagName("form")[0]?.elements["employee_id"]?.closest("div")?.parentElement;
        if (empIdBlock) {
            const worktype = document.querySelector('#work-in-neom');
            if (worktype?.checked && worktype?.closest("div")?.style.display === "block") {
                empIdBlock.style.display = "block";
            } else {
                empIdBlock.style.display = "none";
            }
        }
    }

    function performShowAndHideValidation(childrenElements,item,configMap){
        var elementValuesList = configMap.get(item);

        if(elementValuesList && elementValuesList.length > 0){
            
            for(var i = 0; i < elementValuesList.length; i++){
                var elementObject = elementValuesList[i];
                if(elementObject.isRadio == null)
                    continue;

                if(elementObject.isRadio){
                    hideShowElements(childrenElements,elementObject,true,"SPAN");
                }else{
                    hideShowElements(childrenElements,elementObject,false,"LABEL");
                }
            }
        }
    }

    //iterates over the children elements of the radio button container and calls method showHideElements for each children
    function hideShowElements (childrenElements,elementObject,isRadioElement,tagElementName){
        for(var i = 1; i < childrenElements.length; i++){
            var containerElement = childrenElements[i];
            var isColumnContainer = containerElement.className.includes(COLUMN_CONTAINER_CLASS);

            if(!containerElement.className.includes(elementObject.type) && !isColumnContainer) //validates if the children is of the type of the element that should be displayed, or column container element
                continue;
            
            if(isCheckBoxElement(containerElement.children))//in case the children is a checkbox element, the value is in the legend tag
                tagElementName = "LEGEND";

            if(showHideElements(containerElement.children,elementObject.value,isRadioElement,tagElementName, isColumnContainer)){
                if(containerElement.style.display != "block")
                    containerElement.style.display = "block";
                break;//breaks as the config element has been found, need to iterate over the other config elements
            }
        }
    }

    //recursive function that looks for the configured value to be displayed
    function showHideElements(childElements,elementValue,isRadioElement,tagElementName,isColumnContainer){

        if(childElements == null)
            return false;

        for(var i = 0; i< childElements.length; i++){
            if(childElements[i].tagName === "SELECT" && !isRadioElement){ //stop recursion for dropdown and multidropdown elements to avoid unneeded loop within select values
                break;
            }
            if(childElements[i].tagName === tagElementName && childElements[i].textContent === elementValue){
                if(isRadioElement){
                    childElements[i].closest('label').style.display = "inline-block";
                }else if(isColumnContainer){
                    childElements[i].closest('div[style="display: none;"]').style.display = "block";                
                }

                return true;
            }else{
                if(showHideElements(childElements[i].children,elementValue,isRadioElement,tagElementName,isColumnContainer))
                    return true; //stop recursion when element is found
            }
        }

        return false;
    }

    //validates if element is of checkbox type
    function isCheckBoxElement(elements){
        if(elements == null)
            return false;

        if(elements.length == 0)
            return false;

        return elements[0].className.includes("checkbox");
    }

    //validates if element is of radio button type
    function isRadioElement(elements){
        if(elements == null)
            return false;

        var childrenElements = elements.children;
        if(childrenElements && childrenElements.length > 0){
            return childrenElements[0].className.includes("radio");
        }

        return false;
    }

    //hides radio button elements at label level
    function hideRadioElements(radioElements){
        if(radioElements && radioElements.length > 0){
            var labelRadioChildrenElements = radioElements[0].children;
            for(var i = 0 ; i < labelRadioChildrenElements.length ; i++){
                if(labelRadioChildrenElements[i].tagName === "LABEL"){
                    labelRadioChildrenElements[i].style.display = "none";
                }
            }
        }
    }

    //hides elements in column container
    function hideColumnContainerElements(containerElement){
        var children = containerElement.children;
        
        if(children.length === 0)
            return;

        for(var i = 0; i < children.length; i++){
            if(children[i].className.includes(TEXT_CLASSNAME_COLUMNCONTAINER) || children[i].className.includes(OPTIONS_CLASSNAME_COLUMNCONTAINER)){
                children[i].style.display = "none";
            }else{
                hideColumnContainerElements(children[i]);
            }
        }

    }

    //hide all children elements in the radio button container at div level
    function hideAllChildrenElements(childrenElements) {
        //hiding all elements in contianer except parent radio button element
        for (var i = 1; i < childrenElements.length; i++) {
            var childElement = childrenElements[i];
            if(childElement.className.includes(COLUMN_CONTAINER_CLASS)){
                hideColumnContainerElements(childElement);
            }else{
                childElement.style.display = "none";
                if(isRadioElement(childElement)){
                    hideRadioElements(childElement.children);
                }
            }
        }
    }

    //returns the value of the radio button that has been selected - for first load only, case when returning from servlet with error from backend
    function getCheckedElement(elements){
        var firstElementChildren = elements.children;
        for (var i = 0; i < firstElementChildren.length; i++) {
            var element = firstElementChildren[i];
            if(element.tagName === 'LABEL' && element.children[0].checked){
                return element.children[0].value;
            }
        }
    }

    // Best practice:
    // Use a method like this mutation obeserver to also properly initialize the component
    // when an author drops it onto the page or modified it with the dialog.
    function onDocumentReady() {
        var elements = document.querySelectorAll(selectors.self);
        for (var i = 0; i < elements.length; i++) {
            new FormRadioButtonContainer({ element: elements[i] });
        }

        var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
        var body             = document.querySelector("body");
        var observer         = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                // needed for IE
                var nodesArray = [].slice.call(mutation.addedNodes);
                if (nodesArray.length > 0) {
                    nodesArray.forEach(function(addedNode) {
                        if (addedNode.querySelectorAll) {
                            var elementsArray = [].slice.call(addedNode.querySelectorAll(selectors.self));
                            elementsArray.forEach(function(element) {
                                new FormRadioButtonContainer({ element: element });
                            });
                        }
                    });
                }
            });
        });

        observer.observe(body, {
            subtree: true,
            childList: true,
            characterData: true
        });
    }

    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }

}());
