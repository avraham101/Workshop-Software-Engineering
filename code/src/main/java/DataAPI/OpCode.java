package DataAPI;

import java.io.Serializable;


public enum OpCode implements Serializable {

    Success,
    Store_Not_Found,
    User_NorFound,
    Invalid_Product,
    Invalid_Register_Details,
    Hash_Fail,
    Invalid_Login_Details,


}
