package Domain.PurchasePolicy;

import DataAPI.OpCode;
import DataAPI.PaymentData;
import DataAPI.Response;
import Domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "system_policy")
public class SystemPurchasePolicy extends PurchasePolicy {

    @Column(name="age")
    private int age;

    public SystemPurchasePolicy(int age) {
        this.age = age;
    }

    public SystemPurchasePolicy() {
    }

    @Override
    public boolean isValid() {
        return (age >= 0);
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>();
    }

    @Override
    public Response<Boolean> standInPolicy(PaymentData paymentData, String country,
                          HashMap<Product, Integer> products) {
        if (paymentData.getAge() >= age) {
            return new Response<>(true, OpCode.Success);
        }
        return new Response<>(false, OpCode.Age_Policy_Failed);
    }
}
