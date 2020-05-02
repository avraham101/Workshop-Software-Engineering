import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Title from '../../Component/Title';
import Input from '../../Component/Input';
import Button from '../../Component/Button';
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class Register extends Component {

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
    this.setState({valid_error:''});
    let promise = (received) => {
      if(received === null)
        this.setState({valid_error:'server crashed'});
      else {
        let opt = '' + received.reason;
        if(opt == "Invalid_Register_Details‏")
          this.setState({valid_error:'name or password isnt valid'});
        else if (opt == "User_Name_Already_Exist‏") {
          this.setState({valid_error:'user already exits'});
        }
        else if (opt == "Hash_Fail") {
          this.setState({valid_error:'system error, invalid data'});
        }
        else if(opt ==='Success'){
          let state = this.props.location.state;
          state['name']= this.state.name;
          pass(this.props.history,'/login',this.pathname, state)  
        }
      }
    }
    let id = this.props.location.state.id;
    let userData = {name:this.state.name, password:this.state.password};
    send('/home/register?id='+id,"POST",userData,promise)
    this.setState({click:'true'})
  }

  render() {
    return (
        <BackGroud>
          <Menu state={this.props.location.state}/>
          <Title title = 'Want to Register?'/>
          <div>
            <Input title = 'User Name:' error={this.state.valid_error} type="text" value={this.state.name} onChange={this.handleChangeName} />
            <Input title = 'Password:' error={this.state.valid_error} type="text" value={this.state.password} onChange={this.handleChangePassword} />
            <Button text = 'Submit' onClick={this.handleSubmit}/>
          </div>
        </BackGroud>
    );
  }
}

export default Register;