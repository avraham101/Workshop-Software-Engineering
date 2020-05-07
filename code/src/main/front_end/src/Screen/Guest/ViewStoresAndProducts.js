import React, { Component } from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import Row from "../../Component/Row";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils';
class ViewStoresAndProducts extends Component {
  
  constructor() {
    super();
    this.handleStores = this.handleStores.bind(this);
    this.buildStores = this.buildStores.bind(this);
    this.buildProducts = this.buildProducts.bind(this);
    this.create_products = this.create_products.bind(this);
    this.pathname = "/viewStoresAndProducts";
    this.state = {
      stores: [],
      stores_updated: false,
      products: [],
      store: "",
      showProducts: false,
    };
  }

  buildStores(received) {
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if(opt == 'Success') {
        this.setState({stores:received.value})
      }
      else {
        alert(opt+", Cant Add Product to Store");
      }
    }
  };

  create_stores() {
    if(this.state.stores_updated===false)
      send('/store', 'GET', '', this.buildStores)  
  }

  buildProducts(received) {
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if(opt == 'Success') {
        this.setState({products:received.value})
      }
      else if(opt == 'Store_Not_Found‏') {
        alert('Store Not Found. Soryy.')
        this.setState({stores_updated:true})
        this.create_stores();
      }
      else {
        alert(opt+", Cant Add Product to Store");
      }
    }
  };

  create_products(store) {
    send('/home/product?store='+store, 'GET','', this.buildProducts);  
  }

  handleStores(event, store) {
    this.setState({
      showProducts: true,
      store: store,
    });
    this.create_products(store);
  }

  render_stores_table() {
    let stores = this.state.stores;
    let output = [];
    stores.forEach(
      (element) =>
        output.push(
          <Row onClick={(e) => this.handleStores(e, element.name)}>
            <th>{element.name}</th>
            <th> {element.description} </th>
          </Row>
        )
    );
    return output;
  }

  render_stores() {
    this.create_stores();
    if(this.state.stores.length===0)
      return <p style={{textAlign:'center'}}> No products in store </p>
    return (
      <table style={style_table}>
        <tr>
          <th style={under_line}> Store Name </th>
          <th style={under_line}> Description </th>
        </tr>
        {this.render_stores_table()}
      </table>
    );
  }

  render_product_table() {
    let state = this.props.location.state;
    let onClick = (element) => {
      let product = {
        productName:element.productName,
        storeName:element.storeName,
        category:element.category,
        amount:element.amount,
        price:element.price,
        purchaseType:element.purchaseType,
      }
      state['product'] = product;
      pass(this.props.history,'/addToCart',this.pathname,state)
    };
    let proudcts = this.state.products;
    let output = [];
    proudcts.forEach((element) =>
      output.push(
        <Row onClick={()=>onClick(element)}>
          <th> {element.productName} </th>
          <th> {element.category} </th>
          <th> {element.amount} </th>
          <th> {element.price} </th>
          <th> {element.purchaseType} </th>
        </Row>
      )
    );
    return output;
  }

  render_product() {
    return (
      <div>
        <h3>{this.state.store}</h3>
        <table style={style_table}>
          <tr>
            <th style={under_line}> Product Name </th>
            <th style={under_line}> Category </th>
            <th style={under_line}> Amount </th>
            <th style={under_line}> Price </th>
            <th style={under_line}> Purchase Type </th>
          </tr>
          {this.render_product_table()}
        </table>
      </div>
    );
  }

  render() {
    return (
      <BackGroud>
        <Menu state={this.props.location.state} />
        <Title title="Watch Stores And Products" />
        <div>
          <Title title="Stores :" />
          {this.render_stores()}
          
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
  borderBottom: "2px solid black",
};
