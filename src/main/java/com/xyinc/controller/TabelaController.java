package com.xyinc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyinc.bean.Tabela;
import com.xyinc.repository.TabelaRepository;

@RestController
@RequestMapping("/tabela")
public class TabelaController {
	
	@Autowired
	private TabelaRepository tabelaRepository;
	
	@GetMapping
	public List<Tabela> findAll(){
		return tabelaRepository.findAll();
	}

}
