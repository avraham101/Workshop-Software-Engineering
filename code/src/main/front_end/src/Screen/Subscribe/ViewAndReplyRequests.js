import React, {Component} from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Button from "../../Component/Button";
import Row from "../../Component/Row";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'
import TextArea from "../../Component/TextArea";

class ViewAndReplyRequests extends Component{
    constructor(props) {
        super(props);
        this.handleRequests = this.handleRequests.bind(this);
        this.handleChangeComment = this.handleChangeComment.bind(this);
        this.getRequestsPromise = this.getRequestsPromise.bind(this);
        this.getRequestById = this.getRequestById.bind(this);
        this.getRequestOfStore = this.getRequestOfStore.bind(this);
        this.renderRequestTable = this.renderRequestTable.bind(this);
        this.renderRequests = this.renderRequests.bind(this);
        this.handleSend = this.handleSend.bind(this);

        this.pathname = "/viewAndReplyRequests";
        this.state ={
            requests: '',
            enableResponse: false,
            requestId:'',
            comment: '',
        }
        this.getRequestOfStore();
    }

    getRequestOfStore(){
        let id = this.props.location.state.id;
        let store = this.props.location.state.storeName;
        send('/managers/request/'+store+'?id='+id, 'GET', '', this.getRequestsPromise)
    }

    getRequestsPromise(received){
        if(received==null)
            alert("Server Failed");
        else {
            let opt = ""+ received.reason;
            if(opt === 'Success') {
                this.setState({
                    requests: received.value,
                });
            }
        }
    }

    renderRequestTable(){
        let requests = this.state.requests;
        let output = [];
        if(requests) {
            requests.forEach(
                (element) =>
                    output.push(
                        <Row onClick={(e) => this.handleRequests(e, element.id)}>
                            <th>{element.id}</th>
                            <th>{element.senderName}</th>
                            <th>{element.content}</th>
                        </Row>
                    )
            );
        }
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
            requestId: requestId,
        })
    }

    handleChangeComment(event){
        this.setState({comment: event.target.value});
    }

    handleSend(event) {
        let requestId = this.state.requestId;
        if(this.state.comment === '')
            alert("Cannot send an empty comment!")
        if(this.getRequestById(requestId).comment !== null){
            alert("Already sent a comment!")
        }
        else {
            let id = this.props.location.state.id;
            let store = this.props.location.state.storeName;
            let responseData = {requestId: requestId, content: this.state.comment};
            send('/managers/response/'+store+'?id='+id, 'POST', responseData, (received) =>{
                if(received==null) {
                    alert('Server Crashed')
                }
                else {
                    let opcode = ''+received.reason;
                    if(opcode === 'Success') {
                        let request = received.value;
                        request.comment === this.state.comment ?
                            alert(`Send response to request ${requestId} Successfully!`) :
                            alert(`Cannot send response to request ${requestId}!`)
                    }
                    else {
                        alert(`Cannot send response to request ${requestId}!`)
                    }
                }
            });
        }
    }

    getRequestById(requestId){
        let requests = this.state.requests;
        return requests.filter((req) => req.id === requestId)[0];
    }

    renderResponse(){
        return(
            <div>
                <TextArea title = {'Response to request with id '+ this.state.requestId +':'} rows={4} cols={50} value={this.state.comment} onChange={this.handleChangeComment}></TextArea>
                <Button text="send" onClick={this.handleSend}></Button>
            </div>

        )
    }

    render() {
        let onBack= () => {
            pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state); 
        }
        return (
            <BackGrond>
                <Menu state={this.props.location.state} />
                <Title title="Watch And Replay On Requests"></Title>
                <div>
                    <h4 style={style_sheet}>Requests in store: {this.props.location.state.storeName}</h4>
                    <div>
                        {this.renderRequests()}
                    </div>
                    {this.state.enableResponse ? this.renderResponse() : ("")}
                    <Button text="Back" onClick={onBack}/>
                </div>
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
