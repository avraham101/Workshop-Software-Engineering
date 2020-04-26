package Domain;

import java.util.Objects;

public class Discount1 {
    private double percentage;

    public Discount1(double percentage) {
        this.percentage = percentage;
    }

    // ============================ getters & setters ============================ //

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    // ============================ getters & setters ============================ //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount1 discount1 = (Discount1) o;
        return Double.compare(discount1.percentage, percentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentage);
    }
}
