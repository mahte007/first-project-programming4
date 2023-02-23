package hu.pte.mik;

import hu.pte.mik.model.Client;
import hu.pte.mik.model.Company;
import hu.pte.mik.model.Person;
import hu.pte.mik.service.CompanyService;
import hu.pte.mik.service.IdProvider;
import hu.pte.mik.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Controll {

    private static final Logger LOGGER = Logger.getLogger(Controll.class.toString());

    private final CompanyService companyService = new CompanyService();
    private final PersonService personService = new PersonService();
    private final IdProvider idProvider = IdProvider.getInstance();

    public void start() {
        LOGGER.info("Hello world!");

        this.createDummyList()
            .stream()
            .map(this::convertToClient)
            .forEach(client -> new Thread(() -> this.callService(client)).start());
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
        return switch (strings[0]) {
            case "P" -> new Person(this.idProvider.nextId(), strings[1], strings[2], strings[3]);
            case "C" -> new Company(this.idProvider.nextId(), strings[1], strings[2], strings[3]);
            default -> throw new RuntimeException("Unknown client type: " + strings);
        };
    }

    private void callService(Client client) {
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (client instanceof Person person) {
            this.personService.pay(person);
        } else if (client instanceof Company company) {
            this.companyService.pay(company);
        } else {
            throw new RuntimeException("Unknown client type: " + client);
        }
    }

}
