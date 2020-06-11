import React, {Component} from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'
import DivBetter from "../../Component/DivBetter";

class ApproveOwnerToStore extends Component {
    constructor() {
        super();
        this.renderWaitingOwners = this.renderWaitingOwners.bind(this);
        this.sendApproved = this.sendApproved.bind(this);
        this.promiseApproved = this.promiseApproved.bind(this);
        this.getOwners = this.getOwners.bind(this);
        this.onBack = this.onBack.bind(this);
        this.pathname = "/approveOwnerToStore";
        this.url = '/managers/approve/owner?id=';
        this.state = {
            owners: undefined,
        };
    }
    
    onBack(){
        pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state);
    }

    getOwners() {
        let promiseOwnersList = (received) => {
            if(received==null) {
                alert('Server Crashed');
            }
            else {
                let opcode = ''+received.reason;
                if(opcode === 'Store_Not_Found') {
                    alert('Store Not Found');
                    pass(this.props.history,'/storeManagement',this.pathname, this.props.location.state)
                }
                else if(opcode ==='Success') {
                    this.setState({owners:received.value})
                }
                else {
                    alert('Error Opcode '+opcode);
                }
            }
        }
        let id = this.props.location.state.id;
        let store = this.props.location.state.storeName;
        send('/managers/approve/list?id='+id, 'POST', store, promiseOwnersList);
    }

    promiseApproved(received){
        if(received==null) {
            alert('Server Crashed');
        }
        else {
            let opcode = ''+received.reason;
            if(opcode === 'User_Not_Found') {
                alert('User Not Found');
                this.getOwners();
            }
            else if(opcode === 'Store_Not_Found') {
                alert('Store Not Found');
                pass(this.props.history,'/storeManagement',this.pathname, this.props.location.state)
            }
            else if(opcode === 'Dont_Have_Permission') {
                alert('User dont have premeissions.');
                pass(this.props.history,'/storeManagement',this.pathname, this.props.location.state)    
            }
            else if(opcode === 'Success') {
                alert('Aggremment Approved');
                this.getOwners();
            }
            else {
                alert('Error Opcode '+opcode);
            }
        }
    }
    
    sendApproved(name) {
        let id = this.props.location.state.id;
        let store = this.props.location.state.storeName;
        send('/managers/approve/owner?id='+id+'&store='+store+'&user='+name, 'POST', '', this.promiseApproved);
    }

    renderWaitingOwners() {
        let output = [];
        let list = this.state.owners;
        if(list===undefined || list.length === 0)
            return <p style={{textAlign:'center'}}> No Waiting Approvels </p>;
        list.forEach(element=>{
            let onHover = (event) => {
                event.currentTarget.style.backgroundColor = '#92BAFF'
            }
            let onLeave = (event) => {
                event.currentTarget.style.backgroundColor = ''
            }
            output.push(
                <div style={{float:'left' , border:'1px solid black', marginLeft:'30%', width:'40%'}}
                    onMouseOver={onHover} onMouseLeave={onLeave}>
                    <div style={{float:'left', width:'60%'}}
                        onMouseOver={()=>{}} onMouseLeave={()=>{}}>
                        <p style={{textAlign:'center'}}> User Name: {element}</p>
                    </div>
                    <div style={{float:'left', width:'40%'}}
                        onMouseOver={()=>{}} onMouseLeave={()=>{}}>
                        <Button text="Appointment" onClick={(e)=>{this.sendApproved(element)}}/>
                    </div>
                </div>      
            )
        })
        
        return output;
    }   

    render() {    
        if(this.state.owners===undefined)
            this.getOwners();    
        return (
            <BackGrond>
                <Menu state={this.props.location.state} />
                <Title title={"Approve Owners - " + this.props.location.state.storeName}></Title>
                {this.renderWaitingOwners()}
                <div style={{float:'left' , width:'100%'}}>
                    <Button text="Back" onClick={this.onBack}/>
                </div>
            </BackGrond>
        );
    }
}
export default ApproveOwnerToStore;

const style_sheet = {
    textAlign: 'center',
    color: "black",
    backgroundColor: '#F3F3F3',
    padding: "10px",
    fontFamily: "Arial",
    width:"99%",
    marginTop:0,
}