import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from '../../Component/Menu';
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from '../../Component/Button';
class ManageProductInStore extends Component {
  constructor() {
    super();
    this.state = {
      storeName:'-- Didnt Selected Store --',
      name:'',
      category:'',
      amount: 0,
      price: 0,
      purchase: 'Immeddiate',
    }
  }

  render() {
    return (
      <BackGrond>
        <Menu />
        <Title title="Add Product To Store"/>
        <h2 style={{textAlign:'center'}}>{this.state.storeName}</h2>
        <Input type="text" title = 'Product Name:' value={this.state.name} onChange={()=>{}} />
        <Input type="text" title = 'Category:' value={this.state.category} onChange={()=>{}} />
        <Input type="number" min={1} title = 'Amount:' value={this.state.amount} onChange={(e)=>{this.setState({amount:e.target.value})}} />
        <Input type="number" min={1} title = 'Price' value={this.state.price} onChange={(e)=>{this.setState({price:e.target.value})}} />
        <Input type="text" title = 'Purchase Type:' value={this.state.purchase} onChange={()=>{}} />
        <Button text='Add' onClick={()=>{}}/>
      </BackGrond>
    );
  }
}

export default ManageProductInStore;
