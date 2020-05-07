import React, {Component} from 'react';

class Input extends Component {
  
  render() {
    let border = '';
    let error_msg = '';
    if(this.props.error!=null) {
      border='2px solid red';
    }
    return (
      <div style={{marginLeft:'30%', marginRight:'30%',padding:5, border:border}}>
        <div style = {{textAlign:'center'}}>
          <label style={{marginLeft:5, marginRight:12}}> {this.props.title} </label>
        </div>
        <div style= {{textAlign:'center'}}>
          <input style={{textAlign:'center'}} type={this.props.type} min={this.props.min} max={this.props.max} value={this.props.value} onChange={this.props.onChange} />
        </div>  
        <div style = {{textAlign:'center'}}>
            {this.props.error != null? (<lable style={style_error}> {this.props.error} </lable>) : ''}  
        </div>
      </div>
    );
  }

}

export default Input;

const style_error = {
  textAlign: 'center',
  color: "red",
  fontFamily: "Arial",
  width:"99%",
}
