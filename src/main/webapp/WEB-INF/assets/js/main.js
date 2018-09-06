const addButtons = $('.addToCartButton');
[].forEach.call(addButtons, function(btn){
    btn.onclick=btn.onclick = function(){
        let xhr = new XMLHttpRequest();
        const id = btn.getAttribute("data-id");
        xhr.open("POST",  `/cart/${id}`, true);
        xhr.send();
    };
});

const deleteButtons = $('.delete__btn');
[].forEach.call(deleteButtons, function(btn){
    btn.onclick=btn.onclick = function(){
        let xhr = new XMLHttpRequest();
        const id = btn.getAttribute("data-id");
        xhr.open("DELETE",  `/cart/${id}`, true);
        xhr.send();
        location.reload();
    };
});


let slideIndex = 0;
(function showSlides() {
    console.log(location.pathname);
    if(location.pathname === '/products') {
        const slides = $(".mySlides");
        for (let i = 0; i < slides.length; i++) {
            slides[i].classList.remove("show");
        }
        slideIndex++;
        if (slideIndex > slides.length) {
            slideIndex = 1
        }
        slides[slideIndex - 1].classList.add("show");
        setTimeout(showSlides, 5000); // Change image every 2 seconds
    }
})();