import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";

class OpenStore extends Component {
  constructor() {
    super();
    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangeDescription = this.handleChangeDescription.bind(this);
    this.state = {
      storeName: "",
      storeDescription: "",
    };
  }

  handleChangeName(event) {
    this.setState({ storeName: event.target.value });
  }

  handleChangeDescription(event) {
    this.setState({ storeDescription: event.target.value });
  }

  handleOpen(event) {
    event.preventDefault();
    var str = "Congratulation! The store " + this.state.storeName + " open.\nYou are the owner, enjoy!";
    if(window.confirm(str)){
      this.pass("/manageProducts", this.state.storeName)
    }
  }

  pass(url, storeName) {
    this.props.history.push({
      pathname: url,
      fromPath: "/storeMenu",
      storeName: storeName
    });
  }

  render() {
    return (
      <BackGrond>
        <Menu />
        <Title title="Open Store" />
        <div>
          <Input
            title="Store Name: "
            type="text"
            value={this.state.storeName}
            onChange={this.handleChangeName}
          ></Input>
          <InputBig
            title="Description: "
            type="text"
            value={this.state.storeDescription}
            onChange={this.handleChangeDescription}
          ></InputBig>
          <Button text="Open" onClick={(e) => this.handleOpen(e)}></Button>
        </div>
      </BackGrond>
    );
  }
}
export default OpenStore;

class InputBig extends Input {
  render() {
    return (
      <div style={{ marginLeft: "30%", marginRight: "30%", padding: 15 }}>
        <div style={{ textAlign: "center" }}>
          <label style={{ marginLeft: 5, marginRight: 12 }}>
            {" "}
            {this.props.title}{" "}
          </label>
        </div>
        <div style={{ textAlign: "center" }}>
          <input
            type={this.props.type}
            value={this.props.value}
            onChange={this.props.onChange}
            size={40}
          />
        </div>
      </div>
    );
  }
}
