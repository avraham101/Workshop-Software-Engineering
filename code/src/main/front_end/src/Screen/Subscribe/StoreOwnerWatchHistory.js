import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Purchases from '../../Component/Purchases';
import Button from "../../Component/Button";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

//4.10
class StoreOwnerWatchHistory extends Component {

  constructor(purchases) {
    super();
    this.pathname = "/viewAndReplyRequests";
    this.purchases=[purchase,purchase];
  }

  render() {
    let onBack= () => {
      pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
    }
    return (
      <BackGroud>
        <Menu state={this.props.location.state} />
        <Title title = 'store purchases:'/> 
        <Purchases purchases={this.purchases}/>
        <Button text="Back" onClick={onBack}/>
      </BackGroud>
    );
  }
}

export default StoreOwnerWatchHistory;


const product={
    productName: "peanuts",
    category: "category",
    amount: 3,
    price: 4.5,
    purchaseType: "immediate"
}

const purchase={
    storeName: "store",
    buyer: "buyer",
    products: [product,product],
    date: "21.01.20"
}


