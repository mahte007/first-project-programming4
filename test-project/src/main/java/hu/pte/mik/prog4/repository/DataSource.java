package hu.pte.mik.prog4.repository;

import hu.pte.mik.prog4.model.Client;
import hu.pte.mik.prog4.model.Company;
import hu.pte.mik.prog4.model.Person;
import hu.pte.mik.prog4.service.IdProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataSource {

    private static final DataSource DATA_SOURCE = new DataSource();

    private final IdProvider idProvider = IdProvider.getInstance();
    private final List<Client> dataList;

    private DataSource() {
        this.dataList = this.createDummyList()
                            .stream()
                            .map(this::convertToClient)
                            .collect(Collectors.toList());
    }

    public static DataSource getInstance() {
        return DATA_SOURCE;
    }

    public List<Person> getAllPerson() {
        return this.dataList.stream()
                            .filter(client -> client instanceof Person)
                            .map(client -> (Person) client)
                            .collect(Collectors.toList());
    }

    public List<Company> getAllCompany() {
        return this.dataList.stream()
                            .filter(client -> client instanceof Company)
                            .map(client -> (Company) client)
                            .collect(Collectors.toList());
    }

    private List<String[]> createDummyList() {
        List<String[]> list = new ArrayList<>();

        list.add(new String[]{ "P", "Pista", "address01", "22asdRA" });
        list.add(new String[]{ "C", "Google", "address02", "212421523264532" });
        list.add(new String[]{ "P", "Jóska", "address03", "25asd22A" });
        list.add(new String[]{ "C", "Microsoft", "address042", "51523515" });
        list.add(new String[]{ "P", "János", "address05", "12asd5A" });
        list.add(new String[]{ "C", "OTP", "address06", "1251522152" });

        return list;
    }

    private Client convertToClient(String[] strings) {
        switch (strings[0]) {
            case "P":
                return new Person(this.idProvider.nextId(), strings[1], strings[2], strings[3]);
            case "C":
                return new Company(this.idProvider.nextId(), strings[1], strings[2], strings[3]);
            default:
                throw new RuntimeException("Unknown client type: " + strings);
        }
    }

}
