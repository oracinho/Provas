package br.org.certi.prova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


/**
 * Define o servidor através de um socket para o serviço de tradução de números.
 * O servidor processa apenas uma conexão por vez.
 */
public class NumbersTranslatorServer {
	
	/**
	 * Inicia o servidor e aguarda as requisições HTTP.
	 * @throws IOException
	 */
	public void startServer(int port) throws IOException, IllegalArgumentException {
		NumbersTranslator nc = new NumbersTranslator();
		try (ServerSocket server = new ServerSocket(port)){
			System.out.println("Servidor OK e aguardando requisições.");
			while (true) {
				try (Socket clientSocket = server.accept()){
					String parameter = getParameter(clientSocket);
					if (parameter == null) {
						String httpResponse = "HTTP/1.1 400 Bad Request\r\n\r\n";
						clientSocket.getOutputStream().write(httpResponse.getBytes("ISO-8859-1"));
					}
					try {
						String number = nc.translateNumber(parameter);
						String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + parseResponse(number);
						clientSocket.getOutputStream().write(httpResponse.getBytes("ISO-8859-1"));
					} catch (NumberFormatException e) {
						String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + e.getMessage();
						clientSocket.getOutputStream().write(httpResponse.getBytes("ISO-8859-1"));
					} catch (Exception e) {
						String httpResponse = "HTTP/1.1 500 Internal Server Error\r\n\r\n";
						clientSocket.getOutputStream().write(httpResponse.getBytes("ISO-8859-1"));
					}
				}
			}
		}
	}
	
	/**
	 * Cria uma mensagem de resposta padrão com o número informado em number. 
	 * @param number
	 * @return String 
	 * @throws JsonProcessingException 
	 */
	private String parseResponse(String number) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("JSONResponseSerializer", new Version(1, 0, 0, null, null, null));
		module.addSerializer(String.class, new JSONResponseSerializer(String.class));
		mapper.registerModule(module);
		return mapper.writeValueAsString(number);
	}
	
	/**
	 * Classe interna para definir a estrutura de resposta do JSON 
	 */
	private class JSONResponseSerializer extends StdSerializer<String>{

		private static final long serialVersionUID = 929915948763742575L;

		protected JSONResponseSerializer(Class<String> t) {
			super(t);
		}

		@Override
		public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
			gen.writeStartObject();
			gen.writeStringField("extenso", value);
			gen.writeEndObject();
		}
		
	}
	
	/**
	 * Lê as informações da requisição HTTP e extrai o parâmetro da chamada GET
	 * Se não encontrar retorna null
	 * @param clientSocket 
	 * @return
	 * @throws IOException
	 */
	private String getParameter(Socket clientSocket) throws IOException {
		InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
		BufferedReader reader = new BufferedReader(isr);
		String line = reader.readLine();
		while (line != null && !line.isEmpty()) {
			Pattern p = Pattern.compile("GET\\s\\/(.*)\\sHTTP.*");
			Matcher m = p.matcher(line);
			if(m.matches()) {
				return m.group(1);
			}
			line = reader.readLine();
		}
		return null;
	}

}