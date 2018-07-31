package com.xyinc.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xyinc.bean.Coluna;
import com.xyinc.bean.Tabela;
import com.xyinc.bean.enums.TipoDado;
import com.xyinc.repository.TabelaRepository;
import com.xyinc.service.DatabaseService;

@Controller
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
			mv.addObject("tiposDado",TipoDado.values());
		}
		
		return mv;
	}
	
	
	@PostMapping(params="adicionarColuna")
	public ModelAndView addColuna(Tabela tabela) {
		ModelAndView mv = new ModelAndView("tabela/inc");
		tabela.getColunas().add(new Coluna());
		mv.addObject("tabela",tabela);
		mv.addObject("tiposDado",TipoDado.values());
		return mv;
	}
	
	@PostMapping(params="removerColuna")
	public ModelAndView delColuna(Tabela tabela, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("tabela/inc");
		Integer index = Integer.valueOf(req.getParameter("removerColuna"));		
		tabela.getColunas().remove(index.intValue());
		mv.addObject("tabela",tabela);
		mv.addObject("tiposDado",TipoDado.values());
		return mv;
	}
	
	@PostMapping
	public Tabela inc(Tabela tabela) {
		//Tabela retorno = tabelaRepository.save(tabela);
		//databaseService.criarTabela(tabela);
		return tabela;
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
