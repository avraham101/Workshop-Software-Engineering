import React, {Component} from 'react';
import BackGroud from '../../../Component/BackGrond';
import Menu from '../../../Component/Menu';
import Title from '../../../Component/Title';
import Input from '../../../Component/Input';
import Button from '../../../Component/Button';
import {send} from '../../../Handler/ConnectionHandler';

const FormData = require('form-data')
const https = require('https');

class InitSystem extends Component {

  constructor() {
    super();
    this.state = {
      id: 100,
      name: '',
      password: '',
      error:'',
    };
    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangePassword = this.handleChangePassword.bind(this);
    this.handleConnect = this.handleConnect.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleReceived = this.handleReceived.bind(this);
    this.handleGetId = this.handleGetId.bind(this);
  }

  handleConnect(){
    send('/home/connect','GET','',this.handleGetId)
  }

  handleGetId(received){
    if(received ==null)
      this.setState({error:'Connection unstable'});
    else {
      //let id = received.value;
      this.setState({id:received});
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
    send('/admin', 'POST', msg, this.handleReceived)
  };

  handleReceived(buffer){
    let value = buffer.value;
    if(value)
      this.handleConnect();
  };


  render() {
    return (
      <BackGroud>
        <Menu/>
        <Title title = 'Register Admin'/>
        <div>
          <p>{this.state.id}</p>
          <Input title = 'User Name:' type="text" value={this.state.name} onChange={this.handleChangeName} />
          <Input title = 'Password:' type="text" value={this.state.password} onChange={this.handleChangePassword} />
          <Button text = 'Submit' onClick={this.handleSubmit}/>
        </div>
      </BackGroud>
    );
  }
}

export default InitSystem;