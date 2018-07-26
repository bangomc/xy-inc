package com.xyinc.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xyinc.bean.Coluna;
import com.xyinc.bean.Tabela;

public class ResultsetConverter {
	
	
	public static List<Map<String, Object>> converter(Tabela tabela, List<Object[]> resultSet){
		
		List<Map<String, Object>> retorno = new ArrayList();
		
		resultSet.forEach(resultado->{
			Map<String, Object> tupla = new HashMap<>();
			tupla.put("id", resultado[0]);
			
			int contador = 1;
			
			for (int i = 0; i < tabela.getColunas().size(); i++) {
				Coluna coluna = tabela.getColunas().get(i);
				tupla.put(coluna.getNome(), resultado[contador]);
				contador++;
			}
			
			retorno.add(tupla);
		});
		
		return retorno;
	}

}
