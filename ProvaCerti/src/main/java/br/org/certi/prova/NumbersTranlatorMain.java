package br.org.certi.prova;

import java.io.IOException;

/**
 * Classe principal para iniciar a aplica��o.
 * Mantem uma interface caracter para subir/derrubar o servidor e verificar os erros
 */
public class NumbersTranlatorMain {

	/**
	 * Inicia a aplica��o
	 */
	private void startApp(int port) {
		NumbersTranslatorServer server = new NumbersTranslatorServer();
		try {
			server.startServer(port);
		} catch (IOException e) {
			System.out.println("Ocorreu um erro com o servidor. � prov�vel que a porta informada j� esteja em uso.");
			System.exit(0);
		} catch (IllegalArgumentException e) {
			System.out.println("N�mero da porta fora da faixa aceit�vel.");
			System.exit(0);
		}
	}

	public static void main (String[] args) {
		NumbersTranlatorMain app = new NumbersTranlatorMain();
		if (args.length == 0) {
			System.out.println("Par�metros incorretos. � esperado que o n�mero da porta seja informado.");
			return;
		}
		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
		}catch (Exception e) {
			System.out.println("Par�metros incorretos. � esperado que o n�mero da porta seja informado.");
			return;
		}
			
		app.startApp(port);
	}
}
