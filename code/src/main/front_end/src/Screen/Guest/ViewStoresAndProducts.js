import React, { Component } from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";

class ViewStoresAndProducts extends Component {
  constructor() {
    super();
    this.handleStores = this.handleStores.bind(this);
    this.state = {
      stores: ["store1", "store2", "store3"],
      products: ["product1", "product2", "product3"],
      store: "",
      showProducts: false,
    };
  }

  handleStores(event) {
    this.state.showProducts = true;
    this.setState({ name: event.target.value });
  }

  renderStores() {
    return (
      <div>
        <input type="radio" id="r1" name="store" />
        <label for="r1">{this.state.stores[0]}</label>
        <br></br>
        <input type="radio" id="r2" name="store" />
        <label for="r2">{this.state.stores[1]}</label>
        <br></br>
        <input type="radio" id="r3" name="store" />
        <label for="r3">{this.state.stores[2]}</label>
        <br></br>
      </div>
    );
  }

  render_product() {
    return (
      <table style={style_table}>
        <tr id="r1">{this.state.products[0]}</tr>
        <tr id="r2">{this.state.products[1]}</tr>
        <tr id="r3">{this.state.products[2]}</tr>
      </table>
    );
  }

  render() {
    return (
      <BackGroud>
        <Menu />
        <Title title="Watch Stores And Products" />
        <div>
          <Title title="Stores :" />
          <div>
            {this.renderStores()}
            <h5>
              To watch the products in the store- choose a store and press here
            </h5>
            <Button text="Select" onClick={this.handleStores} />
          </div>
          {this.state.showProducts ? this.render_product() : ""}
        </div>
      </BackGroud>
    );
  }
}

export default ViewStoresAndProducts;

const style_table = {
  width: "99%",
  textAlign: "center",
  border: "2px solid black",
};
