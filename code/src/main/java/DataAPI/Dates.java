package DataAPI;

public class Dates {
    private DateData fromDate;
    private DateData toDate;

    public Dates(DateData fromDate, DateData toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DateData getFromDate() {
        return fromDate;
    }

    public DateData getToDate() {
        return toDate;
    }
}
