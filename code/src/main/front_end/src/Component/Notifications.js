import React, {Component} from 'react';
import {connect} from '../Handler/WebSocketHandler'

export var flag_connected=false;
class Notifications extends Component {
  
  constructor(props){
    super(props)
    this.state = {
      notification:[],  
      counter:0,
    }
  }

  handleNotification(notification) {
    this.state.notification.push(notification);
    this.setState({counter:this.state.notification.length});
  }

  connectHandler(id) {
    if(flag_connected===false) {
      connect(''+id,this.handleNotification);
      flag_connected=true;
    }
  }

  render() {
    this.connectHandler(this.props.id);
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