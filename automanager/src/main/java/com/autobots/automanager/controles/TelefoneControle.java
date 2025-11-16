package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.modelos.TelefoneSelecionador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private TelefoneRepositorio telefoneRepositorio;
    @Autowired
    private TelefoneSelecionador selecionador;
    @Autowired
    private AdicionadorLinkTelefone adicionadorLinkTelefone;

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> obterTelefone(@PathVariable long id){
        List<Telefone> telefones = telefoneRepositorio.findAll();
        Telefone telefone = selecionador.selecionar(telefones, id);
        if (telefone == null) {
            ResponseEntity<Telefone> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return  response;
        } else {
            adicionadorLinkTelefone.adicionarLink(telefone);
            ResponseEntity<Telefone> response = new ResponseEntity<>(telefone, HttpStatus.FOUND);
            return response;
        }
    }

    @GetMapping("/telefones")
    public ResponseEntity<List<Telefone>> obterTelefones() {
        List<Telefone> telefones = telefoneRepositorio.findAll();
        if (telefones.isEmpty()) {
            ResponseEntity<List<Telefone>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        } else {
            adicionadorLinkTelefone.adicionarLink(telefones);
            ResponseEntity<List<Telefone>> response = new ResponseEntity<>(telefones, HttpStatus.FOUND);
            return response;
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarTelefone(@RequestBody Telefone telefone) {
        telefoneRepositorio.save(telefone);
        return ResponseEntity.status(HttpStatus.CREATED).body("Telefone cadastrado com sucesso!");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarTelefone(@RequestBody Telefone telUpdate) {
        Telefone telefone = telefoneRepositorio.getById(telUpdate.getId());
        if (telefone != null) {
            TelefoneAtualizador telAtualizador = new TelefoneAtualizador();
            telAtualizador.atualizar(telefone, telUpdate);
            telefoneRepositorio.save(telefone);
            return ResponseEntity.status(HttpStatus.OK).body("Telefone atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível atualizar o telefone: Verifique os dados inseridos ou se o telefone existe!");
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> deletarTelefone(@RequestBody Telefone telDelete) {
        Telefone telefone = telefoneRepositorio.getById(telDelete.getId());
        if (telefone != null) {
            telefoneRepositorio.delete(telefone);
            return ResponseEntity.status(HttpStatus.OK).body("Telefone excluido com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível excluir este telefone: Verifique o ID inserido ou se o telefone existe!");
        }
    }
}
