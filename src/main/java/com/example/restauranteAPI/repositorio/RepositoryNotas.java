package com.example.restauranteAPI.repositorio;

import com.example.restauranteAPI.modelo_produto.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface RepositoryNotas extends JpaRepository<Nota , UUID> {
    List<Nota> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query(nativeQuery = true, value = """
    SELECT
        n.id_nota,
        c.identificador AS nome_comanda,
        n.valor_total,
        n.data,
        json_agg(n.nomes_prod) as produtos,
        (SELECT json_agg(e) FROM unnest(n.formapagamento) e) as formaspagamento,
        (SELECT json_agg(v) FROM unnest(n.valores) v) as valores
    FROM tabela_notas n
    INNER JOIN tb_comandas c ON c.id_comanda = n.id_comanda
    WHERE
        (CAST(:identificador AS TEXT) IS NULL OR c.identificador = CAST(:identificador AS TEXT)) AND
        (CAST(:data AS TIMESTAMP) IS NULL OR 
            n.data >= CAST(:data AS TIMESTAMP) AND 
            n.data < (CAST(:data AS TIMESTAMP) + INTERVAL '1 DAY')
        ) AND
        (CAST(:produto AS TEXT) IS NULL OR CAST(:produto AS TEXT) = ANY(n.nomes_prod)) AND
        (CAST(:metodos AS TEXT) IS NULL OR EXISTS (
            SELECT 1
            FROM unnest(n.formapagamento, n.valores) AS fp(metodo, valor)
            WHERE
                fp.metodo = ANY(STRING_TO_ARRAY(CAST(:metodos AS TEXT), ',')) 
                AND fp.valor > 0
        ))
    GROUP BY n.id_nota, c.identificador, n.valor_total, n.data
    ORDER BY n.data ASC""")
    List<Object[]> findNotasFiltradas(
            @Param("identificador") String identificador,
            @Param("data") LocalDate data,
            @Param("produto") String produto,
            @Param("metodos") String metodos
    );

    @Query(value = """
    SELECT 
        n.id_nota,
        c.identificador AS nome_comanda,
        n.valor_total,
        n.data,
        (SELECT json_agg(p) FROM unnest(n.nomes_prod) p) AS produtos,
        (SELECT json_agg(e) FROM unnest(n.formapagamento) e) AS formaspagamento,
        (SELECT json_agg(v) FROM unnest(n.valores) v) AS valores
    FROM tabela_notas n
    INNER JOIN tb_comandas c ON c.id_comanda = n.id_comanda
    ORDER BY n.data ASC
""", nativeQuery = true)
    List<Object[]> findallNotas();
}
