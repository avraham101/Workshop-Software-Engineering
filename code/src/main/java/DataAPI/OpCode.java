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
    Invalid_Payment_Data,
    Invalid_Delivery_Data,
    Fail_Buy_Cart,
    Payment_Reject,
    Supply_Reject,
    Not_Found,
    InvalidRequest,
    Invalid_Store_Details,
    Store_Doesnt_Exist,
    Invalid_Review,
    Cant_Add_Review,
    Invalid_Request,
    Null_Request,
    Invalid_Discount,
    No_Stores_To_Manage,
    Invalid_Policy,
    Not_Stands_In_Policy,
    User_Name_Already_Exist,
    NOT_ADMIN,
    Reply_Request,
    Removed_From_Management,
    Buy_Product,
    INVALID_DATE,
    Add_Manager,
    DB_Down,
    Already_Owner,
    ;

}
