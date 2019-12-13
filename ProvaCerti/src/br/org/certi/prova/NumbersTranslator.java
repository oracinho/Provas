package br.org.certi.prova;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementa a tradu��o de um n�mero inteiro dado em d�gitos em um n�mero por extenso.
 * O n�mero deve pertencer ao intervalo -99999 a 99999.
 */
public class NumbersTranslator {
	
	//lista dos n�meros na unidade
	private final String[] units = {"zero", "um", "dois", "tr�s", "quatro", "cinco", "seis", "sete", "oito", "nove"};
	
	//lista das dezenas at� dezenove
	private final String[] numbersTeens = {"dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"};
	
	//lista das dezenas m�ltiplas de 10
	private final String[] tens = {"", "dez", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"};
	
	//lista das centenas
	private final String[] hundreds = {"", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"};
	
	/**
	 * Executa a tradu��o do n�mero informado. Retorna a string contendo o n�mero por exetenso.
	 * O m�todo verifica primeiro se o formato do n�mero est� correto e dentro do intervalo esperado, caso contr�rio dispara a exce��o de formato inv�lido. 
	 * @param numberInput
	 * @return String
	 * @throws NumberFormatException
	 */
	public String translateNumber(String numberInput) {
		if (!checkFormat(numberInput))
			throw new NumberFormatException("N�mero de formato inv�lido. Informar um n�mero entre -99999 e 99999 sem pontua��o");
		
		//faz essa convers�o para retirar os zeros � esquerda
		numberInput = String.valueOf(Integer.parseInt(numberInput));
		return parseNumber(numberInput);
	}
	
	/**
	 * Realiza a leitura do n�mero informado e a sua tradu��o.
	 * Utiliza uma pilha auxiliar para a interpreta��o da posi��o dos n�meros, podendo escalar o range v�lido.
	 * A pilha � organizada de modo que as dezenas fiquem com as unidades nas posi��es pares e as centenas separadas nas posi��es �mpares.
	 * Por exemplo, o n�mero 12345 ter� uma 
	 * pilha de 3 posi��es na seguinte forma: 45 | 3 | 12
	 * A interpreta��o ent�o � realizada da esquerda para a direita.
	 * A posi��o 2 (com o valor 12), par, indica a dezena e unidade, e gera "doze". Al�m disso essa posi��o indica o milhar, portanto concatena-se "mil", gerando "doze mil".
	 * A posi��o 1 (com o valor 3), �mpar, indica a centena, e gera "trezentos".
	 * A posi��o 0 (com o valor 45) indica a dezena e unidade, e gera "quarenta e cinco".
	 * 
	 * Os conectores s�o adicionados no decorrer do processo. Resultando no n�mero "doze mil e trezentos e quarenta e cinco".
	 * 
	 * A escalabilidade do range portanto est� vinculado �s posi��es pares da pilha, onde a posi��o 4 indica milh�o, a 6 indica bilh�o, etc.
	 * @param numberInput
	 * @return
	 */
	private String parseNumber(String numberInput) {
		//convers�o para retirar os zeros � esquerda e testar se o n�mero � positivo ou negativo.
		int number = Integer.parseInt(numberInput);
		boolean isNegative = (number < 0);
		String result = "";
		if (isNegative) {
			number = number * -1; //remove o sinal de menos para a interpreta��o do n�mero
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
		//Aqui se tratam exce��es �s regras de nomenclatura e alguns ajustes no n�mero traduzido
		result = result.replaceAll("\\Aum\\smil", "mil"); //evita escrever "um mil" para manter simplesmente "mil"
		result = result.replaceAll("mil\\se\\s\\z", "mil"); //remove o final " e " quando n�o houver nenhum n�mero ap�s mil.  
		result = result.replaceAll("cento\\se\\szero", "cem"); //converte "cento" em "cem" quando o n�mero for 100
		result = result.replaceAll("(\\w+)\\se\\szero", "$1"); //escreve apenas "zero" se o n�mero � 0
		result = result.trim();
		return result;
	
	}
	
	/**
	 * Traduz o n�mero sNumber informado como centena de acordo com o array hundreds.
	 * 1 - Cento; 2 - Duzentos, etc.
	 * @param sNumber
	 * @return String com o n�mero traduzido
	 */
	private String translateHundreds(String sNumber) {
		int number = Integer.parseInt(sNumber);
		if (number == 0)
			return "";
		return hundreds[number] + " e ";
	}
	
	/**
	 * Traduz o n�mero sNumber informado em unidades e dezenas de acordo com os arrays de units, numberteens e tens.
	 * @param sNumber
	 * @return String com o n�mero traduzido
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
	 * Cria a pilha de interpreta��o do n�mero nString. A pilha � um array de Strings.
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
	 * Valida se o n�mero foi informado no formato correto "-?\d{1,5}".
	 * Pode come�ar com o sinal de menos (-). Possui entre 1 e 5 d�gitos.
	 * Cont�m somente os s�mbolos num�ricos e o sinal de menos e nenhum outro s�mbolo.
	 * @param numberInput
	 * @return boolean
	 */
	private boolean checkFormat(String numberInput) {
		Pattern p = Pattern.compile("-?\\d{1,5}");
		Matcher m = p.matcher(numberInput);
		return m.matches();
	}
	
}
