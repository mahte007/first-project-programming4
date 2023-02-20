package hu.pte.mik.model;

import java.util.Objects;

public class Company extends Client {

    private String taxNumber;

    public Company(Long id, String name, String address, String taxNumber) {
        super(id, name, address);
        this.taxNumber = taxNumber;
    }

    public String getTaxNumber() {
        return this.taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Company company = (Company) o;

        return Objects.equals(this.taxNumber, company.taxNumber);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (this.taxNumber != null ? this.taxNumber.hashCode() : 0);
        return result;
    }
}
