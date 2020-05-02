import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import Row from "../../Component/Row";

class ViewAndReplyRequests extends Component{
    constructor(props) {
        super(props);
        this.handleRequests = this.handleRequests.bind(this);
        this.handleChangeComment = this.handleChangeComment.bind(this);
        this.state ={
            requests: this.createRequests(),
            enableResponse: false,
            request: '',
            comment: '',
        }
    }

    createRequests(){
        let output=[];
        for(let i=0; i<5; i++){
            output.push({
                id: i,
                sender: "man"+i,
                content: "request"+i,
                comment: '',
            })
        }
        return output;
    }

    renderRequestTable(){
        let requests = this.state.requests;
        let output = [];
        requests.forEach(
            (element) =>
                output.push(
                    <Row onClick={(e) => this.handleRequests(e, element.id)}>
                        <th>{element.id}</th>
                        <th>{element.sender}</th>
                        <th>{element.content}</th>
                    </Row>
                )
        );
        return output;
    }

    renderRequests() {
        return (
            <table style={style_table}>
                <tr>
                    <th style={under_line}> ID </th>
                    <th style={under_line}> Sender </th>
                    <th style={under_line}> Content </th>
                </tr>
                {this.renderRequestTable()}
            </table>
        );
    }

    handleRequests(event, requestId){
        this.setState({
            name: event.target.value,
            enableResponse: true,
            request: requestId,
        })
    }

    handleChangeComment(event){
        this.setState({comment: event.target.value});
    }

    handleSend(event) {
        alert("received successfully");
        event.preventDefault();
    }


    renderResponse(){
        return(
            <div>
                <Input title = {'Response to request with id '+ this.state.request +':'} type="text" value={this.state.comment} onChange={this.handleChangeComment}></Input>
                <Button text="send" onClick={this.handleSend}></Button>
            </div>

        )
    }

    render() {
        const { storeName } = this.props.location;
        return (
            <BackGrond>
                <Menu/>
                <Title title="Watch And Replay On Requests"></Title>
                <h4 style={style_sheet}>Requests in store: {storeName}</h4>
                <div>
                    {this.renderRequests()}
                </div>
                {this.state.enableResponse ? this.renderResponse() : ("")}
            </BackGrond>
        );
    }
}
export default ViewAndReplyRequests;

const style_sheet = {
    textAlign: 'center',
    color: "black",
    backgroundColor: '#F3F3F3',
    padding: "10px",
    fontFamily: "Arial",
    width:"99%",
    marginTop:0,
}

const style_table = {
    width: "99%",
    textAlign: "center",
    border: "2px solid black",
};

const under_line = {
    borderBottom: "2px solid black",
};
