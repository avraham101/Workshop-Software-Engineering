package DataAPI;

import java.io.Serializable;


public enum OpCode implements Serializable {

    Success,
    Store_Not_Found,
    User_NorFound,
    Invalid_Product,
    Not_Login, Dont_Have_Permission, Already_Exists,

}
