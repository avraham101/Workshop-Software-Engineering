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
    this.handleDelete = this.handleDelete.bind(this);
    this.renderDelete = this.renderDelete.bind(this);
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
      if(opt !== 'Success') {
        alert("Cant revcive cart");
      }
      else {
        this.setState({cart:received.value});
      }
    }
  }

  getCart() {
    let id = this.props.location.state.id;
    send('/home/cart?id='+id, 'GET', '', this.getPromise) 
  }

  handleDelete(event,product) {
    let id = this.props.state.id;
    let productStr = {productName: product.productName,storeName: product.storeName, amount: product.amount};

    send('/home/cart/delete?id='+id,'POST',productStr,(received) =>{
      alert("promise")
      this.renderDelete(received,product)
    });
  }

  renderDelete(received, product){
    if(received && received.value){
      let index=this.props.cart.products.indexOf(product);
      if(index!==-1){
        this.props.cart.products.splice(index,1);
      }
      this.setState({});
      this.getCart();
      alert("Product Deleted")
    }
    else{
      alert("Invalid Product!")
    }
  }

  render() {
    return (
      <BackGroud>
        <Menu state={this.props.location.state}/>
        <Cart cart={this.state.cart} history={this.props.history} state={this.props.location.state} pathname={this.pathname} handleDelete={this.handleDelete}/>
      </BackGroud>
    );
  }
}

export default ViewProductsInCart;