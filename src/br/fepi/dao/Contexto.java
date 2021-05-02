package br.fepi.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.fepi.helper.FormataDados;
import br.fepi.model.Aluno;
import br.fepi.model.Boleto;
import br.fepi.model.Disciplina;


public class Contexto extends Sessao {
    private Map<String, String> periodosDisponiveis;
    
    public Contexto() {
    	this.periodosDisponiveis = new HashMap<>();
    }

    public Map<String, String> getPeriodosDisponiveis() {
        return periodosDisponiveis;
    }

    public void setPeriodosDisponiveis(Map<String, String> periodosDisponiveis) {
        this.periodosDisponiveis = periodosDisponiveis;
    }

    public boolean coletarPeriodos() {
        Document doc = null;

        final String webSite = "http://portal.fepi.br:8080/Corpore.Net/Source/Edu-Educacional/RM.EDU.CONTEXTO/EduSelecionarContextoModalWebForm.aspx?";

        try {
            doc = Jsoup.connect(webSite)
                    .cookies(this.getDadosAcesso().getCookies())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.atualizarDados(doc);

        //Coleta o valor e o periodo respectivamente:
        ArrayList<String> values = (ArrayList<String>) doc.getElementsByAttributeValue("name", "rdContexto").eachAttr("value");
        ArrayList<String> ids = (ArrayList<String>) doc.getElementsByAttributeValue("style", "width:80px;").eachText();

        for (int i = 0; i < ids.size(); i++) {
            this.periodosDisponiveis.put(ids.get(i), values.get(i));
        }

        if (!this.periodosDisponiveis.isEmpty())
        	return true;
        return false;
    }

    public int entrarNoContexto(String semestre) {
        Connection.Response response = null;

        final String webSite = "http://portal.fepi.br:8080/Corpore.Net/Source/Edu-Educacional/RM.EDU.CONTEXTO/EduSelecionarContextoModalWebForm.aspx?";

        try {
            response = Jsoup.connect(webSite)
                    .method(Connection.Method.POST)
                    .data(this.getDadosAcesso().getData())
                    .data("rdContexto", this.getPeriodosDisponiveis().get(semestre))
                    .cookies(this.getDadosAcesso().getCookies())
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return response.statusCode();
    }

    public List<Disciplina> obterNotas() {
        Document doc = null;

        final String webSite = "http://portal.fepi.br:8080/Corpore.Net/Main.aspx?ActionID=EduNotaEtapaActionWeb&SelectedMenuIDKey=mnNotasEtapa";

        try {
            doc = Jsoup.connect(webSite)
                    .cookies(this.getDadosAcesso().getCookies())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        return new FormataDados().formataNotas(doc);
    }
    
    public List<Disciplina> obterPresenca() {
        Document doc = null;

        final String webSite = "http://portal.fepi.br:8080/Corpore.Net/Main.aspx?ActionID=EduNotaEtapaActionWeb&SelectedMenuIDKey=mnNotasEtapa";

        try {
            doc = Jsoup.connect(webSite)
                    .cookies(this.getDadosAcesso().getCookies())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new FormataDados().formataPresenca(doc);
    }
    
    public Aluno obterDadosAluno() {
    	
    	Document[] doc = new Document[2];
    	
    	doc[0] = null;
    	doc[1] = null;
    	
    	final String webSite1 = "http://portal.fepi.br:8080/Corpore.Net/Main.aspx?ActionID=EduHistoricoEnsSupActionWeb&SelectedMenuIDKey=mnHistorico";
    	final String webSite2 = "http://portal.fepi.br:8080/Corpore.Net/Main.aspx?ActionID=EduDadosPessoaisActionWeb&SelectedMenuIDKey=mnDadosPessoais";
    	
    	try {
            doc[0] = Jsoup.connect(webSite1)
                    .cookies(this.getDadosAcesso().getCookies())
                    .get();
            doc[1] = Jsoup.connect(webSite2)
                    .cookies(this.getDadosAcesso().getCookies())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return new FormataDados().formataDadosAluno(doc);
    }
    
    public String obterFotoAluno() {
    	Document doc = null;
    	
    	final String webSite = "http://portal.fepi.br:8080/Corpore.Net/Main.aspx?ActionID=EduDadosPessoaisActionWeb&SelectedMenuIDKey=mnDadosPessoais";
    	
    	try {
            doc = Jsoup.connect(webSite)
                    .cookies(this.getDadosAcesso().getCookies())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	String urlFoto = "portal.fepi.br:8080/" + doc.getElementsByClass("dxeImage").attr("src");
    	
    	return urlFoto;
    }
    
    public List<Boleto> obterDadosFinanceiros() {
    	
    	Document doc = null;

        final String webSite = "http://portal.fepi.br:8080/Corpore.Net/Main.aspx?ActionID=EduHistoricoFinanceiroActionWeb&SelectedMenuIDKey=mnExtratoFin";

        try {
            doc = Jsoup.connect(webSite)
                    .cookies(this.getDadosAcesso().getCookies())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return new FormataDados().formataBoletos(doc);
    }
    
    public String obterUrlBoleto() {
    	
    	Document doc = null;

        final String webSite = "http://portal.fepi.br:8080/Corpore.Net/Main.aspx?ActionID=EduHistoricoFinanceiroActionWeb&SelectedMenuIDKey=mnExtratoFin";
        
        try {
            doc = Jsoup.connect(webSite)
                    .cookies(this.getDadosAcesso().getCookies())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new FormataDados().formataUrlBoleto(doc);
    }
}
