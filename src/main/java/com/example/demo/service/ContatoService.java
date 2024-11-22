package com.example.demo.service;

import com.example.demo.model.Contato;
import com.example.demo.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;

    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    // Método para salvar ou atualizar um contato com validações
    public Contato salvarContato(Contato contato) {
        // Validação de nome
        if (!StringUtils.hasText(contato.getNome())) {
            throw new IllegalArgumentException("O nome do contato é obrigatório.");
        }

        // Validação de e-mail
        if (!StringUtils.hasText(contato.getEmail()) || !isValidEmail(contato.getEmail())) {
            throw new IllegalArgumentException("O e-mail do contato é obrigatório e deve ser válido.");
        }

        // Validação de telefone (opcional)
        if (contato.getTelefone() != null && contato.getTelefone().length() < 10) {
            throw new IllegalArgumentException("O telefone, se fornecido, deve ter pelo menos 10 caracteres.");
        }

        // Verificar se o e-mail já existe no banco de dados
        Optional<Contato> contatoExistente = contatoRepository.findByEmail(contato.getEmail());
        if (contatoExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um contato com este e-mail.");
        }

        // Salvar o contato
        return contato;//contatoRepository.save(contato);
    }

    // Listar todos os contatos
    public List<Contato> listarContatos() {
        return contatoRepository.findAll();
    }

    // Buscar um contato por ID
    public Contato buscarContatoPorId(Long id) {
        return contatoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Contato não encontrado com o ID: " + id));
    }

    // Buscar um contato por e-mail
    public Contato buscarContatoPorEmail(String email) {
        return contatoRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Contato não encontrado com o e-mail: " + email));
    }

    // Excluir um contato
    public void excluirContato(Long id) {
        try {
            contatoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Não foi possível excluir o contato, pois ele está associado a outras informações.");
        }
    }

    // Validação de formato de e-mail (simplificada)
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
