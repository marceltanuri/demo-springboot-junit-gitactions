package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contatos")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID gerado automaticamente
    private Long id;

    @Column(nullable = false) // Define que o nome é obrigatório
    private String nome;

    @Column(nullable = false, unique = true) // Define que o email é único e obrigatório
    private String email;

    @Column(nullable = true)
    private String telefone;

    // Construtores
    public Contato() {
    }

    public Contato(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // toString() para facilitar a visualização
    @Override
    public String toString() {
        return "Contato{id=" + id + ", nome='" + nome + "', email='" + email + "', telefone='" + telefone + "'}";
    }
}
