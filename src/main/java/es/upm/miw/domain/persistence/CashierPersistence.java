package es.upm.miw.domain.persistence;

import es.upm.miw.domain.model.Cashier;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CashierPersistence {

    Cashier findLast();

    Cashier create(Cashier cashier);

    Cashier update(UUID id, Cashier lastCashier);
}

