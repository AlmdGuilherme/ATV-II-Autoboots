package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco> {

    @Override
    public  void adicionarLink(List<Endereco> lista){
        for (Endereco endereco: lista) {
            long id = endereco.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(EnderecoControle.class)
                            .obterEndereco(id))
                    .withSelfRel();
            endereco.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Endereco objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .obterEnderecos())
                .withRel("enderecos");
        objeto.add(linkProprio);
    }

    @Override
    public void adicionarLinkAtualizacao(Endereco objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .atualizarEnderco(objeto))
                .withRel("Update (Endereço: " + objeto.getId() + ")");
        objeto.add(linkProprio);
    }

    @Override
    public void adicionarLinkExclusao(Endereco objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EnderecoControle.class)
                        .excluirEndereco(objeto))
                .withRel("Delete (Endereço: " + objeto.getId() + ")");
        objeto.add(linkProprio);
    }
}
