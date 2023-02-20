package br.com.alura.loja.dao;

import br.com.alura.loja.model.Categoria;
import br.com.alura.loja.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProdutoDAO {

    private EntityManager em;

    public ProdutoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto) {
        this.em.persist(produto);
    }

    public void atualizar(Produto produto) { this.em.merge(produto); }

    public void remover(Produto produto) {
        produto = this.em.merge(produto);
        this.em.remove(produto);
    }

    public Produto buscarPorID(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> buscaTodos() {
        StringBuilder jpql = new StringBuilder(getSQLBase());
        return em.createQuery(jpql.toString(), Produto.class).getResultList();
    }

    public List<Produto> buscaPorNome(String nome) {
        StringBuilder jpql = new StringBuilder(getSQLBase())
                .append("WHERE p.nome = :nome");
        return em.createQuery(jpql.toString(), Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    /*public List<Produto> buscaPorNomeCategorio(String nome) {
        StringBuilder jpql = new StringBuilder(getSQLBase())
                .append("WHERE p.categoria.nome = :nome");
        return em.createQuery(jpql.toString(), Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }*/

    public List<Produto> buscaPorNomeCategorio(String nome) {
        return em.createNamedQuery("Produto.produtosProCategoria", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscaPorParametro(String nome, BigDecimal preco, LocalDate dataCadastro) {
        StringBuilder jpql = new StringBuilder(getSQLBase())
                .append("WHERE 1 = 1 ");
        if(nome != null && !nome.trim().isEmpty()) {
            jpql.append("AND p.nome = :nome ");
        }
        if(preco != null) {
            jpql.append("AND p.preco = :preco ");
        }
        if(dataCadastro != null) {
            jpql.append("AND p.dataCadastro = :dataCadastro ");
        }

        TypedQuery query = em.createQuery(jpql.toString(), Produto.class);

        if(nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", nome);
        }
        if(preco != null) {
            query.setParameter("preco", preco);
        }
        if(dataCadastro != null) {
            query.setParameter("dataCadastro", dataCadastro);
        }

        return query.getResultList();
    }

    public List<Produto> buscaPorParametroComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);
        Predicate filtros = builder.and();

        if(nome != null && !nome.trim().isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
        }
        if(preco != null) {
            filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
        }
        if(dataCadastro != null) {
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
        }

        query.where(filtros);

        return em.createQuery(query).getResultList();
    }

    private String getSQLBase() {
        return "SELECT p FROM Produto p ";
    }
}
