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
    this.handlePermission = this.handlePermission.bind(this);
    this.pathname = "/storeManagement";
    this.state = {
      manageStores: this.create_manage_stores(),
      ownerStores: this.create_owner_stores(),
      permission: "",
      showStores: false,
    };
  }

  create_owner_stores() {
    let output = [];
    for (let i = 0; i < 5; i++) {
      output.push({
        name: "Store " + i + " in owner ",
        description: "Description " + i,
      });
    }
    return output;
  }

  create_manage_stores() {
    let output = [];
    for (let i = 0; i < 5; i++) {
      output.push({
        name: "Store " + i + " in manage ",
        description: "Description " + i,
      });
    }
    return output;
  }

  render_permission() {
    return (
      <table style={style_table}>
        <Row onClick={(e) => this.handlePermission(e, "Owner")}>Owner</Row>
        <Row onClick={(e) => this.handlePermission(e, "Manager")}>Manager</Row>
      </table>
    );
  }

  handlePermission(event, permission) {
    this.setState({
      name: event.target.value,
      showStores: true,
      permission: permission,
    });
  }

  render_stores() {
    return this.state.permission === "Owner"
      ? this.render_stores_table_style(this.state.ownerStores)
      : this.render_stores_table_style(this.state.manageStores);
  }

  render_stores_table_style(stores) {
    return (
      <div>
        <h3>The stores of the {this.state.permission} :</h3>
        <table style={style_table}>
          <tr>
            <th style={under_line}> Store Name </th>
            <th style={under_line}> Description </th>
          </tr>
          {this.render_stores_table(stores)}
        </table>
      </div>
    );
  }

  render_stores_table(stores) {
    let onClick = (element) => {
      let state = this.props.location.state;
      state['storeName'] = element.name;
      pass(this.props.history,'/storeMenu',this.pathname,this.props.location.state)
    }
    let output = [];
    stores.forEach((element) =>
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
        <h3 style={style_sheet}>Choose permission to work with</h3>
        <h4 style={style_sheet}>
          That will point you to the stores that you have permission to theme.
        </h4>
        <div>{this.render_permission()}</div>
        <div>{this.state.showStores ? this.render_stores() : ""}</div>
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
