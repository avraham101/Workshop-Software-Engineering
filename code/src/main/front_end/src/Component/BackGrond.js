import React, {Component} from 'react';

class BackGrond extends Component {
  
  constructor(){
    super()
    this.state = {
    }
  }

  render() {
    return (
      <div style={style_sheet}>
        {this.props.children}
      </div>
      );
  }
}

export default BackGrond;

const style_sheet = {
  backgroundColor: "#F9F9F9",
  padding: 1,
  width: '100%',
  height: '700px',
}
