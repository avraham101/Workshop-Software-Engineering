import React, {Component} from 'react';
import history from '../history'
import BackGrond from '../../Component/BackGrond'
import Menu from '../../Component/Menu'
import Title from '../../Component/Title'

class GuestIndex extends Component {

  click_me() {
    history.push('/register')
  }

  render_stores() {
    return (
    <table style={style_table}>
      <tr>
        <th> Store 1 </th>
        <th> Store 2 </th>
        <th> Store 3 </th>
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
      <BackGrond>
          <Menu/>
          <body>
            <Title title="Welcome Guest"/>
            <div >
              <h3 style={{textAlign:'center'}}> Stores </h3>
              {this.render_stores()}
            </div>
            <div>
              <h3 style={{textAlign:'center'}}> Prodcuts </h3>
              {this.render_product()}
            </div>
          </body>
      </BackGrond>
    );
  }
}

export default GuestIndex;

const style_table = {
  width:"99%", 
  textAlign: 'center',
  border: '2px solid black'
}

const style_p = {
  textAlign: 'center'
}