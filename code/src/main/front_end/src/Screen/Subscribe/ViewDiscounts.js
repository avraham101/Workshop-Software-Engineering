import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from '../../Component/Menu';
import Title from "../../Component/Title";
import Button from '../../Component/Button';
import DivBetter from '../../Component/DivBetter'
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

const CLASS_AND = 'Domain.Discount.AndDiscount'; 
const CLASS_OR = 'Domain.Discount.OrDiscount';
const CLASS_XOR = 'Domain.Discount.XorDiscount';
const CLASS_STORE = 'Domain.Discount.StoreDiscount';
const CLASS_SIMPLE = 'Domain.Discount.RegularDiscount';
const CLASS_BASIC_TERM = 'Domain.Discount.Term.BaseTerm';
const CLASS_AND_TERM = 'Domain.Discount.Term.AndTerm';
const CLASS_OR_TERM = 'Domain.Discount.Term.OrTerm';
const CLASS_XOR_TERM = 'Domain.Discount.Term.XorTerm';
const CLASS_TERM = 'Domain.Discount.ProductTermDiscount';
  
class ViewDiscounts extends Component {
  
  constructor() {
    super();
    this.pathname = "/viewDiscounts";
    this.getMap = this.getMap.bind(this);
    this.renderRowOfDiscounts = this.renderRowOfDiscounts.bind(this);
    this.state = {
      mapped: false,
      map: {},
    };
    this.elementInRow = 3;
  }

  /* get the map of discounts */
  getMap() {
    if(this.state.mapped==false) {
    let promise = (received) => {
      if (received==null)
        alert("Server Failed");
      else {
        let opt = received.reason;
        if(opt == 'Store_Not_Found') {
            alert("Store Already Exites");
          }
        else if(opt == 'Success') {
          //alert(JSON.stringify(received.value));
          this.setState({map:received.value,mapped:true});
          }
        }
      }
    send('/store/discount/get?store='+this.props.location.state.storeName,'GET','',promise)
    }
  }

   /*this function render the discount list */
   renderSelectedDiscountList(element, deleteOn){
    let output=[];
    switch(element.CLASSNAME) {
      case CLASS_TERM:
        output.push(this.renderDiscountByClass(element, deleteOn));
        return output;
      case CLASS_XOR: case CLASS_OR: case CLASS_AND:
        let list = element.DATA.discounts;
        list.forEach(elemnt =>{
          output.push(this.renderDiscountByClass(elemnt, deleteOn) )
        })
        return output;
      default:
        element.forEach(elemnt =>{
            output.push(this.renderDiscountByClass(elemnt, deleteOn) )
        })
        return output;
    }
  }

  /*the function print the comlicated discount by class */
  renderComlicatedByClass(elemnt){
    let name='';
    switch(elemnt.CLASSNAME) {
      case CLASS_AND: name = 'AND Discount'; break; 
      case CLASS_OR: name = 'OR Discount'; break;
      case CLASS_XOR: name = 'XOR Discount'; break;
      default:
    }
    return (
      <div style= {{border:'1px solid black', textAlign:'center', marginBottom:5, backgroundColor:'white'}}>
        <p style={{borderBottom:'1px solid black', backgroundColor:'#47CEFF', marginTop:0}}> {name} </p>
        <div style={{padding:10}}>
          {this.renderSelectedDiscountList(elemnt, false)}
        </div>
      </div>
    )
  }

  /*the funcion print term discount */
  renderTermByClass(term) {
    let title = 'Simple Term';
    switch(term.CLASSNAME) {
      case CLASS_BASIC_TERM:
        return (        
        <DivBetter>
          <p style={{backgroundColor:'pink',marginTop:0}}>{title}</p>
          <p style={{textAlign:'center'}}> product: {term.DATA.product} </p>
          <p style={{textAlign:'center'}}> amount: {term.DATA.amount} </p>
        </DivBetter>)
      case CLASS_AND_TERM: title = 'AND Term'; break;
      case CLASS_OR_TERM: title = 'OR Term'; break; 
      case CLASS_XOR_TERM: title = 'XOR Term'; break;
      default:
    }
    let renderList = (terms) => {
      let output = [];
      terms.forEach(element => {
        output.push(this.renderTermByClass(element))
      })
      return output;
    }
    return (
      <DivBetter>
        <p style={{backgroundColor:'#E640B5',marginTop:0}}>{title}</p>
        <div style={{padding:3}}>
          {renderList(term.DATA.terms)}
        </div>
      </DivBetter>
    )
  }

  /* this function print diffrent elemnts by the class name */
  renderDiscountByClass(elemnt) {
    switch(elemnt.CLASSNAME) {
      case CLASS_STORE:
        return (<div>
                  <DivBetter style={{margin:4}}>
                    <p style={{backgroundColor:'#FFC242', height:30, marginTop:0}}> Discont</p>
                    <p style={{margin:2}}> Amount: {elemnt.DATA.minAmount} </p>
                    <p style={{margin:2}}> Precentage: {elemnt.DATA.percentage} % </p>
                  </DivBetter>
                </div>);
      case CLASS_AND: case CLASS_XOR: case CLASS_OR:
        return (
          <div style = {{padding:2}}>
            {this.renderComlicatedByClass(elemnt)}
          </div>
        );
      case CLASS_SIMPLE:
        return (
          <DivBetter>
            <p style={{backgroundColor:'lightgreen', marginTop:0}}> Simple Discount </p>
            <p style={{textAlign:'center'}}>Product: {elemnt.DATA.product} </p>
            <p style={{textAlign:'center'}}>Precentage: {elemnt.DATA.percantage} % </p>
          </DivBetter>
        )
      case CLASS_TERM:
        let term = elemnt.DATA.term;
        let target_prodct = elemnt.DATA.product;
        let target_amount = elemnt.DATA.amount;
        return (
          <DivBetter> 
              <p style={{backgroundColor:'#CC84F0',textAlign:'center', marginTop:0}}> Term Discount </p>
              <p style={{textAlign:'center'}}> Product: {target_prodct}</p>
              <p style={{textAlign:'center'}}> Amount: {target_amount}</p>
              <div style={{padding:4}}>
                {this.renderTermByClass(term)}
              </div>
          </DivBetter>
        );
      default:
    }
  }

  /*this function render a disount */
  renderDiscount(index) {
    let discount = this.state.map[index];
    //let obj = JSON.parse(discount);
    return (
      <div>
        <p> {discount} </p>
      </div>)
    //return this.renderDiscountByClass(test2)
  }

  /*the discount render a row of discounts elements */
  renderRowOfDiscounts(index,mapsize) {
    let output = [];
    let end = index+this.elementInRow;
    for(let i=0;i<end && i<mapsize;i++) {
      output.push(
        <div style={{float:'left', width:(98/this.elementInRow)+'%'}}>
          {this.renderDiscount(index+i)}
        </div>
      )
    }
    return output;
  }

  /*this function render the discount map */
  renderDiscountsMap() {
    this.getMap();
    let keys =  Object.getOwnPropertyNames(this.state);
    if(keys.length === 0) {
      return <p style={{textAlign:'center'}}> No disconts in store</p>;
    }
    let output = [];
    let index_key = 0; 
    for(let i=0;i<(keys.length/this.elementInRow);i++) {
      output.push(
        <div>
          {this.renderRowOfDiscounts(index_key,keys.length)}
        </div>)  
      index_key+=this.elementInRow;
    }
    return output;
  }

  /*the main render function */
  render() {
    let onBack= () => {
      pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
    }
    return (
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title="View Discounts Manager"/>
        {this.renderDiscountsMap()}
        <div style={{float:'left', width:'100%'}}>
          <Button text="Back" onClick={onBack}/>
        </div>
      </BackGrond>
    );
  }
}

export default ViewDiscounts;

const titleStyle ={
  textAlign:'center',
  backgroundColor:'lightgray',
  borderBottom:'1px solid black',
  marginBottom:5,
};

const buttonX = {
  backgroundColor:'#B38118',
  margin:5,
}

const buttonSmall = {
  backgroundColor:'#0041B3', 
  color:'white'
}