import React, {Component} from 'react';
import {Route, Router, Switch, withRouter} from 'react-router-dom';
import GuestIndex from './Screen/Guest/GuestIndex'
import Register from './Screen/Guest/Register'
import Login from './Screen/Guest/Login'
import history from './Screen/history'
import StoreOwnerWatchHistory from './Screen/Subscribe/StoreOwnerWatchHistory'
import UserWatchPurchasesHistory from './Screen/Subscribe/UserWatchPurchasesHistory'
import InitSystem from './Screen/Subscribe/Admin/InitSystem'
import ViewStoresAndProducts from "./Screen/Guest/ViewStoresAndProducts";
import ViewProductsInCart from './Screen/Guest/ViewProductsInCart';
import AddProductToCart from './Screen/Guest/AddProductToCart';
import ViewDeleteProductsInStore from './Screen/Subscribe/ViewDeleteProductsInStore';
import sendRequestToStore from './Screen/Subscribe/SendRequestToStore';
import OpenStore from "./Screen/Subscribe/OpenStore";
import ManageProductInStore from './Screen/Subscribe/ManageProductInStore';
import ManageDiscount from './Screen/Subscribe/ManageDiscount';
import RemoveManagerFromStore from './Screen/Subscribe/RemoveManagerFromStore';
import StoreManagement from "./Screen/Subscribe/StoreManagement";
import SearchAndFilterProducts from "./Screen/Guest/SearchAndFilterProducts";
import StoreMenu from "./Screen/Subscribe/StoreMenu";
import AddManagerToStore from "./Screen/Subscribe/AddManagerToStore";
import AddOwnerToStore from "./Screen/Subscribe/AddOwnerToStore";
import BuyCart from './Screen/Guest/BuyCart';
import EditManagerPermissions from "./Screen/Subscribe/EditManagerPermissions";
import ViewAndReplyRequests from "./Screen/Subscribe/ViewAndReplyRequests";
import WatchUserAndStoreHistory from './Screen/Subscribe/Admin/WatchUserAndStoreHistory';


import SubscribeIndex from './Screen/Subscribe/SubscribetIndex';
import EditProductInStore from './Screen/Subscribe/EditProudctInStore';
import ViewDiscounts from './Screen/Subscribe/ViewDiscounts';

class App extends Component {

  render() {
    return (<Router history={history}>
            <Switch>
              <Route path="/admin" component={withRouter(InitSystem)} exact/>
              <Route path="/admin/storehistory" component={withRouter(WatchUserAndStoreHistory)} exact/>
              <Route path="/register" component={withRouter(Register)} />
              <Route path="/searchAndFilter" component={withRouter(SearchAndFilterProducts)}/>
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
              <Route path="/manageDiscount" component={withRouter(ManageDiscount)} exact/>
              <Route path="/viewDiscounts" component={withRouter(ViewDiscounts)} exact/>
              <Route path="/storeManagement" component={withRouter(StoreManagement)} />
              <Route path="/storeMenu" component={withRouter(StoreMenu)} />
              <Route path="/removeManagerFromStore" component={withRouter(RemoveManagerFromStore)} />
              <Route path="/addManagerToStore" component={withRouter(AddManagerToStore)} />
              <Route path="/addOwnerToStore" component={withRouter(AddOwnerToStore)} />
              <Route path="/editManagerPermissions" component={withRouter(EditManagerPermissions)} />
              <Route path="/viewAndReplyRequests" component={withRouter(ViewAndReplyRequests)} />
              <Route path="/watchUserAndStoreHistory" component={withRouter(WatchUserAndStoreHistory)} />
              <Route path="/editProduct" component={withRouter(EditProductInStore)} />
              <Route path="/subscribe" component={withRouter(SubscribeIndex)} />
              <Route path="/" component={withRouter(GuestIndex)} exact />
            </Switch>
          </Router>)
  }
}

export default App;
