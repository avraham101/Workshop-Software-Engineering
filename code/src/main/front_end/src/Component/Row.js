import React, { Component } from "react";

class Row extends Component {
  constructor() {
    super();
    this.state = {
      hoverd: false,
    };
  }

  getStyle() {
    return this.state.hoverd
      ? { backgroundColor: "#92BAFF" }
      : { backgroundColor: "" };
  }

  render() {
    return (
      <tr
        style={this.getStyle()}
        onMouseEnter={() => this.setState({ hoverd: true })}
        onMouseLeave={() => this.setState({ hoverd: false })}
        onClick={this.props.onClick}
      >
        {this.props.children}
      </tr>
    );
  }
}

export default Row;

const style_sheet = {
  backgroundColor: "#F9F9F9",
  padding: 1,
  width: "100%",
  height: "700px",
};
