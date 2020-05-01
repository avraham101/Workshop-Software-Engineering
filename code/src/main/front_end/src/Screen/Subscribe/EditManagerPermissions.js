import React, { Component } from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Button from "../../Component/Button";
import history from "../history";
import Row from "../../Component/Row";

class EditManagerPermissions extends Component {

    constructor() {
        super();
        this.handleManager = this.handleManager.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            managers: this.create_managers(),
            showManagers: false,
            manager: "",
            permissions: []
        };
    }

    handleManager(event, manager) {
        this.setState({
          name: event.target.value,
          showManagers: true,
          manager: manager,
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        this.setState({
            showManagers: false,
        });
        alert("Permissions edited.");
    }

    handleCancel(event) {
        event.preventDefault();
        this.setState({
            showManagers: false,
        })
    }

    create_managers() {
        let output = [];
        for (let i = 0; i < 5; i++) {
          output.push({
            name: "manager name " + i,
          });
        }
        return output;
    }
    
    getManagerSelected() {
        var manager = document.getElementsByName("manager");
        var manager_value;
        for (var i = 0; i < manager.length; i++) {
          if (manager[i].checked) {
            manager_value = manager[i].value;
          }
          return manager_value;
        }
    }
    
    render_managers_table() {
        let managers = this.state.managers;
        let output = [];
        let i = 0;
        managers.forEach(
          (element) =>
            output.push(
              <Row onClick={(e) => this.handleManager(e, element.name)}>
                <th>{element.name}</th>
              </Row>
            ),
          (i = i + 1)
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
                    <h3> add permissions to {this.state.manager}: </h3>
                </tr>
            </table> 
            <div>
                {/* to do: add the permissons to the manager */}
                <table style={style_table}>
                    <tr>
                        <th>
                            <label>
                                <Checkbox
                                    checked={this.state.checked}
                                    onChange={this.handleCheckboxChange}
                                />
                            <span>Inventory managment</span>
                            </label>
                        </th>
                        <th>
                            <label>
                                <Checkbox
                                    checked={this.state.checked}
                                    onChange={this.handleCheckboxChange}
                                />
                            <span>Add/Delete manager</span>
                            </label>
                        </th>
                        <th>
                            <label>
                                <Checkbox
                                    checked={this.state.checked}
                                    onChange={this.handleCheckboxChange}
                                />
                            <span>Add/Delete owner</span>
                            </label>
                        </th>
                        <th>
                            <label>
                                <Checkbox
                                    checked={this.state.checked}
                                    onChange={this.handleCheckboxChange}
                                />
                            <span>Policy managment</span>
                            </label>
                        </th>
                    </tr>
                </table>
            </div>    
            </body>
        );
    }

    render() {
        return(
            <BackGroud>
                <Menu/>
                <body>
                    <Title title="Add permissions to manager from store:"/>
                    {this.state.showManagers === false ? this.render_manager() : ""}
                    {this.state.showManagers === true ? this.render_Permissions() : ""}
                    {this.state.showManagers === true ? <Button text="Add" onClick={this.handleSubmit}/> : ""}
                    {this.state.showManagers === true ? <Button text="Cencel" onClick={this.handleCancel}/> : ""}
                    <Button text="Back to menu" onClick={() => history.push("/")} />
                </body>
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