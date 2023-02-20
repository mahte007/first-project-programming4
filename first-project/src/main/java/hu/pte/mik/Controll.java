package hu.pte.mik;

import hu.pte.mik.model.Client;
import hu.pte.mik.model.Company;
import hu.pte.mik.model.Person;
import hu.pte.mik.service.CompanyService;
import hu.pte.mik.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class Controll {

    private static final Logger LOGGER = Logger.getLogger(Controll.class.toString());

    private final CompanyService companyService = new CompanyService();
    private final PersonService personService = new PersonService();
    private final AtomicLong id = new AtomicLong(0L);

    public void start() {
        LOGGER.info("Hello world!");
        List<String[]> list = this.createDummyList();

        for (String[] strings : list) {
            Client client = this.convertToClient(strings);

            this.callService(client);
        }
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
            case "P" -> new Person(this.id.getAndIncrement(), strings[1], strings[2], strings[3]);
            case "C" -> new Company(this.id.getAndIncrement(), strings[1], strings[2], strings[3]);
            default -> throw new RuntimeException("Unknown client type: " + strings);
        };
    }

    private void callService(Client client) {
        if (client instanceof Person person) {
            this.personService.pay(person);
        } else if (client instanceof Company company) {
            this.companyService.pay(company);
        } else {
            throw new RuntimeException("Unknown client type: " + client);
        }
    }

}
