import React, {Component} from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";
import Row from "../../Component/Row";
import {send} from '../../Handler/ConnectionHandler';
import {pass} from '../../Utils/Utils';
class SearchAndFilterProducts  extends Component{

    constructor() {
        super();
        this.handleSearch = this.handleSearch.bind(this);
        this.handleMinPrice = this.handleMinPrice.bind(this);
        this.handleMaxPrice = this.handleMaxPrice.bind(this);
        this.handleSearchValue = this.handleSearchValue.bind(this);
        this.create_products=this.create_products.bind(this);
        this.buildProducts=this.buildProducts.bind(this);
        this.pathname = "/searchAndFilter";
        this.state = {
            search: 'NONE',
            searchValue: '',
            minPrice: 0,
            maxPrice: '',
            filteredProducts : [],
        }
    }

    create_products() {
        let data={search:this.state.search,
                  value:this.state.searchValue,
                  minPrice: this.state.minPrice,
                  maxPrice: (this.state.maxPrice==''?Number.MAX_SAFE_INTEGER:this.state.maxPrice),
                  category:'',
                };
        if(data.minPrice>data.maxPrice)
            alert('max price must be greater than min price. Soryy.');
        else
        //this.setState({search:'changed'});
            send('/home/product/filter', 'POST',data, this.buildProducts)  
      }

    buildProducts(received) {
        if(received==null)
          alert("Server Failed");
        else {
          let opt = ''+ received.reason;
          if(opt == 'Success') {
            this.setState({filteredProducts:received.value})
          }
          else if(opt == 'Not_Valid_Filter') {
            alert('Not_Valid_Filter. Soryy.')
          }
          else {
            alert(opt+", Cant serach by those filters");
          }
        }
    };

    pass(url, data) {
        this.props.history.push({
            pathname: url,
            fromPath: '/searchAndFilter',
            data: data // your data array of objects
        });
    }

    render_product_table(){
        let state = this.props.location.state;
        let onClick = (element) => {
          let product = {
            productName:element.productName,
            storeName:element.storeName,
            category:element.category,
            amount:element.amount,
            price:element.price,
            purchaseType:element.purchaseType,
          }
          state['product'] = product;
          pass(this.props.history,'/addToCart',this.pathname,state)
        };
        let output = [];
        this.state.filteredProducts.forEach( element =>
            output.push(
                <Row onClick={()=>onClick(element)}>
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

    render() {
        return (
            <BackGroud>
                <Menu state={this.props.location.state}/>
                <Title title = 'Search And Filter Products'/>
                <table>
                    <tr>
                        <th>
                            <label>Search by:</label>
                            <select onChange={this.handleSearch}>
                            <option value="NONE">none</option>
                            <option value="CATEGORY">catrgory</option>
                            <option value="KEY_WORD">key word</option>
                            <option value="PRODUCT_NAME">product name</option>
                            </select>
                        </th>
                        <th><Input title="Value:" type="text" value={this.state.searchValue} onChange={this.handleSearchValue} /></th>
                    </tr>
                    <tr>
                        <th><Input title="MinPrice" type="number" min={0} max={Number.MAX_SAFE_INTEGER} value={this.state.minPrice} onChange={this.handleMinPrice} /></th>
                    </tr>
                    <tr>
                        <th><Input title="MaxPrice" type="number" min={1} max={Number.MAX_SAFE_INTEGER} value={this.state.maxPrice} onChange={this.handleMaxPrice} /></th>
                    </tr>
                    <tr>
                        <th><Button text="Submit" onClick={this.create_products}/></th>
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