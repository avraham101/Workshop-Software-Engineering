import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Row from "../../Component/Row";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class StoreMenu extends Component {
  constructor() {
    super();
    this.pathname = "/storeMenu";
    this.state = {
      manageProduct: true,
      storeDiscounts: true,
      storePolicy: true,
      appointOwner: true,
      appointManager: true,
      managerPermissions: true,
      deleteManager: true,
      viewAndReplyRequests: true,
      ownerWatchPurchaseHistory: true,
    };
  }

  onClick(path) {
    pass(this.props.history,path,this.pathname,this.props.location.state);
  }
  
  render_permissions() {
    return (
      <table style = {style_table}>
        {this.state.manageProduct ? (<Row onClick={() => this.onClick("/manageProducts")}>Add Product</Row>) : ("")}
        {this.state.manageProduct ? (<Row onClick={() => this.onClick("/editProduct")}>Edit Product</Row>) : ("")}
        {this.state.manageProduct ? (<Row onClick={() => this.onClick("/productsDelete")}>Remove Product</Row>) : ("")}
        {this.state.storeDiscounts ? (<Row onClick={() => this.onClick("/manageDiscount")}>Store Discounts</Row>) : ("")}
        {this.state.storePolicy ? (<Row onClick={() => this.onClick("/policy")}>Store Policy</Row>) : ("")}
        {this.state.appointOwner ? (<Row onClick={() => this.onClick("/addOwnerToStore")}>Appoint Owner</Row>):("")}
        {this.state.appointManager ? (<Row onClick={() => this.onClick("/addManagerToStore")}>Appoint Manager</Row>):("")}
        {this.state.managerPermissions ? (<Row onClick={() => this.onClick("/editManagerPermissions")}>Manager Permissions</Row>):("")}
        {this.state.deleteManager ? (<Row onClick={() => this.onClick("/removeManagerFromStore")}>Delete Manager</Row>):("")}
        {this.state.viewAndReplyRequests ? (<Row onClick={() => this.onClick("/viewAndReplyRequests")}>View And Reply Requests</Row>):("")}
        {this.state.ownerWatchPurchaseHistory ? (<Row onClick={() => this.onClick("/storeOwnerWatchHistory")}>Owner Watch Purchase History</Row>):("")}
      </table>
    );
  }

  render() {
    return (
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title={"Welcome to - " + this.props.location.state.storeName} />
        <Title title="What do you want to do in your store?" />
        <div>{this.render_permissions()}</div>
      </BackGrond>
    );
  }
}
export default StoreMenu;

const style_table = {
  width: "99%",
  textAlign: "center",
  border: "2px solid black",
};
