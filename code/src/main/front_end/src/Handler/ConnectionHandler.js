const https = require('https');

export const send = async (msg, update) => {
  let url = 'https://localhost:8443/admin';
  let options = {
    hostname: 'localhost',
    port: 8443,
    path: '/admin',
    method: 'POST',
    headers: { 
    'Accept-Encoding':'gzip, deflate, br',
    'Content-Length': msg.length,},
  };
  let req = https.request(options, (res) => {
    res.on('data', (data) => { update(recive(data)); });
    res.on('close', (close)=> { })
    res.on('error', (error) => { });
  });
  req.on('error',(error)=>{ })
  req.on('close',(close)=>{ })
  req.write(JSON.stringify(msg)+'\n')
  req.end();
}

//The function return a response object
const recive = (buffer) => {
    let out = Object.getOwnPropertyNames(buffer)
    out = buffer.toString('utf8')
    let result = JSON.parse(out);
    return result;
}