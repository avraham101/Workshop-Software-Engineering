import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from "../../Component/Title";
import ProductsDelete from '../../Component/ProductsDelete';

//4.1.2
class ViewDeleteProductsInStore extends Component {

  constructor(products) {
    super();
    this.products=[product,product2];
    this.store="hanut";
  }

  render() {
    return (
      <BackGroud>
        <Menu/>
        <ProductsDelete products={this.products} store={this.store}>
        </ProductsDelete> 
      </BackGroud>
    );
  }
}

export default ViewDeleteProductsInStore;


const review1={
    content:"content1",
    writer:"yuval1",
}

const review2={
    content:"content2",
    writer:"yuval2",
}

const review3={
    content:"content3",
    writer:"yuval3",
}

const review4={
    content:"content4",
    writer:"yuval4",
}

const product={
    productName: "peanuts",
    category: "category",
    amount: 3,
    price: 4.5,
    purchaseType: "immediate",
    reviews:[review1,review2],
}

const product2={
    productName: "peanuts2",
    category: "category",
    amount: 90,
    price: 6.89,
    purchaseType: "immediate",
    reviews:[review3,review4],
}
