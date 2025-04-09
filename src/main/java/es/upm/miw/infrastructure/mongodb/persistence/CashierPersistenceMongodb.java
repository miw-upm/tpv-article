package es.upm.miw.infrastructure.mongodb.persistence;

import es.upm.miw.domain.exceptions.ConflictException;
import es.upm.miw.domain.exceptions.NotFoundException;
import es.upm.miw.domain.model.Cashier;
import es.upm.miw.domain.persistence.CashierPersistence;
import es.upm.miw.infrastructure.mongodb.entities.CashierEntity;
import es.upm.miw.infrastructure.mongodb.repositories.CashierRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CashierPersistenceMongodb implements CashierPersistence {

    private final CashierRepository cashierRepository;

    @Autowired
    public CashierPersistenceMongodb(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    @Override
    public Cashier findLast() {
        return this.cashierRepository.findFirstByOrderByOpeningDateDesc()
                .orElseThrow(() -> new ConflictException("Non existent cashier"))
                .toCashier();
    }

    @Override
    public Cashier create(Cashier cashier) {
        return this.cashierRepository.save(new CashierEntity(cashier)).toCashier();
    }

    @Override
    public Cashier update(UUID id, Cashier cashier) {
        CashierEntity cashierEntity = this.cashierRepository.findById(id).stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Non existent cashier: " + id));
        BeanUtils.copyProperties(cashier, cashierEntity);
        return this.cashierRepository.save(cashierEntity).toCashier();
    }
}
