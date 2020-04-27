package DataAPI.DiscountData.TermData;

import java.util.List;

public class AndTermData {
    private List<TermData> terms;

    public AndTermData(List<TermData> terms) {
        this.terms = terms;
    }

    public List<TermData> getTerms() {
        return terms;
    }

    public void setTerms(List<TermData> terms) {
        this.terms = terms;
    }
}
