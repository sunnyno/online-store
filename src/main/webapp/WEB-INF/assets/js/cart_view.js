class CartView {
    constructor() {
        const _cartView = this;
        this.items = $('.cart__item');
        this.deleteBtn = $('.delete__btn');
        this.total = $('#total__price').first();
        $('.add-to-cart__btn').click(function () {
            _cartView.handleAddToCart(this);
        });

        this.deleteBtn.click(function () {
            _cartView.handleDelete(this);
        });
    }

    handleAddToCart(button) {
        const id = $(button).attr("data-id");
        $(this).trigger('add', id);
    }

    handleDelete(button) {
        const id = $(button).attr("data-id");
        $(this).trigger('delete', id);
    }

    getCartItems() {
        let items = [];
        this.items.each(function () {
            items.push({
                id: $(this).attr("id")
                , quantity: $(this).find('.product__quantity').first().text()
                , price: $(this).find('.product__price').first().text()
            });
        });
        return items;
    }

    removeDeletedItem(item) {
        const product = $(`#${item.id}`).first();
        const productQuantityElement = $(product).find('.product__quantity').first();
        if (item.quantity > 0) {
            productQuantityElement.text(item.quantity);
        } else {
            product.remove();
        }
        this.changeTotal(item.price);

    }

    changeTotal(priceToSubtract) {
        const priceBefore = this.total.text();
        this.total.text(priceBefore - parseFloat(priceToSubtract));
    }
}

export default CartView;