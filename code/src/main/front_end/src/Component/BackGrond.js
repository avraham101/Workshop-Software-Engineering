import React, {Component} from 'react';

class BackGrond extends Component {
  
  constructor(){
    super()
    this.state = {
    }
  }

  render() {
    return (
      <div style={{  backgroundColor: "#F9F9F9", padding: 1, width: '100%', height:this.props.height + 200}}>
        {this.props.children}
      </div>
      );
  }
}

export default BackGrond;
//
// const style_sheet = {
//
// }
