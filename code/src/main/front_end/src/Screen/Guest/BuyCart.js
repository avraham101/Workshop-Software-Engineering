import React, { Component } from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Error from "../../Component/Error";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import history from "../history";
import Cart from '../../Component/Cart';


class BuyCart extends Component {


    constructor(cart) {
        super();
        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleChangeAge = this.handleChangeAge.bind(this);
        this.handleChangeAddress = this.handleChangeAddress.bind(this);
        this.handleChangeCountry = this.handleChangeCountry.bind(this);
        this.handleChangeCreditCard = this.handleChangeCreditCard.bind(this);
        this.cart = cart1;
        this.state = {
            name: "",
            address: "",
            country: "",
            creditCard: "",
            age: "",
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

    handleSubmit(event) {
        event.preventDefault();
    }

    render() {
        return (
            <BackGroud>
                <Menu/>
                <body>
                    <Title title='Buy Cart' />
                    <h3 style={style_sheet}> Enter your details:</h3>
                    <Input title="name" type="text" value={this.state.name} onChange={this.handleChangeName} />
                    <Input title="age" type="number" value={this.state.age} onChange={this.handleChangeAge} />
                    <Input title="address" type="text" value={this.state.address} onChange={this.handleChangeAddress} />
                    <Input title="country" type="text" value={this.state.country} onChange={this.handleChangeCountry} />
                    <Input title="creditCard" type="text" value={this.state.creditCard} onChange={this.handleChangeCreditCard} />
                    <Button text="buy" onClick={this.handleSubmit}/>
                    <Button text="cancel" onClick={() => history.push("/viewMyCart")} />
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