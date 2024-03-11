import { getSwiper } from "../../utility/carouselutil";

function initializeTabSlider() {
    const carouselContexts = document.querySelectorAll(".ta-tab-slider");

    carouselContexts.forEach((context) => {
        const swiperContents = context.querySelector(".ta-tab-slider__content");
        const swiperOpts = {
            slidesPerView: 1,
            effect: "fade",
            pagination: {
                el: ".ta-tab-slider__pagination", // Use the existing pagination class
                clickable: true, // Enable clickable pagination bullets
                renderBullet: function (index, className) {
                    // Return HTML content for each pagination bullet
                    return (
                        '<li class="' + className + '">' +
                        document.querySelectorAll(".ta-tab-slider__pagination li")[index].textContent +
                        "</li>"
                    );
                },
            },
        };
        getSwiper(swiperContents, context, swiperOpts);
    });
}

// Initialize the carousels when the DOM is ready
document.addEventListener("DOMContentLoaded", function () {
    initializeTabSlider();
});