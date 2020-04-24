package DataAPI;

import java.io.Serializable;


public enum OpCode implements Serializable {

    Success,
    Store_Not_Found,
    User_Not_Found,
    Invalid_Product,
    Invalid_Register_Details,
    Hash_Fail,
    Invalid_Login_Details,
    Not_Valid_Filter,
    Not_Login,
    Dont_Have_Permission,
    Already_Exists,
    Invalid_Permissions,
    Not_Found,
    InvalidRequest,

}
