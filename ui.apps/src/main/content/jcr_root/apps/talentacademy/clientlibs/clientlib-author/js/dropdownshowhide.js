(function (document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function (e) {
         showHideHandler($(".cq-dialog-dropdown-showhide", e.target));
    });

     $(document).on("selected", ".cq-dialog-dropdown-showhide", function (e) {
        showHideHandler($(this));
    });

    function showHideHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-select")) {
                // handle Coral3 base drop-down
                Coral.commons.ready(element, function (component) {
                    showHide(component, element);
                    component.on("change", function () {
                        showHide(component, element);
                    });
                });
            }
        })
    }

    function showHide(component, element) {
        // get the selector to find the target elements. its stored as data-.. attribute
        var target = $(element).data("cqDialogDropdownShowhideTarget");
        var $target = $(target);

        if (target) {
            var value;
            if (typeof component.value !== "undefined") {
                value = component.value;
            } else if (typeof component.getValue === "function") {
                value = component.getValue();
            }

            $target.each(function(index, element) {
                // make sure all unselected target elements are hidden.
                // unhide the target element that contains the selected value as data-showhidetargetvalue attribute
				var values = element.dataset.showhidetargetvalue.split(",");
                $.each(values,function(index,targetVal){
                    var show = targetVal && targetVal === value;
                    setVisibilityAndHandleFieldValidation($(element), show);
                    if(show){
                         return false;
                    }
                });
            });
        }
    }


    function setVisibilityAndHandleFieldValidation($element, show) {
         if (show) {
             $element.removeClass("hide");
         } else {
             $element.addClass("hide");
         }
     }



})(document, Granite.$);