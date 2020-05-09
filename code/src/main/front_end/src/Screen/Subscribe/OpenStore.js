import React, {Component} from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class OpenStore extends Component {
  constructor() {
    super();
    this.handleChangeName = this.handleChangeName.bind(this);
    this.handleChangeDescription = this.handleChangeDescription.bind(this);
    this.storeOpenPromise = this.storeOpenPromise.bind(this);
    this.pathname = "/openStore";
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
    let msg = {name:this.state.storeName, description:this.state.storeDescription};
    let id = this.props.location.state.id;
    send('/store?id='+id,'POST',msg,this.storeOpenPromise)
  }

  storeOpenPromise(received) {
    if(received==null)
      alert("Server Failed");
    else {
      let opt = ''+ received.reason;
      if (opt == "Invalid_Store_Details") {
        alert("Invalid Store Details");
      } 
      else if(opt == 'Store_Not_Found') {
        alert("Store Already Exites");
      }
      else if(opt =="Store_Doesnt_Exist‏") {
        alert("Server Broken");
      }
      else if(opt == 'Success') {
        let str = "Congratulation! The store " + this.state.storeName 
                  + " open.\nYou are the owner, enjoy!";
        alert(str);
        let state = this.props.location.state;
        state['storeName'] = this.state.storeName;
        pass(this.props.history,'/manageProducts',this.pathname,state)
      }
      else {
        alert(opt+", Cant Open Store");
      }
    }
  }

  render() {
    let onBack= () => {
      pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
    }
    return (
      <BackGrond>
        <Menu state={this.props.location.state} />
        <Title title="Open Store" />
        <div>
          <Input title="Store Name:" type="text"
            value={this.state.storeName}
            onChange={this.handleChangeName}/>
          <InputBig title="Description: " type="text"
            value={this.state.storeDescription}
            onChange={this.handleChangeDescription}/>
          <Button text="Open" onClick={(e) => this.handleOpen(e)}></Button>
          <Button text="Back" onClick={onBack}/>
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
