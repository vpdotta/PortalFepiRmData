package br.fepi.app;

import br.fepi.dao.Autenticacao;
import br.fepi.dao.Contexto;
import br.fepi.model.Aluno;
import br.fepi.model.Boleto;
import br.fepi.model.DadosAcesso;
import br.fepi.model.Disciplina;

public class App {

	public static void main(String[] args) {
		Aluno aluno = new Aluno("matricula", "senha");
		
		Autenticacao aut = Autenticacao.getAutenticacao();
		DadosAcesso acesso = aut.getDadosAcesso();
		
		if (aut.login(aluno))
			System.out.println("Acesso - OK");
		else
			System.out.println("Acesso - ERRO");
		
		Contexto contexto = new Contexto(); //Sessao
		contexto.setDadosAcesso(acesso);; //Atualiza a sessao
		contexto.coletarPeriodos(); //Obtem dados de acesso e visualizacao
		
		try {
			System.out.println(contexto.getPeriodosDisponiveis());	
		}
		catch (Exception e) {
			System.out.println("Sem periodos - ERRO");
		}
		
		contexto.entrarNoContexto("2020/2");
		for (Disciplina d: contexto.obterNotas()) {
			System.out.println(d.getNotas().toString());
			System.out.println("-----");
		}
		
		for (Disciplina d: contexto.obterPresenca()) {
			System.out.println(d.getPresencas().toString());
			System.out.println("-----");
		}		
		
		for (Boleto boleto : contexto.obterDadosFinanceiros()) {
			System.out.println(boleto.toString());
		}
		
	}
	
}
