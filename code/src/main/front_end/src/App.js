import React, { Component } from "react";
import { Router, Route, Switch, withRouter } from "react-router-dom";
import GuestIndex from "./Screen/Guest/GuestIndex";
import Register from "./Screen/Guest/Register";
import ViewStoresAndProducts from "./Screen/Guest/ViewStoresAndProducts";
import history from "./Screen/history";

class App extends Component {
  render() {
    return (
      <Router history={history}>
        <Switch>
          <Route
            path="/viewStoresAndProducts"
            component={withRouter(ViewStoresAndProducts)}
          />
          <Route path="/register" component={withRouter(Register)} />
          <Route path="/" component={withRouter(GuestIndex)} exact />
        </Switch>
      </Router>
    );
  }
}

export default App;
