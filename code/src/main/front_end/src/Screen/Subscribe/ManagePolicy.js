import React, {Component} from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Button from "../../Component/Button";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

const CLASS_SYSTEM = 'Domain.PurchasePolicy.SystemPurchasePolicy';
const CLASS_BASKET = 'Domain.PurchasePolicy.BasketPurchasePolicy';
const CLASS_AND = 'Domain.PurchasePolicy.ComposePolicys.AndPolicy';
const CLASS_OR = 'Domain.PurchasePolicy.ComposePolicys.OrPolicy';
const CLASS_XOR = 'Domain.PurchasePolicy.ComposePolicys.XorPolicy';

class ManagePolicy extends Component {
  constructor() {
    super();
    this.pathname = "/policy";
    this.list_selected=[];
    this.list_comlicated=[];
    this.state = {
      age:1,
      maxAmount:0,
      selected_policy:undefined,
    };
    this.updateClick = this.updateClick.bind(this);
  }

  renderSystemPurcahsePolicay() {
    let onChange = (event) => {
      if(event.target.value<120)
        this.setState({age:event.target.value})};
    let addChange = () => {
      let obj = {};
      obj['CLASSNAME'] = CLASS_SYSTEM;
      obj['DATA'] = {age:this.state.age}; 
      this.list_selected.push(obj);
      this.setState({age:1})
    }
    return (
      <div>
        <h3 style={titleStyle}> Age Policy </h3>
        <SmallInput title="Minimum Age" type="number" min={0} value={this.state.age}
              onChange={onChange}/>
        <Button text="Add" onClick={addChange}/>
      </div>
    )
  }

  renderBasketPurcahsePolicy() {
    let onChange = (event) => {
        this.setState({maxAmount:event.target.value})};
    let addChange = () => {
      let obj = {};
      obj['CLASSNAME'] = CLASS_BASKET;
      obj['DATA'] = {maxAmount:this.state.maxAmount}; 
      this.list_selected.push(obj);
      this.setState({maxAmount:1})
    }
    return (
      <div>
        <h3 style={titleStyle}> Store Policy </h3>
        <SmallInput title="Maximum Products In Store" type="number" min={0} value={this.state.maxAmount}
              onChange={onChange}/>
        <Button text="Add" onClick={addChange}/>
      </div>
    )
  }

  renderColumn1() {
    return (
      <div style={{height:400}}>
        <div style={{height:'50%'}}>
          {this.renderSystemPurcahsePolicay()}
        </div>
        <div style={{height:'50%'}}>
          {this.renderBasketPurcahsePolicy()}
        </div>
      </div>
    )
  }

  renderByComlicated(element, deleteOn) {
    let selectClick = (element) => {
      this.list_selected.splice(this.list_selected.indexOf(element),1);
      this.setState({selected_policy:element});
    }
    let comlicatedClick = (element) => {
      this.list_selected.splice(this.list_selected.indexOf(element),1);
      this.list_comlicated.push(element);
      this.setState({});
    }
    let deleteClick = (element) => {
      this.list_selected.splice(this.list_selected.indexOf(element),1);
      this.setState({});
    }
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
              {deleteOn?<Triple selectClick={()=>selectClick(element)} 
                          comlicatedClick={()=>comlicatedClick(element)} 
                          deleteClick={()=>deleteClick(element)}/>:''}
            </div>);
  }

  renderByClass(element, deleteOn) {
    let data = element.DATA;
    let selectClick = (element) => {
      this.list_selected.splice(this.list_selected.indexOf(element),1);
      this.setState({selected_policy:element});
    }
    let comlicatedClick = (element) => {
      this.list_selected.splice(this.list_selected.indexOf(element),1);
      this.list_comlicated.push(element);
      this.setState({});
    }
    let deleteClick = (element) => {
      this.list_selected.splice(this.list_selected.indexOf(element),1);
      this.setState({});
    }
    switch(element.CLASSNAME) {
      case CLASS_SYSTEM: 
        return (
            <div style={{border:'2px solid #5CCCC4', margin:4}}>
                <h4 style={{backgroundColor:'#5CCCC4', textAlign:'center', marginTop:0}}>
                  Age Policy  
                </h4>
                <p style={{textAlign:'center'}}> Age: {data.age} </p>
                {deleteOn?<Triple selectClick={()=>selectClick(element)} 
                          comlicatedClick={()=>comlicatedClick(element)} 
                          deleteClick={()=>deleteClick(element)}/>:''}
            </div>
        )
        break;
      case CLASS_BASKET:
        return (
            <div style={{border:'2px solid #9AB0DB', margin:4}}>
                <h4 style={{backgroundColor:'#9AB0DB', textAlign:'center', marginTop:0}}>
                  Store Policy  
                </h4>
                <p style={{textAlign:'center'}}> Max Amount: {data.maxAmount} </p>
                {deleteOn?<Triple selectClick={()=>selectClick(element)} 
                          comlicatedClick={()=>comlicatedClick(element)} 
                          deleteClick={()=>deleteClick(element)}/>:''}
            </div>
        )
      case CLASS_AND: case CLASS_OR: case CLASS_XOR:
        return this.renderByComlicated(element,deleteOn); 
    }
  }

  renderList(list, deleteOn) {
    let output =[];
    list.forEach(element => {
      output.push(this.renderByClass(element,deleteOn))
    });
    return (
      <div style={{padding:4}}>
        {output}
      </div>);
  }

  renderComplex() {
    let clickd = (policy) => {
      this.list_comlicated = [];
      this.list_selected.push(policy);
      this.setState({});
    }
    let andClick = () => {
      if(this.list_comlicated.length==1)
        alert("Please Select more then 1 policy to create a complex one");
      else if(this.list_comlicated.length==0)
        alert("Please Select Policies");
      else {
        let obj = {};
        obj['CLASSNAME']=CLASS_AND;
        obj['DATA']= {policyList:this.list_comlicated}
        clickd(obj);
      }
    }
    let orClick = () => {
      if(this.list_comlicated.length==1)
        alert("Please Select more then 1 policy to create a complex one");
      else if(this.list_comlicated.length==0)
        alert("Please Select Policies");
      else {
        let obj = {};
        obj['CLASSNAME']=CLASS_OR;
        obj['DATA']= {policyList:this.list_comlicated}
        clickd(obj);
      }
    }
    let xorClick = () => {
      if(this.list_comlicated.length==1)
        alert("Please Select more then 1 policy to create a complex one");
      else if(this.list_comlicated.length==0)
        alert("Please Select Policies");
      else {
        let obj = {};
        obj['CLASSNAME']=CLASS_XOR;
        obj['DATA']= {policyList:this.list_comlicated}
        clickd(obj);
      }
    }
    return (
      <div style={{float:'left', width:'100%'}}>
        <h3 style={titleStyle}> Complex Policies </h3>
        {this.renderList(this.list_comlicated, false)}
        <div style={{float:'left', width:'33%'}}>
          <Button text="And" onClick={andClick}/>
        </div>
        <div style={{float:'left', width:'33%'}}>
          <Button text="Or" onClick={orClick}/>
        </div>
        <div style={{float:'left', width:'33%'}}>
          <Button text="Xor" onClick={xorClick}/>
        </div>
      </div>)
  }

  renderSelectedComplex() {
    return (
      <div>
        <h3 style={titleStyle}> Selected Policies </h3>
        {this.renderList(this.list_selected, true)}
        {this.renderComplex()}
      </div>
    )
  }

  updateClick() {
    let promise = (received) => {
      if(received==null)
        alert("Server Failed");
      else {
        console.log(received)
        let opt = ""+ received.reason;
        if(opt === 'Store_Not_Found‏') {
          alert('Store Not Found. By By');
          pass(this.props.history,'/subscribe','',this.props.location.state);
        }
        else if(opt ==='Invalid_Policy‏') {
          alert('Invalid Policy, Please try agian later');
          this.setState({selected_policy:undefined});
        }
        else if(opt ==='Dont_Have_Permission‏'){
          alert('User Dosnt have premission. By By')
          pass(this.props.history,'/subscribe','',this.props.location.state);
        }
        else if(opt ==='Invalid_Product‏'){
          alert('Invalid Policy, there is a product not valid, Please try agian later');
          this.setState({selected_policy:undefined});
        }
        else if(opt === 'Success') {
          alert('Policy added to Store');;
          this.setState({selected_policy:undefined});
        }
      }
    }
    let id = this.props.location.state.id;
    let store = this.props.location.state.storeName;
    let msg = this.state.selected_policy;
    send('/store/policy/add?id='+id+'&'+'store='+store,'POST',msg,promise);
  }

  renderSelectedPolicy() {
    if(this.state.selected_policy===undefined)
      return ''
    let cancleClick = () => {
      this.setState({selected_policy:undefined});
    }
    return (
      <div sstyle={{marginBottom:5}}>
        <div style={{float:'left', paddingLeft:500 ,width:'30%'}}>
          {this.renderByClass(this.state.selected_policy,false)}
        <div style={{textAlign:'center'}}>
          <Button text="Update" onClick={this.updateClick}/>
          <Button text="Cancle" onClick={cancleClick}/>
        </div>
        </div>
      </div> 
    )
  }

  render() {
    let height = 600;
    return (
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title="Policy Managment" />
        <div style={{float:'left', width:'100%'}}>
          {this.renderSelectedPolicy()}
        </div>
        
        <div style={{float:'left', width:'100%'}}>
          <div style={{float:'left', width:'24.8%', borderLeft:'1px solid black', height:height}}>
            {this.renderColumn1()}
          </div>
          <div style={{float:'left', width:'24.8%', borderLeft:'1px solid black', height:height}}>
            <h3 style={titleStyle}> P3  </h3>
          </div>
          <div style={{float:'left', width:'24.8%', borderLeft:'1px solid black', height:height}}>
            <h3 style={titleStyle}> P4  </h3>
          </div>
          <div style={{float:'left', width:'24.7%', borderLeft:'1px solid black', height:height}}>
            {this.renderSelectedComplex()}
          </div>
        </div>
        
      </BackGrond>
    );
  }
}
export default ManagePolicy;

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