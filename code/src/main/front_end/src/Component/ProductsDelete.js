import React, {Component} from 'react';
import Button from '.././Component/Button';
import Row from './Row';
import Title from '.././Component/Title';

class ProductsDelete extends Component {
  
    constructor(props){
        super(props)
        this.products=this.props.products;
        this.store=this.props.store;
        this.handleReviews = this.handleReviews.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.state={
             product:{},
             showReviews:false,
        }
    }

    handleDelete(event,product) {
        event.stopPropagation();
        let index=this.products.indexOf(product);
        if(index!==-1){         
            if(this.state.product.productName===this.products[index].productName){
                this.state.product={};
                this.state.showReviews=false;
            }   
            this.products.splice(index,1);  
        }
        this.setState({
            product: this.state.product,
            showReviews: this.state.showReviews,
        })
      }

  renderProducts(){
    let output=[];
        output.push(
        <table style={style_sheet}>
            <tr>
                <th>{}</th>
                <th>{"product name"}</th>
                <th>{"category"}</th>
                <th>{"price per unit"}</th>
                <th>{"purchase type"}</th>
                <th>{"amount"}</th>
            </tr>
           {this.renderProduct()}
        </table>
    );
    return output
  }

  handleReviews(event,product){
    this.setState({
        product: product,
        showReviews: true,
      });
  }

  render_reviews(){
    let output=[];
    if(!this.state.showReviews)
        return output;
    this.state.product.reviews.forEach(rev => {
        output.push(
        <table style={style_sheet}>
            <tr>
                <th>{"review:"+rev.content}</th>
                <th>{"written by:"+rev.writer}</th>
            </tr>
        </table>
        )
    });
    return output
  }

  renderProduct(){
    let output=[];
    if(this.props===undefined)
        return output
    this.products.forEach(pro => {
        output.push(
            <Row onClick={(e) => this.handleReviews(e, pro)}>
                <Button text = "X" onClick={(t)=>this.handleDelete(t,pro)}/>
                <th>{pro.productName}</th>
                <th>{pro.category}</th>
                <th>{pro.price}</th>
                <th>{pro.purchaseType}</th>
                <th>{pro.amount}</th>
            </Row>
        )
    }); 
    return output;
  }

  renderTitle(){
    if(this.products.length===0)
      return this.store+" has no products";
  else
      return this.store+" products:"
}

  render() {
    return ( 
        <div>
            <Title title = {this.renderTitle()}/>
            {this.renderProducts()} 
            {this.render_reviews()}
        </div>      
    );
  }
}

export default ProductsDelete;

const style_sheet = {
    color: "black",
    padding: "1px",
    margin: "10px",
    lineHeight: 5,
    fontFamily: "Arial",
    width:"80%",
  }


