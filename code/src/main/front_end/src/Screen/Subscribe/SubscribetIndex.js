import React, {Component} from 'react';
import history from '../history';
import BackGrond from '../../Component/BackGrond';
import Title from '../../Component/Title';
import Row from '../../Component/Row';
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils';
import MenuSubscribe from '../../Component/MenuSubscribe';

class SubscribeIndex extends Component {

  constructor(props) {
    super(props);
    this.pathname = "/subscribe"
    this.state = {
      id: -1,
      error:'',
    };
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

  render() {
    return (
      <BackGrond>
          <MenuSubscribe state={this.props.location.state}/>
          <body>
            <Title title={"Welcome user "+this.props.location.state.name}/>
            <div >
              <h3 style={{textAlign:'center'}}> Stores </h3>
              {this.render_stores()}
            </div>
          </body>
      </BackGrond>
    );
  }
}

export default SubscribeIndex;

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