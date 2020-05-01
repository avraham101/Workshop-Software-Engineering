import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";

class AddOwnerToStore extends Component {
    constructor() {
        super();
        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            name:''
        };
    }

    handleChangeName(event){
        this.setState({name: event.target.value});
    }

    handleSubmit(event){
        const { storeName } = this.props.location;
        alert("The user : "+this.state.name+" added successfully\n To be the "+this.getPermission()+" of the store :"+storeName)
        event.preventDefault();
    }

    render() {
        const { storeName } = this.props.location;
        return (
            <BackGrond>
                <Menu />
                <Title title={"Add Manager To Store -" + storeName}></Title>
                <h4 style={style_sheet}>Enter the name of the user you want to add, then press submit</h4>
                <Input title = 'User Name:' type="text" value={this.state.name} onChange={this.handleChangeName} />
                <Button text = 'Submit' onClick={this.handleSubmit}/>
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