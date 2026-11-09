package web.trabalhodaw1.model;

import java.io.Serializable;

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

import web.trabalhodaw1.common.enums.PapelUsuario;

@Entity
@Table(name = "usuario")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "geradorUsuario",
            sequenceName = "usuario_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "geradorUsuario",
            strategy = GenerationType.SEQUENCE
    )
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome_usuario", nullable = false, unique = true)
    @ToString.Include
    private String nomeUsuario;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ToString.Include
    private PapelUsuario papel;

}
