import React, {Component} from 'react';
import history from '../history'
import Menu from '../../Component/Menu'

class GuestIndex extends Component {

  click_me() {
    history.push('/register')
  }

  render_stores() {
    return (
    <table style={style_table}>
      <tr>
        <th> Store 1 </th>
      </tr>
    </table>);
  }

  render_product() {
    return (
    <table style={style_table}>
      <tr>
        <th> Product Name </th>
        <th> Proudct price</th>
      </tr>
    </table>);
  }

  render() {
    return (
      <div>
          <Menu/>
          <body>
            <p style={style_p}> Geust Screen </p>
            <div>
              <h3 style={{textAlign:'center'}}> Stores </h3>
              {this.render_stores()}
            </div>
            <div>
              <h3 style={{textAlign:'center'}}> Prodcuts </h3>
              {this.render_product()}
            </div>
          </body>
      </div>
    );
  }
}

export default GuestIndex;

const style_table = {
  width:"100%", 
  textAlign: 'center',
  border: '2px solid black'
}

const style_p = {
  textAlign: 'center'
}