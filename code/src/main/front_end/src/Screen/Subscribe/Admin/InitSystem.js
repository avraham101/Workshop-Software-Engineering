import React, {Component} from 'react';
import BackGroud from '../../../Component/BackGrond';
import Menu from '../../../Component/Menu';
import Title from '../../../Component/Title';
import Input from '../../../Component/Input';
import Button from '../../../Component/Button';
import {send} from '../../../Handler/ConnectionHandler';
import {pass} from '../../../Utils/Utils'

const FormData = require('form-data')
const https = require('https');

class InitSystem extends Component {

  constructor(props) {
    super(props);
    this.state = {
      id: -1,
      name: '',
      password: '',
      error: 'Cannot register Admin',
      success: "Registered Admin",
    };


    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangePassword = this.handleChangePassword.bind(this);
    this.handleConnect = this.handleConnect.bind(this);
    this.handleGetId = this.handleGetId.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleReceived = this.handleReceived.bind(this);
  }

  handleConnect(){
    send('/home/connect','GET','',this.handleGetId)
  }

  handleGetId(received){
    let opt = ''+received.reason;
    if(opt !== "Success")
      alert(this.state.error)
    else {
      this.setState({id: received.value});
      alert(this.state.success);
      pass(this.props.history,'/','',{id:this.state.id})
    }
  };

  handleChangeName(event) {
    this.setState({name: event.target.value});
  }

  handleChangePassword(event) {
    /* hidden password */
    this.setState({password: event.target.value});
  }

  handleSubmit() {
    let name = this.state.name;
    let password = this.state.password;
    let msg = {name: name, password: password};
    //RECIVED RESPONSE AS MSG
    send('/admin/init', 'POST', msg, this.handleReceived)
  };

  handleReceived(buffer) {
    if (buffer && buffer.value)
      this.handleConnect();
    else
      alert(this.state.error);
  };


  render() {
    return (
      <BackGroud>
        <Title title = 'Register Admin'/>
        <div>
          <Input title = 'User Name:' type="text" value={this.state.name} onChange={this.handleChangeName} />
          <Input title = 'Password:' type="password" value={this.state.password} onChange={this.handleChangePassword} />
          <Button text = 'Submit' onClick={this.handleSubmit}/>
        </div>
      </BackGroud>
    );
  }
}

export default InitSystem;