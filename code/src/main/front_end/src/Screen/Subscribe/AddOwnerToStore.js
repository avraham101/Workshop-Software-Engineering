import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class AddOwnerToStore extends Component {
    constructor() {
        super();
        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.pathname = "/addOwnerToStore";
        this.state = {
            name:''
        };
    }

    handleChangeName(event){
        this.setState({name: event.target.value});
    }

    handleSubmit(event){
        alert("The user : "+this.state.name+" added successfully\n To be the "+this.getPermission()+" of the store :"+this.props.location.state.storeName)
    }

    render() {        
        let onBack= () => {
            pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
        }
        return (
            <BackGrond>
                <Menu state={this.props.location.state} />
                <Title title={"Add Manager To Store -" + this.props.location.state.storeName}></Title>
                <h4 style={style_sheet}>Enter the name of the user you want to add, then press submit</h4>
                <Input title = 'User Name:' type="text" value={this.state.name} onChange={this.handleChangeName} />
                <Button text = 'Submit' onClick={this.handleSubmit}/>
                <Button text="Back" onClick={onBack}/>
            </BackGrond>
        );
    }

    getPermission(){ return "owner"; }
}
export default AddOwnerToStore;

const style_sheet = {
    textAlign: 'center',
    color: "black",
    backgroundColor: '#F3F3F3',
    padding: "10px",
    fontFamily: "Arial",
    width:"99%",
    marginTop:0,
}