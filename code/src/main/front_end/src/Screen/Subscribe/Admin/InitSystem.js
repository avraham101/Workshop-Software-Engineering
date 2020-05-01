import React, {Component} from 'react';
import BackGroud from '../../../Component/BackGrond';
import Menu from '../../../Component/Menu';
import Title from '../../../Component/Title';
import Input from '../../../Component/Input';
import Button from '../../../Component/Button';
//import {send} from '../../../Handler/ConnectionHandler';
import { connect } from '../../../Handler/ConnectionHandler';
import { sendMessage } from '../../../Handler/ConnectionHandler';
//const FormData = require('form-data')
//const https = require('https');

class InitSystem extends Component {

  constructor() {
    super();
    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangePassword = this.handleChangePassword.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.state = {
      name: '',
      password: '',
    };
  }

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
    let msg = {name:name, password:password};
    //RECIVED RESPONSE AS MSG
    connect((msg)=>this.setState({msg:''+msg.value}));
    //send(msg, (msg)=>this.setState({msg:''+msg.value}));
    //sendMessage();
  }

  render() {
    return (
      <BackGroud>
        <Menu/>
        <Title title = 'Register Admin'/>
        <div>
          <p> data {this.state.data} </p>
          <p> msg {this.state.msg} </p>
          <Input title = 'User Name:' type="text" value={this.state.name} onChange={this.handleChangeName} />
          <Input title = 'Password:' type="text" value={this.state.password} onChange={this.handleChangePassword} />
          <Button text = 'Submit' onClick={this.handleSubmit}/>
        </div>
      </BackGroud>
    );
  }
}

export default InitSystem;