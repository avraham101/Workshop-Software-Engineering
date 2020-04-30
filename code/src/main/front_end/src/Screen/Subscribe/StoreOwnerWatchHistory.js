import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Purchases from '../../Component/Purchases';

class StoreOwnerWatchHistory extends Component {

  constructor(purchases) {
    super();
    this.purchases=[purchase,purchase];
  }

  render() {
    return (
      <BackGroud>
        <Menu/>
        <Purchases purchases={this.purchases}>
            <Title title = 'store purchases:'/> 
        </Purchases>
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


