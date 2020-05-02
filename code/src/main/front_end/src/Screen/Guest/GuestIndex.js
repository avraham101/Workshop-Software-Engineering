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
      stores:[],
    };
    this.handleConnect = this.handleConnect.bind(this);
    this.handleGetId = this.handleGetId.bind(this);
    this.buildStores = this.buildStores.bind(this);
    this.create_stores = this.create_stores.bind(this);
  }

  buildStores(received) {
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if(opt == 'Success') {
        this.setState({stores:received.value})
      }
      else {
        alert(opt+", Cant Add Product to Store");
      }
    }
  };

  create_stores() {
    send('/store', 'GET', '', this.buildStores)  
  }

  render_stores_table() {
      let output = [];
      this.state.stores.forEach( element =>
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
    this.create_stores();
    return (
      <BackGrond>
          <Menu state={this.props.location.state}/>
          <body>
            <Title title="Welcome Guest"/>
            <div >
              <h3 style={{textAlign:'center'}}> Stores </h3>
              {this.render_stores()}
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