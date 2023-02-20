package br.com.alura.loja.dao;

import br.com.alura.loja.model.Cliente;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteDAO {

    private EntityManager em;

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Cliente cliente) {
        this.em.persist(cliente);
    }

    public void atualizar(Cliente cliente) { this.em.merge(cliente); }

    public void remover(Cliente cliente) {
        cliente = this.em.merge(cliente);
        this.em.remove(cliente);
    }

    public List<Cliente> buscaTodos() {
        StringBuilder jpql = new StringBuilder(getSQLBase());
        return em.createQuery(jpql.toString(), Cliente.class).getResultList();
    }

    public Cliente buscarPorID(Long id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> buscaPorNome(String nome) {
        StringBuilder jpql = new StringBuilder(getSQLBase())
                .append("WHERE p.nome = :nome");
        return em.createQuery(jpql.toString(), Cliente.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Cliente> buscaPorNomeCategorio(String nome) {
        StringBuilder jpql = new StringBuilder(getSQLBase())
                .append("WHERE p.categoria.nome = :nome");
        return em.createQuery(jpql.toString(), Cliente.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    private String getSQLBase() {
        return "SELECT p FROM Cliente p ";
    }
}
