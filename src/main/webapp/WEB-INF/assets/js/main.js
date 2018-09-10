import Cart from './cart.js';
import CartView from "./cart_view.js";
import CartController from "./cart_controller.js";
import Catalog from "./catalog.js";
import CatalogView from "./catalog_view.js"
import CatalogController from "./catalog_controller.js"


const cartModel = new Cart();
const cartView = new CartView();
new CartController(cartModel, cartView);


const catalogModel = new Catalog();
const catalogView = new CatalogView();
new CatalogController(catalogModel, catalogView);
