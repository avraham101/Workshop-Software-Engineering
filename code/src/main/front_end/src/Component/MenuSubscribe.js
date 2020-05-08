import React, {Component} from 'react';
import Notifications from '../Component/Notifications';
import history from '../Screen/history'
import {pass} from '../Utils/Utils'
import { send } from '../Handler/ConnectionHandler';
const WHITE_BLUE= '#B3D1F0'
class MenuSubscribe extends Component {
  
  constructor(props){
    super(props)
    this.fromPath = this.props.fromPath;
    this.prevState = this.props.state;
    this.refresh = this.refresh.bind(this);
    this.state = {
      color_0: '',
      color_1: '',
      color_2: '',
      color_3: '',
      color_4: '',
      color_5: '',
      color_6: '',
      color_7: '',
      color_8: '',
    }
  }

  logout() {
    let id = this.props.state.id;
    send('/home/logout?id='+id,"POST",'',()=>{});
    pass(history,"/",this.fromPath,{id:id});
  }

  refresh() {
    this.setState({});
  }

  render() {
    return (
        <header style={{backgroundColor: '#FFC242', borderBottom:'2px solid #B38118',marginBottom:0}}>
          {/*<p> {(this.props.state!=undefined)?this.props.state.id:''} </p>*/}
          <table style={style_sheet}>
          <tr>
            <th onClick={() => pass(history,"/subscribe",this.fromPath,this.props.state)}
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
            <th onClick={()=> pass(history,"/openStore",this.fromPath,this.props.state)}
                style={{background:this.state.color_3}}
                onMouseOver={()=>this.setState({color_3: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_3: ''})}> Open Store </th>
            <th onClick={()=> pass(history,"/sendRequest",this.fromPath,this.props.state)}
                style={{background:this.state.color_4}}
                onMouseOver={()=>this.setState({color_4: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_4: ''})}> Send Request </th>
            <th onClick={()=> pass(history,"/userWatchPurchasesHistory",this.fromPath,this.props.state)}
                  style={{background:this.state.color_5}}
                  onMouseOver={()=>this.setState({color_5: WHITE_BLUE})}
                  onMouseLeave={()=>this.setState({color_5: ''})}> Purchases </th>
            <th onClick={()=> pass(history,"/storeManagement",this.fromPath,this.props.state)}
                  style={{background:this.state.color_6}}
                  onMouseOver={()=>this.setState({color_6: WHITE_BLUE})}
                  onMouseLeave={()=>this.setState({color_6: ''})}> Manage Stores </th>
            <th onClick={()=>this.logout()} 
                style={{background:this.state.color_7}} 
                onMouseOver={()=>this.setState({color_7: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_7: ''})}> Logout </th>
            <th onClick={()=>pass(history,"/viewMyCart",this.fromPath,this.props.state)}
                style={{background:this.state.color_8}} 
                onMouseOver={()=>this.setState({color_8: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_8: ''})}> Watch My Cart </th>
          </tr>
        </table>
        </header>
    );
  }
}

export default MenuSubscribe;

const style_sheet = {
  color: "black",
  backgroundColor: '#FFC242',
  fontFamily: "Arial",
  width:"99%",
  height:'50px',
}
