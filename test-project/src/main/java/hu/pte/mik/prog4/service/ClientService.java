package hu.pte.mik.prog4.service;


import hu.pte.mik.prog4.model.Client;

public interface ClientService<T extends Client> {

    void pay(T client);

    void receiveService(T client);

}
