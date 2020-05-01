import React, {Component} from 'react';
import history from '../Screen/history';
import Purchases from '.././Component/Purchases';
import Button from '.././Component/Button';
import Input from '.././Component/Input';

//3.3+3.7
class UserPurchases extends Purchases {
  
    constructor(props){
        super(props)
        this.event=this.props.event
        this.eventName=this.props.eventName
        this.handleChangeReview = this.handleChangeReview.bind(this);
    }

     handleChangeReview(event,product) {
         product.review= event.target.value;
         this.setState({})
       }

      handleSubmit(event,product) {
        event.preventDefault();
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
        output.push(
            <tr>
             <th><Input title = 'Review:' type="text" value={pro.review} onChange={(t)=>this.handleChangeReview(t,pro)} />  </th> 
            </tr>            
        )
        output.push(
        <tr>
            <Button text = {this.eventName} onClick={(t)=>this.handleSubmit(t,pro)}/>
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

export default UserPurchases;
