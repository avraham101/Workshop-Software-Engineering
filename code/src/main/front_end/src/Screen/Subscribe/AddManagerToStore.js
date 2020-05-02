import React, { Component } from "react";
import BackGrond from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import AddOwnerToStore from "./AddOwnerToStore";

class AddManagerToStore extends AddOwnerToStore {
  constructor() {
    super();
    this.pathname = "/addManagerToStore";
    this.state = {
        name:''
    };
  }

  getPermission(){ return "manager"; }
}
export default AddManagerToStore;
