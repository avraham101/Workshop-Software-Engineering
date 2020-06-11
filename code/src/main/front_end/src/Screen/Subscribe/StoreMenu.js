import React, {Component} from "react";
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
    this.init_permissions=this.init_permissions.bind(this);
    this.buildpermissions=this.buildpermissions.bind(this);
    this.update_permissions=this.update_permissions.bind(this);
    this.flag=true;
    this.state = {
      manageProduct: true,
      storeDiscounts: true,
      storePolicy: true,
      appointOwner: true,
      appointManager: true,
      managerPermissions: true,
      deleteManager: true,
    };
  }

  onClick(path) {
    pass(this.props.history,path,this.pathname,this.props.location.state);
  }

  
  init_permissions() {
    let id=this.props.location.state.id;
    if(this.flag){
      this.flag=false;
      send('/home/permissions/'+this.props.location.state.storeName+'?id='+id, 'GET','',this.buildpermissions);
    }  
  }

  buildpermissions(received) {
    if(received==null)
      alert("Server Failed");
    else{
      let opt = ''+ received.reason;
      if(opt === 'Success') {
        this.update_permissions(received.value);
      }
      else if(opt === 'Dont_Have_Permission') {
        alert('Dont Have Permission for that store. Soryy.')
        pass(this.props.history,'/storeManagement',this.pathname,this.props.location.state);
      }
      else {
        alert(opt+", Cant get your permissions ");
        pass(this.props.history,'/storeManagement',this.pathname,this.props.location.state);
      }
    }
  }

  update_permissions(permissions){
    this.setState({
      manageProduct: permissions.includes("OWNER")||permissions.includes("PRODUCTS_INVENTORY"),
      storeDiscounts: permissions.includes("OWNER")||permissions.includes("CRUD_POLICY_DISCOUNT"),
      storePolicy: permissions.includes("OWNER")||permissions.includes("CRUD_POLICY_DISCOUNT"),
      appointOwner: permissions.includes("OWNER")||permissions.includes("ADD_OWNER"),
      appointManager: permissions.includes("OWNER")||permissions.includes("ADD_MANAGER"),
      managerPermissions: permissions.includes("OWNER")||permissions.includes("ADD_MANAGER"),
      deleteManager: permissions.includes("OWNER")||permissions.includes("DELETE_MANAGER"),
      approveOwner: permissions.includes("OWNER"),
    });
  }
  
  render_permissions() {
    return (
      <table style = {style_table}>
        {this.state.manageProduct ? (<Row onClick={() => this.onClick("/manageProducts")}>
            <p style={paragraph}> Add Product </p></Row>) : ("")}
        {this.state.manageProduct ? (<Row onClick={() => this.onClick("/editProduct")}>
            <p style={paragraph}> Edit Product </p></Row>) : ("")}
        {this.state.manageProduct ? (<Row onClick={() => this.onClick("/productsDelete")}>
            <p style={paragraph}> View & Delete Product </p> </Row>) : ("")}
        {this.state.storeDiscounts ? (<Row onClick={() => this.onClick("/manageDiscount")}>
            <p style={paragraph}>Add Discounts</p> </Row>) : ("")}
        {this.state.storeDiscounts ? (<Row onClick={() => this.onClick("/viewDiscounts")}>
            <p style={paragraph}>View & Delete Discounts</p> </Row>) : ("")}
        {this.state.storePolicy ? (<Row onClick={() => this.onClick("/policy")}>
            <p style={paragraph}> Store Policy</p> </Row>) : ("")}
        {this.state.appointOwner ? (<Row onClick={() => this.onClick("/addOwnerToStore")}>
            <p style={paragraph}>Appoint Owner</p> </Row>):("")}
        {this.state.approveOwner ? (<Row onClick={() => this.onClick("/approveOwnerToStore")}>
            <p style={paragraph}>Approved Owner </p> </Row>):("")}
        {this.state.appointManager ? (<Row onClick={() => this.onClick("/addManagerToStore")}>
            <p style={paragraph}>Appoint Manager</p> </Row>):("")}
        {this.state.managerPermissions ? (<Row onClick={() => this.onClick("/editManagerPermissions")}>
            <p style={paragraph}>Manager Permissions</p> </Row>):("")}
        {this.state.deleteManager ? (<Row onClick={() => this.onClick("/removeManagerFromStore")}>
            <p style={paragraph}>Delete Manager</p> </Row>):("")}
        <Row onClick={() => this.onClick("/viewAndReplyRequests")}>
            <p style={paragraph}>View And Reply Requests</p> </Row>
        <Row onClick={() => this.onClick("/storeOwnerWatchHistory")}>
            <p style={paragraph}>Owner Watch Purchase History</p> </Row>
      </table>
    );
  }

  render() {
    this.init_permissions();
    return (
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title={"Welcome to - " + this.props.location.state.storeName} />
        <Title title="What do you want to do in your store?" />
        <div style={{paddingLeft:'25%', width:'50%'}}>
          {this.render_permissions()}
        </div>
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

const paragraph = {
  textAlign:'center',
  fontSize:19,
  margin:0,
  padding:0,
  borderBottom:'1px solid black',
}