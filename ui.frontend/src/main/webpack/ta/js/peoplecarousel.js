import { getSwiper } from "../../utility/carouselutil";

function initializeCarousels() {
    const carouselContexts = document.querySelectorAll(".ta-people-carousel");

    carouselContexts.forEach((context) => {
        const swiperContents = context.querySelector(".cmp-carousel__content");
        const swiperOpts = {
            slidesPerView: 1.2,
            direction: "horizontal",
            loop: false,
            breakpoints: {
                320: {
                    slidesPerView: 1.1,
                    spaceBetween: 20,
                    slidesOffsetAfter: 0,
                },
                480: {
                    slidesPerView: 1.2,
                    spaceBetween: 20,
                    slidesOffsetAfter: 30,
                },
                901: {
                    slidesPerView: 1.8,
                    spaceBetween: 20,
                    slidesOffsetAfter: 250,
                },
            },
        };

        getSwiper(swiperContents, context, swiperOpts);
    });
}

document.addEventListener("DOMContentLoaded", function () {
    initializeCarousels();
});