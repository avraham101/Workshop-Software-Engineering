import React, {Component} from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Button from "../../Component/Button";
import Row from "../../Component/Row";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class EditManagerPermissions extends Component {

    constructor(props) {
        super(props);
        this.handleManager = this.handleManager.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCheckboxChange = this.handleCheckboxChange.bind(this);
        this.getManagersAppointedByMe = this.getManagersAppointedByMe.bind(this);
        this.getManagersPromise = this.getManagersPromise.bind(this);
        this.addOrRemovePermissionsPromise = this.addOrRemovePermissionsPromise.bind(this);

        this.pathname = "/editManagerPermissions";
        this.state = {
            managers: [],
            showManagers: false,
            manager: "",
            permissions: [],
            removePermission: false
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

    handleSubmit() {
        let id = this.props.location.state.id;
        let store = this.props.location.state.storeName;
        let managerData = {userName: this.state.manager , storeName: store, permissions: this.state.permissions}
        let url = this.state.removePermission ? '/managers/permissions/delete' : '/managers/permissions'
        console.log("in submit")
        send(url+'?id='+id, 'POST', managerData, this.addOrRemovePermissionsPromise)
    }

    addOrRemovePermissionsPromise(received){
        console.log("here");
        if(received==null)
            alert("Server Failed");
        else {
            console.log(received)
            let opt = ""+ received.reason;
            if(opt === 'Success') {
                if(received.value) {
                    this.state.removePermission ? alert("Remove Permissions successfully!") :
                        alert("Added Permissions successfully!")
                }
            }
            else if( opt === 'Not_Found')
                alert("Permissions Not Found to Remove")
            else if (opt === 'Invalid_Permissions')
                alert("Inserted Invalid Permissions")
            else if (opt === 'Dont_Have_Permission')
                alert("You don't have permission to add these permissions")
            else if (opt === 'Already_Exists')
                alert("User already have all these permissions")
            else if (opt === "User_Not_Found")
                alert("User Not Found");
            else if (opt === "Store_Not_Found")
                alert("User Not Found");
        }
        this.setState({
            showManagers: false,
            permissions : [],
            removePermission: false
        });
    }

    handleCancel(event) {
        event.preventDefault();
        this.setState({
            showManagers: false,
            permissions : [],
            removePermission: false
        })
    }


    handleCheckboxChange(event, value){
        let checked = event.target.checked;
        let filteredPermissions = this.state.permissions.filter(perm => perm !== value);
        if(checked) {
            filteredPermissions.push(value);
            console.log(filteredPermissions);
        }
        this.setState({permissions: filteredPermissions});
    }


    render_managers_table() {
        let managers = this.state.managers;
        let output = [];
        managers.forEach(
          (element) =>
            output.push(
              <Row onClick={(e) => this.handleManager(e, element.name)}>
                <th>{element.name}</th>
              </Row>
            ),
        );
        return output;
    }
    
    render_manager() {
        return (
          <table style={style_table}>
            <tr>
              <th style={under_line}> store managers: </th>
            </tr>
            {this.render_managers_table()}
          </table>
        );
    }
    
    render_Permissions() {
        return (
            <body>
               <table style={style_table} >
                <tr>
                    <h2> add/remove permissions to {this.state.manager}: </h2>
                </tr>
                   <tr>
                       <label>
                           <Checkbox
                               onChange={(e)=> {
                                   this.setState({removePermission: e.target.checked});
                               }}
                           />
                           <span>Check in order to remove permission</span>
                       </label>
                   </tr>
            </table> 
            <div>
                <table style={style_table}>
                    <tr>
                        <th>
                            <label>
                                <Checkbox
                                    onChange={(e)=> this.handleCheckboxChange(e,"PRODUCTS_INVENTORY")}
                                />
                            <span>Inventory management</span>
                            </label>
                        </th>
                        <th>
                            <label>
                                <Checkbox
                                    onChange={(e)=> this.handleCheckboxChange(e,"ADD_MANAGER")}
                                />
                            <span>Add/Delete manager</span>
                            </label>
                        </th>
                        <th>
                            <label>
                                <Checkbox
                                    onChange={(e)=> this.handleCheckboxChange(e,"ADD_OWNER")}
                                />
                            <span>Add/Delete owner</span>
                            </label>
                        </th>
                        <th>
                            <label>
                                <Checkbox
                                    onChange={(e)=> this.handleCheckboxChange(e,"CRUD_POLICY_DISCOUNT")}
                                />
                            <span>Policy management</span>
                            </label>
                        </th>
                    </tr>
                </table>
            </div>    
            </body>
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
                    <Title title="Edit permissions to manager from store:"/>
                    {this.state.showManagers === false ? this.render_manager() : ""}
                    {this.state.showManagers === false ? <Button text="Back" onClick={onBack}/> : ""}
                    {this.state.showManagers === true ? this.render_Permissions() : ""}
                    {this.state.showManagers === true ? <Button text="Add" onClick={this.handleSubmit}/> : ""}
                    {this.state.showManagers === true ? <Button text="Back" onClick={this.handleCancel}/> : ""}
                </div>
            </BackGroud>
        );
    }
}


export default EditManagerPermissions;

const style_table = {
    width: "99%",
    textAlign: "center",
    border: "2px solid black",
};
  
const under_line = {
    borderBottom: "2px solid black",
};

const Checkbox = props => (
    <input type="checkbox" {...props} />
)