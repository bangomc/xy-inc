package com.xyinc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xyinc.bean.Coluna;
import com.xyinc.bean.Tabela;
import com.xyinc.bean.enums.TipoDado;
import com.xyinc.repository.TabelaRepository;
import com.xyinc.service.DatabaseService;

/**
 * Controller para inclusao de tabelas e colunas.
 * Persiste no banco e executa comandos para sua criacao.
 * 
 * @author Cristhiano Roberto
 *
 */

@Controller
@RequestMapping("/")
public class TabelaController {
	
	@Autowired
	private TabelaRepository tabelaRepository;
	
	@Autowired
	private DatabaseService databaseService;
	
	@GetMapping
	public ModelAndView novaTabela() {
		ModelAndView mv = new ModelAndView("inc");
		mv.addObject("tabela", new Tabela());
		mv.addObject("tiposDado",TipoDado.values());				
		return mv;
	}
		
	@PostMapping(params="adicionarColuna")
	public ModelAndView addColuna(Tabela tabela) {
		ModelAndView mv = new ModelAndView("inc");
		tabela.getColunas().add(new Coluna());
		mv.addObject("tabela",tabela);
		mv.addObject("tiposDado",TipoDado.values());
		return mv;
	}
	
	@PostMapping(params="removerColuna")
	public ModelAndView delColuna(Tabela tabela, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("inc");
		Integer index = Integer.valueOf(req.getParameter("removerColuna"));		
		tabela.getColunas().remove(index.intValue());
		mv.addObject("tabela",tabela);
		mv.addObject("tiposDado",TipoDado.values());
		return mv;
	}
	
	@PostMapping
	public ModelAndView inc(@Valid Tabela tabela, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("inc");
		mv.addObject("tabela",tabela);
		mv.addObject("tiposDado",TipoDado.values());
		if(bindingResult.hasErrors()) {			
			return mv;
		}
		Tabela tabelaExiste = tabelaRepository.findByNome(tabela.getNome());
		if(null!=tabelaExiste) {
			bindingResult.addError(new FieldError("tabela", "nome", "tabela já existe"));
			return mv;
		}
		
		tabelaRepository.save(atributosNomeMinusculo(tabela));				
		databaseService.criarTabela(tabela);
		mv = novaTabela();
		mv.addObject("mensagem", "tabela incluida com sucesso");
		return mv;
	}
	
	/**
	 * Transforma o nome da tabela e seus atributos em minusculo
	 * @param tabela
	 * @return
	 */
	private Tabela atributosNomeMinusculo(Tabela tabela) {
		tabela.setNome(tabela.getNome().toLowerCase());
		tabela.getColunas().forEach(coluna->{
			coluna.setNome(coluna.getNome().toLowerCase());
		});
		return tabela;
	}
		
}
