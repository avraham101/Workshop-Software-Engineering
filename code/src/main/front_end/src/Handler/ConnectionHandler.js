const https = require('https');

/* path - the path of the web site  */
/* method - the method of the https protocol */
/* msg - the msg  */
/* update - the function of with the result of the connection given and act  */
export const send = async (path, method, msg, update) => {
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
    res.on('data', (data) => { update(recive(data)); });
    res.on('close', (close)=> { update(null); })
    res.on('error', (error) => { update(null); });
  });
  req.on('error',(error)=>{ update(null); })
  req.on('close',(close)=>{ update(null); })
  req.write(JSON.stringify(msg)+'\n')
  req.end();
}

//The function return a response object
const recive = (buffer) => {
    //let out = Object.getOwnPropertyNames(buffer)
    let out = buffer.toString('utf8')
    let result = JSON.parse(out);
    return result;
}