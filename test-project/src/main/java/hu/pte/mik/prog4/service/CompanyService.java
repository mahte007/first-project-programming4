package hu.pte.mik.prog4.service;


import hu.pte.mik.prog4.model.Company;

import java.util.Objects;
import java.util.logging.Logger;

public class CompanyService implements ClientService<Company> {

    private static final Logger LOGGER = Logger.getLogger(CompanyService.class.toString());

    @Override
    public void pay(Company client) {
        LOGGER.info(Objects.toString(client));
    }

    @Override
    public void receiveService(Company client) {
        LOGGER.info("receive" + client.toString());
    }

}
