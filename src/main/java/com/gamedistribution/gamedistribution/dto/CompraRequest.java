package com.gamedistribution.gamedistribution.dto;

/**
 * DTO para receber os dados de uma nova compra via requisição POST.
 */
public class CompraRequest {
    private Long clienteId;
    private Long jogoId;
    // private Integer quantidade; // Adicione se necessário

    // Getters
    public Long getClienteId() {
        return clienteId;
    }

    public Long getJogoId() {
        return jogoId;
    }

    // Setters (Opcional, mas útil para deserialização do JSON)
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setJogoId(Long jogoId) {
        this.jogoId = jogoId;
    }
}