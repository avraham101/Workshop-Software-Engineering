package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.Date;

public class DiscountTestData {
    private double percentage;
    private Date expirationDate;

    public DiscountTestData(double percentage, Date expirationDate) {
        this.percentage = percentage;
        this.expirationDate = expirationDate;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountTestData that = (DiscountTestData) o;

        if (Double.compare(that.percentage, percentage) != 0) return false;
        return expirationDate != null ? expirationDate.equals(that.expirationDate) : that.expirationDate == null;
    }
}
