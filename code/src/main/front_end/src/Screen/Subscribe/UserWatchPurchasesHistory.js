import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Purchases from '../../Component/Purchases';
import UserPurchases from '../../Component/UserPurchases';


class UserWatchPurchasesHistory extends Component {

  constructor(purchases) {
    super();
    this.purchases=[purchase,purchase];
  }

  render() {
    return (
      <BackGroud>
        <Menu/>
        <UserPurchases purchases={this.purchases} eventName="add review">
            <Title title = 'my purchases:'/> 
        </UserPurchases>
      </BackGroud>
    );
  }
}

export default UserWatchPurchasesHistory;


const product={
    productName: "peanuts",
    category: "category",
    amount: 3,
    price: 4.5,
    purchaseType: "immediate",
    review: '',
}

const product2={
    productName: "peanuts2",
    category: "category",
    amount: 3,
    price: 4.5,
    purchaseType: "immediate",
    review: '',
}

const purchase={
    storeName: "store",
    buyer: "buyer",
    products: [product,product2],
    date: "21.01.20"
}