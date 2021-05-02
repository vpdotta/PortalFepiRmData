package br.fepi.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Disciplina {
	private String nomeDisciplina, situacao;
	private Map<Integer, Integer> notas;
	private Map<String, Integer> presencas;
	
	public Disciplina() {
		this.notas = new HashMap<Integer, Integer>();
		this.presencas = new LinkedHashMap<String, Integer>();
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}
	
	public void setNomeMateria(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Map<Integer, Integer> getNotas() {
		return notas;
	}

	public void setNotas(Map<Integer, Integer> notaBim) {
		this.notas = notaBim;
	}

	public Map<String, Integer> getPresencas() {
		return presencas;
	}

	public void setPresencas(Map<String, Integer> presencas) {
		this.presencas = presencas;
	}	
}
