import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Button from '../../Component/Button';
import Input from '../../Component/Input';
import {send} from '../../Handler/ConnectionHandler';
import DivBetter from '../../Component/DivBetter';


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
      let onHover = (event) => {
          event.currentTarget.style.backgroundColor = '#92BAFF'
      }
      let onLeave = (event) => {
          event.currentTarget.style.backgroundColor = ''
      } 
      output.push(
        <div style={{float:'left', width:'100%', border:'1px solid black'}} 
              onMouseOver={onHover} onMouseLeave={onLeave}>
          <div style={{float:'left',width:'100%', backgroundColor:'#9BEBE5'}}>
            <div style={{float:'left',width:'20%', textAlign:'center'}}> 
              <p> Buyer: {element.buyer} </p>
            </div>
            <div style={{float:'left',width:'20%', textAlign:'center'}}> 
              <p> Store: {element.storeName} </p>
            </div>
            <div style={{float:'left',width:'20%'}}> 
              <p> Total Price: {element.price} </p>
            </div>
            <div style={{float:'left',width:'20%'}}> 
              <p> Date: {element.date.substring(0,index)} </p>
            </div>
            <div style={{float:'left',width:'20%'}}> 
              <p> Hour: {element.date.substring(index+1)} </p>
            </div>
          </div>
          <div style={{float:'left',width:'100%'}}>
            {this.renderProduct(element.product)}
          </div>
        </div>
        )
    });
    this.counter=1;
    return output
  }

  renderProduct(products){
    let output=[];
    products.forEach(pro => {
      output.push(
        <div style={{float:'left', width:'90%', marginLeft:'5%'}}>
          <div style={{float:'left', width:'25%'}}>
            <p> Product Name: {pro.productName} </p>
          </div>
          <div style={{float:'left', width:'25%'}}>
            <p> Category: {pro.category} </p>
          </div>
          <div style={{float:'left', width:'25%'}}>
            <p> Amount: {pro.amount} </p>
          </div>
          <div style={{float:'left', width:'25%'}}>
            <p> Purchase Type: {pro.purchaseType} </p>
          </div>
          <div style={{float:'left', width:'50%'}}>
            <Input title = 'Review:' type="text" value={pro.review} onChange={(t)=>this.handleChangeReview(t,pro)} />
          </div>
          <div style={{float:'left', width:'50%'}}>
            <Button text = {"add review"} onClick={(t)=>this.handleSubmit(t,pro)}/>
          </div>
        </div>)
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
