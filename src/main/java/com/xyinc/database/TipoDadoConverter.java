package com.xyinc.database;

import com.xyinc.bean.enums.TipoDado;

/**
 * Abstracao que deve ser implementada para cada especificidade de sintaxe de banco de dados utilizado. 
 * 
 * @author Cristhiano Roberto
 *
 */
public interface TipoDadoConverter {

	public String converter(TipoDado tipoDado);
	
}
