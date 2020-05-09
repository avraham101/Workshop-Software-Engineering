import React, {Component} from 'react';
import Button from '.././Component/Button';
import Title from '.././Component/Title';
import Row from '.././Component/Row';
import {pass} from '../Utils/Utils'

class Cart extends Component {
  
    constructor(props){
        super(props)
        this.renderTitle = this.renderTitle.bind(this);
        this.renderCart = this.renderCart.bind(this);
        this.renderProduct = this.renderProduct.bind(this);
    }


    renderTitle(){
        if(this.props.cart.products.length===0)
        return "your cart is empty";
    else
        return "products in my cart:"
    }

    handleBuyCart(){
        this.props.cart.products=[]
        this.setState({})
    }

  renderCart(){
    let onClick = () => {
        pass(this.props.history,'/buyCart',this.props.pathname,this.props.state)
    }
    let output=[];
    output.push(
        <div>
            <div style={{width:'100%'}}>
                <div style={{float:'left', width:'90%'}}>
                    <h3 style={{textAlign:'center'}}>{"total price :"+this.props.cart.priceBeforeDiscount}</h3>
                </div>
                <div style={{float:'left', width:'10%'}}>
                    <Button text = "Buy Cart" onClick={()=>onClick()}/>
                </div>
            </div>
            <table style={style_sheet}>
                <tr>
                    <th style= {{ borderBottom:'1px solid black'}}>{}</th>
                    <th style= {{ borderBottom:'1px solid black'}}>product name</th>
                    <th style= {{ borderBottom:'1px solid black'}}>store</th>
                    <th style= {{ borderBottom:'1px solid black'}}>category</th>
                    <th style= {{ borderBottom:'1px solid black'}}>price per unit</th>
                    <th style= {{ borderBottom:'1px solid black'}}>purchase type</th>
                    <th style= {{ borderBottom:'1px solid black'}}>amount</th>
                    <th style= {{ borderBottom:'1px solid black'}}></th>
                    <th style= {{ borderBottom:'1px solid black'}}></th>
                </tr>
                {this.renderProduct(this.props.cart.products)}
            </table>
        </div>
    );
    return output
  }

  renderProduct(products){
    let output=[];
    products.forEach(pro => {
        output.push(
            <Row>
                <Button text = "X" onClick={(t)=>this.props.handleDelete(t,pro)}/>
                <th>{pro.productName}</th>
                <th>{pro.storeName}</th>
                <th>{pro.category}</th>
                <th>{pro.price}</th>
                <th>{pro.purchaseType}</th>
                <th>{pro.amount}</th>
                <th><input type="number" min={1} onChange={(e)=>pro.amount=parseInt(e.target.value)}/></th>
                <Button text = "Edit" onClick={(t)=>this.props.handleEdit(t,pro,pro.amount)}/>
            </Row>
        )
    }); 
    return output;
  }


  render() {
    return ( 
        <div>
            <Title title = {this.renderTitle()}/>
            {this.renderCart()}
        </div>      
    );
  }
}

export default Cart;

const style_sheet = {
    color: "black",
    padding: "1px",
    margin: "10px",
    lineHeight: 5,
    fontFamily: "Arial",
    border:'1px solid black',
    width:"99%",
  }