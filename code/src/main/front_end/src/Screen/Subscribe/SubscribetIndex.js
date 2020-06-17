import React, {Component} from 'react';
import BackGrond from '../../Component/BackGrond';
import Title from '../../Component/Title';
import Row from '../../Component/Row';
import {send} from '../../Handler/ConnectionHandler';
import MenuSubscribe from '../../Component/MenuSubscribe';
import logo_store from '../../Assests/store.jpg';
class SubscribeIndex extends Component {

  constructor(props) {
    super(props);
    this.pathname = "/subscribe"
    this.state = {
      id: -1,
      error:'',
      stores:[],
      flag:false
    };
    this.buildStores = this.buildStores.bind(this);
  }

  buildStores(received) {
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if(opt == 'Success') {
        this.setState({stores:received.value, flag:true})
      }
      else {
        alert(opt+", Cant Add Product to Store");
      }
    }
  };


  create_stores() {
    if(this.state.flag===false) {
      send('/store', 'GET', '', this.buildStores)  
    }
  }

  renderStore(element) {
    let m = (90  / 3);
    return ( <div style={{float:'left', width:m+'%', border:'1px solid black', textAlign:'center', margin:'1%'}}>
                <div style={{float:'left', width:'100%', background:'#3086DB'}}>
                  <p> {element.name} </p>
                </div>
                <div style={{float:'left', width:'100%'}}>
                  <p> {element.description} </p>
                </div>
                <div style={{float:'left', width:'100%'}}>
                  <img src={logo_store}/>
                </div>
             </div>
    )
  }

  render_stores_table() {
      let output = [];
      this.state.stores.forEach( element =>
        output.push( this.renderStore(element)
        //   <Row>
        //     <th> {element.name} </th>
        //     <th> {element.description} </th>
        //   </Row>
        )
      )
      return output;
  }

  render_stores() {
    return this.render_stores_table();
    // <table style={style_table}>
    //   <tr>
    //     <th style = {under_line}> Store Name </th>
    //     <th style = {under_line}> Description </th>
    //   </tr>
    //   {this.render_stores_table()}
    // </table>);
  }

  render() {
    this.create_stores();
    return (
      <BackGrond>
          <MenuSubscribe state={this.props.location.state}/>
          {/*<Notifications id={this.props.location.state.id}/>*/}
          <body>
            <Title title={"Welcome user "+this.props.location.state.name}/>
            <div >
              <h3 style={{textAlign:'center'}}> Stores </h3>
              {(this.state.stores.length===0)?<h3 style={{textAlign:'center'}}> no stores on board</h3>: this.render_stores()}
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