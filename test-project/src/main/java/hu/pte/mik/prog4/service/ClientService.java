package hu.pte.mik.prog4.service;


import hu.pte.mik.prog4.model.Client;

import java.util.List;

public interface ClientService<T extends Client> {

    List<T> getAll();

    void pay(T client);

    void receiveService(T client);

}
