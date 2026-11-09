package web.trabalhodaw1.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import web.trabalhodaw1.common.enums.Status;

@Entity
@Table(name = "carro")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Carro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "geradorCarro",
            sequenceName = "carro_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "geradorCarro",
            strategy = GenerationType.SEQUENCE
    )
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @ToString.Include
    private String placa;

    @Column(nullable = false)
    @ToString.Include
    private String cor;

    @Column(nullable = false)
    @ToString.Include
    private String modelo;

    @Column(nullable = false)
    @ToString.Include
    private String marca;

    @Column(name = "valor_diaria", nullable = false, precision = 10, scale = 2)
    @ToString.Include
    private BigDecimal valorDiaria;

    @Column(nullable = false)
    @ToString.Include
    private boolean disponivel = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ToString.Include
    private Status status = Status.ATIVO;

}
