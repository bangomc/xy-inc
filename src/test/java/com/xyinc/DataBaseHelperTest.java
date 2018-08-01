package com.xyinc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.xyinc.bean.Coluna;
import com.xyinc.bean.Tabela;
import com.xyinc.bean.enums.TipoDado;
import com.xyinc.database.TipoDadoConverterH2;
import com.xyinc.service.DatabaseService;

@RunWith(SpringRunner.class)
public class DataBaseHelperTest {
	
	@Test
	public void criarStringCreateTable() {
		Tabela tabela = new Tabela();
		tabela.setNome("Produto");
		
		List<Coluna> colunas = new ArrayList<>();
		colunas.add(new Coluna("codigo", TipoDado.INT));
		colunas.add(new Coluna("descricao", TipoDado.STRING));
		colunas.add(new Coluna("valor", TipoDado.DECIMAL));
		
		tabela.setColunas(colunas);
		
		DatabaseService dbh = new DatabaseService();
		dbh.setTipoDadoConverter(new TipoDadoConverterH2());
		
		String sqlExpected = "CREATE TABLE Produto(id bigint auto_increment,codigo bigint, descricao varchar(255), valor decimal(20,2), CONSTRAINT Produto_pkey PRIMARY KEY (id))";
		
		assertEquals(sqlExpected,dbh.gerarStringCreateTable(tabela));
	}
	
	@Test
	public void criarStringInsert() {
		Tabela tabela = new Tabela();
		tabela.setNome("Produto");
		
		List<Coluna> colunas = new ArrayList<>();
		colunas.add(new Coluna("codigo", TipoDado.INT));
		colunas.add(new Coluna("descricao", TipoDado.STRING));
		colunas.add(new Coluna("valor", TipoDado.DECIMAL));
		
		tabela.setColunas(colunas);
		
		Map<String, Object> bean = new HashMap<>();
		bean.put("codigo", 123);
		bean.put("descricao", "sapato");
		bean.put("valor", 10.20D);
		
		DatabaseService dbh = new DatabaseService();
		dbh.setTipoDadoConverter(new TipoDadoConverterH2());
		
		String sqlExpected = "INSERT INTO Produto(id,codigo,descricao,valor) VALUES(default,123,'sapato',10.2)";
		
		assertEquals(sqlExpected,dbh.gerarStringInsert(tabela,bean));
		
	}
	
	@Test
	public void criarStringUpdate() {
		Tabela tabela = new Tabela();
		tabela.setNome("Produto");
		
		List<Coluna> colunas = new ArrayList<>();
		colunas.add(new Coluna("codigo", TipoDado.INT));
		colunas.add(new Coluna("descricao", TipoDado.STRING));
		colunas.add(new Coluna("valor", TipoDado.DECIMAL));
		
		tabela.setColunas(colunas);
		
		Map<String, Object> bean = new HashMap<>();
		bean.put("id", 112);
		bean.put("codigo", 123);
		bean.put("descricao", "sapato");
		bean.put("valor", 10.20D);
		
		DatabaseService dbh = new DatabaseService();
		dbh.setTipoDadoConverter(new TipoDadoConverterH2());
		
		String sqlExpected = "UPDATE Produto SET codigo=123, descricao='sapato', valor=10.2 WHERE id = 112";
		
		assertEquals(sqlExpected,dbh.gerarStringUpdate(tabela,bean));
		
	}
}
