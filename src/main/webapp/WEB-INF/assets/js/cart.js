class Cart {
    constructor(items = []) {
        this.items = items;
    }


    addItem(item) {
        this.items.push(item);
        $(this).trigger('change', this.items);
        return item.id;
    }

    removeItem(id) {
        const index = this.items.findIndex(item => item.id === id);
        if (index > -1) {
            const item = this.items[index];
            const itemQuantity = --item.quantity;
            if (itemQuantity === 0) {
                this.items.splice(index, 1);
            }
            return item;
        }
    }

    loadItems(data) {
        this.items = data || [];
    }
}

export default Cart;