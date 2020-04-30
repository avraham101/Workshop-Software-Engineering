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
      stores: this.create_stores(),
      products: this.create_products(),
      store: "",
      showProducts: false,
    };
  }

    create_stores() {
        let output = []
        for(let i =0; i<5;i++) {
            output.push({
                name:'Store '+i,
                description:'Description '+i
            })
        }
        return output
    }

    create_products() {
        let output = []
        for(let i =0; i<5;i++) {
            output.push({
                productName:'product '+i,
                category:'category '+i,
                reviews: [],
                amount: i,
                price:i,
                priceAfterDiscount: i,
                purchaseType:'purchase type '+i,
            })
        }
        return output
    }

  handleStores(event) {
    this.state.showProducts = true;
    this.setState({ name: event.target.value });
  }

    render_stores_table() {
        let stores = this.state.stores;
        let output = [];
        let i =0;
        stores.forEach( element =>
            output.push(
                <tr>
                    <th> <input type="radio" id={i} name="store" /> </th>
                    <th>{element.name}</th>
                    <th> {element.description} </th>
                </tr>
            ),
            i=i+1
        )
        return output;
    }

    render_stores() {
        return (
            <table style={style_table}>
                <tr>
                    <th style = {under_line}> </th>
                    <th style = {under_line}> Store Name </th>
                    <th style = {under_line}> Description </th>
                </tr>
                {this.render_stores_table()}
            </table>);
    }

    render_product_table(){
        let proudcts = this.state.products;
        let output = [];
        proudcts.forEach( element =>
            output.push(
                <tr>
                    <th> {element.productName} </th>
                    <th> {element.category} </th>
                    <th> {element.amount} </th>
                    <th> {element.price} </th>
                    <th> {element.purchaseType} </th>
                </tr>
            )
        )
        return output;
    }

    render_product() {
        return (
            <table style={style_table}>
                <tr>
                    <th style = {under_line}> Product Name </th>
                    <th style = {under_line}> Category </th>
                    <th style = {under_line}> Amount </th>
                    <th style = {under_line}> Price </th>
                    <th style = {under_line}> Purchase Type </th>
                </tr>
                {this.render_product_table()}
            </table>);
    }


  render() {
    return (
      <BackGroud>
        <Menu />
        <Title title="Watch Stores And Products" />
        <div>
          <Title title="Stores :" />
          <div>
            {this.render_stores()}
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

const under_line = {
    borderBottom: '2px solid black'
}