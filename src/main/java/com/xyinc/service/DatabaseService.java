package com.xyinc.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyinc.bean.Coluna;
import com.xyinc.bean.Tabela;
import com.xyinc.bean.enums.TipoDado;
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
		List<Object[]> resultado = query.getResultList();
		return resultado;
	}
		
	public Object[] findById(Tabela tabela, Long id) {
		String sql = gerarStringSelectById(tabela,id);
		Query query = em.createNativeQuery(sql);
		List<Object[]> lista = query.getResultList();
		if(null!=lista && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
	@Transactional
	public int upd(Tabela tabela,Map<String,Object> bean) {
		String sql = gerarStringUpdate(tabela, bean);
		Query query = em.createNativeQuery(sql);
		return query.executeUpdate();
	}
	
	@Transactional
	public int del(Tabela tabela, Long id) {
		String sql = gerarStringDelete(tabela, id);
		Query query = em.createNativeQuery(sql);
		return query.executeUpdate();
	}
	
	public String gerarStringDelete(Tabela tabela, Long id) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ");
		sb.append(tabela.getNome());
		sb.append(" WHERE id = ");
		sb.append(id);
		return sb.toString();
	}
	
	public String gerarStringUpdate(Tabela tabela,Map<String,Object> bean) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		sb.append(tabela.getNome());
		sb.append(" SET ");
		
		int ultimo = tabela.getColunas().size() - 1;
		for (int i = 0; i < tabela.getColunas().size(); i++) {
			Coluna coluna = tabela.getColunas().get(i);
			sb.append(coluna.getNome());
			sb.append("=");
			Object valor = bean.get(coluna.getNome());
			if(coluna.getTipoDado()==TipoDado.STRING) {
				sb.append("'");	
				sb.append(valor);
				sb.append("'");
			}else {
				sb.append(valor);
			}
			if(i!=ultimo) {
				sb.append(", ");
			}
		}
		sb.append(" WHERE id = ");
		sb.append(bean.get("id"));
		return sb.toString();
	}
	
	
	public String gerarStringSelectById(Tabela tabela, Long id) {
		StringBuffer sb = new StringBuffer();
		sb.append(gerarStringSelect(tabela));
		sb.append(" WHERE id = ");
		sb.append(id);
		return sb.toString();
	}
	
	@Transactional
	public int inc(Tabela tabela,Map<String,Object> bean) {
		String sql = gerarStringInsert(tabela, bean);
		Query query = em.createNativeQuery(sql);
		return query.executeUpdate();
	}
	
	public String gerarStringInsert(Tabela tabela, Map<String,Object> bean) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append(tabela.getNome());
		sb.append("(id,");
		
		int ultimo = tabela.getColunas().size() - 1;
		for (int i = 0; i < tabela.getColunas().size(); i++) {
			Coluna coluna = tabela.getColunas().get(i);
			sb.append(coluna.getNome());
			if(i!=ultimo) {
				sb.append(",");
			}
		}
		sb.append(") VALUES(default,");
		for (int i = 0; i < tabela.getColunas().size(); i++) {
			Coluna coluna = tabela.getColunas().get(i);
			Object valor = bean.get(coluna.getNome());
			
			if(coluna.getTipoDado()==TipoDado.STRING || coluna.getTipoDado()==TipoDado.DATA) {
				sb.append("'");	
				sb.append(valor);
				sb.append("'");
			}else {
				sb.append(valor);
			}
			
			if(i!=ultimo) {
				sb.append(",");
			}
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	public String gerarStringSelectAll(Tabela tabela) {
		StringBuffer sb = new StringBuffer();
		sb.append(gerarStringSelect(tabela));
		sb.append(" ORDER BY ID");
		return sb.toString();
	}
	
	public String gerarStringSelect(Tabela tabela) {
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
