package com.xyinc.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tabela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7446929258786431894L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	
	@OneToMany
	private List<Coluna> colunas;
}
