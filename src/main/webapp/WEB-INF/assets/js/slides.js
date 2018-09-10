(() => {
    let slideIndex = 0;
    (function showSlides() {
        const slides = $(".mySlides");
        for (let i = 0; i < slides.length; i++) {
            slides[i].classList.remove("show");
        }
        slideIndex++;
        if (slideIndex > slides.length) {
            slideIndex = 1
        }
        slides[slideIndex - 1].classList.add("show");
        setTimeout(showSlides, 5000); // Change image every 5 seconds
    })()
})();
