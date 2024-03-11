(function ($, $document) {
    "use strict";
    $.validator.register("foundation.validation.validator", {
        selector: "coral-multifield",
        validate: function(element) {
            var el = $(element);
            let max = el.data("max");
            let itemsLength = el["0"].items.getAll().length;
                if(itemsLength > max) {
                    el["0"].items.last().remove();
                    return "Maximum numbers of items allowed are: " + max;
                }
        }
    });
})($, $(document));