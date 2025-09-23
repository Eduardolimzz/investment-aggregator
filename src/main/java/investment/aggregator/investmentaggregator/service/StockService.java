package investment.aggregator.investmentaggregator.service;

import investment.aggregator.investmentaggregator.dto.CreateStockDto;
import investment.aggregator.investmentaggregator.entity.Stock;
import investment.aggregator.investmentaggregator.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {

        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}
