import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Row from "../../Component/Row";

class StoreMenu extends Component {
  constructor() {
    super();
    this.state = {
      manageProduct: true,
      storeDiscounts: true,
      appointOwner: true,
      appointManager: true,
      managerPermissions: true,
      deleteManager: true,
      viewAndReplyRequests: true,
      ownerWatchPurchaseHistory: true,
    };
  }

  pass(url, storeName) {
    this.props.history.push({
      pathname: url,
      fromPath: "/storeMenu",
      storeName: storeName
    });
  }

  render_permissions() {
    const { storeName } = this.props.location;
    return (
      <table style = {style_table}>
        {this.state.manageProduct ? (<Row onClick={() => this.pass("/manageProducts",storeName)}>Manage Product</Row>) : ("")}
        {this.state.storeDiscounts ? (<Row onClick={() => this.pass("/manageDiscount",storeName)}>Store Discounts</Row>) : ("")}
        {this.state.appointOwner ? (<Row onClick={() => this.pass("/addOwnerToStore",storeName)}>Appoint Owner</Row>):("")}
        {this.state.appointManager ? (<Row onClick={() => this.pass("/addManagerToStore",storeName)}>Appoint Manager</Row>):("")}
        {this.state.managerPermissions ? (<Row onClick={() => this.pass("/editManagerPermissions",storeName)}>Manager Permissions</Row>):("")}
        {this.state.deleteManager ? (<Row onClick={() => this.pass("/removeManagerFromStore",storeName)}>Delete Manager</Row>):("")}
        {this.state.viewAndReplyRequests ? (<Row onClick={() => this.pass("/viewAndReplyRequests",storeName)}>View And Reply Requests</Row>):("")}
        {this.state.ownerWatchPurchaseHistory ? (<Row onClick={() => this.pass("/storeOwnerWatchHistory",storeName)}>Owner Watch Purchase History</Row>):("")}
      </table>
    );
  }

  render() {
    const { storeName } = this.props.location;
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
