package br.com.alura.loja.testes;

import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.model.Cliente;
import br.com.alura.loja.model.ItemPedido;
import br.com.alura.loja.model.Pedido;
import br.com.alura.loja.model.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.util.TesteUtil;

import javax.persistence.EntityManager;

public class CadastroDePedido {
    public static void main(String[] args) {
        TesteUtil.popularBanco();

        EntityManager em = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);
        PedidoDAO pedidoDAO = new PedidoDAO(em);

        Produto produto = produtoDAO.buscarPorID(1L);
        Cliente cliente = clienteDAO.buscarPorID(1L);

        em.getTransaction().begin();
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(3, pedido, produto));
        pedidoDAO.cadastrar(pedido);
        em.getTransaction().commit();
        System.out.println("O total vendo foi: R$" + pedidoDAO.valorTotalVendida());
        em.close();
    }
}
