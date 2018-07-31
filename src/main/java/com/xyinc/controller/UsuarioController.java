package com.xyinc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xyinc.bean.Usuario;
import com.xyinc.repository.UsuarioRepository;
import com.xyinc.util.SenhaEncoder;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView("usuario/inc");
		mv.addObject(new Usuario());
		return mv;
	}
	
	@PostMapping
	public String inc(@Valid Usuario usuario, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "usuario/inc";
		}
		//Verifica se senha e contra senha estao iguais
		if(!usuario.getSenha().equals(usuario.getContraSenha())) {
			FieldError error = new FieldError("usuario", "contraSenha", "senha não confere");
			bindingResult.addError(error);
			return "usuario/inc";
		}
		usuario.setSenha(SenhaEncoder.criptografar(usuario.getSenha()));
		usuarioRepository.save(usuario);
		return "redirect:/";
	}

}
