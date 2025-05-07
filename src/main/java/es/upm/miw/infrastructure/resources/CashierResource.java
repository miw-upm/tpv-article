package es.upm.miw.infrastructure.resources;

import es.upm.miw.domain.model.Cashier;
import es.upm.miw.domain.model.CashierClose;
import es.upm.miw.domain.model.CashierState;
import es.upm.miw.domain.services.CashierService;
import es.upm.miw.infrastructure.resources.dtos.CashierLastDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize(Security.ADMIN_MANAGER_OPERATOR)
@RequestMapping(CashierResource.CASHIERS)
public class CashierResource {
    public static final String CASHIERS = "/cashiers";

    public static final String LAST = "/last";
    public static final String STATE = "/state";

    private final CashierService cashierService;

    @Autowired
    public CashierResource(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    @PostMapping
    public void createOpened() {
        this.cashierService.createOpened();
    }

    @GetMapping(value = LAST)
    public CashierLastDto findLast() {
        return new CashierLastDto(this.cashierService.findLast());
    }

    @GetMapping(value = LAST + STATE)
    public CashierState findLastState() {
        return this.cashierService.findLastState();
    }

    @PatchMapping(value = LAST)
    public Cashier closeCashier(@Valid @RequestBody CashierClose cashierClose) {
        return cashierService.close(cashierClose);
    }
}
