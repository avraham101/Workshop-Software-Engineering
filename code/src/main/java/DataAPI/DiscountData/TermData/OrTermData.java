package DataAPI.DiscountData.TermData;

import DataAPI.DiscountData.DiscountData;

import java.util.List;

class OrTermData implements DiscountData {
    private List<TermData> terms;

    public OrTermData(List<TermData> terms) {
        this.terms=terms;
    }

    public List<TermData> getTerms() {
        return terms;
    }

    public void setTerms(List<TermData> terms) {
        this.terms = terms;
    }
}
