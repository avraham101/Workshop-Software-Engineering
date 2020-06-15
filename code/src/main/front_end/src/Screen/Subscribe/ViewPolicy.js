import React, {Component} from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Button from "../../Component/Button";
import DivBetter from '../../Component/DivBetter';
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

const CLASS_SYSTEM = 'Domain.PurchasePolicy.SystemPurchasePolicy';
const CLASS_BASKET = 'Domain.PurchasePolicy.BasketPurchasePolicy';
const CLASS_COUNRTY = 'Domain.PurchasePolicy.UserPurchasePolicy';
const CLASS_PRODUCT = 'Domain.PurchasePolicy.ProductPurchasePolicy';
const CLASS_AND = 'Domain.PurchasePolicy.ComposePolicys.AndPolicy';
const CLASS_OR = 'Domain.PurchasePolicy.ComposePolicys.OrPolicy';
const CLASS_XOR = 'Domain.PurchasePolicy.ComposePolicys.XorPolicy';

class ViewPolicy extends Component {
  constructor() {
    super();
    this.pathname = "/viewPolicies";
    this.state = {
      selected_policy:undefined,
    };
    this.getPolicy = this.getPolicy.bind(this);
  }

  renderByComlicated(element, deleteOn) {
    let title ='';
    let color = '';
    switch(element.CLASSNAME) {
      case CLASS_AND: title='And Policy'; color='#33FF9F'; break; 
      case CLASS_OR: title='Or Policy';  color='#B2FF4D'; break;
      case CLASS_XOR:  title='Xor Policy';  color='#FFFA70'; break;
    }
    let list = element.DATA.policyList;
    let output = [];
    list.forEach(policy => {
      output.push(
        <div>
          {this.renderByClass(policy,false)}
        </div>
      )
    })
    return (<div style={{border:'1px solid black'}}>
              <h4 style={{backgroundColor:color, textAlign:'center', marginTop:0}}> {title} </h4>
              <div style={{padding:4}}>
                {output}
              </div>
            </div>);
  }

  renderByClass(element, deleteOn) {
    let data = element.DATA;
    switch(element.CLASSNAME) {
      case CLASS_SYSTEM: 
        return (
            <div style={{border:'2px solid #5CCCC4', margin:4}}>
                <h4 style={{backgroundColor:'#5CCCC4', textAlign:'center', marginTop:0}}>
                  Age Policy  
                </h4>
                <p style={{textAlign:'center'}}> Age: {data.age} </p>
            </div>
        )
      case CLASS_BASKET:
        return (
            <div style={{border:'2px solid #9AB0DB', margin:4}}>
                <h4 style={{backgroundColor:'#9AB0DB', textAlign:'center', marginTop:0}}>
                  Store Policy  
                </h4>
                <p style={{textAlign:'center'}}> Max Amount: {data.maxAmount} </p>
            </div>
        )
      case CLASS_AND: case CLASS_OR: case CLASS_XOR:
        return this.renderByComlicated(element,deleteOn); 
      case CLASS_COUNRTY:
        let list = data.countries;
        return (
          <div style={{border:'2px solid #B352FF', margin:4}}>
            <h4 style={{backgroundColor:'#B352FF', textAlign:'center', marginTop:0}}>
              Country Policy 
            </h4>
            {this.renderCountriesList(list)}
          </div>
        )
      case CLASS_PRODUCT:
        let map = data.amountPerProduct;
        let output = [];
        let keys = Object.getOwnPropertyNames(map) 
        keys.forEach(key => {
          let obj = map[key];
          let min = obj.min; 
          let max = obj.max;
          output.push(
            <div>
              <p style={{textAlign:'center'}}> Product: {key} </p>
              <p style={{textAlign:'center'}}> Min Amount of product: {min} </p>
              <p style={{textAlign:'center'}}> Max Amount of product: {max} </p>
            </div>
          )
        });
        return (
          <div style={{border:'2px solid #B0C8FF', margin:4}}>
            <h4 style={{backgroundColor:'#B0C8FF', textAlign:'center', marginTop:0}}>
              Product Policy
            </h4>
            {output}
          </div>
        )
    }
  }

  renderCountriesList(list) {
    let output = [];
    list.forEach(element=>{
      output.push(
        <p style={{textAlign:'center'}}> Country: {element} </p>
      )
    });
    return output;
  }

  getPolicy(){
    let promise = (received)=> {
      if(received==null)
        alert("Server Failed");
      else {
        let opt = ''+ received.reason;
        if(opt === 'Success') {
          console.log(received.value);
          let obj = JSON.parse(received.value);
          this.setState({selected_policy:obj});
        }
        else if(opt === 'Store_Not_Found‏') {
          alert('Store Not found. Back to the prev page');
          pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state);
        }
      }
    }
    let storeName = this.props.location.state.storeName;
    send('/store/policy/get?store='+storeName,'GET','',promise);
  }

  checkEmptyPolicy() {
    let obj = this.state.selected_policy;
    if(obj===undefined)
      return true;
    let class_name = obj.CLASSNAME;
    if(class_name!==CLASS_AND)
      return false;
    let data = obj.DATA;
    let list = data.policyList;
    if(list.length===0)
      return true;
    return false;
  }

  renderPolicy() {
    if(this.state.selected_policy === undefined) {
      this.getPolicy();  
    }
    if(this.checkEmptyPolicy()) {
      return <p style={{textAlign:'center'}}> No Policy To Store Yet </p>
    }
    if(this.state.selected_policy=== undefined)
      return
    
    return (
    <div style={{width:'40%', paddingLeft:425}}>
        <h2 style={titleStyle}> Policy </h2>
        {this.renderByClass(this.state.selected_policy, false)}
      </div>
    )
  }

  render() {
    let height = 600;
    let storeName = this.props.location.state.storeName;
    let onBack= () => {
      pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
    };
    return (
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title={"View Policy "+storeName}/>
        {this.renderPolicy()}
        <div style={{float:'left', width:'100%'}}>
          <Button text="Back" onClick={onBack}/>
        </div>
      </BackGrond>
    );
  }
}
export default ViewPolicy;

const titleStyle ={
  textAlign:'center',
  backgroundColor:'lightgray',
  borderBottom:'1px solid black',
  marginBottom:5,
};
