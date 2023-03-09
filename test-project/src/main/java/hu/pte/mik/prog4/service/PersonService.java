package hu.pte.mik.prog4.service;


import hu.pte.mik.prog4.model.Person;
import hu.pte.mik.prog4.repository.PersonRepository;

import java.util.List;
import java.util.logging.Logger;

public class PersonService implements ClientService<Person> {

    private static final Logger LOGGER = Logger.getLogger(PersonService.class.toString());

    private final PersonRepository personRepository = new PersonRepository();

    @Override
    public List<Person> getAll() {
        return this.personRepository.listAll();
    }

    @Override
    public void pay(Person client) {
        LOGGER.info("pay" + client.toString());
    }

    @Override
    public void receiveService(Person client) {
        LOGGER.info("receive" + client.toString());
    }

}
