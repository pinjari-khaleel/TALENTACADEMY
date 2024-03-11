(function($, document, Coral) {
    "use strict";

    //script for form text dialog to enable constraint message field and new pattern field for text field type text

    var selectors = {
        dialog_selector: ".cmp-form-text__editDialog",
        textfieldtype_selector: ".cmp-form-text__types",
        patternfield_selector: ".cmp-form-text__patterntextfield",
        constraintmessage_selector: ".cmp-form-text__constraintmessage",
        isiqamacheckbox_selector: ".cmp-form-check__isiqama"
    };

    var TEXT_FIELD_TYPE = 'text';

    //loading the text form dialog
    document.on("foundation-contentloaded", function(e) {
        if ($(e.target).find(selectors.dialog_selector).length > 0) {
            Coral.commons.ready(e.target, function(component) {
                var textfieldtype = $(component).find(selectors.textfieldtype_selector);
                var elements = {};
                elements.patternfieldcomponent = $(component).find(selectors.patternfield_selector);
                elements.constraintmessagecomponent = $(component).find(selectors.constraintmessage_selector);
                elements.iqamacheckboxcomponent = $(component).find(selectors.isiqamacheckbox_selector);


                checkAndDisplay(textfieldtype[0],elements); //call to check the first time the dialog is loaded

                if(textfieldtype){
                    textfieldtype.on("change", function() { //load change event on text type field in the dialog
                        checkAndDisplay(this, elements);
                    });
                }
            });
        }
    });

    //show the error message, isiqama, and pattern fields when type text is text
    function checkAndDisplay(component, elements){
        if(component.value == TEXT_FIELD_TYPE){
            elements.patternfieldcomponent.show();
            elements.iqamacheckboxcomponent.show();
            elements.constraintmessagecomponent.show();
        }else{
            elements.patternfieldcomponent.hide();
            elements.iqamacheckboxcomponent.hide();
        }
    }

})(jQuery, jQuery(document), Coral);