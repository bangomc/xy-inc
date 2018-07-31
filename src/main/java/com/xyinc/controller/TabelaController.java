package com.xyinc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xyinc.bean.Coluna;
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
	
	@GetMapping("/teste")
	public ModelAndView teste() {
		ModelAndView mv = new ModelAndView("tabela/inc");
		Optional<Tabela> tabela = tabelaRepository.findById(1L);
		
		if(tabela.isPresent()) {
			mv.addObject("tabela",tabela.get());
		}
		
		return mv;
	}
	
	public ModelAndView addColuna(Coluna coluna) {
		ModelAndView mv = new ModelAndView("tabela/inc");
		
		return mv;
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
