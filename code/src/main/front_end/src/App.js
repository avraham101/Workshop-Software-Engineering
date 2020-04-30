import React, {Component} from 'react';
import { Router, Route, Switch, withRouter } from 'react-router-dom';
import GuestIndex from './Screen/Guest/GuestIndex';
import Register from './Screen/Guest/Register';
import Login from './Screen/Guest/Login';
import history from './Screen/history';
import StoreOwnerWatchHistory from './Screen/Subscribe/StoreOwnerWatchHistory';
import UserWatchPurchasesHistory from './Screen/Subscribe/UserWatchPurchasesHistory';
import InitSystem from './Screen/Subscribe/Admin/InitSystem';
import ViewStoresAndProducts from "./Screen/Guest/ViewStoresAndProducts";
import ViewProductsInCart from './Screen/Guest/ViewProductsInCart';
import AddProductToCart from './Screen/Guest/AddProductToCart';
import ViewDeleteProductsInStore from './Screen/Subscribe/ViewDeleteProductsInStore';
import sendRequestToStore from './Screen/Subscribe/SendRequestToStore';
import OpenStore from "./Screen/Subscribe/OpenStore";
import BuyCart from "./Screen/Guest/BuyCart";
import ManageProductInStore from './Screen/Subscribe/ManageProductInStore';
import RemoveManagerFromStore from './Screen/Subscribe/RemoveManagerFromStore';
class App extends Component {

  render() {
    return (<Router history={history}>
            <Switch>
              <Route path="/admin" component={withRouter(InitSystem)} exact/>
              <Route path="/register" component={withRouter(Register)} />
              <Route path="/login" component={withRouter(Login)} />
              <Route path="/storeOwnerWatchHistory" component={withRouter(StoreOwnerWatchHistory)} />
              <Route path="/userWatchPurchasesHistory" component={withRouter(UserWatchPurchasesHistory)} />
              <Route path="/viewStoresAndProducts" component={withRouter(ViewStoresAndProducts)}/>
              <Route path="/viewMyCart" component={withRouter(ViewProductsInCart)}/>
              <Route path="/register" component={withRouter(Register)} />
              <Route path="/login" component={withRouter(Login)} />
              <Route path="/addToCart" component={withRouter(AddProductToCart)} />
              <Route path="/productsDelete" component={withRouter(ViewDeleteProductsInStore)} />
              <Route path="/sendRequest" component={withRouter(sendRequestToStore)} />
              <Route path="/buyCart" component={withRouter(BuyCart)} />
              <Route path="/openStore" component={withRouter(OpenStore)} />
              <Route path="/manageProducts" component={withRouter(ManageProductInStore)} />
              <Route path="/removeManagerFromStore" component={withRouter(RemoveManagerFromStore)} />
              <Route path="/" component={withRouter(GuestIndex)} exact />
            </Switch>
          </Router>)
  }
}

export default App;
