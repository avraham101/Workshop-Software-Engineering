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
    this.handleSubmit = this.handleSubmit.bind(this);
    this.state = {
      product: this.props.location.state.product,
      amount: 0,
    };
  }

  handleChangedAmount(event) {
    this.setState({ amount: event.target.value });
  }

  handleSubmit(event) {
    event.preventDefault();
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
            <Button
              color="primary"
              aria-label="add to shopping cart"
              text="add"
              onClick={(e) =>
                (parseInt(this.state.amount) < this.state.product.amountInStore)
                  ? this.setState({ enough: true })
                  : this.setState({ enough: false })
              }
            />
            {this.state.enough === false ? <Error text="Close Me" /> : ""}
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