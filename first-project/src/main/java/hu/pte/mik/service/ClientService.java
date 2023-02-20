package hu.pte.mik.service;

import hu.pte.mik.model.Client;

public interface ClientService<T extends Client> {

    void pay(T client);

    void receiveService(T client);

}
