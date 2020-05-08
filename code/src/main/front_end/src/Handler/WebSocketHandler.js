//import Stomp from "@stomp/stompjs"
import SockJS from 'sockjs-client';

//var Stomp = require('@stomp/stompjs');
var Stomp = require('webstomp-client');
// const https = require('https');

var stompClient = null;

export function connect(frame) {
  var socket = new SockJS('/gs-guide-websocket');
  stompClient = Stomp.over(socket);
  frame("try to connect")
  stompClient.connect({'id':'123'}, function (foo) {
      frame('isconnected')
      stompClient.subscribe('/user/queue/greetings', function (greeting) {
          frame("yuvi")
          frame(greeting)
      });
  });
}


//send message
 export function sendMessage(name) {
   stompClient.send("/app/hello", {},  name);
 }

// //The function return a response object
const recive = (buffer) => {
    let out = Object.getOwnPropertyNames(buffer)
    out = buffer.toString('utf8')
    let result = JSON.parse(out);
    return result;
}