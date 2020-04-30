import React, {Component} from 'react';
import history from '../Screen/history';
import Button from '.././Component/Button';
import Title from '.././Component/Title';

class Cart extends Component {
  
    constructor(props){
        super(props)
        this.cart=this.props.cart;
        this.handleDelete = this.handleDelete.bind(this);
    }


    handleDelete(event,product) {
        let index=this.cart.products.indexOf(product);
        if(index!==-1){         
            this.cart.products.splice(index,1);
        }

        this.setState({})
      }

      handleEditAmount(event,product){
        let index=this.cart.products.indexOf(product);
        //this.cart.products[index].amount=product.amount;
        this.setState({})
      }

      renderTitle(){
          if(this.cart.products.length===0)
            return "your cart is empty";
        else
            return "products in my cart:"
      }

      handleBuyCart(event){
          this.cart.products=[]
        this.setState({})
      }

  renderCart(){
    let output=[];
        output.push(
        <table style={style_sheet}>
            <tr>
                <th>{"total price :"+this.cart.price}</th>
                <th><Button text = "Buy Cart" onClick={()=>history.push("/buyCart")}/></th>
            </tr>
            <tr>
                <th>{}</th>
                <th>{"product name"}</th>
                <th>{"store"}</th>
                <th>{"category"}</th>
                <th>{"price per unit"}</th>
                <th>{"purchase type"}</th>
                <th>{"amount"}</th>
                <th>{}</th>
                <th>{}</th>
            </tr>
           {this.renderProduct(this.cart.products)}
        </table>
    );
    return output
  }

  renderProduct(products){
    let output=[];
    products.forEach(pro => {
        output.push(
            <tr>
                <Button text = "X" onClick={(t)=>this.handleDelete(t,pro)}/>
                <th>{pro.productName}</th>
                <th>{pro.store}</th>
                <th>{pro.category}</th>
                <th>{pro.price}</th>
                <th>{pro.purchaseType}</th>
                <th>{pro.amount}</th>
                <th><input type="number" min={1} onChange={(e)=>pro.amount=parseInt(e.target.value)}/></th>
                <Button text = "Edit" onClick={(t)=>this.handleEditAmount(t,pro)}/>
            </tr>
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
    width:"80%",
  }