import React, {Component} from 'react';
import history from '../Screen/history'

class Purchases extends Component {
  
    constructor(props){
        super(props)
        this.purchases=this.props.purchases;
    }


  renderPurchase(){
    let output=[];
    this.purchases.forEach(element => {
        output.push(
        <table style={style_sheet}>
            <tr>
                <th>{element.buyer}</th>
                <th>{element.storeName}</th>
                <th>{element.date}</th>
            </tr>
           {this.renderProduct(element.products)}
        </table>
        )
    });
    return output
  }

  renderProduct(products){
    let output=[];
    products.forEach(pro => {
        output.push(
            <tr>
                <th>{pro.productName}</th>
                <th>{pro.category}</th>
                <th>{pro.amount}</th>
                <th>{pro.price}</th>
                <th>{pro.purchase}</th>
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
    backgroundColor: "#4DA0F3",
    padding: "15px",
    margin: "10px",
    lineHeight: 5,
    fontFamily: "Arial",
    width:"99%",
  }

