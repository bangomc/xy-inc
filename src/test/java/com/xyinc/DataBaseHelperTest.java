package com.xyinc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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
}
