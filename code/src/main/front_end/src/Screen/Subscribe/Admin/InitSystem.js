import React, {Component} from 'react';
import BackGroud from '../../../Component/BackGrond';
import Menu from '../../../Component/Menu';
import Title from '../../../Component/Title';
import Input from '../../../Component/Input';
import Button from '../../../Component/Button';
const FormData = require('form-data')
const https = require('https');

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

  send(msg) {
    let url = 'https://localhost:8443/admin';
    let options = {
      hostname: 'localhost',
      port: 8443,
      path: '/admin',
      method: 'POST',
      headers: { 
      'Accept-Encoding':'gzip, deflate, br',
      'Content-Length': msg.length,},
    };
    
    let req = https.request(options, (res) => {
      res.on('data', (d) => { this.setState({data:this.recive(d)});});
      res.on('close', (c)=> { this.setState({close:c})})
      res.on('error', (e) => { this.setState({error:e}) });
    
    });
    req.on('error',(r)=>{ this.setState({error:r.stack}) })
    //req.on('response',(r)=>{r.on('data',body=>this.recive(body))})
    req.on('close',(r)=>{ this.setState({close:r}) })
    req.write(JSON.stringify(msg)+'\n')
    req.end();
  }


//The function send the msg to the server
  sendPost =  async (msg) => {
    let url = 'https://localhost:8443/admin';
    let result = JSON.stringify(msg);
    let response = await fetch(url, {
      method:'POST',
      headers: {  
        'Accept-Encoding':'gzip, deflate, br',
        'Content-Length': result.length,
      },
      body: result+'\n'
    })
    .then( res => res.text())
    .then( d=>{ this.recive(d); })
    .catch( err => { this.setState({err_name:err,err_message:err.message});})};

  //The function recive text from the server and execute the proper Msg
  recive(buffer) {
    let out = Object.getOwnPropertyNames(buffer)
    //out = Object.getOwnPropertyNames(out);
    //out= buffer.toString('utf8')
    return JSON.parse(buffer);
  }

  handleSubmit() {
    let name = this.state.name;
    let password = this.state.password;
    let msg = {name:name, password:password};
    this.send(msg);
    this.setState({msg:msg}) 
  }

  render() {
    return (
      <BackGroud>
        <Menu/>
        <Title title = 'Register Admin'/>
        <div>
          <p> {this.state.data} </p>
          <Input title = 'User Name:' type="text" value={this.state.name} onChange={this.handleChangeName} />
          <Input title = 'Password:' type="text" value={this.state.password} onChange={this.handleChangePassword} />
          <Button text = 'Submit' onClick={this.handleSubmit}/>
        </div>
      </BackGroud>
    );
  }
}

export default InitSystem;