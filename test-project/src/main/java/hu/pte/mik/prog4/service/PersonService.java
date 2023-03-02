package hu.pte.mik.prog4.service;


import hu.pte.mik.prog4.model.Person;

import java.util.logging.Logger;

public class PersonService implements ClientService<Person> {

    private static final Logger LOGGER = Logger.getLogger(PersonService.class.toString());

    @Override
    public void pay(Person client) {
        LOGGER.info("pay" + client.toString());
    }

    @Override
    public void receiveService(Person client) {
        LOGGER.info("receive" + client.toString());
    }

}
