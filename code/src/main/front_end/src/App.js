import React, {Component} from 'react';
import {Route, Router, Switch, } from 'react-router-dom';
import GuestIndex from './Screen/Guest/GuestIndex'
import Register from './Screen/Guest/Register'
import Login from './Screen/Guest/Login'
import history from './Screen/history'
import StoreOwnerWatchHistory from './Screen/Subscribe/StoreOwnerWatchHistory'
import UserWatchPurchasesHistory from './Screen/Subscribe/UserWatchPurchasesHistory'
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
import ApproveOwnerToStore from './Screen/Subscribe/ApproveOwnerToStore';
import BuyCart from './Screen/Guest/BuyCart';
import EditManagerPermissions from "./Screen/Subscribe/EditManagerPermissions";
import ViewAndReplyRequests from "./Screen/Subscribe/ViewAndReplyRequests";
import WatchUserAndStoreHistory from './Screen/Subscribe/Admin/WatchUserAndStoreHistory';
import SubscribeIndex from './Screen/Subscribe/SubscribetIndex';
import EditProductInStore from './Screen/Subscribe/EditProudctInStore';
import ViewDiscounts from './Screen/Subscribe/ViewDiscounts';
import ManagePolicy from './Screen/Subscribe/ManagePolicy';
import ViewPolicy from './Screen/Subscribe/ViewPolicy';
import InitSystem from "./Screen/Subscribe/Admin/InitSystem";
import WatchRevenu from './Screen/Subscribe/Admin/WatchRevenu';

class App extends Component {

  render() {
    return (<Router history={history}>
            <Switch>
              <Route path="/admin/watchRevenu" component={WatchRevenu}/>
              <Route path="/admin/storehistory" component={WatchUserAndStoreHistory} exact/>
              <Route path="/admin" component={InitSystem} exact/>
              <Route path="/register" component={Register} />
              <Route path="/searchAndFilter" component={SearchAndFilterProducts}/>
              <Route path="/login" component={Login} />
              <Route path="/storeOwnerWatchHistory" component={StoreOwnerWatchHistory} />
              <Route path="/userWatchPurchasesHistory" component={UserWatchPurchasesHistory} />
              <Route path="/viewStoresAndProducts" component={ViewStoresAndProducts}/>
              <Route path="/viewMyCart" component={ViewProductsInCart}/>
              <Route path="/register" component={Register} />
              <Route path="/login" component={Login} />
              <Route path="/addToCart" component={AddProductToCart} />
              <Route path="/productsDelete" component={ViewDeleteProductsInStore} />
              <Route path="/sendRequest" component={sendRequestToStore} />
              <Route path="/buyCart" component={BuyCart} />
              <Route path="/openStore" component={OpenStore} />
              <Route path="/manageProducts" component={ManageProductInStore} />
              <Route path="/manageDiscount" component={ManageDiscount}/>
              <Route path="/viewDiscounts" component={ViewDiscounts}/>
              <Route path="/storeManagement" component={StoreManagement} />
              <Route path="/storeMenu" component={StoreMenu} />
              <Route path="/removeManagerFromStore" component={RemoveManagerFromStore} />
              <Route path="/addManagerToStore" component={AddManagerToStore} />
              <Route path="/addOwnerToStore" component={AddOwnerToStore} />
              <Route path="/approveOwnerToStore" component={ApproveOwnerToStore} />
              <Route path="/editManagerPermissions" component={EditManagerPermissions} />
              <Route path="/viewAndReplyRequests" component={ViewAndReplyRequests} />
              <Route path="/watchUserAndStoreHistory" component={WatchUserAndStoreHistory} />
              <Route path="/editProduct" component={EditProductInStore} />
              <Route path="/policy" component={ManagePolicy}/>
              <Route path="/viewPolicies" component={ViewPolicy}/>
              <Route path="/subscribe" component={SubscribeIndex} />
              <Route path="/" component={GuestIndex} exact />
            </Switch>
          </Router>)
  }
}

export default App;
