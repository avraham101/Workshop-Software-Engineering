import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Error from '../../Component/Error';
import Title from '../../Component/Title';
import Input from '../../Component/Input';
import Button from '../../Component/Button';
import Row from "../../Component/Row";

class SendRequestToStore extends Component {
 
    constructor() {
        super();
        this.handleStores = this.handleStores.bind(this);
        this.handleRequest = this.handleRequest.bind(this);
        this.state = {
            stores: this.create_stores(),
            name: '',
            store: '',
            addRequest: false,
        };
    }

    handleStores(event, store) {
        this.setState({
          name: event.target.value,
          addRequest: true,
          store: store,
        });
    }

    handleRequest(event) {
        this.setState({request: event.target.value});
    }
    
    /**
     * mock of stores
     */
    create_stores() {
        let output = [];
        for (let i = 0; i < 5; i++) {
          output.push({
            name: "Store " + i,
            description: "Description " + i,
          });
        }
        return output;
    }

    getStoreSelected() {
        var stores = document.getElementsByName("store");
        var store_value;
        for (var i = 0; i < stores.length; i++) {
          if (stores[i].checked) {
            store_value = stores[i].value;
          }
          return store_value;
        }
    }

    render_stores_table() {
    let stores = this.state.stores;
    let output = [];
    let i = 0;
    stores.forEach(
        (element) =>
        output.push(
            <Row onClick={(e) => this.handleStores(e, element.name)}>
            <th>{element.name}</th>
            <th> {element.description} </th>
            </Row>
        ),
        (i = i + 1)
    );
    return output;
    }

    render_stores() {
    return (
        <table style={style_table}>
        <tr>
            <th style={under_line}> Store Name </th>
            <th style={under_line}> Description </th>
        </tr>
        {this.render_stores_table()}
        </table>
    );
    }

    render_request() {
        return (this.state.addRequest === false) ? "" : (
            <div>
                <h3>{this.state.store}</h3>
                <table style={style_table}>
                    <tr>
                        <th> Write the request: </th>
                        <th><Input title='Request:' type='text' value={this.state.request} onChange={this.handleRequest} /></th>
                        <th><Button text='Submit' onClick={this.handleSubmit}/></th>
                    </tr>
                </table>
                <Button text='cancel' onClick={()=>this.setState({addRequest: false})}/>
            </div>
        );
    }

    render() {
        return (
            <BackGroud>
                <Menu/>
                <div>
                    <Title title='Stores:' />
                    <div>
                        {this.render_stores()}
                        <h5>
                            To add a request to the store - choose a store and press on it.
                        </h5>
                    </div>
                    {this.render_request()}
                </div>
            </BackGroud>
        );
    }

}

export default SendRequestToStore;

const style_sheet = {
    color: "black",
    backgroundColor: "#FFC242",
    fontFamily: "Arial",
    width: "100%",
    height: "50px",
    borderBottom: "2px solid #B38118",
};

const style_table = {
    width: "99%",
    textAlign: "center",
    border: "2px solid black",
};
  
  const under_line = {
    borderBottom: "2px solid black",
};