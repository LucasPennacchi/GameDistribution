package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe auxiliar para representar a chave primária composta da entidade JogoPublicadora.
 */
@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JogoPublicadoraId implements Serializable {

    /**
     * Mapeia explicitamente para a coluna 'id_jogo' no banco.
     * Isso impede que o Hibernate crie colunas duplicadas ou confusas.
     */
    @Column(name = "id_jogo")
    private Long idJogo;

    /**
     * Mapeia explicitamente para a coluna 'id_publicadora' no banco.
     */
    @Column(name = "id_publicadora")
    private Long idPublicadora;

    // Implementação manual de equals e hashCode para garantir consistência no JPA

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JogoPublicadoraId that = (JogoPublicadoraId) o;
        return Objects.equals(idJogo, that.idJogo) &&
                Objects.equals(idPublicadora, that.idPublicadora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, idPublicadora);
    }
}