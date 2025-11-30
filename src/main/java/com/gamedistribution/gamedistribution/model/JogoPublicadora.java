package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representa a entidade JOGO_PUBLICADORA no banco de dados.
 * Mapeia a tabela de associação M:N entre Jogo e Publicadora.
 */
@Entity
@Table(name = "JOGO_PUBLICADORA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogoPublicadora {

    /**
     * Chave primária composta.
     */
    @EmbeddedId
    private JogoPublicadoraId id = new JogoPublicadoraId();

    /**
     * Relacionamento M:1 com Jogo. Mapeado pelo 'idJogo' na chave composta.
     * @JsonIgnore garante que esta referência não inicie um ciclo Jogo -> Publicadora -> JogoPublicadora -> Jogo.
     */
    @ManyToOne
    @MapsId("idJogo")
    @JoinColumn(name = "id_jogo", nullable = false)
    @JsonIgnore // Essencial para quebrar o ciclo
    private Jogo jogo;

    /**
     * Relacionamento M:1 com Publicadora. Mapeado pelo 'idPublicadora' na chave composta.
     * Queremos ver qual publicadora está sendo referenciada, então mantemos este campo.
     */
    @ManyToOne
    @MapsId("idPublicadora")
    @JoinColumn(name = "id_publicadora", nullable = false)
    private Publicadora publicadora;

    /**
     * Data de início da campanha de divulgação pela publicadora.
     */
    @Column(name = "data_inicio_divulgacao", nullable = false)
    private LocalDate dataInicioDivulgacao;
}