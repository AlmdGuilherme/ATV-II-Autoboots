package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.modelos.DocumentoSelecionador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private DocumentoRepositorio documentoRepositorio;
    @Autowired
    private DocumentoSelecionador selecionador;
    @Autowired
    private AdicionadorLinkDocumento adicionadorLinkDocumento;

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
        List<Documento> documentos = documentoRepositorio.findAll();
        Documento documento = selecionador.selecionar(documentos, id);
        if (documento == null) {
            ResponseEntity<Documento> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        } else {
            adicionadorLinkDocumento.adicionarLink(documento);
            ResponseEntity<Documento> response = new ResponseEntity<>(documento, HttpStatus.FOUND);
            return response;
        }
    }

    @GetMapping("/documentos")
    public ResponseEntity<List<Documento>> obterDocumentos() {
        List<Documento> documentos = documentoRepositorio.findAll();
        if (documentos.isEmpty()) {
            ResponseEntity<List<Documento>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        } else {
            adicionadorLinkDocumento.adicionarLink(documentos);
            ResponseEntity<List<Documento>> response = new ResponseEntity<>(documentos ,HttpStatus.FOUND);
            return response;
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarDocumento(@RequestBody Documento documento) {
        documentoRepositorio.save(documento);
        return ResponseEntity.status(HttpStatus.CREATED).body("Documento cadastrado e salvo com sucesso!");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarDocumento(@RequestBody Documento docUpdate) {
        Documento documento = documentoRepositorio.getById(docUpdate.getId());
        if (documento != null) {
            DocumentoAtualizador docAtualizador = new DocumentoAtualizador();
            docAtualizador.atualizar(documento, docUpdate);
            documentoRepositorio.save(documento);
            return ResponseEntity.status(HttpStatus.OK).body("Documento atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível atualizar o documento: Verifique os dados inseridos ou se o documento existe.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> excluirDocumento(@RequestBody Documento docDelete) {
        Documento documento = documentoRepositorio.getById(docDelete.getId());
        if (documento != null) {
            documentoRepositorio.delete(documento);
            return  ResponseEntity.status(HttpStatus.OK).body("Documento removido com sucesso!");
        } else {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível deletar o documento: Verifique os dados inseridos ou se o documento existe.");
        }
    }
}
