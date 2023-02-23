package hu.pte.mik.model;

import hu.pte.mik.xml.XmlElement;

import java.util.Objects;

public class Person extends Client {

    @XmlElement
    private String idNumber;

    public Person(Long id, String name, String address, String idNumber) {
        super(id, name, address);
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Person person = (Person) o;

        return Objects.equals(this.idNumber, person.idNumber);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (this.idNumber != null ? this.idNumber.hashCode() : 0);
        return result;
    }
}
