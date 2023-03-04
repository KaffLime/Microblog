package com.artem.korolchuk.study.microblog.service;

import com.artem.korolchuk.study.microblog.entity.Client;
import java.util.List;

public interface ClientService {
    void create(Client client);
    List<Client> readAll();
    Client read(int id);
    boolean update(Client client, int id);
    boolean delete(int id);
}
