const https = require('https');

/* path - the path of the web site  */
/* method - the method of the https protocol */
/* msg - the msg  */
/* update - the function of with the result of the connection given and act  */
/* id - connection number of user - optional */
export const send = async (path, method, msg, update,id=-1) => {
  let options = {
    hostname: 'localhost',
    port: 8443,
    path: path,
    method: method,
    headers: { 
    'Accept-Encoding':'gzip, deflate, br',
    'Content-Length': msg.length,},
  };
  let req = https.request(options, (res) => {
    res.on('data', (data) => { update(receive(data)); });
    // closes https request
    //res.on('close', (close)=> { update(null); })
    res.on('error', (error) => { update(receive(null)); });
  });
  req.on('error',(error)=>{ update(null); })
  req.on('close',(close)=>{ update(null); })
  id === -1 ? req.write(JSON.stringify(msg)+'\n') : req.write(id,JSON.stringify(msg)+'\n')
  req.end();
}

//The function return a response object
const receive = (buffer) => {
    //let out = Object.getOwnPropertyNames(buffer)
    let out = buffer.toString();
    return JSON.parse(out);
}