package investment.aggregator.investmentaggregator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_stock")
public class Stock {

    @Id
    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "description")
    private String description;
}
