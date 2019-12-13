package br.org.certi.prova;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementa a tradução de um número inteiro dado em dígitos em um número por extenso.
 * O número deve pertencer ao intervalo -99999 a 99999.
 */
public class NumbersTranslator {
	
	//lista dos números na unidade
	private final String[] units = {"zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove"};
	
	//lista das dezenas até dezenove
	private final String[] numbersTeens = {"dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"};
	
	//lista das dezenas múltiplas de 10
	private final String[] tens = {"", "dez", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"};
	
	//lista das centenas
	private final String[] hundreds = {"", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"};
	
	/**
	 * Executa a tradução do número informado. Retorna a string contendo o número por exetenso.
	 * O método verifica primeiro se o formato do número está correto e dentro do intervalo esperado, caso contrário dispara a exceção de formato inválido. 
	 * @param numberInput
	 * @return String
	 * @throws NumberFormatException
	 */
	public String translateNumber(String numberInput) {
		if (!checkFormat(numberInput))
			throw new NumberFormatException("Número de formato inválido. Informar um número entre -99999 e 99999 sem pontuação");
		
		//faz essa conversão para retirar os zeros à esquerda
		numberInput = String.valueOf(Integer.parseInt(numberInput));
		return parseNumber(numberInput);
	}
	
	/**
	 * Realiza a leitura do número informado e a sua tradução.
	 * Utiliza uma pilha auxiliar para a interpretação da posição dos números, podendo escalar o range válido.
	 * A pilha é organizada de modo que as dezenas fiquem com as unidades nas posições pares e as centenas separadas nas posições ímpares.
	 * Por exemplo, o número 12345 terá uma 
	 * pilha de 3 posições na seguinte forma: 45 | 3 | 12
	 * A interpretação então é realizada da esquerda para a direita.
	 * A posição 2 (com o valor 12), par, indica a dezena e unidade, e gera "doze". Além disso essa posição indica o milhar, portanto concatena-se "mil", gerando "doze mil".
	 * A posição 1 (com o valor 3), ímpar, indica a centena, e gera "trezentos".
	 * A posição 0 (com o valor 45) indica a dezena e unidade, e gera "quarenta e cinco".
	 * 
	 * Os conectores são adicionados no decorrer do processo. Resultando no número "doze mil e trezentos e quarenta e cinco".
	 * 
	 * A escalabilidade do range portanto está vinculado às posições pares da pilha, onde a posição 4 indica milhão, a 6 indica bilhão, etc.
	 * @param numberInput
	 * @return
	 */
	private String parseNumber(String numberInput) {
		//conversão para retirar os zeros à esquerda e testar se o número é positivo ou negativo.
		int number = Integer.parseInt(numberInput);
		boolean isNegative = (number < 0);
		String result = "";
		if (isNegative) {
			number = number * -1; //remove o sinal de menos para a interpretação do número
			result = "menos ";
		}
		
		String nString = Integer.toString(number);
		String[] nStack = createNumberStack(nString);
		for (int i = nStack.length - 1; i >= 0; i--) {
			if (i % 2 == 0) {
				result = result + translateTens(nStack[i]);
			} else {
				result = result + translateHundreds(nStack[i]);
			}
			if (i == 2) {
				result = result + " mil e ";
			}
		}
		//Aqui se tratam exceções às regras de nomenclatura e alguns ajustes no número traduzido
		result = result.replaceAll("\\Aum\\smil", "mil"); //evita escrever "um mil" para manter simplesmente "mil"
		result = result.replaceAll("mil\\se\\s\\z", "mil"); //remove o final " e " quando não houver nenhum número após mil.  
		result = result.replaceAll("cento\\se\\szero", "cem"); //converte "cento" em "cem" quando o número for 100
		result = result.replaceAll("(\\w+)\\se\\szero", "$1"); //escreve apenas "zero" se o número é 0
		result = result.trim();
		return result;
	
	}
	
	/**
	 * Traduz o número sNumber informado como centena de acordo com o array hundreds.
	 * 1 - Cento; 2 - Duzentos, etc.
	 * @param sNumber
	 * @return String com o número traduzido
	 */
	private String translateHundreds(String sNumber) {
		int number = Integer.parseInt(sNumber);
		if (number == 0)
			return "";
		return hundreds[number] + " e ";
	}
	
	/**
	 * Traduz o número sNumber informado em unidades e dezenas de acordo com os arrays de units, numberteens e tens.
	 * @param sNumber
	 * @return String com o número traduzido
	 */
	private String translateTens(String sNumber) {
		int number = Integer.parseInt(sNumber);
		if (number >= 0 && number <= 9) {
			return units[number];
		} else if (number >= 10 && number <=19) {
			return numbersTeens[number - 10];
		} else {
			int tensDigit = Integer.parseInt(String.valueOf(sNumber.charAt(0)));
			int unitDigit = Integer.parseInt(String.valueOf(sNumber.charAt(1)));
			return tens[tensDigit] + " e " + units[unitDigit];
		}
	}
	
	/**
	 * Cria a pilha de interpretação do número nString. A pilha é um array de Strings.
	 * 00 - dezena e unidade de milhar
	 * 0  - centena
	 * 00 - dezena e unidade
	 * @param nString
	 * @return String[]
	 */
	private String[] createNumberStack(String nString) {
		int stackSize = Math.floorDiv(nString.length(), 3) * 2;
		if (nString.length() % 3 > 0) {
			stackSize++;
		}
		String[] nStack = new String[stackSize];
		int stackPosition = 0;
		String stackNumber = "";
		for (int i = nString.length() - 1; i >= 0 ; i--) {
			stackNumber = String.valueOf(nString.charAt(i)) + stackNumber; 
			if (stackPosition == 0 || stackPosition % 2 == 0) {
				nStack[stackPosition] = stackNumber;
				if (stackNumber.length() == 2) {
					stackPosition++;
					stackNumber = "";
				}
			} else {
				nStack[stackPosition] = stackNumber;
				stackPosition++;
				stackNumber = "";
			}
		}
		return nStack;
	}
	
	/**
	 * Valida se o número foi informado no formato correto "-?\d{1,5}".
	 * Pode começar com o sinal de menos (-). Possui entre 1 e 5 dígitos.
	 * Contém somente os símbolos numéricos e o sinal de menos e nenhum outro símbolo.
	 * @param numberInput
	 * @return boolean
	 */
	private boolean checkFormat(String numberInput) {
		Pattern p = Pattern.compile("-?\\d{1,5}");
		Matcher m = p.matcher(numberInput);
		return m.matches();
	}
	
}
