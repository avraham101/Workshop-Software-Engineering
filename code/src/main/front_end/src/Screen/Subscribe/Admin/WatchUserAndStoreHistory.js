import React, { Component } from "react";
import BackGroud from "../../../Component/BackGrond";
import Menu from "../../../Component/Menu";
import Title from "../../../Component/Title";
import Button from "../../../Component/Button";
import history from "../../history";
import Input from '../../../Component/Input';
import {send} from '../../../Handler/ConnectionHandler';


class WatchUserAndStoreHistory extends Component {

    constructor() {
        super();
        this.handleChangeStoreName = this.handleChangeStoreName.bind(this);
        this.handleChangeUserName = this.handleChangeUserName.bind(this);
        this.create_Purchases_stores=this.create_Purchases_stores.bind(this);
        this.buildPurchases=this.buildPurchases.bind(this);
        this.renderPurchas=this.renderPurchase.bind(this);
        this.create_Purchases_user=this.create_Purchases_user.bind(this);
        this.state = {
            user: "",
            store: "",
            purchases:[],
            showStores: true,
        };
    }

    create_Purchases_stores(){
        this.flag=false;
        send('/admin/storehistory/'+this.state.store+'?id='+0, 'GET','', this.buildPurchases);
    }

    create_Purchases_user(){
        send('/admin/userhistory/'+this.state.user+'?id='+0, 'GET','', this.buildPurchases);
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

    handleChangeStoreName(event) {
        this.setState({store: event.target.value});
    }

    handleChangeUserName(event) {
        this.setState({user: event.target.value});
    }

    render_stores() {
        return (
            <BackGroud>
                <Title title='Watch store history:' />
                <Input title='Store name:' type="text" value={this.state.store} onChange={this.handleChangeStoreName} />
                <Button text='show history of the store' onClick={this.create_Purchases_stores}/>
            </BackGroud>
        );
    }

    render_users() {
        return (
            <BackGroud>
                <Title title='Watch user history' />
                <Input title='user name:' type="text" value={this.state.user} onChange={this.handleChangeUserName} />
                <Button text='show history of the user' onClick={this.create_Purchases_user}/>
            </BackGroud>
        );
    }

    render() {
        return (
            <BackGroud>
                <Menu/>
                <body>
                    {this.state.showStores === true ? this.render_stores() : this.render_users()}
                    <Button text="switch" onClick={() => this.setState({showStores: !this.state.showStores})} />
                    <Button text="Back to menu" onClick={() => history.push("/")} />
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