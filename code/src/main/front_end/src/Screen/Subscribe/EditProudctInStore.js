import React, {Component} from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from '../../Component/Menu';
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from '../../Component/Button';
import Row from '../../Component/Row';
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class EditProductInStore extends Component {
  constructor() {
    super();
    this.pathname = "/editProduct";
    this.editClick = this.editClick.bind(this);
    this.editProductPromise = this.editProductPromise.bind(this);
    this.getProducts = this.getProducts.bind(this);
    this.state = {
      products:[],
      updated_products: false,
      name:undefined,
      category:'',
      amount: 1,
      price: 1,
      purchase: 'IMMEDDIATE',
    }
  }

  getProducts() {
    let buildProducts = (received) => {
      if(received===null)
        alert("Server Failed");
      else {
        let opt = ''+ received.reason;
        if(opt === 'Success') {
          this.setState({products:received.value, updated_products:true})
        }
        else if(opt === 'Store_Not_Found‏') {
          alert('Store Not Found. Soryy.')
          pass(this.props.history,'/storeManagement','',this.props.location.state);
        }
        else {
          alert("Cant Access products");
          pass(this.props.history,'/storeManagement','',this.props.location.state);
        }
      }
    }
    if(this.state.updated_products === false) {
      send('/home/product?store='+this.props.location.state.storeName, 'GET','', buildProducts);  
    }
  }


  editClick() {
    let msg ={};
    msg['productName']=this.state.name;
    msg['storeName']=this.props.location.state.storeName;
    msg['category']=this.state.category;
    msg['amount'] = this.state.amount;
    msg['price'] = this.state.price;
    msg['purchaseType'] = this.state.purchase;
    let id = this.props.location.state.id;
    send('/home/product/edit?id='+id,'POST',msg,this.editProductPromise)
  }

  editProductPromise(received) {
    if(received===null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if (opt === "Invalid_Product") {
        alert("The product data isnt valid. please insert again");
      } 
      else if(opt === 'Store_Not_Found‏') {
        alert("Store doesn't Exits. Go back to main menu");
        pass(this.props.history,'/subscribe',this.pathname,this.props.location.state)
      }
      else if(opt ==="Dont_Have_Permission‏") {
        alert("User Doesnt have premission for that. By By");
        pass(this.props.history,'/storeManagement',this.pathname,this.props.location.state)
      }
      else if(opt === 'Success') {
        alert("Product Edit");
        this.setState({
          name:undefined,
          category:'',
          amount: 1,
          price: 1,
          updated_products:false,
        })
      }
      else {
        alert(opt+", Cant Add Product to Store");
      }
    }
  }

  /* the function render the selected product */
  renderSelectedProduct() {
    if(this.state.name === undefined)
      return <p style={{textAlign:'center'}} Didnt selected a product></p>
    return ( 
    <div>
        <h2 style={{textAlign:'center'}}>{this.state.name} </h2>
        <Input type="text" title = 'Category:' value={this.state.category} 
              onChange={(e)=>{this.setState({category:e.target.value})}} />
        <Input type="number" min={1} title = 'Amount:' value={this.state.amount} 
              onChange={(e)=>{this.setState({amount:e.target.value})}} />
        <Input type="number" min={1} title = 'Price' value={this.state.price} 
              onChange={(e)=>{this.setState({price:e.target.value})}} />
        <Input type="text" title = 'Purchase Type:' value={this.state.purchase} 
              onChange={()=>{}} />
        <Button text='Edit' onClick={this.editClick}/>
    </div>);
  }

  /* the function print the product table */
  render_product_table() {
    let onClick = (element) => {
      this.setState({
        name: element.productName,
        category: element.category,
        amount: element.amount,
        price: element.price,
        purchase: element.purchaseType,
      });
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
    this.getProducts();
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
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title={"Add Product To Store:"}/>
        <h2 style={{textAlign:'center'}}>{this.props.location.state.storeName}</h2>
        {this.render_product()}
        {this.renderSelectedProduct()}
      </BackGrond>
    );
  }
}

export default EditProductInStore;

const style_table = {
  width: "99%",
  textAlign: "center",
  border: "2px solid black",
};

const under_line = {
  borderBottom: "2px solid black",
};
