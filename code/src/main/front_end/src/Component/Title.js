import React, {Component} from 'react';

class Menu extends Component {
  
  render() {
    return (
      <h2 style={style_sheet}>
        {this.props.title}
      </h2>
      );
  }
}

export default Menu;

const style_sheet = {
  textAlign: 'center',
  color: "black",
  backgroundColor: '#F3F3F3',
  padding: "10px",
  fontFamily: "Arial",
  width:"99%",
  marginTop:0,
}
