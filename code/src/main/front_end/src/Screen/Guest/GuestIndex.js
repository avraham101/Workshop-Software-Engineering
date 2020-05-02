import React, {Component} from 'react';
import history from '../history';
import BackGrond from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Row from '../../Component/Row';
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class GuestIndex extends Component {

  constructor(props) {
    super(props);
    this.pathname = "/"
    this.state = {
      id: -1,
      error:'',
    };
    this.handleConnect = this.handleConnect.bind(this);
    this.handleGetId = this.handleGetId.bind(this);

    //this.handleConnect();
  }


  create_stores() {
    let output = [];
    for(let i =0; i<5;i++) {
      output.push({
        name:'Store '+i,
        description:'Description '+i
      });
    }
    return output;
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
    return output;
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

  render_product_table(){
    let state = this.props.location.state;
    let onClick = (element) => {
      let product = {
        productName:element.productName,
        storeName:element.storeName,
        category:element.category,
        amount:element.amount,
        price:element.price,
        purchaseType:element.purchaseType,
      }
      state['product'] = product;
      pass(this.props.history,'/addToCart',this.pathname,state)
    };
    let products = this.create_products();
    let output = [];
    products.forEach( element =>(
      output.push(
        <Row onClick={()=>onClick(element)}>
          <th> {element.productName} </th>
          <th> {element.storeName} </th>
          <th> {element.category} </th>
          <th> {element.amount} </th>
          <th> {element.price} </th>
          <th> {element.purchaseType} </th>
        </Row>
      )
    ));
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

  handleConnect(){
    send('/home/connect','GET','',this.handleGetId)
  }

  handleGetId(received) {
    if(received === null)
      this.props.location.state = {id:-1};
    else {
      let opt = '' + received.reason;
      if (opt !== "Success") {
        this.props.location.state = {id:-1};
      } 
      else {
        this.props.location.state = {id:received.value};
      }
    }
    this.setState({});
  };

  times=0;

  render() {
    if(this.props.location.state === undefined)
      this.handleConnect();
    if(this.props.location.state === undefined || this.props.location.state.id==-1)
      return <p style={{color:'red'}}>Page not found: 404 </p>
    return (
      <BackGrond>
          <Menu state={this.props.location.state}/>
          <body>
            <Title title="Welcome Guest"/>
            <div >
              <h3 style={{textAlign:'center'}}> Stores </h3>
              {this.render_stores()}
            </div>
            <div>
              <h3 style={{textAlign:'center'}}> Products </h3>
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