import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from '../../Component/Menu';
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from '../../Component/Button';
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class ManageProductInStore extends Component {
  constructor() {
    super();
    this.pathname = "/manageProducts";
    this.addClick = this.addClick.bind(this);
    this.addProductPromise = this.addProductPromise.bind(this);
    this.state = {
      name:'',
      category:'',
      amount: 1,
      price: 1,
      purchase: 'IMMEDDIATE',
    }
  }

  addClick() {
    let msg ={};
    msg['productName']=this.state.name;
    msg['storeName']=this.props.location.state.storeName;
    msg['category']=this.state.category;
    msg['amount'] = this.state.amount;
    msg['price'] = this.state.price;
    msg['purchaseType'] = this.state.purchase;
    let id = this.props.location.state.id;
    send('/home/product?id='+id,'POST',msg,this.addProductPromise)
  }

  addProductPromise(received) {
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if (opt == "Invalid_Product") {
        alert("The product data isnt valid. please insert again");
      } 
      else if(opt == 'Store_Not_Found‏') {
        alert("Store doesn't Exits. Go back to main menu");
        pass(this.props.history,'/subscribe',this.pathname,this.props.location.state)
      }
      else if(opt =="Dont_Have_Permission‏") {
        alert("User Doesnt have premission for that. By By");
        pass(this.props.history,'/subscribe',this.pathname,this.props.location.state)
      }
      else if(opt =='Already_Exists‏') {
        alert("Product Already Exits, Please insert Again");
        this.setState({name:''});
      }
      else if(opt == 'Success') {
        alert("Product Added. Go to Store mangment");
        pass(this.props.history,'/storeManagement',this.pathname,this.props.location.state)
      }
      else {
        alert(opt+", Cant Add Product to Store");
      }
    }
  }

  render() {
    return (
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title={"Add Product To Store:"}/>
        <h2 style={{textAlign:'center'}}>{this.props.location.state.storeName}</h2>
        <Input type="text" title = 'Product Name:' value={this.state.name} 
              onChange={(e)=>{this.setState({name:e.target.value})}} />
        <Input type="text" title = 'Category:' value={this.state.category} 
              onChange={(e)=>{this.setState({category:e.target.value})}} />
        <Input type="number" min={1} title = 'Amount:' value={this.state.amount} 
              onChange={(e)=>{this.setState({amount:e.target.value})}} />
        <Input type="number" min={1} title = 'Price' value={this.state.price} 
              onChange={(e)=>{this.setState({price:e.target.value})}} />
        <Input type="text" title = 'Purchase Type:' value={this.state.purchase} 
              onChange={()=>{}} />
        <Button text='Add' onClick={this.addClick}/>
      </BackGrond>
    );
  }
}

export default ManageProductInStore;
