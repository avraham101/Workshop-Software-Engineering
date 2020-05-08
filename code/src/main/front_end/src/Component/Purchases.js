import React, {Component} from 'react';

class Purchases extends Component {
  
    constructor(props){
        super(props)
        this.purchases=this.props.purchases;
        this.counter=1;
    }


  renderPurchase(){
    let output=[];
    this.purchases.forEach(element => {
        output.push(
        <table style={style_sheet}>
            <tr>
                {"purchase "+this.counter+++":"}
                <th>{"buyer:"+element.buyer}</th>
                <th>{"store:"+element.storeName}</th>
                <th>{"date:"+element.date}</th>
            </tr>
           {this.renderProduct(element.products)}
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
                <th>{"price per unit:"+pro.price}</th>
                <th>{"purchase type:"+pro.purchaseType}</th>
            </tr>
        )
    }); 
    return output;
  }


  render() {
    return (
        <div>
            {this.renderPurchase()} 
        </div>      
    );
  }
}

export default Purchases;

const style_sheet = {
    color: "black",
    padding: "1px",
    margin: "10px",
    lineHeight: 5,
    fontFamily: "Arial",
    width:"80%",
  }

