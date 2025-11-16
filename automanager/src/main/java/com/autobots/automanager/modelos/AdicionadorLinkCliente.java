package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.entidades.Cliente;

@Component
public class AdicionadorLinkCliente implements AdicionadorLink<Cliente> {

	@Override
	public void adicionarLink(List<Cliente> lista) {
		for (Cliente cliente : lista) {
			long id = cliente.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ClienteControle.class)
							.obterCliente(id))
					.withSelfRel();
			cliente.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Cliente objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.obterClientes())
				.withRel("clientes");
		objeto.add(linkProprio);
	}

    @Override
    public void adicionarLinkAtualizacao(Cliente objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ClienteControle.class)
                        .atualizarCliente(objeto))
                .withRel("Update (Cliente: " + objeto.getId() + ")");
        objeto.add(linkProprio);
    }

    @Override
    public void adicionarLinkExclusao(Cliente objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(ClienteControle.class)
                        .excluirCliente(objeto))
                .withRel("Delete (Cliente: " + objeto.getId() + ")");
        objeto.add(linkProprio);
    }
}