package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.modelos.EnderecoSelecionador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio enderecoRepositorio;
    @Autowired
    private EnderecoSelecionador selecionador;
    @Autowired
    private AdicionadorLinkEndereco adicionadorLinkEndereco;

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterEndereco(@PathVariable long id){
        List<Endereco> enderecos = enderecoRepositorio.findAll();
        Endereco endereco = selecionador.selecionar(enderecos, id);
        if (endereco == null){
            ResponseEntity<Endereco> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return  response;
        } else {
            adicionadorLinkEndereco.adicionarLink(endereco);
            ResponseEntity<Endereco> response = new ResponseEntity<>(endereco, HttpStatus.FOUND);
            return response;
        }
    }

    @GetMapping("/enderecos")
    public ResponseEntity<List<Endereco>> obterEnderecos(){
        List<Endereco> enderecos = enderecoRepositorio.findAll();
        if(enderecos.isEmpty()) {
            ResponseEntity<List<Endereco>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        } else {
            adicionadorLinkEndereco.adicionarLink(enderecos);
            ResponseEntity<List<Endereco>> response = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
            return  response;
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastarEndereco(@RequestBody Endereco endereco) {
        enderecoRepositorio.save(endereco);
        return ResponseEntity.status(HttpStatus.OK).body("Endereço cadastrado com sucesso!");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarEnderco(@RequestBody Endereco addrUpdate) {
        Endereco endereco = enderecoRepositorio.getById(addrUpdate.getId());
        if (endereco != null) {
            EnderecoAtualizador addrAtualizador = new EnderecoAtualizador();
            addrAtualizador.atualizar(endereco, addrUpdate);
            enderecoRepositorio.save(endereco);
            return ResponseEntity.status(HttpStatus.OK).body("Endereço atualizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível atualizar o endereço: Verifique os dados inseridos ou se o endereço existe");
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluirEndereco(@RequestBody Endereco endDelete) {
        Endereco endereco = enderecoRepositorio.getById(endDelete.getId());
        if (endereco != null) {
            enderecoRepositorio.delete(endereco);
            return ResponseEntity.status(HttpStatus.OK).body("Endereço deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível excluir esse endereço: Verifique os dados inseridos ou se o endereço existe!");
        }
    }

}
