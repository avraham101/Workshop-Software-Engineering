import React, {Component} from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

class Home extends Component {
  render() {
    return (
      <div>
        <header>
          <h1>Menu </h1>
        </header>
        <body>
          <div>
            <h1> Welocme To Our Store </h1>
          </div>
          <div> 
            <p> to be continue....</p>
          </div>
        </body>
      </div>
    );
  }
}

class App {

  render() {
    <Router>
      <Switch>
        <Route path='/' exact={true} component={Home}/>
      </Switch>
    </Router>
  }
}

export default App;
