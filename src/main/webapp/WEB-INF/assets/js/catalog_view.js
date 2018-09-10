class CatalogView {
    constructor() {
        const _catalogView = this;
        $('.add-product__btn').click(function () {
            _catalogView.handleAddProduct();
        })
    }

    handleAddProduct() {
        const name = $('#name').first().val();
        const price = $('#price').first().val();
        if ((/^\d+.?\d*$/).test(price)) {
            $(this).trigger('add', {name, price});
        } else {
            $('#price__error').show();
        }
    }
}

export default CatalogView;