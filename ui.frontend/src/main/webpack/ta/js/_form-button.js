(function() {
    "use strict";

    const NS = "cmp";
    const NEW_IS = "neomFormButton";
    const FIELDSET_TAGNAME = "FIELDSET";
    const LEGEND_TAGNAME = "LEGEND";
    const RADIO_CLASS = "options--radio";
    const CHECKBOX_CLASS = "options--checkbox";
    const REQUIRED_CLASS = "required";
    const RADIOBUTTONCONTAINER_CLASS = "radiobuttoncontainer";
    const STYLEBLOCK_NONE = "none";
    const FORM_TAGNAME = "FORM";

    const selectors = {
        self: "[data-" + NS + '-is="' + NEW_IS + '"]'
    };

    /**
     * Constructor
     * @param {form button element} element 
     */
    function FormButton(element) {
        if (element) {
            // prevents multiple initialization
            element.removeAttribute("data-" + NS + "-is");
            this._buttonComponent = element;
            this._formElement = this._buttonComponent.closest("form");
            this._buttonIsDisabled = false;//property to indicate when the button has disabled flag property set and needs validation before submitting with recaptcha

            if(this._buttonComponent.disabled){
                this._buttonIsDisabled = true;
                this._formElement.addEventListener("input", this._onChange.bind(this));
                this._formElement.addEventListener("change", this._onChange.bind(this));
            }

            this._buttonComponent.addEventListener("click", this._onClick.bind(this));
        }
    }

    /**
     * form onchange event load
     * @param {onchange/input event} event 
     */
    FormButton.prototype._onChange = function(event){
        let isValidForm = true;
        var formElements = this._formElement.elements;
        for(var i = 0; i < formElements.length ; i++){
            var element = formElements[i];
            if(isElementInRadioButtonContainer(element,false))
                continue;

            if(element.tagName === FIELDSET_TAGNAME 
                && (element.className.includes(RADIO_CLASS) || element.className.includes(CHECKBOX_CLASS))){
                if(!isValidFieldSetElement(element)){
                    isValidForm = false;
                    break;
                }
            }
            if(!element.validity.valid){
                isValidForm = false;
                break;
            }
            var isIqamaValid = element.getAttribute("isIqamaValid");
            if(isIqamaValid){
                isValidForm = false;
                break;
            }
            if(element.name == 'phoneCode' && element.required && !element.value){
				isValidForm = false;
				break;
			}
        }
        this._buttonComponent.disabled = !isValidForm;
    }

    /**
     * handles button onclick event
     * @param {button onclick event} event 
     */
    FormButton.prototype._onClick = function(event) {
        addInpuHiddenFieldsToForm(this._formElement);
        if(this._buttonIsDisabled){
            this._formElement.noValidate = true;
        }
        if(typeof grecaptcha !== 'undefined'){
            event.preventDefault();
            if(!this._formElement.noValidate){
                if(this._formElement.checkValidity())
                    runRecaptcha();
            }else{
                runRecaptcha();
            }
        }

        const formId = this._formElement.elements["form_id"]?.value;
        const consent = this._formElement.elements["contact-permissions"]?.value;
        const leadType = this._formElement.elements["lead_type"]?.value?.replace(/ /g, "_")?.toLowerCase();
        sessionStorage.setItem("formId", formId);
        sessionStorage.setItem("formName", `${formId}_${leadType}`);
        sessionStorage.setItem("consent", consent);
    };

    /**
     * calling recaptcha api
     */
    function runRecaptcha(){
        grecaptcha.reset();
        grecaptcha.execute();
    }

    /**
     * add hidden fields from browser local storage
     * @param {form element} formElement 
     */
    function addInpuHiddenFieldsToForm(formElement){
        let courseId = localStorage.getItem("courseId");
        let learningProgramId = localStorage.getItem("learningProgramId");

        if(courseId !== ""){
            setHiddenInputToForm("courseId",courseId,formElement);
        }

        if(learningProgramId !== ""){
            setHiddenInputToForm("learningProgramId",learningProgramId,formElement);
        }
    }

    /**
     * Creates hidden field and appends it to the form element
     * @param {hidden field name} name 
     * @param {hidden field value} value 
     * @param {html element to append hidden element} element 
     */
    function setHiddenInputToForm(name,value,element){
        var input = document.createElement("input");

        input.setAttribute("type", "hidden");
        input.setAttribute("name", name);
        input.setAttribute("value", value);

        if(element.children[name] !== undefined){
            var inputTmp = element.children[name];
            element.removeChild(inputTmp);
        }

        element.appendChild(input);
    }

    /**
     * validates if the fieldset is valid when it has required flag
     * @param {fieldset element} fieldsetElement 
     */
    function isValidFieldSetElement(fieldsetElement){
        var fieldSetChildrenElements = fieldsetElement.children;
        var fieldSetInputElements = fieldsetElement.elements;

        if(fieldSetChildrenElements !== null && fieldSetChildrenElements.length > 0){
            if(fieldSetChildrenElements[0].tagName === LEGEND_TAGNAME && fieldSetChildrenElements[0].className.includes(REQUIRED_CLASS)){
                for(var i = 0; i < fieldSetInputElements.length; i++){
                    if(fieldSetInputElements[i].checked)
                        return true;
                }
                return false;
            }
        }

        return true;
    }

    /**
     * validates if element is part of radio button container and it has no display none
     * @param {form element} element 
     * @param {element should be evaluated} isElementEvaluated 
     * @returns 
     */
    function isElementInRadioButtonContainer(element,isElementEvaluated){
        if(element.style.display === STYLEBLOCK_NONE){
            isElementEvaluated = true;
        }

        if(element.parentElement.className.includes(RADIOBUTTONCONTAINER_CLASS)){
            if(isElementEvaluated == true){
                return true;
            }else{
                return false;
            }
        }else if(element.parentElement.tagName === FORM_TAGNAME){
            return false;
        }else{
            return isElementInRadioButtonContainer(element.parentElement,isElementEvaluated);
        }
    }

    /**
     * loads element - base initial function to create object
     */
    function onDocumentReady() {
        var elements = document.querySelectorAll(selectors.self);
        for (var i = 0; i < elements.length; i++) {
            new FormButton(elements[i]);
        }

        var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
        var body = document.querySelector("body");
        var observer = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                // needed for IE
                var nodesArray = [].slice.call(mutation.addedNodes);
                if (nodesArray.length > 0) {
                    nodesArray.forEach(function(addedNode) {
                        if (addedNode.querySelectorAll) {
                            var elementsArray = [].slice.call(addedNode.querySelectorAll(selectors.self));
                            elementsArray.forEach(function(element) {
                                new FormButton(element);
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

})();
