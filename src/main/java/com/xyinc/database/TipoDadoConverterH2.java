package com.xyinc.database;

import org.springframework.stereotype.Component;

import com.xyinc.bean.enums.TipoDado;

@Component
public class TipoDadoConverterH2 implements TipoDadoConverter {

	@Override
	public String converter(TipoDado tipoDado) {
		String tipoDadoConvertido = "";
		switch (tipoDado) {
			case DATA:
				tipoDadoConvertido = "date";
				break;
			case DECIMAL:
				tipoDadoConvertido = "decimal(20,2)";
				break;
			case INT:
				tipoDadoConvertido = "bigint";
				break;
			default:
				tipoDadoConvertido = "varchar(255)";
		}
		return tipoDadoConvertido;
	}

}
