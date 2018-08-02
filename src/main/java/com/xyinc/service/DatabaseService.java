package com.xyinc.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyinc.bean.Coluna;
import com.xyinc.bean.Tabela;
import com.xyinc.bean.enums.TipoDado;
import com.xyinc.database.TipoDadoConverter;

/**
 * Responsavel por criar e executar comandos SQL dinamicos no banco de dados. 
 * 
 * @author Cristhiano Roberto
 *
 */

@Service
public class DatabaseService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TipoDadoConverter tipoDadoConverter;
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void criarTabela(Tabela tabela) {		
		try {
			String sql = gerarStringCreateTable(tabela);
			Query query = em.createNativeQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
			log.error("Erro ao criar tabela "+tabela.getNome());
		}
	}
	
	public List<Object[]> findAll(Tabela tabela) {
		List<Object[]> resultado = null;
		try {
			String sql = gerarStringSelectAll(tabela);
			Query query = em.createNativeQuery(sql);
			resultado = query.getResultList();
		} catch (Exception e) {
			log.error("Erro ao executar findAll na tabela "+tabela.getNome());
		}
		return resultado;
	}
		
	public Object[] findById(Tabela tabela, Long id) {
		try {
			String sql = gerarStringSelectById(tabela,id);
			Query query = em.createNativeQuery(sql);
			List<Object[]> lista = query.getResultList();
			if(null!=lista && !lista.isEmpty()) {
				return lista.get(0);
			}
		} catch (Exception e) {
			log.error("Erro ao executar findById na tabela "+tabela.getNome());
		}
		return null;
	}
	
	@Transactional
	public int upd(Tabela tabela,Map<String,Object> bean) {
		int retorno = 0;
		try {
			String sql = gerarStringUpdate(tabela, bean);
			Query query = em.createNativeQuery(sql);
			retorno = query.executeUpdate(); 
		} catch (Exception e) {
			log.error("Erro ao executar update na tabela "+tabela.getNome());
		}
		return retorno;
	}
	
	@Transactional
	public int del(Tabela tabela, Long id) {
		int retorno = 0;
		try {
			String sql = gerarStringDelete(tabela, id);
			Query query = em.createNativeQuery(sql);
			retorno = query.executeUpdate();			
		} catch (Exception e) {
			log.error("Erro ao executar delete na tabela "+tabela.getNome());
		}
		return retorno;
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
			if(coluna.getTipoDado()==TipoDado.STRING || coluna.getTipoDado()==TipoDado.DATA) {
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
		int retorno = 0;
		try {
			String sql = gerarStringInsert(tabela, bean);
			Query query = em.createNativeQuery(sql);
			retorno = query.executeUpdate();			
		} catch (Exception e) {
			log.error("Erro ao executar insert na tabela "+tabela.getNome());
		}
		return retorno;
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
