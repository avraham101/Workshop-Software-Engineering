package Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="revenue")
public class Revenue implements Serializable {

    @Id
    @Column(name="date")
    private LocalDate date;

    @Column(name="profit")
    private double profit;

    public Revenue(double profit) {
        this.profit = profit;
        this.date=LocalDate.now();
    }

    public Revenue() {
    }

    public LocalDate getDate() {
        return date;
    }

    public double getProfit() {
        return profit;
    }

    public void addProfit(double earnings){
        this.profit+=earnings;
    }
}
