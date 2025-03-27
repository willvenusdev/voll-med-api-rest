package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
        @NotNull
        long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
