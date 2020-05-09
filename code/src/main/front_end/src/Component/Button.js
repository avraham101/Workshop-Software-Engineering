import React, {Component} from 'react';

class Button extends Component {
  constructor() {
    super();
    this.state = {
      color:'#4287FF'
    } 
  }
    
  render() {
    return (
      <div style={{ textAlign:'center', margin:10}}>
        <button style={{  textAlign: 'center', color: "black", backgroundColor: this.state.color, 
            padding: "10px", fontFamily: "Arial", fontSize: 14, border:'1px solid black',}} 
          onMouseDown={(event)=>{this.setState({color:'white'})}}
          onMouseUp={(event)=>{this.setState({color:'#4287FF'})}}
          onClick={this.props.onClick}> {this.props.text} </button>
      </div>
      );
  }
}

export default Button;

const style_sheet = {
  textAlign: 'center',
  color: "black",
  backgroundColor: '#4287FF',
  padding: "10px",
  fontFamily: "Arial",
  fontSize: 14,
  border:'1px solid black',
}
