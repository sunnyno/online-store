class CartController {
    constructor(model, view) {
        const _cartController = this;
        this.model = model;
        this.view = view;

        $(this.view).on('add', function (event, id) {
            _cartController.addToCart(id);
        });

        $(this.view).on('delete', function (event, id) {
            _cartController.deleteFromCart(id);
        });

        const items = this.view.getCartItems();
        this.loadModel(items);
    }

    addToCart(id) {
        const itemId = this.model.addItem({id});
        $.post(`/cart/${itemId}`);
    }

    deleteFromCart(id) {
        $.ajax({
            url: `/cart/${id}`,
            type: 'DELETE'
            , success: function () {
                const item = this.model.removeItem(id);
                this.view.removeDeletedItem(item);
            }.bind(this)
        });
    }

    loadModel(data) {
        this.model.loadItems(data);
    }

}

export default CartController;