import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Cart from '../../Component/Cart';

//2.7.1+2.7.2+2.7.3
class ViewProductsInCart extends Component {

  constructor(cart) {
    super();
    this.cart=cart1;
  }

  render() {
    return (
      <BackGroud>
        <Menu/>
        <Cart cart={this.cart}>
        </Cart>
      </BackGroud>
    );
  }
}

export default ViewProductsInCart;


const product={
    productName: "peanuts",
    store: "hanut",
    category: "category",
    amount: 3,
    price: 4.5,
    purchaseType: "immediate",
}

const product2={
    productName: "peanuts2",
    store: "hanut aheret",
    category: "category",
    amount: 90,
    price: 6.89,
    purchaseType: "immediate"
}

const cart1={
    price: 30,
    products: [product,product2],
}