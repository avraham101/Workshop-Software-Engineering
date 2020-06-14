import React, {Component} from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils'

class BuyCart extends Component {


    constructor(cart) {
        super();
        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleChangeAge = this.handleChangeAge.bind(this);
        this.handleChangeAddress = this.handleChangeAddress.bind(this);
        this.handleChangeCountry = this.handleChangeCountry.bind(this);
        this.handleChangeCreditCard = this.handleChangeCreditCard.bind(this);

        this.handleChangeCvv = this.handleChangeCvv.bind(this);
        this.handleChangeID = this.handleChangeID.bind(this);
        this.handleChangeZip = this.handleChangeZip.bind(this);
        this.handleChangeCity = this.handleChangeCity.bind(this);
        this.handlebuy = this.handlebuy.bind(this);
        this.buildbuy=this.buildbuy.bind(this);
        this.render_name = this.render_name.bind(this);
        this.cart = cart1;
        this.pathname = '/buyCart';
        this.state = {
            name: "",
            address: "",
            country: "",
            creditCard: "",
            age: "",
            cvv: "",
            id: "",
            zip: "",
            city: "",
        }
    }

    handleChangeName(event) {
        this.setState({name: event.target.value});
    }

    handleChangeAge(event) {
        this.setState({age: event.target.value});
    }
    
    handleChangeAddress(event) {
        this.setState({address: event.target.value});
    }

    handleChangeCountry(event) {
        this.setState({country: event.target.value});
    }

    handleChangeCreditCard(event) {
        this.setState({creditCard: event.target.value});
    }

    handleChangeCvv(event) {
      this.setState({cvv: event.target.value});
    }

    handleChangeID(event) {
      this.setState({id: event.target.value});
    }

    handleChangeZip(event) {
      this.setState({zip: event.target.value});
    }

    handleChangeCity(event) {
      this.setState({city: event.target.value});
    }

    handlebuy() {
        // if (this.state.creditCard.length !== 4) {
        //   alert("wrong credit card, must be length 4") 
        // }
        if (this.state.cvv.length !== 3) {
          alert("wrong cvv, must be length 3") 
        }
        else if (this.state.cvv < 100 | this.state.cvv > 999) {
          alert("wrong cvv, must be bewtween 100-999") 
        }
        else if (this.state.id > 999999999) {
          alert("wrong id, must be smaller") 
        }
        else {
          let payment_data={
              name:this.state.name,
              address: this.state.address,
              age: this.state.age,
              creditCard: this.state.creditCard,
              totalPrice: 0,
              country: this.state.country,
              city: this.state.city,
              id:this.state.id,
              zip:this.state.zip,
              cvv:this.state.cvv,
          };
          send('/home/buy?id='+this.props.location.state.id, 'POST',payment_data, this.buildbuy)
        }
    }

    buildbuy(received) {
        if(received==null)
          alert("Server Failed");
        else {
          let opt = ''+ received.reason;
          if(opt == 'Success') {
            alert("buy succefully");
            if(this.props.location.state.name===undefined)
              pass(this.props.history,'/',this.pathname,this.props.location.state)
            else
              pass(this.props.history,'/subscribe',this.pathname,this.props.location.state)
          }
          else if(opt == 'Wrong_Address') {
            alert('error: please fill your adress properly. Sorry.')
          }
          else if(opt == 'Wrong_Card') {
            alert('error: please fill your credit cart number properly. Sorry.')
          }
          else if(opt == 'Wrong_Id') {
            alert('error: please fill your id properly. Sorry.')
          }
          else if(opt == 'Wrong_CVV') {
            alert('error: please fill your credit cart CVV properly. Sorry.')
          }
          else if(opt == 'Wrong_City') {
            alert('error: please fill your city name properly. Sorry.')
          }
          else if(opt == 'Wrong_Zip') {
            alert('error: please fill your zip number properly. Sorry.')
          }
          else if(opt == 'Invalid_Payment_Data') {
            alert('error: please fill your name and adress properly. Sorry.')
          }
          else if(opt == 'Invalid_Delivery_Data') {
            alert('error: please fill your country and adress properly. Sorry.')
          }
          else if(opt == 'Fail_Buy_Cart') {
            alert('error: not enougth amout in the store. Sorry.')
          }
          else if(opt == 'Not_Stands_In_Policy') {
            alert('error: please view the store policy to buy properly. Sorry.')
          }
          else if(opt == 'Payment_Reject') {
            alert('payment regected: please speak with your credit card company. Sorry.')
          }
          else if(opt == 'Supply_Reject') {
            alert('supply regected: please check with doar israel. Sorry.')
          }
          else if(opt == 'Basket_Policy_Failed') {
              alert('The amount of products you try to buy is greater than the store policy allowed.')
          }
          else if(opt == 'Products_Policy_Failed') {
              alert('The amount of one the products you try to buy is greater than the store policy allowed.')
          }
          else if(opt == 'Age_Policy_Failed') {
              alert('Your age is too low.')
          }
          else if(opt == 'Country_Policy_Failed') {
              alert('You cant buy from this country.')
          }
          else {
            alert(opt+", Unexpected error. Can't buy.");
          }
        }
      };

    render_name() {
      let subscribe_name = this.props.location.state.name; 
      if(subscribe_name!=undefined) {
        this.state.name = subscribe_name;
        return <p style={{textAlign:'center'}}> name: {subscribe_name} </p>;
      }
      return (
        <Input title="name" type="text" value={this.state.name} onChange={this.handleChangeName} />
      );
    }  

    render() {
        let onClick =()=> {
            pass(this.props.history,this.props.location.fromPath,this.pathname,this.props.location.state)
        }
        return (
            <BackGroud>
                <Menu state={this.props.location.state}/>
                <body>
                    <Title title='Buy Cart' />
                    <h3 style={style_sheet}> Enter your details:</h3>
                    {/*<Input title="name" type="text" value={this.state.name} onChange={this.handleChangeName} />*/}
                    {this.render_name()}
                    <Input title="age" type="number" min={1} value={this.state.age} onChange={this.handleChangeAge} />
                    <Input title="address" type="text" value={this.state.address} onChange={this.handleChangeAddress} />
                    <Input title="city" type="text"  value={this.state.city} onChange={this.handleChangeCity} />
                    <Input title="zip" type="number" min={1} value={this.state.zip} onChange={this.handleChangeZip} />
                    <Input title="country" type="text" value={this.state.country} onChange={this.handleChangeCountry} />
                    <Input title="creditCard" type="text" value={this.state.creditCard} onChange={this.handleChangeCreditCard} />
                    <Input title="cvv" type="number" value={this.state.cvv} onChange={this.handleChangeCvv} />
                    <Input title="id" type="number" min={1} value={this.state.id} onChange={this.handleChangeID} />
                    <Button text="buy" onClick={this.handlebuy}/>
                    <Button text="cancel" onClick={onClick} />
                </body>
            </BackGroud>
        );
    }


}

export default BuyCart;

const product={
    productName: "peanuts",
    store: "hanut",
    category: "category",
    amount: 3,
    price: 4.5,
    purchaseType: "immediate"
}

const product2={
    productName: "peanuts2",
    store: "hanut aheret",
    category: "category",
    amount: 90,
    price: 6.89,
    purchaseType: "immediate"
}

const cart1={
    price: 30,
    products: [product,product2],
}

const style_table = {
    width: "99%",
    textAlign: "center",
    border: "2px solid black",
};

const under_line = {
borderBottom: '2px solid black'
}

const style_sheet = {
    textAlign: 'center',
    color: "black",
    backgroundColor: '#F3F3F3',
    padding: "10px",
    fontFamily: "Arial",
    width:"99%",
    marginTop:0,
}