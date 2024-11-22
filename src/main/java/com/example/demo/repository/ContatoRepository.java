package com.example.demo.repository;

import com.example.demo.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    // Exemplo de consulta personalizada para buscar um contato pelo email
    Optional<Contato> findByEmail(String email);

    // Adicione outras consultas personalizadas, conforme necessário
}
