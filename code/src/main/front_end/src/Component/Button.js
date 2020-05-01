import React, {Component} from 'react';

class Button extends Component {
  
  render() {
    return (
      <div style={{ textAlign:'center', margin:10}}>
        <button style={style_sheet} onClick={this.props.onClick}> {this.props.text} </button>
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
  fontFamily: "David",
  fontSize: 16,
}
