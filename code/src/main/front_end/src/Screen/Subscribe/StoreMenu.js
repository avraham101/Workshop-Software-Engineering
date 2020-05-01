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
      managerPermissions: true,
      deleteManager: true,
      viewAndReplyRequests: true,
      ownerWatchPurchaseHistory: true,
    };
  }

  pass(url) {
    this.props.history.push({
      pathname: url,
      fromPath: "/storeMenu",
    });
  }

  render_permissions() {
    return (
      <table style = {style_table}>
        {this.state.manageProduct ? (<Row onClick={() => this.pass("/manageProducts")}>Manage Product</Row>) : ("")}
        {this.state.storeDiscounts ? (<Row onClick={() => this.pass("/manageDiscount")}>Store Discounts</Row>) : ("")}
        {this.state.appointOwner ? (<Row onClick={() => this.pass("/TODO")}>Appoint Owner</Row>):("")}
        {this.state.managerPermissions ? (<Row onClick={() => this.pass("/TODO")}>Manager Permissions</Row>):("")}
        {this.state.deleteManager ? (<Row onClick={() => this.pass("/removeManagerFromStore")}>Delete Manager</Row>):("")}
        {this.state.viewAndReplyRequests ? (<Row onClick={() => this.pass("/TODO")}>View And Reply Requests</Row>):("")}
        {this.state.ownerWatchPurchaseHistory ? (<Row onClick={() => this.pass("/storeOwnerWatchHistory")}>Owner Watch Purchase History</Row>):("")}
      </table>
    );
  }

  render() {
    const { storeName } = this.props.location;
    return (
      <BackGrond>
        <Menu />
        <Title title={"Welcome to - " + storeName} />
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
