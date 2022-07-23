package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, String> {
	
	Evento findByCodigo (Long codigo);
}
