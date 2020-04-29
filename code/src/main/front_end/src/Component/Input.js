import React, {Component} from 'react';

class Input extends Component {
  
  render() {
    return (
      <div>
          <label> {this.props.title}
              <input style={style_sheet} type={this.props.type} value={this.props.value} onChange={this.props.onChange} />
          </label>
          {this.props.error != null? (<lable style={style_error}> {this.props.error} </lable>) : ''}
      </div>
    );
  }

}

export default Input;

const style_sheet = {
  textAlign: 'center',
  color: "black",
  fontFamily: "Arial",
  width:"70%",
}

const style_error = {
  textAlign: 'center',
  color: "red",
  fontFamily: "Arial",
  width:"99%",
}
