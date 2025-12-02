package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

/**
 * Representa a entidade JOGO_PUBLICADORA no banco de dados.
 * Mapeia a tabela de associação M:N entre Jogo e Publicadora.
 */
@Entity
@Table(name = "JOGO_PUBLICADORA")
@Getter
@Setter
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class JogoPublicadora {

    /**
     * Chave primária composta (ID embutido).
     */
    @EmbeddedId
    private JogoPublicadoraId id = new JogoPublicadoraId();

    /**
     * Relacionamento M:1 com Jogo.
     * @MapsId("idJogo") diz ao Hibernate: "Use o valor em 'id.idJogo' para esta chave estrangeira".
     * @JoinColumn força o nome da coluna física para 'id_jogo', garantindo que bata com o banco.
     */
    @ManyToOne
    @MapsId("idJogo")
    @JoinColumn(name = "id_jogo", nullable = false)
    @JsonIgnore // Evita ciclo Jogo -> Publicacoes -> Jogo
    @EqualsAndHashCode.Exclude
    private Jogo jogo;

    /**
     * Relacionamento M:1 com Publicadora.
     * @MapsId("idPublicadora") usa o valor em 'id.idPublicadora'.
     */
    @ManyToOne
    @MapsId("idPublicadora")
    @JoinColumn(name = "id_publicadora", nullable = false)
    @EqualsAndHashCode.Exclude // Evita ciclo Publicadora -> Publicacoes -> Publicadora
    private Publicadora publicadora;

    @Column(name = "data_inicio_divulgacao", nullable = false)
    private LocalDate dataInicioDivulgacao;
}