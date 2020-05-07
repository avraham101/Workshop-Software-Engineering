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
    this.handleEdit = this.handleEdit.bind(this);
    this.promiseEdit = this.promiseEdit.bind(this);
    this.state = {
      cart:{ priceBeforeDiscount: 0,
             products: [],}, 
             cartPrice:0,
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
        this.setState({cart:received.value, 
          cartPrice:received.value.priceBeforeDiscount});
      }
    }
  }

  getCart() {
    let id = this.props.location.state.id;
    send('/home/cart?id='+id, 'GET', '', this.getPromise)
  }

  handleDelete(event,product) {
    let id = this.props.location.state.id;
    let productStr = {productName: product.productName,storeName: product.storeName, amount: product.amount};

    send('/home/cart/delete?id='+id,'POST',productStr,(received) =>{
      this.renderDelete(received,product)
    });
  }

  renderDelete(received, product){
    if(received && received.value){
      let index=this.state.cart.products.indexOf(product);
      if(index!==-1){
        this.state.cart.products.splice(index,1);
      }
      this.getCart();
      alert("Product Deleted")
    }
    else{
      alert("Invalid Product!")
    }
  }

  /* the function responseble for editing a product in my cart */
  handleEdit(event, product, newAmount) {
    let id = this.props.location.state.id;
    let productStr = {productName: product.productName,storeName: product.storeName, amount: newAmount};

    send('/home/cart/edit?id='+id, 'POST', productStr, this.promiseEdit);
  }

  /* the function responseble for the promise from of edit*/
  promiseEdit(received) {
    console.log(received);
    if(received==null) {
      alert('Server Crashed')
    }
    else {
      let opcode = ''+received.reason;
      if(opcode === 'Success') {
        alert('Edit Product Amount Successfully');
        this.getCart();
      }
      else {
        alert('Cant Edit Product. Please try again.')
      }
    }
  } 
  

  render() {
    return (
      <BackGroud>
        <Menu state={this.props.location.state}/>
        <Cart cart={this.state.cart} history={this.props.history} 
              state={this.props.location.state} pathname={this.pathname} 
              cartPrice ={this.state.cartPrice}
              handleDelete={this.handleDelete}
              handleEdit={this.handleEdit}/>
      </BackGroud>
    );
  }
}

export default ViewProductsInCart;