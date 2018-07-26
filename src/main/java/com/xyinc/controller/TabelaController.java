package com.xyinc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyinc.bean.Tabela;
import com.xyinc.repository.TabelaRepository;
import com.xyinc.service.DatabaseService;

@RestController
@RequestMapping("/tabela")
public class TabelaController {
	
	@Autowired
	private TabelaRepository tabelaRepository;
	
	@Autowired
	private DatabaseService databaseService;
	
	@GetMapping
	public List<Tabela> findAll(){
		return tabelaRepository.findAll();
	}
	
	@PostMapping
	public Tabela inc(@RequestBody Tabela tabela) {
		Tabela retorno = tabelaRepository.save(tabela);
		databaseService.criarTabela(tabela);
		return retorno;
	}

	public TabelaRepository getTabelaRepository() {
		return tabelaRepository;
	}

	public void setTabelaRepository(TabelaRepository tabelaRepository) {
		this.tabelaRepository = tabelaRepository;
	}

	public DatabaseService getDatabaseService() {
		return databaseService;
	}

	public void setDatabaseService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}
	
	

}
