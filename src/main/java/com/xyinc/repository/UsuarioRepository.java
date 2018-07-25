package com.xyinc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyinc.bean.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
