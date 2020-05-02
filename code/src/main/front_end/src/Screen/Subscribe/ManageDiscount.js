import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from '../../Component/Menu';
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from '../../Component/Button';
import Row from '../../Component/Row'
import DivBetter from '../../Component/DivBetter'

const CLASS_AND = 'Domain.Discount.AndDiscount'; 
const CLASS_OR = 'Domain.Discount.OrDiscount';
const CLASS_XOR = 'Domain.Discount.XorDiscount';
const CLASS_STORE = 'Domain.Discount.StoreDiscount';
const MAX_HEIGHT = 1000;
class ManageDiscount extends Component {
  
  constructor() {
    super();
    this.storeDiscounts = [];
    this.selectedDiscounts = [];
    this.comlicatedDiscounts = [];
    this.state = {
      storeName:'-- Didnt Selected Store --',
      store_amount:1,
      store_precentag:1,
      chosenDiscount:{},
      selectedProduct:undefined,
      selectedProductSimpleDiscount:undefined,
      selectedProductsTerm:undefined,
      selectedProudctTarget:undefined,
    }
    this.products = this.create_products(5);
  }

  /* the function create a product list */
  create_products(num) {
    let output = []
    for(let i =0; i<num;i++) {
      output.push({
        productName:'product '+i,
        storeName:'store '+i,
        category:'category '+i,
        reviews: [],
        amount: i,
        price:i,
        priceAfterDiscount: i,
        purchaseType:'purchase type '+i,
      })
    }
    return output
  }

  /* this function add discount to store discount list */
  addStoreDiscount(){
    let data = {minAmount:this.state.store_amount, percentage:this.state.store_precentag}
    if(data.percentage <= 100 && data.minAmount>0) {
      this.storeDiscounts.push({CLASSNAME: CLASS_STORE, DATA:data})
      this.setState({})
    }
  }  

  /* this function add discount to selected list */
  addDiscountToSelectedDiscounts(discount){
    this.selectedDiscounts.push(discount);
    this.setState({});
  }

  /* this function render the store discount list */
  renderStoreDiscontList(){
    let output = [];
    let deleteClick= (elemnt)=> {
      this.storeDiscounts.splice(this.storeDiscounts.indexOf(elemnt),1);
      this.setState({})
    }
    let comlicatedClick= (elemnt)=>{
      this.addDiscountToSelectedDiscounts(elemnt); 
      this.storeDiscounts.splice(this.storeDiscounts.indexOf(elemnt),1);
      this.setState({})
    }
    let selectClick = (elment)=> {
      this.storeDiscounts.splice(this.storeDiscounts.indexOf(elment),1);
      this.setState({chosenDiscount:elment});
    }
    this.storeDiscounts.forEach(elemnt => {
      output.push(
        <div style={{borderBottom:'1px solid black', textAlign:'center'}}>
          <table style={{width:"100%"}}>
            <DivBetter>
              <p style={{backgroundColor:'#FFC242', height:30, marginTop:0}}> Discont</p>
              <p style={{margin:2}}> Amount: {elemnt.DATA.minAmount} </p>
              <p style={{margin:4}}> Precentage: {elemnt.DATA.percentage} % </p>
            </DivBetter>
          </table>
          <Triple selectClick={()=>selectClick(elemnt)} 
                  comlicatedClick={()=>comlicatedClick(elemnt)} 
                  deleteClick={()=>deleteClick(elemnt)}/>
        </div>    
      )
    })
    return output;
  }

  /* this function render the store discount section */
  renderStoreDiscount(width) {
    return (
      <div style={{float: 'left', width:width, borderLeft: '1px solid black',height:MAX_HEIGHT}}>
              <h2 style={titleStyle}> Store Discount </h2>
              <div>
                <SmallInput title = 'Minmum Price:' type="number" min={0} value={this.state.store_amount} 
                    onChange={(e)=>{this.setState({store_amount:e.target.value})}} />
                <SmallInput title = 'Discount Precentage:' type="number" min={0} max={100} value={this.state.store_precentag} 
                    onChange={(e)=>{this.setState({store_precentag:e.target.value})}} />
                <Button text='Add' onClick={()=>this.addStoreDiscount()}/>
              </div>
              <div> 
                <h2 style={titleStyle}> Discounts </h2>
                {this.renderStoreDiscontList()}
              </div>         
      </div>
    )
  }

  renderSelectedProductPicked(){
    let element = this.state.selectedProduct;
    return (element===undefined)? '':
    (
      <DivBetter style={{borderBottom:'1px solid black', textAlign:"center", marginTop:0}}>
        <h3 style={{marginTop:0, borderBottom:'1px solid black',backgroundColor:'#FFC242'}}> {element.productName} </h3>
        <p> Price: {element.price} $ </p>
        <p> Category: {element.category} </p>
        <p> Amount In Store: {element.amount} </p>
        <p> Type: {element.purchaseType} </p>
      </DivBetter>
    ) 
  }

  /*the function print the selected product and move him to Simple Discount or Term Discount */
  renderSelectedProduct(width) {
      return (
        <div>
          <h2 style={titleStyle}> Selected Product </h2>
          <div style={{padding:4}}>
            {this.renderSelectedProductPicked()}
            <div style={{float:'left',width:'33%'}}> 
              <Button text="Select as Simple Discount"/>
            </div>
            <div style={{float:'left',width:'33%'}}> 
              <Button text="Select as target Term Discount" />
            </div>
            <div style={{float:'left',width:'33%'}}> 
              <Button text="Select as Term Discount" />
            </div>
          </div>
        </div>
      )
  }

  renderSimpleDiscount(width) {
      let onSelect = () => {

      }
      return (
        <div style={{float: 'left', width:width, borderRight:'1px solid black', marginBottom:0}}>
          <h3 style={titleStyle}> Simple Discount </h3>
          <div>
            <h4 style={titleStyle}>Selected Simple Discount </h4>
            {this.renderProduct(this.state.selectedProductSimpleDiscount,onSelect)}
            <SmallInput title = 'Discount Prcentage:' type="number" min={0} value={this.state.store_amount} 
                    onChange={(e)=>{this.setState({store_amount:e.target.value})}} />
            <Button text="Select" onClick={onSelect} />
          </div>
          <div>
            <h4 style={titleStyle}>Discounts </h4>
          </div>
        </div>
      )
  }

  renderTermDiscount(width) {
    return (
      <div style={{float: 'left', width:width,}}>
        <h3 style={titleStyle}> Term Discount </h3>
        <div>
          <h4 style={titleStyle}> Selected Term Target </h4>
          <h4 style={titleStyle}> Selected Term Discount </h4>
          <h4 style={titleStyle}> Terms </h4>
          <div>
            <div style={{float:'left',width:'33%'}}> 
              <Button text="AND"/>
            </div>
            <div style={{float:'left',width:'33%'}}> 
              <Button text="OR" />
            </div>
            <div style={{float:'left',width:'33%'}}>
              <Button text="XOR" />
            </div>
            <div style={{float:'left',width:'100%'}}>
              <Button text="Create Term Discount" />
            </div>
          </div>
          <h4 style={titleStyle}> Discounts </h4>
          
        </div>
      </div>
    )
  }

  /* this function render a product */
  renderProduct(element, onClick) {
    if(element==undefined)
      return (<p style={{color:'red'}}></p>)
    return (
      <div>
      <DivBetter style={{borderBottom:'1px solid black', textAlign:"center"}} onClick={()=>onClick(element)}>
        <h3 style={{marginTop:0, borderBottom:'1px solid black',backgroundColor:'#FFC242'}}> {element.productName} </h3>
        <p> Price: {element.price} $ </p>
        <p> Type: {element.purchaseType} </p>
      </DivBetter>
      </div>
    )
  }

  /*the function render list of product */
  renderProductsList(list) {
    let output = [];
    let onClick = (element) => {
      this.setState({selectedProduct:element})
    }
    list.forEach(element=>{
      output.push(this.renderProduct(element,onClick))    
    })
    return output;
  }

  /*the function render products list */
  renderProducts(width) {
    return (
    <div style={{float: 'left', width:width ,  borderRight: '1px solid black',overflowY: 'scroll', height:MAX_HEIGHT}}>
      <h2 style={titleStyle}> Products </h2>
      {this.renderProductsList(this.products)}
    </div>
    );
  }

  /* this function print diffrent elemnts by the class name */
  renderDiscountByClass(elemnt, deleteOn) {
    let onClick = (x)=>{this.selectedDiscounts.splice(this.selectedDiscounts.indexOf(x),1);
                        this.setState({})};
    switch(elemnt.CLASSNAME) {
      case CLASS_STORE:
        return (<div>
                  <DivBetter style={{margin:4}}>
                    <p style={{backgroundColor:'#FFC242', height:30, marginTop:0}}> Discont</p>
                    <p style={{margin:2}}> Amount: {elemnt.DATA.minAmount} </p>
                    <p style={{margin:2}}> Precentage: {elemnt.DATA.percentage} % </p>
                    {deleteOn? <button style={buttonX} onClick={onClick}> X</button> :''}
                  </DivBetter>
                </div>);
      case CLASS_AND: case CLASS_XOR: case CLASS_OR:
        return (
          <div style = {{padding:2}}>
            {this.renderComlicatedByClass(elemnt, false)}
          </div>
        );
    }
  }

  /*this function render the discount list */
  renderSelectedDiscountList(list, deleteOn){
    let output=[];
    list.forEach(elemnt =>{
      output.push(this.renderDiscountByClass(elemnt, deleteOn) )
    })
    return output;
  }

  /*this function responble for rendering the select discount and chosing xor, and, or */
  renderSelctedDiscounts(width){
    let addDiscountToComplicated = (discount) => {
      this.comlicatedDiscounts.push(discount);
      this.selectedDiscounts = [];
      this.setState({error_selected:undefined})
    }
    let andClick = (e) => {
      if(this.selectedDiscounts.length>1)
        addDiscountToComplicated({CLASSNAME:CLASS_AND,DATA:{discounts:this.selectedDiscounts}})
      else if(this.selectedDiscounts.length==1)
        this.setState({error_selected:'please select more then 1 discount'});
      else
        this.setState({error_selected:'empty discounts'});
    };
    let orClick = (e) => {
      if(this.selectedDiscounts.length>1)
        addDiscountToComplicated({CLASSNAME:CLASS_OR,DATA:{discounts:this.selectedDiscounts}})
      else if(this.selectedDiscounts.length==1)
        this.setState({error_selected:'please select more then 1 discount'});
      else
        this.setState({error_selected:'empty discounts'});
    };
    let xorClick = (e) => {
      if(this.selectedDiscounts.length>1)
        addDiscountToComplicated({CLASSNAME:CLASS_XOR,DATA:{discounts:this.selectedDiscounts}})
      else if(this.selectedDiscounts.length===1)
        this.setState({error_selected:'please select more then 1 discount'});
      else
        this.setState({error_selected:'empty discounts'});
    };
    return (
      <div style={{overflowY: 'scroll',height:MAX_HEIGHT, width:width, borderLeft:'1px solid black'}}>
        <h2 style={titleStyle}> Selected Discounts </h2>
        <div style={{padding:4}}>
          {this.renderSelectedDiscountList(this.selectedDiscounts, true)}
        </div>
        <p style={{color:'red'}}> {this.state.error_selected!=undefined?this.state.error_selected:''} </p>
        <div style={{float:'left',width:'33%'}}> 
          <Button text="AND" onClick={andClick}/>
        </div>
        <div style={{float:'left',width:'33%'}}> 
          <Button text="OR" onClick={orClick}/>
        </div>
        <div style={{float:'left',width:'33%'}}>
          <Button text="XOR" onClick={xorClick} />
        </div>
        <div style={{float:'left',width:'100%'}}>
          <h2 style={titleStyle}> Complicated Discounts </h2>
          <div style={{padding:10}}>
          {this.renderComplicatedDiscountList(true)}
          </div>
        </div>
      </div>
    )
  }

  /*the function print the comlicated discount by class */
  renderComlicatedByClass(elemnt, deletOn){
    let name='';
    switch(elemnt.CLASSNAME) {
      case CLASS_AND: name = 'AND Discount'; break; 
      case CLASS_OR: name = 'OR Discount'; break;
      case CLASS_XOR: name = 'XOR Discount'; break;
    }
    let deleteClick= (elemnt)=> {
      this.comlicatedDiscounts.splice(this.comlicatedDiscounts.indexOf(elemnt),1);
      this.setState({})
    }
    let comlicatedClick= (elemnt)=>{
      this.addDiscountToSelectedDiscounts(elemnt); 
      this.comlicatedDiscounts.splice(this.comlicatedDiscounts.indexOf(elemnt),1);
      this.setState({})
    }
    let selectClick = (elment)=> {
      this.comlicatedDiscounts.splice(this.comlicatedDiscounts.indexOf(elemnt),1);
      this.setState({chosenDiscount:elemnt});
    }
    return (
      <div style= {{border:'1px solid black', textAlign:'center', marginBottom:5, backgroundColor:'white'}}>
        <p style={{borderBottom:'1px solid black', backgroundColor:'#47CEFF', marginTop:0}}> {name} </p>
        <div style={{padding:10}}>
          {this.renderSelectedDiscountList(elemnt.DATA.discounts, false)}
        </div>
        {deletOn?<Triple selectClick={()=>selectClick(elemnt)} comlicatedClick={()=>comlicatedClick(elemnt)} deleteClick={()=>deleteClick(elemnt)}/>:''}
      </div>
    )
  }

  /*the function print the comlicated discount list */
  renderComplicatedDiscountList() {
    let output = [];
    this.comlicatedDiscounts.forEach( elemnt=>{
      output.push(this.renderComlicatedByClass(elemnt,true));
    })
    return output;
  }

  render() {
    return (
      <BackGrond height={MAX_HEIGHT}>
        <Menu />
        <Title title="Discount Manager"/>
        <h2 style={{textAlign:'center'}}>{this.state.storeName}</h2>
        <p>
          {JSON.stringify(this.state.chosenDiscount)}
        </p>
        <div style={{height:MAX_HEIGHT}}>
          {this.renderProducts('15%')}
          <div style={{float: 'left', width:'84%'}}>
            <div style={{float: 'left', width:'50%'}}>
              {this.renderSelectedProduct()}
              {this.renderSimpleDiscount('50%')}
              {this.renderTermDiscount('49.7%')}
            </div>
            {this.renderStoreDiscount('24%')}
            {this.renderSelctedDiscounts('25%')}
          </div>
        </div>
      </BackGrond>
    );
  }
}

export default ManageDiscount;

class SmallInput extends Component {
  
  render() {
    return (
      <div>
        <div style = {{textAlign:'center'}}>
          <label style={{marginLeft:5, marginRight:12}}> {this.props.title} </label>
        </div>
        <div style= {{textAlign:'center'}}>
          <input style={{textAlign:'center'}} type={this.props.type} min={this.props.min} max={this.props.max} value={this.props.value} onChange={this.props.onChange} />
        </div>  
      </div>
    );
  }
}

class Triple extends Component {

  render() {
    return (
    <table style={{width:"100%"}}>
      <tr sylte={{ textAlign:'center'}}>
        <th><button style={buttonSmall} onClick={this.props.selectClick}> select </button> </th>
        <th><button style={buttonSmall} onClick={this.props.comlicatedClick}> complicated </button> </th>
        <th><button style={buttonX} onClick={this.props.deleteClick} > X </button> </th>
      </tr>
    </table>
    );
  }
}

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