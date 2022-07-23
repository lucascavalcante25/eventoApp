package com.repository;

import org.springframework.data.repository.CrudRepository;

import com.model.Convidado;
import com.model.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{

	Iterable<Convidado> findByEvento(Evento evento);
	
	Convidado findByRg(String rg);
}
