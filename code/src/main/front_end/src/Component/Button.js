import React, {Component} from 'react';

class Button extends Component {
  
  render() {
    return (
      <button style={style_sheet} onClick={this.props.onClick}> {this.props.text} </button>
      );
  }
}

export default Button;

const style_sheet = {
  textAlign: 'center',
  color: "black",
  backgroundColor: '#F3F3F3',
  padding: "10px",
  fontFamily: "Arial",
}
