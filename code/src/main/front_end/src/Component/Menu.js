import React, {Component} from 'react';
import history from '../Screen/history'
import {pass} from '../Utils/Utils'
import MenuSubscribe from './MenuSubscribe';

const WHITE_BLUE= '#B3D1F0'
class Menu extends Component {
  
  constructor(props){
    super(props)
    this.fromPath = this.props.fromPath
    this.prevState = this.props.state
    this.state = {
      color_0: '',
      color_1: '',
      color_2: '',
      color_3: '',
      color_4: '',
      color_5: '',
    }
  }

  renderGuest() {
    return (
        <header style={{backgroundColor: '#FFC242', borderBottom:'2px solid #B38118'}}>
          {/*<p>{(this.props.state!=undefined)?this.props.state.id:''}</p>*/}
          <table style={style_sheet}>
          <tr style={{width:'100%'}}>
            <th onClick={() => pass(history,"/guest",this.fromPath,this.props.state)}
                style={{background:this.state.color_0}}
                onMouseOver={()=>this.setState({color_0: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_0: ''})}> Menu </th>
            <th onClick={() => pass(history,"/viewStoresAndProducts",this.fromPath,this.props.state)}
                style={{background:this.state.color_1}}
                onMouseOver={()=>this.setState({color_1: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_1: ''})}> Watch Store and Products </th>
            <th onClick={()=> pass(history,"/searchAndFilter",this.fromPath,this.props.state)}
                style={{background:this.state.color_2}}
                onMouseOver={()=>this.setState({color_2: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_2: ''})}> Search and Filter Products </th>
            <th onClick={()=>pass(history,"/register",this.fromPath,this.props.state)} 
                style={{background:this.state.color_3}} 
                onMouseOver={()=>this.setState({color_3: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_3: ''})}> Register </th>
            <th onClick={()=>pass(history,"/login",this.fromPath,this.props.state)}
                style={{background:this.state.color_4}}
                onMouseOver={()=>this.setState({color_4: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_4: ''})}> Login </th>
            <th onClick={()=>pass(history,"/viewMyCart",this.fromPath,this.props.state)}
                style={{background:this.state.color_5}} 
                onMouseOver={()=>this.setState({color_5: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_5: ''})}> Watch My Cart </th>
          </tr>
        </table>
        </header>
    );
  }

  render() {
    return (this.props.state==undefined)? this.renderGuest():
           (this.props.state.logged==undefined)? this.renderGuest():
            <MenuSubscribe state ={this.props.state}/>
           
  }
}

export default Menu;

const style_sheet = {
  color: "black",
  backgroundColor: '#FFC242',
  fontFamily: "Arial",
  width:"100%",
  height:'50px',
  padding:0,
  margin:0,
}
