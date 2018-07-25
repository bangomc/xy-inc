package com.xyinc.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.xyinc.bean.enums.TipoDado;

@Entity
public class Coluna implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5762756080226302659L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	
	private TipoDado tipoDado;
	
	@ManyToOne
	private Tabela tabela;

}
