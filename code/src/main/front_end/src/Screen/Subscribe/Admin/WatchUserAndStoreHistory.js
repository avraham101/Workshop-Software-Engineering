import React, {Component} from "react";
import BackGroud from "../../../Component/BackGrond";
import Menu from "../../../Component/Menu";
import Title from "../../../Component/Title";
import Button from "../../../Component/Button";
import {send} from '../../../Handler/ConnectionHandler';
import {pass} from "../../../Utils/Utils";


class WatchUserAndStoreHistory extends Component {

    constructor() {
        super();
        this.handleChangeStoreName = this.handleChangeStoreName.bind(this);
        this.handleChangeUserName = this.handleChangeUserName.bind(this);
        this.create_Purchases_stores=this.create_Purchases_stores.bind(this);
        this.buildPurchases=this.buildPurchases.bind(this);
        this.renderPurchas=this.renderPurchase.bind(this);
        this.create_Purchases_user=this.create_Purchases_user.bind(this);
        this.render_optinos=this.render_optinos.bind(this);
        this.create_list=this.create_list.bind(this);
        this.buildStores=this.buildStores.bind(this);
        this.buildUsers=this.buildUsers.bind(this);
        this.switch_state=this.switch_state.bind(this);
        this.flag=true;
        this.state = {
            user: "",
            store: "",
            purchases:[],
            showStores: true,
            values:[],
        };
        
    }

    create_Purchases_stores(){
        this.flag=false;
        let id = this.props.location.state.id;
        send('/admin/storehistory/'+this.state.store+'?id='+id, 'GET','', this.buildPurchases);
    }

    create_Purchases_user(){
        let id = this.props.location.state.id;
        send('/admin/userhistory/'+this.state.user+'?id='+id, 'GET','', this.buildPurchases);
    }
      
      buildPurchases(received){
        if(received==null)
          alert("Server Failed");
        else {
          let opt = ''+ received.reason;
          if(opt == 'Success') {
            this.setState({purchases:received.value});
          }
          else if(opt == 'Store_Not_Found') {
            alert("the store does'nt exist");
          }
          else if(opt == 'Dont_Have_Permission') {
            alert("you don't have permission to watch the store history");
          }
          else {
            alert(opt+", Can't present your store purchases");
          }
        }
      }
     
  renderPurchase(){
    let output=[];
    this.state.purchases.forEach(element => {
      let index=element.date.indexOf('T'); 
      output.push(
      <table style={style_sheet}>
          <tr>
              {"purchase "+this.counter+++":"}
              <th>{"buyer:"+element.buyer}</th>
              <th>{"store:"+element.storeName}</th>
              <th>{"date:"+element.date.substring(0,index)}</th>
              <th>{"hour:"+element.date.substring(index+1)}</th>
            </tr>
           {this.renderProduct(element.product)}
        </table>
        )
    });
    this.counter=1;
    return output
  }

  renderProduct(products){
    let output=[];
    products.forEach(pro => {
        output.push(
            <tr>
                <th>{"product name:"+pro.productName}</th>
                <th>{"category:"+pro.category}</th>
                <th>{"amount:"+pro.amount}</th>
                <th>{"price per unit:"+pro.price}</th>
                <th>{"purchase type:"+pro.purchaseType}</th>
            </tr>
        )
    }); 
    return output;
  }

  create_list(){
    if(this.flag){
      this.flag=false;
      if(this.state.showStores){
        send('/store', 'GET','', this.buildStores);
      }
      else{
          let id = this.props.location.state.id;
          send('/admin/allusers/'+this.state.user+'?id='+id, 'GET','', this.buildUsers);
      }
    }
  }

  buildStores(received){
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
    if(opt == 'Success') {
      let stores=[];
      received.value.forEach(store=>{
        stores.push(store.name);
      });
      this.setState({values:stores});
    }
    else {
      alert(opt+", Can't present purchases");
      this.setState({values:[]});
    }
  }
  }

  buildUsers(received){
    if(received==null)
    alert("Server Failed");
  else {
    let opt = ''+ received.reason;
    if(opt == 'Success') {
      this.setState({values:received.value});
    }
    else if(opt == 'NOT_ADMIN') {
      alert("you are not admin");
      this.setState({values:[]});
    }
    else {
      alert(opt+", Can't present purchases");
      this.setState({values:[]});
    }
  }
  }

    handleChangeStoreName(event) {
        this.setState({store: event.target.value,});
    }

    handleChangeUserName(event) {
        this.setState({user: event.target.value,});
    }

    render_stores() {
        return (
            <BackGroud>
                <Title title='Watch store history:' />
                <div style={select_style}>
                    <label style = {{textAlign:'center'}}>Store name: </label>
                  </div>
                  <div style={select_style}>
                    <select  onChange={this.handleChangeStoreName}>
                    <option value="">-------------------</option>
                    {this.render_optinos()}
                    </select>
                  </div>
                <Button text='show history of the store' onClick={this.create_Purchases_stores}/>
            </BackGroud>
        );
    }

    render_optinos(){
      let output=[];
      this.state.values.forEach(element => {
        output.push(
          <option value={element}>{element}</option>
        )
      })
      return output;
    }

    render_users() {
        return (
            <BackGroud>
                <Title title='Watch user history:' />
                  <div style={select_style}>
                    <label style = {{textAlign:'center'}}>User name: </label>
                  </div>
                  <div style={select_style}>
                    <select  onChange={this.handleChangeUserName}>
                    <option value="">-------------------</option>
                    {this.render_optinos()}
                    </select>                    
                  </div>
                
                <Button text='show history of the user' onClick={this.create_Purchases_user}/>
            </BackGroud>
        );
    }

    switch_state(){
      this.setState({showStores: !this.state.showStores,purchases:[], user:'',store:'',values:[]});
      this.flag=true;
    }

    render() {
        let onBack= () => {
            pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state);
        }
      this.create_list();
        return (
            <BackGroud>
                <Menu state={this.props.location.state}/>
                <body>
                    {this.state.showStores === true ? this.render_stores() : this.render_users()}
                    <Button text="switch" onClick={this.switch_state} />
                </body>
                {this.renderPurchase()} 
            </BackGroud>
        );
    }

}

export default WatchUserAndStoreHistory;

const style_sheet = {
    color: "black",
    padding: "1px",
    margin: "10px",
    lineHeight: 5,
    fontFamily: "Arial",
    width:"80%",
  }

const select_style={
  display: "flex",
  justifyContent: "center",
  alignItems: "center"
}