import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Cart from '../../Component/Cart';
import {send} from '../../Handler/ConnectionHandler';

//2.7.1+2.7.2+2.7.3
class ViewProductsInCart extends Component {

  constructor(props) {
    super(props);
    this.getCart = this.getCart.bind(this);
    this.getPromise = this.getPromise.bind(this);
    this.state = {
      cart:{ priceBeforeDiscount: 0,
             products: [],}
    }
    this.pathname = '/viewMyCart';
    this.getCart();
  }

  getPromise(received) {
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ""+ received.reason;
      if(opt != 'Success') {
        alert("Cant revcive cart");
      }
      else {
        this.setState({cart:received.value});
        alert('priceBeforeDiscount '+received.value.priceBeforeDiscount)
        alert('products '+received.value.products)
      }
    }
  }

  getCart() {
    let id = this.props.location.state.id;
    send('/home/cart?id='+id, 'GET', '', this.getPromise) 
  }

  render() {
    return (
      <BackGroud>
        <Menu state={this.props.location.state}/>
        <Cart cart={this.state.cart} history={this.props.history} state={this.props.location.state} pathname={this.pathname}/>
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