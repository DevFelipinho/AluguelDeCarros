package web.trabalhodaw1.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import web.trabalhodaw1.model.Usuario;

@Entity
@Table(name = "cliente")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    @ToString.Include
    private String nome;

    @Column(nullable = false, unique = true)
    @ToString.Include
    private String cnh;

    @Column(name = "data_nascimento")
    @ToString.Include
    private LocalDate dataNascimento;

    @ToString.Include
    private String telefone;

}
