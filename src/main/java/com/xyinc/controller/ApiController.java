package com.xyinc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyinc.bean.Tabela;
import com.xyinc.database.ResultsetConverter;
import com.xyinc.repository.TabelaRepository;
import com.xyinc.service.DatabaseService;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private TabelaRepository tabelaRepository;
	
	@Autowired
	private DatabaseService databaseService;
	
	@RequestMapping("/{nometabela}")
	public List<Map<String,Object>> findAll(@PathVariable("nometabela") String nometabela){
		Tabela tabela = tabelaRepository.findByNome(nometabela);
		if(null!=tabela) {
			List resultSet = databaseService.findAll(tabela);
			return ResultsetConverter.converter(tabela, resultSet);
		}
		return null;
	}

}
