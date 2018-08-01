package com.xyinc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyinc.bean.Tabela;
import com.xyinc.database.ResultsetConverter;
import com.xyinc.exception.NotFoundException;
import com.xyinc.repository.TabelaRepository;
import com.xyinc.service.DatabaseService;

/**
 * Controller para exposicao dos verbos da arquitetura REST.
 * Disponibiliza GET,POST,PUT e DELETE para tabelas previamente cadastradas. 
 * 
 * @author Cristhiano Roberto
 *
 */

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private TabelaRepository tabelaRepository;
	
	@Autowired
	private DatabaseService databaseService;
	
	@GetMapping("/{nometabela}")
	public List<Map<String,Object>> findAll(@PathVariable("nometabela") String nometabela){
		Tabela tabela = retornaTabela(nometabela);
		List resultSet = databaseService.findAll(tabela);
		return ResultsetConverter.converter(tabela, resultSet);
	}
	
	@GetMapping("/{nometabela}/{idRegistro}")
	public Map<String,Object> findById(@PathVariable("nometabela") String nometabela, @PathVariable("idRegistro") String idRegistro){
		Tabela tabela = retornaTabela(nometabela);
		Object[] resultSet = databaseService.findById(tabela, Long.valueOf(idRegistro));
		return ResultsetConverter.converter(tabela, resultSet);
	}
		
	@PostMapping("/{nometabela}")
	public ResponseEntity<String> inc(@PathVariable("nometabela") String nometabela, @RequestBody Map<String,Object> bean){
		Tabela tabela = retornaTabela(nometabela);
		databaseService.inc(tabela, bean);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{nometabela}/{idRegistro}")
	public ResponseEntity<String> upd(@PathVariable("nometabela") String nometabela, @PathVariable("idRegistro") String idRegistro, @RequestBody Map<String,Object> bean){
		Tabela tabela = retornaTabela(nometabela);
		Long id = Long.valueOf(idRegistro);
		Object[] resultSet = databaseService.findById(tabela, id);
		if(null==resultSet) {
			throw new NotFoundException();
		}
		bean.put("id", id);
		databaseService.upd(tabela, bean);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{nometabela}/{idRegistro}")
	public ResponseEntity<String> del(@PathVariable("nometabela") String nometabela, @PathVariable("idRegistro") String idRegistro) {
		Tabela tabela = retornaTabela(nometabela);
		Long id = Long.valueOf(idRegistro);
		Object[] resultSet = databaseService.findById(tabela, id);
		if(null==resultSet) {
			throw new NotFoundException();
		}
		databaseService.del(tabela, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private Tabela retornaTabela(String nometabela) {
		Tabela tabela = tabelaRepository.findByNome(nometabela);
		if(null==tabela) {
			throw new NotFoundException();
		}
		return tabela;
	}

}
