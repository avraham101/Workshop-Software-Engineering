import React, { Component } from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Error from "../../Component/Error";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
//import IconButton from '@material-ui/core/IconButton';
//import AddShoppingCartIcon from '@material-ui/icons/AddShoppingCart';
import history from "../history";

class AddProductToCart extends Component {
  /**
   * constructor
   * @param {the product that choosed to add to cart} product
   */
  constructor(product) {
    super();
    this.handleChangedAmount = this.handleChangedAmount.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.state = {
      product: product,
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
    return (
      <table style={style_table}>
        <tr>
          <th> Product name </th>
          <th> Price </th>
          <th> Amount in the store </th>
        </tr>
        <tr>
          <th> {product.name} </th>
          <th> {product.price} </th>
          <th> {product.amountInStore} </th>
        </tr>
      </table>
    );
  }

  render() {
    return (
      <BackGroud>
        <Menu />
        <body>
          <Title title="Add product to cart" />
          {this.renderProducts()}
          <Input
            title="Choose amount to cart:"
            type="text"
            value={this.state.amount}
            onChange={this.handleChangedAmount}
          />
          <div>
            <Button
              color="primary"
              aria-label="add to shopping cart"
              text="add"
              onClick={(e) =>
                parseInt(this.state.amount) < product.amountInStore
                  ? this.setState({ enough: true })
                  : this.setState({ enough: false })
              }
            />
            {this.state.enough === false ? <Error text="Close Me" /> : ""}
          </div>
        </body>
        <Button text="back" onClick={() => history.push("/")} />
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

const product = {
  name: "pen",
  price: 20,
  amountInStore: 100,
};
