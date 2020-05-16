package Domain;

import DataAPI.PurchaseTypeData;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

import static DataAPI.PurchaseTypeData.*;

@Entity
@Table(name="purchase_type")
public class PurchaseType implements Serializable {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name="purchaseType")
    private PurchaseTypeData purchaseTypeData= IMMEDDIATE;


    public PurchaseType() {
        //this.purchaseTypeData = PurchaseTypeData.IMMEDDIATE;//
    }



    /**
     *
     * @return immediate purchaseTypeData
     */
    public PurchaseTypeData getPurchaseTypeData() {
        return this.purchaseTypeData;
    }

}
