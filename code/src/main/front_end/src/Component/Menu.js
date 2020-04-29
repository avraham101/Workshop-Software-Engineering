import React, {Component} from 'react';
import history from '../Screen/history'

const WHITE_BLUE= '#B3D1F0'
class Menu extends Component {
  
  constructor(){
    super()
    this.state = {
      color_1: '',
      color_2: '',
      color_3: '',
      color_4: '',
      color_5: '',
    }
  }

  render() {
    return (
        <header>
        <table style={style_sheet}>
          <tr>
            <th style={{background:this.state.color_1}} 
                onMouseOver={()=>this.setState({color_1: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_1: ''})}> Watch Store and Products </th>
            <th style={{background:this.state.color_2}} 
                onMouseOver={()=>this.setState({color_2: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_2: ''})}> Search and Filter Products </th>
            <th onClick={()=>history.push('/register')} 
                style={{background:this.state.color_3}} 
                onMouseOver={()=>this.setState({color_3: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_3: ''})}> Register </th>
            <th style={{background:this.state.color_4}} 
                onMouseOver={()=>this.setState({color_4: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_4: ''})}> Login </th>
            <th style={{background:this.state.color_5}} 
                onMouseOver={()=>this.setState({color_5: WHITE_BLUE})}
                onMouseLeave={()=>this.setState({color_5: ''})}> Watch My Cart </th>
          </tr>
        </table>
        </header>
    );
  }
}

export default Menu;

const style_sheet = {
  color: "black",
  backgroundColor: "#4DA0F3",
  padding: "15px",
  fontFamily: "Arial",
  width:"99%",
}
