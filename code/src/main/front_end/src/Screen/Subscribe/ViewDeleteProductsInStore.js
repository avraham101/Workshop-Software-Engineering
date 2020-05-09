import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from "../../Component/Title";
import Row from '../../Component/Row';
import Button from '../../Component/Button';
import DivBetter from '../../Component/DivBetter'
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils';

//4.1.2
class ViewDeleteProductsInStore extends Component {

  constructor() {
    super();
    this.getProducts = this.getProducts.bind(this);
    this.deleteProduct = this.deleteProduct.bind(this);
    this.state = {
      products:[],
      updated_products:false,
      productName: undefined,
      category: '',
      amount: 0,
      price: 0,
      reviews: [],
      purchase: '',
    }
    this.store="hanut";
  }

  /*this function gets the products from store */
  getProducts() {
    let buildProducts = (received) => {
      if(received===null)
        alert("Server Failed");
      else {
        let opt = ''+ received.reason;
        if(opt === 'Success') {
          this.setState({products:received.value, updated_products:true})
        }
        else if(opt === 'Store_Not_Found‏') {
          alert('Store Not Found. Soryy.')
          pass(this.props.history,'/storeManagement','',this.props.location.state);
        }
        else {
          alert("Cant Access products");
          pass(this.props.history,'/storeManagement','',this.props.location.state);
        }
      }
    }
    if(this.state.updated_products === false) {
      send('/home/product?store='+this.props.location.state.storeName, 'GET','', buildProducts);  
    }
  }
  /* the function print the product table */
  render_product_table() {
    let onClick = (element) => {
      this.setState({
        productName: element.productName,
        category: element.category,
        amount: element.amount,
        price: element.price,
        purchase: element.purchaseType,
        reviews: element.reviews,
      });
    };
    let proudcts = this.state.products;
    let output = [];
    proudcts.forEach((element) =>
      output.push(
        <Row onClick={()=>onClick(element)}>
          <th> {element.productName} </th>
          <th> {element.category} </th>
          <th> {element.amount} </th>
          <th> {element.price} </th>
          <th> {element.purchaseType} </th>
        </Row>
      )
    );
    return output;
  }

  render_product() {
    this.getProducts();
    return (
      <div>
        <h3>{this.state.store}</h3>
        <table style={style_table}>
          <tr>
            <th style={under_line}> Product Name </th>
            <th style={under_line}> Category </th>
            <th style={under_line}> Amount </th>
            <th style={under_line}> Price </th>
            <th style={under_line}> Purchase Type </th>
          </tr>
          {this.render_product_table()}
        </table>
      </div>
    );
  }

  deleteProduct() {
    let promise = (received) => {
      if(received===null)
        alert("Server Failed");
      else {
        let opt = ''+received.reason;
        if (opt == "Invalid_Product") {
          alert("The product data isnt valid.");
          pass(this.props.history,'/storeManagement',this.pathname,this.props.location.state);
        } 
        else if(opt == 'Store_Not_Found‏') {
          alert("Store doesn't Exits. Go back to main menu");
          pass(this.props.history,'/storeManagement',this.pathname,this.props.location.state);
        }
        else if(opt =="Dont_Have_Permission‏") {
          alert("User Doesnt have premission for that. By By");
          pass(this.props.history,'/storeManagement',this.pathname,this.props.location.state)
        }
        else if(opt == "Success") {
          alert("Product Deleted");
          this.setState({
            productName:undefined,
            category:'',
            amount: 1,
            price: 1,
            updated_products:false,
          })
        }
        else {
          alert(" Cant Delete product. Try again later to Store");
          pass(this.props.history,'/storeManagement',this.pathname,this.props.location.state)
        }
      }
    }
    let msg ={};
    msg['productName']=this.state.productName;
    msg['storeName']=this.props.location.state.storeName;
    msg['category']=this.state.category;
    msg['amount'] = this.state.amount;
    msg['price'] = this.state.price;
    msg['purchaseType'] = this.state.purchase;
    let id = this.props.location.state.id;
    send('/home/product/delete?id='+id,'POST', msg, promise)
  }

  render_reviews() {
    if(this.state.reviews.length===0)
      return <p style={{textAlign:'center'}}> Didnt have reviews yet</p>
    let output = [];
    this.state.reviews.forEach(element => {
      output.push(
        <DivBetter>
          <div>
            <div style={{float:'left', width:'30%'}}>
              <p style={{textAlign:'center'}}> Writer: {element.writer} </p>
            </div>
            <div style={{float:'left', width:'69%'}}>
              <p style={{textAlign:'center'}}> {element.content} </p>
            </div>
          </div>
        </DivBetter>
      )
    });
    return output;
     
  }

  render_selected_product() {
    if(this.state.productName===undefined)
      return ''
    //finish
    return (
      <div>
          <div style={{margin:2 ,width:'30%', float:'left', padding:0, border:"2px solid #89CCC1"}}>
            <h2 style={{textAlign:'center', backgroundColor:'#89CCC1', marginTop:0}}> 
                {this.state.productName} </h2>
            <p style={{textAlign:'center'}}> Category: {this.state.category} </p>
            <p style={{textAlign:'center'}}> Amount: {this.state.amount} </p>
            <p style={{textAlign:'center'}}> Price: {this.state.price} </p>
            <p style={{textAlign:'center'}}> purchaseType: {this.state.purchaseType} </p>
            <Button text="Delete" onClick={this.deleteProduct}/>
          </div>
          <div style={{width:'67.5%', float:'left'}}>
            <h2 style={{textAlign:'center', marginTop:0}}> Reviews </h2>
            {this.render_reviews()}
          </div>
      </div>
    )
  }

  render() {
    return (
      <BackGroud>
        <Menu state={this.props.location.state} />
        <Title title="Veiw Products In Store"/>
        <h2 style={{textAlign:'center'}}>{this.props.location.state.storeName}</h2>
        {this.render_product()}
        {this.render_selected_product()}
      </BackGroud>
    );
  }
}

export default ViewDeleteProductsInStore;

const style_table = {
  width: "99%",
  textAlign: "center",
  border: "2px solid black",
};

const under_line = {
  borderBottom: "2px solid black",
};
