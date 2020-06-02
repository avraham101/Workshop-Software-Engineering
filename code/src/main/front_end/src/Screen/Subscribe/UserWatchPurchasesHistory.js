import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Button from '../../Component/Button';
import Input from '../../Component/Input';
import {send} from '../../Handler/ConnectionHandler';


class UserWatchPurchasesHistory extends Component {

constructor(purchases) {
  super();
  this.buildPurchases=this.buildPurchases.bind(this);
  this.create_Purchases=this.create_Purchases.bind(this);
  this.counter=1;
  this.state={
    purchases:[],
  }   
  this.flag=true;
}


handleChangeReview(event,product) {
  product.review= event.target.value;
  this.setState({})
}

handleSubmit(event,product) {
  let writer = this.props.location.state.name;
  let rev={
    store:product.store,
    productName:product.productName,
    content:product.review,
    writer:writer,
  }
  send('/home/product/review?id='+this.props.location.state.id, 'POST',rev, this.sendReview);
}

sendReview(received){
if(received==null)
  alert("Server Failed");
  else {
    alert(JSON.stringify(received));
    let opt = ''+ received.reason;
    if(opt === 'Success') {
      alert("review was added successfully");
    }
    else if(opt==='Invalid_Review') {
      alert("Can't send empty review");
    }
    else if(opt==='Store_Not_Found') {
      alert("Store not found");
    }
    else if(opt==='Cant_Add_Review') {
      alert("the product was removed from the store");
    }
    else {
      alert(opt+", Can't add your review");
    }
  }
}

create_Purchases(){
  if(this.flag){
  this.flag=false;
  send('/home/history?id='+this.props.location.state.id, 'GET','', this.buildPurchases);
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
    else {
      alert(opt+", Can't present your purchases");
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
  output.push(
      <tr>
        <th><Input title = 'Review:' type="text" value={pro.review} onChange={(t)=>this.handleChangeReview(t,pro)} />  </th> 
      </tr>            
  )
  output.push(
  <tr>
      <Button text = {"add review"} onClick={(t)=>this.handleSubmit(t,pro)}/>
  </tr>
  )
}); 
return output;
}

render() {
  this.create_Purchases();
  return (
    <BackGroud>
      <Menu state={this.props.location.state} />
      <Title title ={ this.state.purchases.length===0 ? "you did'nt make any purchases":'my purchases:'}/> 
      <div>
          {this.renderPurchase()} 
      </div>
    </BackGroud>
  );
}
}

export default UserWatchPurchasesHistory;

const style_sheet = {
color: "black",
padding: "1px",
margin: "10px",
lineHeight: 5,
fontFamily: "Arial",
width:"80%",
}
