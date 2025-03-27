package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.DadosAtualizacaoMedico;
import med.voll.api.domain.dto.DadosCadastrosMedicos;
import med.voll.api.domain.dto.DadosListagemMedico;
import med.voll.api.domain.dto.DadosDetalhamentoMedico;
import med.voll.api.domain.model.Medico;
import med.voll.api.domain.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository; //estancia o repositório onde buscará os dados do banco

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastrosMedicos dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);
        // Para o cadastro, eu preciso retornar um código 201, pois esta requisição precisa retornar ao fron da aplicação
        // algumas ifnormações como o body com as informações que foram cadastradas e o location da requisição.

        var uri = uriBuilder.path("medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) { //definindo a paginação na listagem dos resultados
        var page = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.exclur();

        return ResponseEntity.noContent().build();
        //utilizando o reponseEntity para definir a requisição http. irá retornar o código 204
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
