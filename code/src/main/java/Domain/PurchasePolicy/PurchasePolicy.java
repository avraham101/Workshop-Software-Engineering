package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import DataAPI.Response;
import Domain.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="policy")
public abstract class PurchasePolicy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pol_id")
    protected Integer id;

    public Integer getId() {
        return id;
    }

    /**
     * check that the policy is valid
     * @return - true if valid, false if not
     */
    public abstract  boolean isValid();

    /**
     * get the list of the products of each policy
     * @return - List of products
     */
    public abstract List<String> getProducts();

    /**
     * check if the buy is stand in the policy terms
     * @param paymentData - the data of the payment
     * @param country - the country of the user
     * @param products - the products of the basket
     * @return - true if stand, false if not
     */
    public abstract Response<Boolean> standInPolicy(PaymentData paymentData, String country, HashMap<Product, Integer> products);

}
