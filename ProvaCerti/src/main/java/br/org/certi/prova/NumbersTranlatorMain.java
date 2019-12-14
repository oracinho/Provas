package br.org.certi.prova;

import java.io.IOException;

/**
 * Classe principal para iniciar a aplicação.
 * Mantem uma interface caracter para subir/derrubar o servidor e verificar os erros
 */
public class NumbersTranlatorMain {

	/**
	 * Inicia a aplicação
	 */
	private void startApp(int port) {
		NumbersTranslatorServer server = new NumbersTranslatorServer();
		try {
			server.startServer(port);
		} catch (IOException e) {
			System.out.println("Ocorreu um erro com o servidor. É provável que a porta informada já esteja em uso.");
			System.exit(0);
		} catch (IllegalArgumentException e) {
			System.out.println("Número da porta fora da faixa aceitável.");
			System.exit(0);
		}
	}

	public static void main (String[] args) {
		NumbersTranlatorMain app = new NumbersTranlatorMain();
		if (args.length == 0) {
			System.out.println("Parâmetros incorretos. É esperado que o número da porta seja informado.");
			return;
		}
		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
		}catch (Exception e) {
			System.out.println("Parâmetros incorretos. É esperado que o número da porta seja informado.");
			return;
		}
			
		app.startApp(port);
	}
}
