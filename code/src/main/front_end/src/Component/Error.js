import React, {Component} from 'react';

const RED = '#FF0000';
const GREEN = '#00FF00'

class Error extends Component {

    constructor(){
        super()
        this.state = {
          color_1: '',
          color_2: '',
        }
      }

    render() {
        return (
            <table style={style_sheet}>
                <tr>
                    <th style={{background:this.state.color_1}} 
                        onMouseOver={()=>this.setState({color_1: GREEN})}
                        onMouseLeave={()=>this.setState({color_1: ''})}
                        onClick={this.props.yes}> Yes 
                    </th>
                    <th style={{background:this.state.color_2}} 
                        onMouseOver={()=>this.setState({color_2: RED})}
                        onMouseLeave={()=>this.setState({color_2: ''})}
                        onClick={this.props.no}> No
                    </th>
                </tr>
            </table>
        );
    }

}

export default Error;

const style_sheet = {
  color: "black",
  backgroundColor: "#4DA0F3",
  padding: "15px",
  fontFamily: "Arial",
  width:"99%",
}