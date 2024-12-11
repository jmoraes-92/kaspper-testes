package com.orcamentos.kaspper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orcamentos.kaspper.exception.ResourceNotFoundException;
import com.orcamentos.kaspper.model.Cliente;
import com.orcamentos.kaspper.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvar(Cliente cliente) {
		if (clienteRepository.existsByEmail(cliente.getEmail())) {
			throw new IllegalArgumentException("Já existe um cliente com o email informado.");
		}
		return clienteRepository.save(cliente);
	}

	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}

	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
	}

	public void excluir(Long id) {
		if (!clienteRepository.existsById(id)) {
			throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
		}
		clienteRepository.deleteById(id);
	}
}
