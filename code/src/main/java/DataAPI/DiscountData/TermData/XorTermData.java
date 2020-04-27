package DataAPI.DiscountData.TermData;

import java.util.List;

public class XorTermData {
    private List<TermData> terms;

    public XorTermData(List<TermData> terms) {
        this.terms = terms;
    }

    public List<TermData> getTerms() {
        return terms;
    }

    public void setTerms(List<TermData> terms) {
        this.terms = terms;
    }
}
