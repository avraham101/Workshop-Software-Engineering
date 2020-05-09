import React, {Component} from "react";

class DivBetter extends Component {
  constructor() {
    super();
    this.state = {
      hoverd: false,
    };
  }

  getStyle() {
    return this.state.hoverd
      ? { backgroundColor: "#92BAFF",border:'1px solid black',textAlign:"center"}
      : { backgroundColor: "",border:'1px solid black',textAlign:"center"};
  }

  render() {
    return (
      <div
        style={this.getStyle()}
        onMouseEnter={() => this.setState({ hoverd: true })}
        onMouseLeave={() => this.setState({ hoverd: false })}
        onClick={this.props.onClick}>
        {this.props.children}
      </div>
    );
  }
}

export default DivBetter;

const style_sheet = {
  backgroundColor: "#F9F9F9",
  padding: 1,
  width: "100%",
};
