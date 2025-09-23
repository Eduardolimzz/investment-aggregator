package investment.aggregator.investmentaggregator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_users")
public class User {
    //esse atributo da nossa classe vai ser um identificador no banco de dados, userid Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)//cada inserção de um novo usuario vai criar automaticamente
    private UUID userid;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;
}