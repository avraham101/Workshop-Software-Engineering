import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Row from "../../Component/Row";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class StoreManagement extends Component {
  constructor() {
    super();
    this.create_stores = this.create_stores.bind(this);
    this.pathname = "/storeManagement";
    this.state = {
      stores: [],
    };
    this.create_stores();
  }

  create_stores() {
    send('/home/product/filter', 'GET','',this.buildstores);  
  }


  render_stores() {
    return (
      <div>
        <h3>The stores of the {this.state.permission} :</h3>
        <table style={style_table}>
          <tr>
            <th style={under_line}> Store Name </th>
            <th style={under_line}> Description </th>
          </tr>
          {this.render_stores_table()}
        </table>
      </div>
    );
  }

  render_stores_table() {
    let onClick = (element) => {
      let state = this.props.location.state;
      state['storeName'] = element.name;
      pass(this.props.history,'/storeMenu',this.pathname,this.props.location.state)
    }
    let output = [];
    this.state.stores.forEach((element) =>
      output.push(
        <Row onClick={() =>onClick(element)}>
          <th> {element.name} </th>
          <th> {element.description} </th>
        </Row>
      )
    );
    return output;
  }

  pass(url, storeName) {
    this.props.history.push({
      pathname: url,
      fromPath: "/storeManagement",
      storeName: storeName, // your data array of objects
    });
  }

  render() {
    return (
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title="Manage Store" />
        <div>{this.render_stores()}</div>
      </BackGrond>
    );
  }
}
export default StoreManagement;

const style_table = {
  width: "99%",
  textAlign: "center",
  border: "2px solid black",
};

const under_line = {
  borderBottom: "2px solid black",
};

const style_sheet = {
  textAlign: "center",
  color: "black",
  backgroundColor: "#F3F3F3",
  padding: "1px",
  fontFamily: "Arial",
  width: "99%",
  marginTop: 0,
};
