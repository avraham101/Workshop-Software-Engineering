import React, {Component} from "react";
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
const MAX_HEIGHT = 1000;
  
class ManageDiscount extends Component {
  
  constructor() {
    super();
    this.storeDiscounts = [];
    this.simpleDiscounts = [];
    this.selectedDiscounts = [];
    this.comlicatedDiscounts = [];
    this.termDiscounts = [];
    this.selectedProductsTerms = [];
    this.targetProductTerm = undefined;
    this.pathname = "/manageDiscount";
    this.state = {
      products: [],
      store_amount:1,
      store_precentag:1,
      chosenDiscount:undefined,
      selectedProduct:undefined,
      selectedProductSimpleDiscount:undefined,
      simple_precentag:1,
      selectedProudctTerm:undefined,
      target_amount:1,
    };
    this.renderProducts = this.renderProducts.bind(this);
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
    if(this.storeDiscounts.length===0)
      return
    let output = [<h2 style={titleStyle}> Discounts </h2>];
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
        <p> Type: {element.purchaseType} </p>
      </DivBetter>
    ) 
  }

  /* */

  /*the function print the selected product and move him to Simple Discount or Term Discount */
  renderSelectedProduct(width) {
      let onClickSimple = () => {
        this.setState({ selectedProduct:undefined,
          selectedProductSimpleDiscount:this.state.selectedProduct,})
      }
      let onClickTerm = () => {
        this.setState({
          selectedProduct:undefined,
          selectedProudctTerm:this.state.selectedProduct,
          })
      }
      return (
        <div>
          <h2 style={titleStyle}> Selected Product </h2>
          <div style={{padding:4}}>
            {this.renderSelectedProductPicked()}
            <div style={{float:'left',width:'50%'}}> 
              <Button text="Select as Simple Discount" onClick={onClickSimple}/>
            </div>
            <div style={{float:'left',width:'50%'}}> 
              <Button text="Select as Term Product" onClick={onClickTerm} />
            </div>
          </div>
        </div>
      )
  }


  /* the function render simple discount selection */
  renderSimpleDiscontSelection() {
    let onSelect = () => {
      let data = {};
      let pName = this.state.selectedProductSimpleDiscount.productName;
      data["CLASSNAME"]=CLASS_SIMPLE;
      data["DATA"] = {product:pName,percantage:this.state.simple_precentag};
      this.simpleDiscounts.push(data);
      this.setState({selectedProductSimpleDiscount:undefined});
    }
    if(this.state.selectedProductSimpleDiscount===undefined)
      return (<p style={{textAlign:'center'}}> No product Selected </p>)
    return (
      <div>
        {this.renderProduct(this.state.selectedProductSimpleDiscount,onSelect)}
        <SmallInput title = 'Discount Prcentage:' type="number" min={0} max={100} value={this.state.simple_precentag} 
              onChange={(e)=>{this.setState({simple_precentag:e.target.value})}} />
        <Button text="Select" onClick={onSelect}/>
      </div>
    );
  }

  renderSimpleDiscountList() {
    if(this.simpleDiscounts.length===0)
      return
    let deleteClick = (elemnt)=>{
      this.simpleDiscounts.splice(this.simpleDiscounts.indexOf(elemnt),1);
      this.setState({})
    }
    let comlicatedClick= (elemnt)=>{
      this.addDiscountToSelectedDiscounts(elemnt); 
      this.simpleDiscounts.splice(this.simpleDiscounts.indexOf(elemnt),1);
      this.setState({})
    }
    let selectClick = (elemnt)=> {
      this.simpleDiscounts.splice(this.simpleDiscounts.indexOf(elemnt),1);
      this.setState({chosenDiscount:elemnt});
    }
    let output = [<h4 style={titleStyle}>Discounts </h4>];
    this.simpleDiscounts.forEach(elemnt => {
      output.push(
        <DivBetter>
          <p style={{backgroundColor:'lightgreen', marginTop:0}}> Simple Discount </p>
          <p style={{textAlign:'center'}}>Product: {elemnt.DATA.product} </p>
          <p style={{textAlign:'center'}}>Precentage: {elemnt.DATA.percantage} % </p>
          <Triple selectClick={()=>selectClick(elemnt)} 
                  comlicatedClick={()=>comlicatedClick(elemnt)} 
                  deleteClick={()=>deleteClick(elemnt)}/>
        </DivBetter>);
    });
    return output;
  }

  /*the function render simple discount */
  renderSimpleDiscount(width) {
      return (
        <div style={{float: 'left', width:width, borderRight:'1px solid black', marginBottom:0}}>
          <h3 style={titleStyle}> Simple Discount </h3>
          {this.renderSimpleDiscontSelection()}
          <div>
            {this.renderSimpleDiscountList()}
          </div>
        </div>
      )
  }

  /*the funcion print terms */
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

  /* the function render list of terms */
  renderTermsList() {
    let onX = (element) => {
      this.selectedProductsTerms.splice(this.selectedProductsTerms.indexOf(element),1);
      this.setState({})
    }
    let output = [];
    this.selectedProductsTerms.forEach(element => {
      output.push(
        <div style={{textAlign:'center'}}>
          {this.renderTermByClass(element)}
          <button style={buttonX} onClick={()=>onX(element)} > X </button>
        </div>
      )
    })
    return output;
  }

  /* the function render the selected Product Terms */
  renderTermsSelected() {
    let element = this.selectedProductsTerms;
    let morethen1 = () => {
      if(this.selectedProductsTerms.length<=1) {
        alert('please select more then 1 term to create complex terms.');
        return false;
      }
      return true;
    }
    let onAnd = () =>{
      if(morethen1()) {
        let data = this.selectedProductsTerms;
        let term = {};
        term['CLASSNAME'] = CLASS_AND_TERM;
        term['DATA'] = {terms:data}
        this.selectedProductsTerms = [];
        this.selectedProductsTerms.push(term);
        this.setState({})
      }
    }
    let onOr = () => {
      if(morethen1()) {
        let data = this.selectedProductsTerms;
        let term = {};
        term['CLASSNAME'] = CLASS_OR_TERM;
        term['DATA'] = {terms:data}
        this.selectedProductsTerms = [];
        this.selectedProductsTerms.push(term);
        this.setState({})
      }
    }
    let onXor = () => {
      if(morethen1()) {
        let data = this.selectedProductsTerms;
        let term = {};
        term['CLASSNAME'] = CLASS_XOR_TERM;
        term['DATA'] = {terms:data}
        this.selectedProductsTerms = [];
        this.selectedProductsTerms.push(term);
        this.setState({})
      }
    }

    if(element.length===0)
      return <p style={{textAlign:'center'}}> No Terms Selected </p>
    return (
      <div style={{float:'left', width:'100%'}}>
        <h4 style={titleStyle}> Selected Terms </h4>
        <div style={{overflowY: 'scroll'}}>
          {this.renderTermsList()}
        </div>
        <div>
          <div style={{float:'left',width:'33%'}}> 
            <Button text="AND" onClick={onAnd}/>
          </div>
          <div style={{float:'left',width:'33%'}}> 
            <Button text="OR" onClick={onOr}/>
          </div>
          <div style={{float:'left',width:'33%'}}>
            <Button text="XOR" onClick={onXor}/>
          </div>
          <p></p>
        </div>
      </div>
    )
  }

  /*the function render the target term */
  renderTargetTerm() {
    let element = this.state.selectedProudctTerm;
    if(element===undefined)
      return <p style={{textAlign:'center'}}> No Proudct Select to Term </p>
    let onClickTarget = () => {
      this.targetProductTerm = this.state.selectedProudctTerm;
      this.targetProductTerm.amount = this.state.target_amount;
      this.setState({
        selectedProudctTerm:undefined,
        target_amount:0,
      })
    }
    let onClickTerm = () => {
      if(this.selectedProductsTerms!==undefined) {
        let data = {}
        data['product'] = this.state.selectedProudctTerm.productName;
        data['amount'] = this.state.target_amount;
        let object = {CLASSNAME: CLASS_BASIC_TERM, DATA:data}
        this.selectedProductsTerms.push(object);
      }
      this.setState({
        selectedProudctTerm:undefined,
      })
    }
    return (
        <div style={{marginBottom:2}}>
          <DivBetter>
             <h3 style={{marginTop:0, borderBottom:'1px solid black',backgroundColor:'#FFC242'}}> {element.productName} </h3>
              <SmallInput title="amount" type="number" min={1} value={this.state.target_amount} 
                      onChange={(e)=>this.setState({target_amount:e.target.value})}/>
          <p></p>
          </DivBetter>
            <div style={{float:'left',width:'50%',}}> 
              <Button text="Select as target Term Discount" onClick={onClickTarget} />
            </div>
            <div style={{float:'left',width:'50%'}}> 
              <Button text="Select as Term Discount" onClick={onClickTerm}/>
            </div>
        </div>
    )
  }

  renderTragetProduct() {
    if(this.targetProductTerm===undefined)
      return <p style={{textAlign:'center'}}> No Target Product Selected </p>
    let element = this.targetProductTerm;
    let onClick = () => {
      if(this.selectedProductsTerms.length===0) 
        alert('no products term selected. please select before create a term discount')
      else if(this.selectedProductsTerms.length>1) 
        alert('please select only 1 term.')
      else {
        let object = {};
        object['CLASSNAME'] = CLASS_TERM;
        object['DATA'] = {term:this.selectedProductsTerms[0],
                          product:this.targetProductTerm.productName,
                          amount:this.targetProductTerm.amount}
       this.comlicatedDiscounts.push(object);
        this.targetProductTerm = undefined;
        this.selectedProductsTerms = [];
        this.setState({})
      }
    }
    return (
      <div style={{float:'left', width:'100%', textAlign:'center', border:'1px solid black', margin:2}}>
        <h4 style={{backgroundColor:'pink', marginTop:0}}> Target Product </h4>
        <p> Proudct: {element.productName} </p>
        <p> amount: {element.amount} </p>
        <Button text="Create Term Discount" onClick={onClick}/>
      </div>
    )
  }

  /* the function render the terms section */
  renderTermDiscount(width) {
    return (
      <div style={{float: 'left', width:width,}}>
        <h3 style={titleStyle}> Term Discount </h3>
        {this.renderTargetTerm()}
        {this.renderTragetProduct()}
        {this.renderTermsSelected()}
      </div>
    )
  }

  /* this function render a product */
  renderProduct(element, onClick) {
    if(element===undefined)
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



  //this function get the list of product from the server by given store
  getProducts(storeName) {
    let buildProducts = (received) => {
      if(received==null)
        alert("Server Failed");
      else {
        let opt = ''+ received.reason;
        if(opt === 'Success') {
          this.setState({products: received.value});
        }
        else {
          alert("Cant get Products from store. please try again later");
          pass(this.props.history,'/storeManagement','',this.props.location.state);
        }
      }
    }
    send('/home/product?store='+storeName, 'GET','', buildProducts)  
  }

  /*the function render products list */
  renderProducts(width) {
    if(this.state.products.length === 0) {
      this.getProducts(this.props.location.state.storeName);
    }
    return (
    <div style={{float: 'left', width:width ,  borderRight: '1px solid black',overflowY: 'scroll', height:MAX_HEIGHT}}>
      <h2 style={titleStyle}> Products </h2>
      {this.renderProductsList(this.state.products)}
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
      case CLASS_SIMPLE:
        return (
          <DivBetter>
            <p style={{backgroundColor:'lightgreen', marginTop:0}}> Simple Discount </p>
            <p style={{textAlign:'center'}}>Product: {elemnt.DATA.product} </p>
            <p style={{textAlign:'center'}}>Precentage: {elemnt.DATA.percantage} % </p>
            {deleteOn? <button style={buttonX} onClick={onClick}> X</button> :''}
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
      else if(this.selectedDiscounts.length===1)
        this.setState({error_selected:'please select more then 1 discount'});
      else
        this.setState({error_selected:'empty discounts'});
    };
    let orClick = (e) => {
      if(this.selectedDiscounts.length>1)
        addDiscountToComplicated({CLASSNAME:CLASS_OR,DATA:{discounts:this.selectedDiscounts}})
      else if(this.selectedDiscounts.length===1)
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
        <p style={{color:'red'}}> {this.state.error_selected!==undefined?this.state.error_selected:''} </p>
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
      default:
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
          {this.renderSelectedDiscountList(elemnt, false)}
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



  /*the function responseble to render the send discount */
  renderSendDiscount() {
    if(this.state.chosenDiscount === undefined)
      return
    let sendToServer = () => {
      let id = this.props.location.state.id;
      let msg = this.state.chosenDiscount;
      send('/store/discount?id='+id+'&store='+this.props.location.state.storeName,'POST',msg,promise);
      alert('please wait this takes time');
    }
    let promise = (received) => {
      if(received === null)
        alert("Server Crashed pls try again layter");
      else {
        let opt = '' + received.reason;
        if(opt === 'Dont_Have_Permission') {
          alert('You dont have premession to this action!');
          pass(this.props.history,'/storeManagement','',this.props.location.state);
        }
        else if(opt === 'Invalid_Product') {
          alert('Product dont exits in Store any more.');
          pass(this.props.history,'/storeManagement','',this.props.location.state);
        }
        else if(opt ==='Success'){
          alert('Discount Added to store');
          this.setState({chosenDiscount:undefined, products:[]})}
      }
    }

    let cancle = () => {
      this.setState({chosenDiscount:undefined});
    }
    return (
      <div style={{width:'100%', marginBottom:10}}>
        <div style={{float:'left', width:'40%', paddingLeft:300}}>
          {this.renderDiscountByClass(this.state.chosenDiscount,false)}
        </div>
        <div style={{float:'left', width:'29%'}}>
          <div style={{textAlign:'center'}}>
            <Button text="Cancle" onClick={cancle}/>
          </div>
          <div style={{textAlign:'center'}}>
            <Button text="Send" onClick={sendToServer}/>
          </div>
        </div>
      </div>
      
    )
  }

  /*the main render function */
  render() {
    let onBack= () => {
      pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
    }
    return (
      <BackGrond height={MAX_HEIGHT}>
        <Menu state={this.props.location.state} />
        <Title title="Discount Manager"/>
        <h2 style={{textAlign:'center', marginTop:0}}>{this.props.location.state.storeName}</h2>
        {this.renderSendDiscount()}
        <div style={{float:'left',height:MAX_HEIGHT, width:'100%'}}>
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
          <Button text="Back" onClick={onBack}/>
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