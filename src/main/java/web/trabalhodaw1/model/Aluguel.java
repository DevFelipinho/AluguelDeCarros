package web.trabalhodaw1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import web.trabalhodaw1.model.Cliente;
import web.trabalhodaw1.model.Carro;

@Entity
@Table(name = "aluguel")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Aluguel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "geradorAluguel",
            sequenceName = "aluguel_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "geradorAluguel",
            strategy = GenerationType.SEQUENCE
    )
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carro_id", nullable = false)
    private Carro carro;

    @Column(name = "valor_diaria", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal valorDiaria;

    @Column(name = "data_aluguel", nullable = false)
    @ToString.Include
    private LocalDate dataAluguel;

    @Column(name = "data_retorno")
    @ToString.Include
    private LocalDate dataRetorno;

    @Column(nullable = false)
    @ToString.Include
    private boolean pago = false;

}
