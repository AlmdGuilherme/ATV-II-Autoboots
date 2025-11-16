package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.entidades.Documento;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento> {

    @Override
    public void adicionarLink(List<Documento> lista) {
        for (Documento documento: lista) {
            long id = documento.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .obterDocumento(id))
                    .withSelfRel();
            documento.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Documento objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .obterDocumentos())
                .withRel("documentos");
        objeto.add(linkProprio);
    }

    @Override
    public void adicionarLinkAtualizacao(Documento objeto) {
        Link linkProporio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .atualizarDocumento(objeto))
                .withRel("Update (Doc: " + objeto.getId() + ")");
        objeto.add(linkProporio);
    }

    @Override
    public void adicionarLinkExclusao(Documento objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .excluirDocumento(objeto))
                .withRel("Delete (Doc: " + objeto.getId() + ")");
        objeto.add(linkProprio);
    }
}
