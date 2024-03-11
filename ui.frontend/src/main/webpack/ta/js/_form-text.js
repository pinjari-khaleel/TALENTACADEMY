(function() {
    "use strict";

    var NS = "cmp";
    var IS = "formText";
    var NEW_IS = "neomFormText";
    var IS_DASH = "form-text";
    var BORDER_ERROR_CLASS = " -hasError";

    var selectors = {
        self: "[data-" + NS + '-is="' + NEW_IS + '"]'
    };

    var properties = {
        /**
         * A validation message to display if there is a type mismatch between the user input and expected input.
         *
         * @type {String}
         */
        constraintMessage: "",
        /**
         * A validation message to display if no input is supplied, but input is expected for the field.
         *
         * @type {String}
         */
        requiredMessage: "",
        /**
         * A boolean field to know if the field needs the national id validation
         *
         * @type {Boolean}
         */
        isIqama: false
    };

    /**
     * returns the attributes starting with data in the element
     * @param {the div element} element 
     * @returns 
     */
    function readData(element) {
        var data = element.dataset;
        var options = [];
        var capitalized = IS;
        capitalized = capitalized.charAt(0).toUpperCase() + capitalized.slice(1);
        var reserved = ["is", "hook" + capitalized];

        for (var key in data) {
            if (Object.prototype.hasOwnProperty.call(data, key)) {
                var value = data[key];

                if (key.indexOf(NS) === 0) {
                    key = key.slice(NS.length);
                    key = key.charAt(0).toLowerCase() + key.substring(1);

                    if (reserved.indexOf(key) === -1) {
                        options[key] = value;
                    }
                }
            }
        }

        return options;
    }

    /**
     * initialize the Form object
     * @param {object with the div element and the attributes from previous function} config 
     */
    function FormText(config) {
        if (config.element) {
            // prevents multiple initialization
            config.element.removeAttribute("data-" + NS + "-is");

            if (config.element.getElementsByClassName("cmp-form-text__error-message")){
                this._errorMessageElement = config.element.getElementsByClassName("cmp-form-text__error-message")[0];
            }
        }

        this._cacheElements(config.element);
        this._setupProperties(config.options);

        this._elements.input.addEventListener("invalid", this._onInvalid.bind(this));
        this._elements.input.addEventListener("input", this._onInput.bind(this));
        this._elements.input.addEventListener("change", this._onChange.bind(this));
        this._elements.input.addEventListener("focusout", this._onChange.bind(this));
    }

    /**
     * handles the invalid event in the form text object
     * @param {event} event 
     */
    FormText.prototype._onInvalid = function(event) {
        event.target.setCustomValidity("");
        event.preventDefault();
        this._validateFormAndHandleErrorMessage(event);
    };

    /**
     * handles the input event - cleans the error messages styles
     * @param {event} event 
     */
    FormText.prototype._onInput = function(event) {
        this._cleanErrorMessageInFormField(event);
    };

    /**
     * handles the change value event in the text form object (this function is called for both events, change and focusout)
     * @param {event} event 
     */
    FormText.prototype._onChange = function(event) {
        this._cleanErrorMessageInFormField(event);
        this._validateFormAndHandleErrorMessage(event);
    };

    /**
     * handles the form text validations by reading the target validity from event
     * @param {event} event 
     * @returns 
     */
    FormText.prototype._validateFormAndHandleErrorMessage = function (event) {
        if (event.target.validity.typeMismatch || event.target.validity.patternMismatch) {
            if (this._properties.constraintMessage) {
                this._showErrorMessage(this._properties.constraintMessage);
                return;
            }
        } else if (event.target.validity.valueMissing) {
            if (this._properties.requiredMessage) {
                this._showErrorMessage(this._properties.requiredMessage);
                return;
            }
        }
        if(JSON.parse(this._properties.isIqama) === true){
            if(!isNotValidIqamaNZValidation(this._elements.input.value)){
                if (this._properties.constraintMessage) {
                    this._showErrorMessage(this._properties.constraintMessage);
                    this._elements.input.setAttribute("isIqamaValid","false");
                }
            }
        }
    }

    /**
     * validates only the NZ processing rules on an iqama input text field, other rules are validated via regular expression
     * @param {iqama input text} value 
     * @returns 
     */
    function isNotValidIqamaNZValidation(value){
        value = toEnglishNumber(value);
        let oddOperation = 0;
        for(let i = 0; i < 9; i++){
            if(i % 2 == 0){
                let individualResult = Number(value.charAt(i)) * 2;
                if(individualResult >= 10){
                    individualResult = Math.floor(individualResult/10) + individualResult%10;
                }
                oddOperation = oddOperation + individualResult;
            }else{
                oddOperation = oddOperation + Number(value.charAt(i));
            }
        }

        let substractionResult = 10 - (oddOperation%10);
        if(Number(value.charAt(9)) == 0)
            return (substractionResult%10) == 0;
        else
            return Number(value.charAt(9)) == substractionResult;
    }

    /**
     * Function to convert Arabic number to english
     * @param {*} strNum 
     * @returns 
     */
    function toEnglishNumber(strNum) {
        const ar = '٠١٢٣٤٥٦٧٨٩'.split('');
        const en = '0123456789'.split('');
        strNum = strNum.replace(/[٠١٢٣٤٥٦٧٨٩]/g, x => en[ar.indexOf(x)]);
        strNum = strNum.replace(/[^\d]/g, '');
        return strNum;
     }

    /**
     * removes all css styles and error messages from the text form object
     * @param {event} event 
     */
    FormText.prototype._cleanErrorMessageInFormField = function (event) {
        event.target.setCustomValidity("");
        this._errorMessageElement.style.display = "none";
        this._errorMessageElement.innerHTML = "";
        this._elements.input.className = this._elements.input.className.replace(BORDER_ERROR_CLASS,"");
        this._elements.input.removeAttribute("isIqamaValid");
    }

    /**
     * displays the div error message and sets the css classes accordingly
     * @param {error message text} errorMessage 
     */
    FormText.prototype._showErrorMessage = function(errorMessage) {
        this._errorMessageElement.innerHTML = errorMessage;
        this._errorMessageElement.style.display = "block";
        this._elements.input.className += BORDER_ERROR_CLASS;
    }

    /**
     * loads the elements in wrapper into the elements object
     * @param {wrapper} wrapper 
     */
    FormText.prototype._cacheElements = function(wrapper) {
        this._elements = {};
        this._elements.self = wrapper;
        var hooks = this._elements.self.querySelectorAll("[data-" + NS + "-hook-" + IS_DASH + "]");
        for (var i = 0; i < hooks.length; i++) {
            var hook = hooks[i];
            var capitalized = IS;
            capitalized = capitalized.charAt(0).toUpperCase() + capitalized.slice(1);
            var key = hook.dataset[NS + "Hook" + capitalized];
            this._elements[key] = hook;
        }
    };

    /**
     * loads the attributes of the div into the properties structure part of the form text object
     * @param {options from readData function} options 
     */
    FormText.prototype._setupProperties = function(options) {
        this._properties = {};

        for (var key in properties) {
            if (Object.prototype.hasOwnProperty.call(properties, key)) {
                var property = properties[key];
                if (options && options[key] != null) {
                    if (property && typeof property.transform === "function") {
                        this._properties[key] = property.transform(options[key]);
                    } else {
                        this._properties[key] = options[key];
                    }
                } else {
                    this._properties[key] = properties[key]["default"];
                }
            }
        }
    };

    function hideFutureDates(){
      let dateObj = new Date();
      dateObj.setDate(dateObj.getDate() - 1);
      const yesterday = dateObj.toISOString().split('T')[0];
      var form = document.getElementsByTagName("form")[0];
      if(form){
       form.elements["date_of_birth"]?.setAttribute('max', yesterday);
       form.elements["date_of_birth"]?.setAttribute('onkeydown', 'return false');
      }
    }

    /**
     * initialization component function
     */
    function onDocumentReady() {
        var elements = document.querySelectorAll(selectors.self);
        for (var i = 0; i < elements.length; i++) {
            new FormText({ element: elements[i], options: readData(elements[i]) });
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
                                new FormText({ element: element, options: readData(element) });
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
        hideFutureDates();
    }

    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }

})();