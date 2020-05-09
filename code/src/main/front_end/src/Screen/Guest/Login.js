import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Input from '../../Component/Input';
import Button from '../../Component/Button';
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class Login extends Component {

  constructor() {
    super();
    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangePassword = this.handleChangePassword.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.state = {
      name: '',
      password: '',
    }
  }

  handleChangeName(event) {
    this.setState({name: event.target.value});
  }

  handleChangePassword(event) {
    /* hidden password */
    this.setState({password: event.target.value});
  }

  handleSubmit() {
    //this.setState({server_error:'',valid_error:'',user_error:''});
    let promise = (received) => {
      if(received === null) {
        alert("Server Crashed")
        //this.setState({server_error: 'server crashed'});
      }
      else {
        let opt = '' + received.reason;

        if(opt ==='Success'){
          let state = this.props.location.state;
          state['name']= this.state.name;
          state['logged']=true;
          pass(this.props.history,'/subscribe',this.pathname, state)
        }
        else {
          if('Invalid_Login_Details‏'.startsWith(opt,0)) {
            this.setState({valid_error: "Invalid Name or Password"});
          }
          else if ("User_Not_Found‏".startsWith(opt,0)) {
            this.setState({valid_error: "User Not Found"});
          }
        }
      }
    }
    let id = this.props.location.state.id;
    let userData = {name:this.state.name, password:this.state.password};
    send('/home/login?id='+id,"POST",userData,promise)
  }

  render() {
    return (
        <BackGroud>
          <Menu state={this.props.location.state}/>
          <Title title = 'Want to Login?'/>
          <div>
            <Input title = 'User Name:' error={this.state.valid_error !== undefined? "": this.state.valid_error } type="text" value={this.state.name} onChange={this.handleChangeName} />
            <Input title = 'Password:' error={this.state.valid_error} type="password" value={this.state.password} onChange={this.handleChangePassword} />
            <Button text = 'Submit' onClick={this.handleSubmit}/>
            <Button text="cancel" onClick={() => pass(this.props.history,'/',this.pathname, this.props.location.state)  } />

          </div>
        </BackGroud>
    );
  }
}

export default Login;
