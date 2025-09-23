    package investment.aggregator.investmentaggregator.repository;

    import investment.aggregator.investmentaggregator.entity.AccountStock;
    import investment.aggregator.investmentaggregator.entity.AccountStockId;
    import investment.aggregator.investmentaggregator.entity.User;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.UUID;

    @Repository
    public interface AccountStockRepository extends JpaRepository<AccountStock , AccountStockId> {
    }