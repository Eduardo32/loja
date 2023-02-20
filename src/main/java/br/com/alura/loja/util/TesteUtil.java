package br.com.alura.loja.util;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.model.Categoria;
import br.com.alura.loja.model.Cliente;
import br.com.alura.loja.model.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class TesteUtil {
    public static void popularBanco() {
        Categoria categoria = new Categoria("CELULARES");
        Produto produto = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), categoria);
        Cliente cliente = new Cliente("Eduardo", "12223345566");

        EntityManager em = JPAUtil.getEntityManager();
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        em.getTransaction().begin();
        categoriaDAO.cadastrar(categoria);
        produtoDAO.cadastrar(produto);
        clienteDAO.cadastrar(cliente);
        em.getTransaction().commit();
        em.close();
    }
}
