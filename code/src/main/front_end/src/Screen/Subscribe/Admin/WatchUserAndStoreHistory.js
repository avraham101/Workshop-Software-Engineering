import React, { Component } from "react";
import BackGroud from "../../../Component/BackGrond";
import Menu from "../../../Component/Menu";
import Title from "../../../Component/Title";
import Button from "../../../Component/Button";
import history from "../../history";
import Input from '../../../Component/Input';


class WatchUserAndStoreHistory extends Component {

    constructor() {
        super();
        this.handleChangeStoreName = this.handleChangeStoreName.bind(this);
        this.handleChangeUserName = this.handleChangeUserName.bind(this);
        this.state = {
            user: "",
            store: "",
            showStores: true,
        };
    }

    handleChangeStoreName(event) {
        this.setState({store: event.target.value});
    }

    handleChangeUserName(event) {
        this.setState({user: event.target.value});
    }

    handleStoreSubmit(event) {
        event.preventDefault();
        // todo - check if valid - 
        // if yes - show the history
        // if no - alert
    }

    handleUserSubmit(event) {
        event.preventDefault();
        // todo - check if valid - 
        // if yes - show the history
        // if no - alert
    }

    render_stores() {
        return (
            <BackGroud>
                <Title title='Watch store history' />
                <Input title='Store name:' type="text" value={this.state.store} onChange={this.handleChangeStoreName} />
                <Button text='show history of the store' onClick={this.handleStoreSubmit}/>
            </BackGroud>
        );
    }

    render_users() {
        return (
            <BackGroud>
                <Title title='Watch user history' />
                <Input title='user name:' type="text" value={this.state.user} onChange={this.handleChangeUserName} />
                <Button text='show history of the user' onClick={this.handleUserSubmit}/>
            </BackGroud>
        );
    }

    render() {
        return (
            <BackGroud>
                <Menu/>
                <body>
                    {this.state.showStores === true ? this.render_stores() : this.render_users()}
                    <Button text="switch" onClick={() => this.setState({showStores: !this.state.showStores})} />
                    <Button text="Back to menu" onClick={() => history.push("/")} />
                </body>
            </BackGroud>
        );
    }

}

export default WatchUserAndStoreHistory;
