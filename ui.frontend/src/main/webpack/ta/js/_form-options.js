(function() {
    "use strict";

    var NS = "cmp";
    var NEW_IS = "neomFormDropDownOptions";
    var BORDER_ERROR_CLASS = " -hasError";

    var selectors = {
        self: "[data-" + NS + '-is="' + NEW_IS + '"]'
    };

    var properties = {
        /**
         * A validation message to display if no input is supplied, but input is expected for the field.
         *
         * @type {String}
         */
        requiredMessage: "",
        firstElementText: ""
    };

    /**
     * initialize the Form object
     * @param {object with the div element and the attributes from previous function} config 
     */
    function FormOptions(element) {
        if (element) {
            // prevents multiple initialization
            element.removeAttribute("data-" + NS + "-is");

            this._element = element;

            if (this._element.parentElement.getElementsByClassName("cmp-form-options__error-message")){
                this._errorMessageElement = this._element.parentElement.getElementsByClassName("cmp-form-options__error-message")[0];
            }

            this._setupProperties();

            if(this._element.tagName === 'SELECT'){
                let option = document.createElement("option");
                option.text = this._properties.firstElementText ? this._properties.firstElementText : "Select";
                option.value = "";
                option.disabled = true;
                option.selected = getDefaultSelection(this._element);
                this._element.add(option, this._element[0]);
            }

            this._element.addEventListener("invalid", this._onInvalid.bind(this));
            this._element.addEventListener("change", this._onChange.bind(this));
            this._element.addEventListener("focusout", this._onChange.bind(this));
        }
    }

    /**
     * loads the attributes of the fieldset select into the properties structure part of the form object
     * @param {options from readData function} options 
     */
    FormOptions.prototype._setupProperties = function() {
        this._properties = {};

        var data = this._element.dataset;

        if(data){
            for (var key in properties) {
                if (Object.prototype.hasOwnProperty.call(properties, key)) {
                    if (data[key] != null) {
                        this._properties[key] = data[key];
                    } else {
                        this._properties[key] = properties[key]["default"];
                    }
                }
            }
        }
    };

    /**
     * handles the invalid event in the form text object
     * @param {event} event 
     */
    FormOptions.prototype._onInvalid = function(event) {
        event.target.setCustomValidity("");
        event.preventDefault();
        this._validateFormAndHandleErrorMessage(event);
    };

    /**
     * handles the change value event in the text form object (this function is called for both events, change and focusout)
     * @param {event} event 
     */
    FormOptions.prototype._onChange = function(event) {
        this._cleanErrorMessageInFormField(event);
        this._validateFormAndHandleErrorMessage(event);
    };

    /**
     * handles the form text validations by reading the target validity from event
     * @param {event} event 
     * @returns 
     */
    FormOptions.prototype._validateFormAndHandleErrorMessage = function (event) {
        if (event.target.validity.valueMissing) {
            if (this._properties.requiredMessage) {
                this._showErrorMessage(this._properties.requiredMessage);
                return;
            }
        }
    }

    /**
     * removes all css styles and error messages from the options form object
     * @param {event} event 
     */
    FormOptions.prototype._cleanErrorMessageInFormField = function (event) {
        event.target.setCustomValidity("");
        this._errorMessageElement.style.display = "none";
        this._errorMessageElement.innerHTML = "";
        this._element.className = this._element.className.replace(BORDER_ERROR_CLASS,"");
    }

    /**
     * displays the div error message and sets the css classes accordingly
     * @param {error message text} errorMessage 
     */
    FormOptions.prototype._showErrorMessage = function(errorMessage) {
        this._errorMessageElement.innerHTML = errorMessage;
        this._errorMessageElement.style.display = "block";
        this._element.className += BORDER_ERROR_CLASS;
    }

    /**
     * returns if an options is authored to be selected
     * @param {list of options in a select element} options 
     * @returns 
     */
    function getDefaultSelection(selectElement){
        if(selectElement.length == 0)
            return true;

        for(let i = 0; i < selectElement.length; i++){
            let option = selectElement[i];
            if(option.dataset !== null && JSON.parse(option.dataset.isSelected) === true){
                option.selected = true;
                return false;
            }
        }
        
        return true;
    }

    if(document.querySelector("#new_form")?.elements["form_id"]?.value==='nta_register_interest') {
        const checkbox = document.querySelector('[data-fieldid="category_interest"]')?.querySelectorAll("input[type=checkbox]");
        checkbox?.forEach((check) => {
            check?.addEventListener("change", function () {
                var checkboxNotChecked = document.querySelector('[data-fieldid="category_interest"]').querySelectorAll("input[type=checkbox]:not(:checked)");
                var checked = document.querySelector('[data-fieldid="category_interest"]').querySelectorAll("input[type=checkbox]:checked").length
                if(checked === 3) {
                    checkboxNotChecked.forEach(((element) => element.setAttribute('disabled', '')));
                } else {
                     checkboxNotChecked.forEach(((element) => element.removeAttribute('disabled')));
                }
            });
        });
    }

    /**
     * initialization component function
     */
    function onDocumentReady() {
        var elements = document.querySelectorAll(selectors.self);
        for (var i = 0; i < elements.length; i++) {
            new FormOptions(elements[i]);
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
                                new FormOptions(element);
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