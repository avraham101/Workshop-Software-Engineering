import React, {Component} from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Row from "../../Component/Row";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils';
import Button from "../../Component/Button";

class ViewStoresAndProducts extends Component {
  
  constructor(props) {
    super(props);
    this.handleStores = this.handleStores.bind(this);
    this.buildStores = this.buildStores.bind(this);
    this.buildProducts = this.buildProducts.bind(this);
    this.create_products = this.create_products.bind(this);
    this.create_stores = this.create_stores.bind(this);
    this.render_stores_table = this.render_stores_table.bind(this);
    this.render_stores = this.render_stores.bind(this);
    this.render_product_table = this.render_product_table.bind(this);
    this.render_product = this.render_product.bind(this);
    this.renderDiscountAndPolicyButtons = this.renderDiscountAndPolicyButtons.bind(this);
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
      if(opt === 'Success') {
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
      if(opt === 'Success') {
        this.setState({products:received.value})
      }
      else if(opt === 'Store_Not_Found‏') {
        alert('Store Not Found. Sorry.')
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

  renderStore(element) {
    this.create_stores();
    if(this.state.stores.length===0)
      return <p style={{textAlign:'center'}}> No products in store </p>
    let onHover = (event) => {
        event.currentTarget.style.backgroundColor = '#92BAFF'
    }
    let onLeave = (event) => {
        event.currentTarget.style.backgroundColor = ''
    } 
    
    let m = (90  / 3);
    return ( <div style={{float:'left', width:m+'%', border:'1px solid black', textAlign:'center', margin:'1%'}}
                    onMouseOver = {onHover} onMouseLeave = {onLeave}
                    onClick={(e) => this.handleStores(e, element.name)}>
                <div style={{float:'left', width:'100%', background:'#3086DB'}}>
                  <p> {element.name} </p>
                </div>
                <div style={{float:'left', width:'100%'}}>
                  <p> {element.description} </p>
                </div>
                <div style={{float:'left', width:'100%'}}>
                  <img src={require('../../Assests/store.jpg')} width="100" height="100" />
                </div>
             </div>
    )
  }

  render_stores_table() {
    let stores = this.state.stores;
    let output = [];
    stores.forEach(
      (element) => output.push( this.renderStore(element)
          // <Row onClick={(e) => this.handleStores(e, element.name)}>
          //   <th>{element.name}</th>
          //   <th> {element.description} </th>
          // </Row>
        )
    );
    return output;
  }

  render_stores() {
    this.create_stores();
    if(this.state.stores.length===0)
      return <p style={{textAlign:'center'}}> No products in store </p>
    return this.render_stores_table();
  }

  render_product_table() {
    let state = this.props.location.state;
    let onClick = (element) => {
      state['product'] = {
        productName: element.productName,
        storeName: element.storeName,
        category: element.category,
        amount: element.amount,
        price: element.price,
        purchaseType: element.purchaseType,
      };
      pass(this.props.history,'/addToCart',this.pathname,state)
    };
    let products = this.state.products;
    let output = [];
    products.forEach((element) =>
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

  renderDiscountAndPolicyButtons(){
    let s = this.props.location.state;
    s["storeName"] = this.state.store;
    let onBackDiscounts = () => pass(this.props.history,"/viewDiscounts",this.pathname,s);
    let onBackPolicies = () => pass(this.props.history,'/viewPolicies',this.pathname,s);
    return(
        <table style={style_table}>
          <tr>
            <th>
              <Button text="View Store Discounts" onClick={onBackDiscounts}>
              </Button>
            </th>
            <th>
              <Button text="View Store Policies" onClick={onBackPolicies}>
              </Button>
            </th>
          </tr>
        </table>
    );
  }

  render_product() {
    return (
      <div style={{float:'left', width:'100%'}}>
        <h3 style={{textAlign: "center"}}> Store: {this.state.store}</h3>
        {this.renderDiscountAndPolicyButtons()}
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
   // if(this.state.id === '') this.setState({id:this.props.location.state.id});
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
