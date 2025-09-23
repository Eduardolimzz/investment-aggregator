package investment.aggregator.investmentaggregator.controller;

import investment.aggregator.investmentaggregator.dto.AccountStockResponseDto; // Importe correto
import investment.aggregator.investmentaggregator.dto.AssociateAccountStockDto;
import investment.aggregator.investmentaggregator.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                               @RequestBody AssociateAccountStockDto dto){
        accountService.associateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> listStocks(@PathVariable("accountId") String accountId){
        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }
}