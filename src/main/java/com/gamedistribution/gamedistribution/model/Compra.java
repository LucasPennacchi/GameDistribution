package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Representa a entidade COMPRA (carrinho/transação) no banco de dados.
 * Mapeia a tabela 'COMPRA'.
 */
@Entity
@Table(name = "COMPRA")
@Getter
@Setter
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    /**
     * Chave primária da entidade Compra. Mapeia a coluna 'id_compra'.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long id;

    /**
     * Data e hora exata em que a compra foi realizada. Campo obrigatório.
     */
    @Column(name = "data_compra", nullable = false)
    private LocalDateTime dataCompra;

    /**
     * Valor total pago na transação. Campo obrigatório.
     */
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    /**
     * Relacionamento N:1 com a entidade Cliente.
     * Mapeia a chave estrangeira 'id_cliente'.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    /**
     * Relacionamento 1:N com ItemCompra.
     * @JsonIgnore impede o ciclo recursivo Compra -> ItemCompra -> Compra.
     */
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ItemCompra> itensDaCompra;
}