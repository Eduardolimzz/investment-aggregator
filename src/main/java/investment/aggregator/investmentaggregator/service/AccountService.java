package investment.aggregator.investmentaggregator.service;

import investment.aggregator.investmentaggregator.dto.AccountResponseDto;
import investment.aggregator.investmentaggregator.dto.AccountStockResponseDto;
import investment.aggregator.investmentaggregator.dto.AssociateAccountStockDto;
import investment.aggregator.investmentaggregator.entity.AccountStock;
import investment.aggregator.investmentaggregator.entity.AccountStockId;
import investment.aggregator.investmentaggregator.repository.AccountRepository;
import investment.aggregator.investmentaggregator.repository.AccountStockRepository;
import investment.aggregator.investmentaggregator.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;

    public AccountService(StockRepository stockRepository,
                          AccountRepository accountRepository,
                          AccountStockRepository accountStockRepository) {
        this.stockRepository = stockRepository;
        this.accountRepository = accountRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(String accountId, AssociateAccountStockDto dto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );
        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDto> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as -> new AccountStockResponseDto(
                        as.getStock().getStockId(),
                        as.getQuantity(),
                        calculateTotal(as)))
                .toList();
    }
    private Double calculateTotal(AccountStock accountStock) {
        return 0.0;
    }
}
