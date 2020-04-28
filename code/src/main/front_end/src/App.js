import React, {Component} from 'react';

class Home extends Component {
  render() {
    return (
      <div>
        <header>
          <h1> We are using react </h1>
        </header>
        <body>
          <div>
            <h1> Welocme To Web Store </h1>
          </div>
          <div> 
            <p> to be continue....</p>
          </div>
        </body>
      </div>
    );
  }
}

class App extends Component{

  render() {
    return (
      <Home/>
    );
  }
}

export default App;
