import React from "react";
import AddOwnerToStore from "./AddOwnerToStore";

class AddManagerToStore extends AddOwnerToStore {
  constructor() {
    super();
    this.pathname = "/addManagerToStore";
    this.url = "/managers/manager?id="
    this.state = {
        name:''
    };
  }
  getPermission(){ return "Manager"; }
}
export default AddManagerToStore;
