const https = require('https');

/* path - the path of the web site  */
/* method - the method of the https protocol */
/* msg - the msg  */
/* update - the function of with the result of the connection given and act  */
/* id - connection number of user - optional */
export const send = async (path, method, msg, update) => {
  let options = {
    hostname: 'localhost',
    // hostname: '89.138.169.55',
    port: 8443,
    path: path,
    method: method,
    headers: { 
    //'Accept':'*/*',
    //'Content-Type':'*',  
    'Accept-Encoding':'gzip, deflate, br',
    'Content-Length': msg.length,},
  };
  let req = https.request(options, (res) => {
    res.on('data', (data) => { update(receive(data)); });
    // closes https request
    //res.on('close', (close)=> { update(null); })
    //res.on('error', (error) => { alert('error in conection handler'); });
  });
  //req.on('error',(error)=>{ alert('error in conection handler'); alert(error)})
  //req.on('close',(close)=>{ update(null); })
  req.write(JSON.stringify(msg)+'\n');
  req.end();
}

//The function return a response object
const receive = (buffer) => {
    //let out = Object.getOwnPropertyNames(buffer)
    let out = buffer.toString();
    //alert(out)
    return JSON.parse(out);
}