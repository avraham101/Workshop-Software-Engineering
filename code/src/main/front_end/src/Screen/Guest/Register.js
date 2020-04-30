import React, { Component } from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";

class Register extends Component {
  constructor() {
    super();
    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangePassword = this.handleChangePassword.bind(this);
    this.state = {
      name: "",
      password: "",
    };
  }

  handleChangeName(event) {
    this.setState({ name: event.target.value });
  }

  handleChangePassword(event) {
    /* hidden password */
    this.setState({ password: event.target.value });
  }

  handleSubmit(event) {
    event.preventDefault();
  }

  render() {
    return (
      <BackGroud>
        <Menu />
        <Title title="Want to Register?" />
        <div>
          <Input
            title="User Name:"
            type="text"
            value={this.state.name}
            onChange={this.handleChangeName}
          />
          <Input
            title="Password:"
            type="text"
            value={this.state.password}
            onChange={this.handleChangePassword}
          />
          <Button text="Submit" onClick={this.handleSubmit} />
        </div>
      </BackGroud>
    );
  }
}

export default Register;
