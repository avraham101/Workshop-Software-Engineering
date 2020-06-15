package Domain.Notification;

import DataAPI.OpCode;
import Domain.DayVisit;

public class VisitNotification extends Notification<DayVisit> {
    private DayVisit visit;

    public VisitNotification(DayVisit todayVisit) {
        super(OpCode.Day_Visit);
        this.visit=todayVisit;
    }

    @Override
    public DayVisit getValue() {
        return visit;
    }

}
