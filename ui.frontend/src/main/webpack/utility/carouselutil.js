export const getSwiper = (swiperContents, context, swiperOpts) => {
    const nextEl= context.querySelector(".carousel-button__next");
    const prevEl= context.querySelector(".carousel-button__prev");
    return new Swiper(swiperContents, {
        slideToClickedSlide: false,
        spaceBetween: 30,
        slidesOffsetAfter: 0,
        noSwipingClass: "m-button",
        navigation: {
            nextEl,
            prevEl,
            disabledClass: "carousel-button-disabled",
        },
        on: {
            init: function () {
              if (this.slides.length <= this.params.slidesPerView) {
                nextEl.style.display = 'none';
                prevEl.style.display = 'none';
              }
            }
        },
        ...swiperOpts,
    });
};