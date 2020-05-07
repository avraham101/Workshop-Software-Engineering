import React, {Component} from 'react';
import history from '../Screen/history'
import {pass} from '../Utils/Utils'
import MenuSubscribe from './MenuSubscribe';

const WHITE_BLUE= '#B3D1F0'
class Notifications extends Component {
  
  constructor(props){
    super(props)
    this.fromPath = this.props.fromPath
    this.state = {
      counter:0,
    }
  }

  render() {
    return (
      <div style={{width:'100%',backgroundColor:'#72CC87',margin:0}}>
        <h4 style={{textAlign:'right',margin:0, paddingRight:10}}> Notifications: 
            {this.state.counter} </h4>
        {/*<p>{(this.props.state!=undefined)?this.props.state.id:''}</p>*/}
      </div>
    );
  }
}

export default Notifications;