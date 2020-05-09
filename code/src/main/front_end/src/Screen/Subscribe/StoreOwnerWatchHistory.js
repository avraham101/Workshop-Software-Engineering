import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Button from "../../Component/Button";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

//4.10
class StoreOwnerWatchHistory extends Component {

  constructor(purchases) {
    super();
    this.pathname = "/viewAndReplyRequests";
    this.buildPurchases=this.buildPurchases.bind(this);
    this.create_Purchases=this.create_Purchases.bind(this);
    this.renderPurchase=this.renderPurchase.bind(this);
    this.state={
      purchases:[],
    }
    this.flag=true;
    this.counter=1;
  }

  create_Purchases(){
    if(this.flag){
      this.flag=false;
      send('/managers/history/'+this.props.location.state.storeName+'?id='+this.props.location.state.id, 'GET','', this.buildPurchases);
    }
  }
  
  buildPurchases(received){
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if(opt == 'Success') {
        this.setState({purchases:received.value});
      }
      else if(opt == 'Store_Not_Found') {
        alert("the store does'nt exist");
      }
      else if(opt == 'Dont_Have_Permission') {
        alert("you don't have permission to watch the store history");
      }
      else {
        alert(opt+", Can't present your store purchases");
      }
    }
  }

  renderPurchase(){
    let output=[];
    this.state.purchases.forEach(element => {
      let index=element.date.indexOf('T'); 
      output.push(
      <table style={style_sheet}>
          <tr>
              {"purchase "+this.counter+++":"}
              <th>{"buyer:"+element.buyer}</th>
              <th>{"store:"+element.storeName}</th>
              <th>{"total price:"+element.price}</th>
              <th>{"date:"+element.date.substring(0,index)}</th>
              <th>{"hour:"+element.date.substring(index+1)}</th>
            </tr>
           {this.renderProduct(element.product)}
        </table>
        )
    });
    this.counter=1;
    return output
  }

  renderProduct(products){
    let output=[];
    products.forEach(pro => {
        output.push(
            <tr>
                <th>{"product name:"+pro.productName}</th>
                <th>{"category:"+pro.category}</th>
                <th>{"amount:"+pro.amount}</th>
                <th>{"purchase type:"+pro.purchaseType}</th>
            </tr>
        )
    }); 
    return output;
  }

  render() {
    let onBack= () => {
      pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
    }
    this.create_Purchases();
    return (
      <BackGroud>
        <Menu state={this.props.location.state} />
        <Title title = {this.state.purchases.length===0 ? "you didn't have sales yet":'store purchases:'}/> 
        {this.renderPurchase()} 
        <Button text="Back" onClick={onBack}/>
      </BackGroud>
    );
  }
}

export default StoreOwnerWatchHistory;

const style_sheet = {
  color: "black",
  padding: "1px",
  margin: "10px",
  lineHeight: 5,
  fontFamily: "Arial",
  width:"80%",
}


