package com.xyinc.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.xyinc.bean.enums.TipoDado;

@Entity
public class Coluna implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5762756080226302659L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotNull
	private TipoDado tipoDado;
	
	public Coluna() {};
		
	public Coluna(String nome, TipoDado tipoDado) {
		super();
		this.nome = nome;
		this.tipoDado = tipoDado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoDado getTipoDado() {
		return tipoDado;
	}

	public void setTipoDado(TipoDado tipoDado) {
		this.tipoDado = tipoDado;
	}

}
