package com.xyinc.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyinc.bean.Coluna;
import com.xyinc.bean.Tabela;
import com.xyinc.database.TipoDadoConverter;

@Service
public class DatabaseService {
	
	@Autowired
	private TipoDadoConverter tipoDadoConverter;
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void criarTabela(Tabela tabela) {
		String sql = gerarStringCreateTable(tabela);
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
	}
	
	public List<Object[]> findAll(Tabela tabela) {
		String sql = gerarStringSelectAll(tabela);
		Query query = em.createNativeQuery(sql);
		List<Object[]> resultado =  query.getResultList();
		return resultado;
	}
	
	public String gerarStringSelectAll(Tabela tabela) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id,");
		
		int ultimo = tabela.getColunas().size() - 1;
		for (int i = 0; i < tabela.getColunas().size(); i++) {
			Coluna coluna = tabela.getColunas().get(i);
			sb.append(coluna.getNome());
			if(i!=ultimo) {
				sb.append(",");
			}
		}
		
		sb.append(" FROM ");
		sb.append(tabela.getNome());
		sb.append(" ORDER BY ID");
		return sb.toString();
	}
	
	public String gerarStringCreateTable(Tabela tabela) {
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ");
		sb.append(tabela.getNome());
		sb.append("(");
		
		sb.append(gerarStringID());
		
		tabela.getColunas().forEach(coluna->{
			sb.append(coluna.getNome());
			sb.append(" ");
			sb.append(tipoDadoConverter.converter(coluna.getTipoDado()));
			sb.append(", ");	
		});
		
		sb.append(gerarStringChavePrimaria(tabela.getNome()));	
		sb.append(")");
		return sb.toString();
	}
	
	private String gerarStringID() {
		return "id bigint auto_increment,";
	}
	
	private String gerarStringChavePrimaria(String nomeTabela) {
		return "CONSTRAINT "+nomeTabela+"_pkey PRIMARY KEY (id)";
	}

	public TipoDadoConverter getTipoDadoConverter() {
		return tipoDadoConverter;
	}

	public void setTipoDadoConverter(TipoDadoConverter tipoDadoConverter) {
		this.tipoDadoConverter = tipoDadoConverter;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
