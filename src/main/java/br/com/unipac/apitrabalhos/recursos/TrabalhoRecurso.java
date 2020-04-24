package br.com.unipac.apitrabalhos.recursos;


import br.com.unipac.apitrabalhos.model.domain.Trabalho;
import br.com.unipac.apitrabalhos.model.domain.repository.TrabalhoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/trabalhos")
public class TrabalhoRecurso {

    @Autowired
    private TrabalhoRepository trabalhoRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<Trabalho> getTrabalho(@PathVariable("id") Long id){
        Optional<Trabalho> trabalho = trabalhoRepositorio.findById(id);

        if (!trabalho.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trabalho.get());
    }

    @PostMapping
    public ResponseEntity<Trabalho> addTrabalho(@RequestBody Trabalho trabalho){
        log.info("Gravou o trabalho: " +trabalho.toString());

        Trabalho saved = trabalhoRepositorio.save(trabalho);

        URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(url).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trabalho> updateTrabalho(@PathVariable("id") Long id, @RequestBody Trabalho novoTrabalho){
        Optional<Trabalho> trabalho = trabalhoRepositorio.findById(id);

        if (trabalho.isPresent()) {
            Trabalho trabalhoParaUpdate = trabalho.get();
            trabalhoParaUpdate.setTituloDoTrabalho(novoTrabalho.getTituloDoTrabalho());
            trabalhoParaUpdate.setProfessor(novoTrabalho.getProfessor());
            trabalhoParaUpdate.setData(novoTrabalho.getData());

            trabalhoRepositorio.save(trabalhoParaUpdate);

            return ResponseEntity.ok(trabalhoParaUpdate);
        }else{
            log.info("Trabalho nao pode ser alterado: " + trabalho.toString());
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        try {
            trabalhoRepositorio.deleteById(id);
            return ResponseEntity.ok("Dados Deletados");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Dados nao pode ser removidos" + e.getMessage());
        }
    }

}
