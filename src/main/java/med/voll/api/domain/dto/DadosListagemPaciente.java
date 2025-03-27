package med.voll.api.domain.dto;

import med.voll.api.domain.model.Paciente;

public record DadosListagemPaciente(long id, String nome, String email, String cpf) {

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }

}
