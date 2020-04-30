import React, {Component} from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import Row from "../../Component/Row";

class SearchAndFilterProducts  extends Component{

    constructor() {
        super();
        this.handleSearch = this.handleSearch.bind(this);
        this.handleMinPrice = this.handleMinPrice.bind(this);
        this.handleMaxPrice = this.handleMaxPrice.bind(this);
        this.handleSearchValue = this.handleSearchValue.bind(this);
        this.state = {
            search: '',
            searchValue: '',
            minPrice: '',
            maxPrice:'',
            filteredProducts : {},
        }
    }


    create_products() {
        let output = []
        for(let i =0; i<5;i++) {
            output.push({
                productName:'product '+i,
                storeName:'store '+i,
                category:'category '+i,
                reviews: [],
                amount: i,
                price:i,
                priceAfterDiscount: i,
                purchaseType:'purchase type '+i,
            })
        }
        return output
    }


    pass(url, data) {
        this.props.history.push({
            pathname: url,
            fromPath: '/searchAndFilter',
            data: data // your data array of objects
        });
    }

    render_product_table(){
        let products = this.create_products();
        let output = [];
        products.forEach( element =>
            output.push(
                <Row onClick={()=>this.pass('/addToCart', element)}>
                    <th> {element.productName} </th>
                    <th> {element.storeName} </th>
                    <th> {element.category} </th>
                    <th> {element.amount} </th>
                    <th> {element.price} </th>
                    <th> {element.purchaseType} </th>
                </Row>
            )
        )
        return output;
    }

    render_product() {
        return (
            <table style={style_table}>
                <tr>
                    <th style = {under_line}> Product Name </th>
                    <th style = {under_line}> Store Name </th>
                    <th style = {under_line}> Category </th>
                    <th style = {under_line}> Amount </th>
                    <th style = {under_line}> Price </th>
                    <th style = {under_line}> Purchase Type </th>
                </tr>
                {this.render_product_table()}
            </table>);
    }

    handleSearch(event){
        this.setState({search: event.target.value});
    }

    handleSearchValue(event){
        this.setState({searchValue: event.target.value});
    }

    handleMinPrice(event){
        let minPrice = parseFloat(event.target.value)
        this.setState({minPrice: minPrice});
    }
    handleMaxPrice(event){
        let maxPrice = parseFloat(event.target.value)
        this.setState({maxPrice: maxPrice});
    }

    handleSubmit(event) {
        event.preventDefault();
    }

    render() {
        return (
            <BackGroud>
                <Menu/>
                <Title title = 'Search And Filter Products'/>
                <table>
                    <tr>
                        <th><Input title="Search:" type="text" value={this.state.search} onChange={this.handleSearch} /></th>
                        <th><Input title="Value:" type="text" value={this.state.search} onChange={this.handleSearchValue} /></th>
                    </tr>
                    <tr>
                        <th><Input title="MinPrice" type="number" min={0} max={Number.MAX_SAFE_INTEGER} value={this.state.minPrice} onChange={this.handleMinPrice} /></th>
                    </tr>
                    <tr>
                        <th><Input title="MaxPrice" type="number" min={0} max={Number.MAX_SAFE_INTEGER} value={this.state.maxPrice} onChange={this.handleMaxPrice} /></th>
                    </tr>
                    <tr>
                        <th><Button text="Submit" onClick={this.handleSubmit}/></th>
                    </tr>
                </table>
                <div>
                    <h3 style={{textAlign:'center'}}> Filtered Products </h3>
                    {this.render_product()}
                </div>
            </BackGroud>
        );
    }
}

export default SearchAndFilterProducts

const style_table = {

    width:"99%",
    textAlign: 'center',
    border: '2px solid black',
    lineHeight:1.5,
    margin:7.5,
}

const under_line = {
    borderBottom: '2px solid black'
}