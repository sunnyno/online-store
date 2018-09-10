class CatalogController {
    constructor(model, view) {
        const _catalogController = this;
        this.model = model;
        this.view = view;

        $(this.view).on('add', function (event, product) {
            _catalogController.addProduct(product);
        });
    }

    addProduct(product) {
        this.model.addProduct(product);
        $.post('/product/add', {name: product.name, price: product.price},
            $(location).attr('pathname', '/products'));

    }

}

export default CatalogController;