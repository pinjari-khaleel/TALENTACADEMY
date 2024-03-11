import { getSwiper } from "../../utility/carouselutil";

function initializeCommonCarousels() {
    const carouselContexts = document.querySelectorAll(
        ".coursecareercarousel, .futureacademiescarousel,.ta-skill-cards"
    );

    carouselContexts.forEach((context) => {
        const carouselWrapper = context.querySelector(".swiper-wrapper");
        if(carouselWrapper){
        const swiperContents = context.querySelector(".cmp-carousel__content");
        let maxCount;
        let hasPagination = context.querySelector(".swiper-pagination") ? true : false;					
        if (context.classList.contains("coursecareercarousel")) {
            maxCount = 3.2;
        } else if (context.classList.contains("futureacademiescarousel")) {
            maxCount = 1.2;
        } else if (context.hasAttribute("data-max-items")) {
            maxCount = context.getAttribute("data-max-items");
        } else {
            maxCount = 1;
        }

        const swiperOpts = {
            slidesPerView: maxCount,
            pagination: {
                el: hasPagination ? ".swiper-pagination" : null,
            },
            breakpoints: {
                320: {
                    slidesPerView: 1.2,
                    spaceBetween: 10,
                    slidesOffsetAfter: 0,
                },
                768: {
                    slidesPerView: Math.min(maxCount, 1.8),
                    spaceBetween: 20,
                    slidesOffsetAfter: 30,
                },
                991: {
                    slidesPerView: Math.min(maxCount, 2.2),
                    spaceBetween: 20,
                    slidesOffsetAfter: 30,
                },
                1200: {
                    slidesPerView: Math.min(maxCount, 2.8),
                    spaceBetween: 20,
                    slidesOffsetAfter: 30,
                },
                1400: {
                    slidesPerView: Math.min(maxCount, 3.2),
                    spaceBetween: 20,
                    slidesOffsetAfter: 30,
                },
            },
        };
        getSwiper(swiperContents, context, swiperOpts);
		} else {
			context.style.display = "none";
		}
    });
}

// Initialize the carousels when the DOM is ready
document.addEventListener("DOMContentLoaded", function () {
    initializeCommonCarousels();
});