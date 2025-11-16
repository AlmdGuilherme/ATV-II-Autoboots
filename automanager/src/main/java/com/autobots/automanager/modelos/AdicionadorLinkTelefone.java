package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilderFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkTelefone implements  AdicionadorLink<Telefone>{

    private final WebMvcLinkBuilderFactory webMvcLinkBuilderFactory;

    public AdicionadorLinkTelefone(WebMvcLinkBuilderFactory webMvcLinkBuilderFactory) {
        this.webMvcLinkBuilderFactory = webMvcLinkBuilderFactory;
    }

    @Override
    public void adicionarLink(List<Telefone> lista) {
        for (Telefone telefone : lista) {
            long id = telefone.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(TelefoneControle.class)
                            .obterTelefone(id))
                    .withSelfRel();
            telefone.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Telefone objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(TelefoneControle.class)
                        .obterTelefones())
                .withRel("telefones");
        objeto.add(linkProprio);
    }

    @Override
    public void adicionarLinkAtualizacao(Telefone objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(TelefoneControle.class)
                        .atualizarTelefone(objeto))
                .withRel("Update (Telefone: "  + objeto.getId() + ")");
        objeto.add(linkProprio);
    }

    @Override
    public void adicionarLinkExclusao(Telefone objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(TelefoneControle.class)
                        .deletarTelefone(objeto))
                .withRel("Delete (Telefone: "  + objeto.getId() + ")");
        objeto.add(linkProprio);
    }
}
