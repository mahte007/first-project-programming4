package hu.pte.mik.model;

import hu.pte.mik.xml.XmlElement;
import hu.pte.mik.xml.XmlSerializable;

import java.util.Objects;

@XmlSerializable
public abstract class Client {

    @XmlElement
    private Long id;
    @XmlElement
    private String name;
    @XmlElement
    private String address;

    public Client(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!Objects.equals(this.id, client.id)) return false;
        if (!Objects.equals(this.name, client.name)) return false;
        return Objects.equals(this.address, client.address);
    }

    @Override
    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        result = 31 * result + (this.address != null ? this.address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", address='" + this.address + '\'' +
                '}';
    }
}
