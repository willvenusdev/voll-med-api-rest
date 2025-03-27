package med.voll.api.domain.dto;

import med.voll.api.domain.model.Endereco;
import med.voll.api.domain.model.Paciente;

public record DadosDetalhamentoPaciente(Long id, String nome, String telefone, String cpf, Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getCpf(), paciente.getNome(), paciente.getTelefone(), paciente.getEndereco());
    }

}
