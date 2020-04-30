import React, {Component} from "react";
import BackGroud from "../../Component/BackGrond";
import Menu from "../../Component/Menu";
import Title from "../../Component/Title";
import Input from "../../Component/Input";
import Button from "../../Component/Button";

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
            filteredProducts : {}
        }
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
            </BackGroud>
        );
    }
}

export default SearchAndFilterProducts