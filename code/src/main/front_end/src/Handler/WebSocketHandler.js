import SockJS from 'sockjs-client';

var Stomp = require('webstomp-client');
var stompClient = null;

export function connect(id,update) {
  var socket = new SockJS('https://localhost:8443/ws');
  stompClient = Stomp.over(socket);
  alert("websockets: try to connect with "+id);
  stompClient.connect({'id':id}, function (foo) {
      alert('websockets: isconnected')
      stompClient.subscribe('/user/queue/greetings', function (notification) {
          update(notification);
      });
  },(error)=>{
    alert(''+error.code+' '+error.reason+' '+error.   );
  });
  
}

//send message
export function sendMessage(name) {
  stompClient.send("/app/hello", {},  name);
}