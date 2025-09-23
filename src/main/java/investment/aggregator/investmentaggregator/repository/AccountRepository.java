    package investment.aggregator.investmentaggregator.repository;

    import investment.aggregator.investmentaggregator.entity.Account;
    import investment.aggregator.investmentaggregator.entity.User;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.UUID;

    @Repository
    public interface AccountRepository extends JpaRepository<Account, UUID> {
    }