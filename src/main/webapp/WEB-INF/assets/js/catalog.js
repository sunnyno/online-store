class Catalog {
    constructor(products = []) {
        this.products = products;
    }

    addProduct(product) {
        this.products.push(product);
        return product;
    }
}

export default Catalog;