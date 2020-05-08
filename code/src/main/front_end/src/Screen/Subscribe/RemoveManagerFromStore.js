import React, { Component } from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Button from "../../Component/Button";
import history from "../history";
import Row from "../../Component/Row";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'


class RemoveManagerFromStore extends Component {

    constructor(props) {
        super(props);
        this.handleManager = this.handleManager.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.getManagersAppointedByMe = this.getManagersAppointedByMe.bind(this);
        this.getManagersPromise = this.getManagersPromise.bind(this);
        this.render_managers_table = this.render_managers_table.bind(this);
        this.render_manager = this.render_manager.bind(this);
        this.renderManagers = this.renderManagers.bind(this);
        this.deleteManagerPromise = this.deleteManagerPromise.bind(this);


        this.pathname = "/removeManagerFromStore";
        this.state = {
            managers: '',
            showManagers: false,
        };
        this.getManagersAppointedByMe();
    }


    getManagersAppointedByMe(){
        let id = this.props.location.state.id;
        let store = this.props.location.state.storeName;
        send('/home/managers/'+store+'?id='+id, 'GET', '', this.getManagersPromise)
    }

    getManagersPromise(received){
        if(received==null)
            alert("Server Failed");
        else {
            console.log(received)
            let opt = ""+ received.reason;
            if(opt === 'Success') {
                let names = received.value;
                let newManagers = [];
                names.forEach(
                    (name) =>{
                        newManagers.push({name: name});
                    }
                );
                this.setState({managers: newManagers });
            }
        }
    }

    handleManager(event, manager) {
        this.setState({
          name: event.target.value,
          showManagers: true,
          manager: manager,
        });
    }

    handleSubmit(event) {
        this.setState({
            showManagers: false,
        });
        let id = this.props.location.state.id;
        let store = this.props.location.state.storeName;
        let managerData = {userName: this.state.manager , storeName: store}
        send('/managers/deleteManager/?id='+id, 'POST', managerData, this.deleteManagerPromise)
    }

    deleteManagerPromise(received){
        if(received==null)
            alert("Server Failed");
        else {
            console.log(received)
            let opt = ""+ received.reason;
            if(opt === 'Success') {
                if(received.value) {
                    alert("Deleted Manager successfully!");
                    this.getManagersAppointedByMe();
                }
                else
                    alert("Cannot delete Manager");
            }
            else if (opt === "User_Not_Found")
                alert("User Not Found");
            else if (opt === "Store_Not_Found")
                alert("User Not Found");
        }
    }

    handleCancel(event) {
        event.preventDefault();
        this.setState({
            showManagers: false,
        })
    }

    
    render_managers_table() {
        let managers = this.state.managers;
        let output = [];
        if(managers) {
            managers.forEach(
                (element) =>
                    output.push(
                        <Row onClick={(e) => this.handleManager(e, element.name)}>
                            <th>{element.name}</th>
                        </Row>
                    ),
            );
        }
        return output;
    }
    
    render_manager() {
        return (
          <table style={style_table}>
            <tr>
              <th style={under_line}> manager: </th>
            </tr>
            {this.render_managers_table()}
          </table>
        );
    }
    
    renderManagers() {
        return (
           <table style={style_table} >
               <tr>
                    <h3> remove {this.state.manager}? </h3>
               </tr>
           </table> 
        );
    }

    render() {
      let onBack= () => {
        pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
      }
      return(
          <BackGroud>
              <Menu state={this.props.location.state} />
              <div>
                  <Title title="Delete manager from store:"/>
                  {this.state.showManagers === false ? this.render_manager() : ""}
                  {this.state.showManagers === false ? <Button text="Back" onClick={onBack}/> : ""}
                  {this.state.showManagers === true ? this.renderManagers() : ""}
                  {this.state.showManagers === true ? <Button text="Remove" onClick={this.handleSubmit}/> : ""}
                  {this.state.showManagers === true ? <Button text="Back" onClick={this.handleCancel}/> : ""}
              </div>
          </BackGroud>
      );
    }
}


export default RemoveManagerFromStore;

const style_table = {
    width: "99%",
    textAlign: "center",
    border: "2px solid black",
};
  
const under_line = {
    borderBottom: "2px solid black",
};
  
  
