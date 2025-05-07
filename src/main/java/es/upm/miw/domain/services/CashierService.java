package es.upm.miw.domain.services;

import es.upm.miw.domain.exceptions.BadRequestException;
import es.upm.miw.domain.model.Cashier;
import es.upm.miw.domain.model.CashierClose;
import es.upm.miw.domain.model.CashierState;
import es.upm.miw.domain.persistence.CashierPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CashierService {

    private final CashierPersistence cashierPersistence;

    @Autowired
    public CashierService(CashierPersistence cashierPersistence) {
        this.cashierPersistence = cashierPersistence;
    }

    public void createOpened() {
        Cashier last = lastByOpenedAssure(false);
        Cashier newCashier = Cashier.builder()
                .id(UUID.randomUUID())
                .initialCash(last.getFinalCash())
                .openingDate(LocalDateTime.now())
                .cashSales(BigDecimal.ZERO)
                .cardSales(BigDecimal.ZERO)
                .usedVouchers(BigDecimal.ZERO)
                .deposit(BigDecimal.ZERO)
                .withdrawal(BigDecimal.ZERO)
                .comment("")
                .build();
        cashierPersistence.create(newCashier);
    }

    private Cashier lastByOpenedAssure(boolean opened) {
        Cashier last = cashierPersistence.findLast();
        if (last.isClosed() ^ opened) {
            return last;
        } else {
            String msg = opened ? "Open cashier was expected: " : "Close cashier was expected: ";
            throw new BadRequestException(msg + last.getId());
        }
    }

    public Cashier findLast() {
        return cashierPersistence.findLast();
    }

    public CashierState findLastState() {
        return new CashierState(cashierPersistence.findLast());
    }

    public Cashier close(CashierClose cashierClose) {
        Cashier lastCashier = lastByOpenedAssure(true);
        lastCashier.close(cashierClose.getFinalCash(), cashierClose.getFinalCard(), cashierClose.getComment());
        return cashierPersistence.update(lastCashier.getId(), lastCashier);
    }

    public Cashier addSale(BigDecimal cash, BigDecimal card, BigDecimal voucher) {
        Cashier lastCashier = lastByOpenedAssure(true);
        lastCashier.addSale(cash, card, voucher);
        return cashierPersistence.update(lastCashier.getId(), lastCashier);
    }
}

