import React, { Component } from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Error from "../../Component/Error";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import history from "../history";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils';

class AddProductToCart extends Component {

  /**
   * constructor
   * @param props
   */
  constructor(props) {
    super(props);
    this.handleChangedAmount = this.handleChangedAmount.bind(this);
    this.handleAdd = this.handleAdd.bind(this);
    this.addPromise = this.addPromise.bind(this);
    this.state = {
      product: this.props.location.state.product,
      amount: 1,
    };
  }

  /**
   * print the details about the product to add to cart
   */
  renderProducts() {
    let product = this.state.product;
    return (this.state.product === undefined)?
    ( <h3> Didn't selected product </h3> ) :
    ( <table style={style_table}>
        <tr>
          <th style = {under_line}> Product Name </th>
          <th style = {under_line}> Store Name </th>
          <th style = {under_line}> Category </th>
          <th style = {under_line}> Amount </th>
          <th style = {under_line}> Price </th>
          <th style = {under_line}> Purchase Type </th>
        </tr>
        {}
        <tr>
          <th> {product.productName} </th>
          <th> {product.storeName} </th>
          <th> {product.category} </th>
          <th> {product.amount} </th>
          <th> {product.price} </th>
          <th> {product.purchaseType} </th>
        </tr>
      </table>
      );
  }

  addPromise(received) {
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if(opt == 'Not_Found') {
        alert("Product dosen't in the store any more. Went to menu");
        pass(this.props.history,'/subscribe',this.pathname,this.props.location.state);
      }
      else if(opt == 'Invalid_Product') {
        alert("Product is already in your cart, if you wasn't to add more please edit your cart. Went to menu");
        pass(this.props.history,'/subscribe',this.pathname,this.props.location.state);
      }
      else if(opt == 'Store_Not_Found') {
        alert("the store isn't exiist please try with a different store. Went to menu");
        pass(this.props.history,'/subscribe',this.pathname,this.props.location.state);
      }
      else if(opt == 'Success') {
        alert("Product Added to Cart.");
        pass(this.props.history,'/viewMyCart',this.pathname,this.props.location.state);
      }
      else {
        alert(opt+", Cant Add Product to Cart");
      }
    }
  }

  handleAdd() {
    if(this.state.amount > this.state.product.amount){
      alert("wrong amont selected");
    }
    else {
      let msg = { productName:this.state.product.productName , 
                  storeName:this.state.product.storeName ,
                  amount:this.state.amount};
      let id = this.props.location.state.id;
      send('/home/cart?id='+id, 'POST', msg, this.addPromise) 
    }
  }

  handleChangedAmount(event) {
    this.setState({amount: event.target.value });
  }

  render() {
    return (
      <BackGroud>
        <Menu state={this.props.location.state}/>
        <body>
          <Title title="Add product to cart" />
          {this.renderProducts()}
          <Input
            title="Choose amount to cart:"
            type="number"
            min={1}
            value={this.state.amount}
            onChange={this.handleChangedAmount}
          />
          <div>
            <Button text="add" onClick={this.handleAdd}/>
          </div>
        </body>
        <Button text="back" onClick={() => pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state)} />
      </BackGroud>
    );
  }
}

export default AddProductToCart;

const style_table = {
  width: "99%",
  textAlign: "center",
  border: "2px solid black",
};

const under_line = {
  borderBottom: '2px solid black'
}