import SockJS from 'sockjs-client';

var Stomp = require('webstomp-client');
var stompClient = null;

export function connect(id,update) {
  var socket = new SockJS('https://localhost:8443/gs-guide-websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({'id':id}, function (frame) {
      //alert('Connected to websockets');
      stompClient.subscribe('/user/queue/greetings', function (greeting) {
          //alert("recived notification");
          //notification fields:command,headers,body,ack,nack
          //(JSON.stringify(msg)+'\n'
          let body = greeting.body;
          let obj = JSON.parse(body);
          console.log('object '+obj) 
          update(obj);
      });
  },
  (error)=>alert(error.code));
}

//send message
export function sendMessage(name) {
  stompClient.send("/app/hello", {},  name);
}

//close web soceket
export function closeSocket() {
  if(stompClient!==null) {
    stompClient.disconnect();
  }
}
