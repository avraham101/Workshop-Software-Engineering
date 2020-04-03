package Domain;

import java.util.Objects;

public class Discount {
    private double percentage;

    public Discount(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Double.compare(discount.percentage, percentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentage);
    }
}
