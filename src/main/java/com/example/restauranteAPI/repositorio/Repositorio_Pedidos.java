package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Modelo_Comanda;
import com.example.restauranteAPI.modelo_produto.Modelo_Produto;
import com.example.restauranteAPI.modelo_produto.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface Repositorio_Pedidos  extends JpaRepository<Pedidos , UUID> {
 List<Pedidos> findByIdcomanda(Modelo_Comanda comanda);
 @Query("SELECT p FROM Pedidos p WHERE p.idcomanda = :idcomanda AND p.id_produto = :idproduto")
 List<Pedidos> findByComandaAndProduto(@Param("idcomanda") Modelo_Comanda idcomanda,
                                       @Param("idproduto") Modelo_Produto idproduto);
 @Query(value = "SELECT " +
         "c.id_comanda, " +
         "json_agg(pedidos.id_pedido) as id_pedidos, " +
         "c.identificador AS nome_comanda, " +
         "json_agg(produto.nome_produto) as produtos, " +
         "json_agg(pedidos.quantidade) as quantidades, " +
         "MAX(pedidos.hora_finalizado) as hora_finalizado,"+
         "MIN(pedidos.hora_criado) as hora_criado " +
         "FROM pedidos " +
         "INNER JOIN \"produtos-restaurante\" produto ON produto.id_produto = pedidos.id_produto " +
         "INNER JOIN tb_comandas c ON c.id_comanda = pedidos.id_comanda " +
         "WHERE pedidos.status = :status " +
         "GROUP BY c.id_comanda " +
         "ORDER BY MIN(pedidos.hora_criado) ASC",
         nativeQuery = true)
 List<Object[]> findbystatusO(@Param("status") String status);

 @Query(value = "SELECT " +
         "c.id_comanda, " +
         "json_agg(pedidos.id_pedido) as id_pedidos, " +
         "c.identificador AS nome_comanda, " +
         "json_agg(produto.nome_produto) as produtos, " +
         "json_agg(pedidos.quantidade) as quantidades, " +
         "MIN(pedidos.hora_finalizado) as hora_finalizado " +
         "FROM pedidos " +
         "INNER JOIN \"produtos-restaurante\" produto ON produto.id_produto = pedidos.id_produto " +
         "INNER JOIN tb_comandas c ON c.id_comanda = pedidos.id_comanda " +
         "WHERE pedidos.status = 'Finalizado' " +
         "AND (:identificador IS NULL OR c.Identificador = :identificador) " +
         "AND (CAST(:inicioDiaf AS TIMESTAMP) IS NULL OR pedidos.hora_finalizado >= CAST(:inicioDiaf AS TIMESTAMP)) " +
         "AND (CAST(:fimDiaf AS TIMESTAMP) IS NULL OR pedidos.hora_finalizado < CAST(:fimDiaf AS TIMESTAMP)) " +
         "AND (CAST(:inicioDiac AS TIMESTAMP) IS NULL OR pedidos.hora_criado >= CAST(:inicioDiac AS TIMESTAMP)) " +
         "AND (CAST(:fimDiac AS TIMESTAMP) IS NULL OR pedidos.hora_criado < CAST(:fimDiac AS TIMESTAMP)) " +
         "GROUP BY c.id_comanda " +
         "HAVING (:nomeProduto IS NULL OR bool_or(produto.nome_produto = :nomeProduto)) " +
         "ORDER BY MIN(pedidos.hora_criado) ASC",
         nativeQuery = true)
 List<Object[]> findbyNomeproduto(
         @Param("nomeProduto") String nomeProduto,
         @Param("identificador") String identificador,
         @Param("inicioDiaf") LocalDateTime inicioDiaf,
         @Param("fimDiaf") LocalDateTime fimDiaf,
         @Param("inicioDiac") LocalDateTime inicioDiac,
         @Param("fimDiac") LocalDateTime fimDiac
 );
}
