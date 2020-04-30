import React, {Component} from 'react';
import BackGroud from '../../Component/BackGrond';
import Menu from '../../Component/Menu';
import Error from '../../Component/Error';
import Title from '../../Component/Title';
import Input from '../../Component/Input';
import Button from '../../Component/Button';

class SendRequestToStore extends Component {
 
    constructor() {
        super();
        this.state = {
            stores: this.create_stores(),
        };
    }
    
    /**
     * mock of stores
     */
    create_stores() {
        let output = [];
        for (let i = 0; i < 5; i++) {
          output.push({
            name: "Store " + i,
            description: "Description " + i,
          });
        }
        return output;
    }

    render() {
        return (
            <BackGroud>
                <Menu/>
                <div>
                    <Title title='Stores:' />
                    
                </div>
            </BackGroud>
        );
    }

}

export default SendRequestToStore;