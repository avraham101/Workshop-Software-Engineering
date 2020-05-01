import React, {Component} from 'react';
import history from '../history';
import BackGrond from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Row from '../../Component/Row';

class GuestIndex extends Component {

  create_stores() {
    let output = []
    for(let i =0; i<5;i++) {
      output.push({
        name:'Store '+i,
        description:'Description '+i
      })
    }
    return output
  }

  create_products() {
    let output = []
    for(let i =0; i<5;i++) {
      output.push({
        productName:'product '+i,
        storeName:'store '+i,
        category:'category '+i,
        reviews: [],
        amount: i,
        price:i,
        priceAfterDiscount: i,
        purchaseType:'purchase type '+i,
      })
    }
    return output
  }

  click_me() {
    history.push('/register')
  }

  render_stores_table() {
      let stores = this.create_stores();
      let output = [];
      stores.forEach( element =>
        output.push(
          <Row>
            <th> {element.name} </th>
            <th> {element.description} </th>
          </Row>
        )
      )
      return output;
  }

  render_stores() {
    return (
    <table style={style_table}>
      <tr>
        <th style = {under_line}> Store Name </th>
        <th style = {under_line}> Description </th>
      </tr>
      {this.render_stores_table()}
    </table>);
  }

  pass(url, data) {
    this.props.history.push({
      pathname: url,
      fromPath: './',
      data: data // your data array of objects
    });
  }

  render_product_table(){
    let proudcts = this.create_products();
    let output = [];
    proudcts.forEach( element =>
      output.push(
        <Row onClick={()=>this.pass('/addToCart', element)}>
          <th> {element.productName} </th>
          <th> {element.storeName} </th>
          <th> {element.category} </th>
          <th> {element.amount} </th>
          <th> {element.price} </th>
          <th> {element.purchaseType} </th>
        </Row>
      )
    )
    return output;
  }

  render_product() {
    return (
    <table style={style_table}>
        <tr>
          <th style = {under_line}> Product Name </th>
          <th style = {under_line}> Store Name </th>
          <th style = {under_line}> Category </th>
          <th style = {under_line}> Amount </th>
          <th style = {under_line}> Price </th>
          <th style = {under_line}> Purchase Type </th>
        </tr>
        {this.render_product_table()}
    </table>);
  }

  render() {
    return (
      <BackGrond>
          <Menu/>
          <body>
            <Title title="Welcome Guest"/>
            <div >
              <h3 style={{textAlign:'center'}}> Stores </h3>
              {this.render_stores()}
            </div>
            <div>
              <h3 style={{textAlign:'center'}}> Prodcuts </h3>
              {this.render_product()}
            </div>
          </body>
      </BackGrond>
    );
  }
}

export default GuestIndex;

const style_table = {
  
  width:"99%", 
  textAlign: 'center',
  border: '2px solid black',
  lineHeight:1.5,
  margin:7.5,
}

const under_line = {
  borderBottom: '2px solid black'
}

const style_p = {
  textAlign: 'center'
}