package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.model.Convidado;
import com.model.Evento;
import com.repository.ConvidadoRepository;
import com.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired(required = true)
	private EventoRepository er;
	
	@Autowired(required = true)
	private ConvidadoRepository cr;

	// Método para renderizar a view de cadastro do evento
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}

//	Salva os eventos e retorna para a pagina index
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String saveEvento(Evento evento) {
		er.save(evento);
		return "redirect:/";
	}
	
//	Varre a Lista de Eventos e renderiza na página /eventos
	@RequestMapping(value = "/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}
	
//	Lista os detalhes do evento
	@RequestMapping("/{codigo}")
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		System.out.println("evento" + evento);
		
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados", convidados);
		return mv;
	}
	
//	Deleta um evento
	@RequestMapping("/deletar")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
//	Deletar Convidado e retorna para o evento
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		
		return "redirect:/" + codigo;
	}
	
//	Salva um convidado em um evento
	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String salvarConvidado(@PathVariable("codigo") long codigo, Convidado convidado) {
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		return "redirect:/{codigo}";
	}
	
}
