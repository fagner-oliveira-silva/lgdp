package br.com.record.lgpd.app;

import java.io.IOException;

import br.com.record.lgpd.model.Aplicacao;
import br.com.record.lgpd.model.BancoDeDados;
import br.com.record.lgpd.model.EnderecoIp;
import br.com.record.lgpd.model.EnumSGBD;
import br.com.record.lgpd.model.Instancia;
import br.com.record.lgpd.model.Servidor;

public class App {
	
	public static void main(final String[] args) {

		String strIp = "192.168.0.37";
		EnderecoIp ip = new EnderecoIp(strIp);
		final Servidor serverdb = new Servidor("DBSERVER", ip);
		//ServicoDeBD sqlServer = new ServicoDeBD("RECORD", "51086", serverdb, new TipoSGBDPersist(EnumSGBD.SQLSERVER) );
		Instancia sqlServer = new Instancia("RECORD", "51086", serverdb, EnumSGBD.SQLSERVER );
		final BancoDeDados bdP12 = new BancoDeDados(sqlServer, "P12");
		final BancoDeDados bdIntegracao = new BancoDeDados(sqlServer, "INTEGRACAO");
		final BancoDeDados bdGer = new BancoDeDados(sqlServer, "GER");
		
		Aplicacao P12 = new Aplicacao("Protheus", "Sistema de Gestão Empresarial");
		P12.adicionaBDAcessado(bdP12);
		P12.adicionaBDAcessado(bdIntegracao);

		Aplicacao GER = new Aplicacao("GER", "GER");
		GER.adicionaBDAcessado(bdIntegracao);
		GER.adicionaBDAcessado(bdGer);

		clearScreen();
		System.out.println(P12.toString());

	}

	public static void clearScreen() {
		// Limpa a tela no windows, no linux e no MacOS
		if (System.getProperty("os.name").contains("Windows")) {
			try {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Runtime.getRuntime().exec("clear");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
