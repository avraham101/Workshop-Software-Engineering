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
        this.promiseAppointOwner = this.promiseAppointOwner.bind(this);
        this.onBack = this.onBack.bind(this);
        this.pathname = "/managers/addOwnerToStore";
        this.url = '/owner?id=';
        this.state = {
            name:''
        };
    }

    handleChangeName(event){
        this.setState({name: event.target.value});
    }

    handleSubmit(event){
        let id = this.props.location.state.id;
        let storeName = this.props.location.state.id;
        let managerData = {
            storeName: storeName,
            userName: this.state.name,
        }
        send(this.url+id, 'POST', managerData, this.promiseAppointOwner);
    }

    promiseAppointOwner(received){
        console.log(received);
        if(received==null) {
            alert('Server Crashed')
        }
        else {
            let opcode = ''+received.reason;
            if(opcode === 'Success' && received.value) {
                alert("The user : "+this.state.name+" added successfully\n To be the "+this.getPermission()+" of the store :"+this.props.location.state.storeName)

            }
            else {
                alert(`Cant Appoint ${this.getPermission()}!\n Please try again.`)
            }
        }
    }

    onBack(){
        pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state);
    }

    render() {        

        return (
            <BackGrond>
                <Menu state={this.props.location.state} />
                <Title title={"Add Manager To Store - " + this.props.location.state.storeName}></Title>
                <h4 style={style_sheet}>Enter the name of the user you want to add, then press submit</h4>
                <Input title = 'User Name:' type="text" value={this.state.name} onChange={this.handleChangeName} />
                <Button text = 'Submit' onClick={this.handleSubmit}/>
                <Button text="Back" onClick={this.onBack}/>
            </BackGrond>
        );
    }
    getPermission(){ return "Owner"; }
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