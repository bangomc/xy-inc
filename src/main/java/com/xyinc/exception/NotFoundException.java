package com.xyinc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Responsavel por informar a falta de um recurso
 * 
 * @author Cristhiano Roberto
 *
 */
@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2066559813521029308L;

}
