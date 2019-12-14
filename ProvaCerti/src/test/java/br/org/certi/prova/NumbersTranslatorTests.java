package br.org.certi.prova;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Realiza testes unitários dos resultados das conversões
 */
public class NumbersTranslatorTests {
	
	NumbersTranslator converter = new NumbersTranslator();

	@Test
	void validPositiveNumbers() {
		assertEquals("zero", converter.translateNumber("0"));
		assertEquals("u", converter.translateNumber("1"));
		assertEquals("dois", converter.translateNumber("2"));
		assertEquals("três", converter.translateNumber("00003"));
		assertEquals("dez", converter.translateNumber("10"));
		assertEquals("treze", converter.translateNumber("13"));
		assertEquals("quatorze", converter.translateNumber("14"));
		assertEquals("setenta", converter.translateNumber("70"));
		assertEquals("setenta e nove", converter.translateNumber("79"));
		assertEquals("oitenta", converter.translateNumber("80"));
		assertEquals("oitenta e três", converter.translateNumber("83"));
		assertEquals("noventa", converter.translateNumber("90"));
		assertEquals("noventa e cinco", converter.translateNumber("95"));
		assertEquals("cem", converter.translateNumber("100"));
		assertEquals("cento e um", converter.translateNumber("101"));
		assertEquals("cento e onze", converter.translateNumber("111"));
		assertEquals("cento e cinquenta", converter.translateNumber("150"));
		assertEquals("setecentos", converter.translateNumber("700"));
		assertEquals("setecentos e setenta e sete", converter.translateNumber("777"));
		assertEquals("mil", converter.translateNumber("1000"));
		assertEquals("mil e um", converter.translateNumber("1001"));
		assertEquals("mil e quatrocentos e vinte e seis", converter.translateNumber("1426"));
		assertEquals("mil e quinhentos", converter.translateNumber("1500"));
		assertEquals("quatro mil e quinhentos", converter.translateNumber("4500"));
		assertEquals("sete mil e oitocentos e trinta e dois", converter.translateNumber("7832"));
		assertEquals("nove mil", converter.translateNumber("9000"));
		assertEquals("onze mil e quinhentos", converter.translateNumber("11500"));
		assertEquals("noventa e nove mil", converter.translateNumber("99000"));
		assertEquals("noventa e nove mil e novecentos e noventa e nove", converter.translateNumber("99999"));
	}

	@Test
	void validNegativeNumbers() {
		assertEquals("zero", converter.translateNumber("-0"));
		assertEquals("zero", converter.translateNumber("-000"));
		assertEquals("zero", converter.translateNumber("-00000"));
		assertEquals("menos um", converter.translateNumber("-1"));
		assertEquals("menos dois", converter.translateNumber("-2"));
		assertEquals("menos cinco", converter.translateNumber("-00005"));
		assertEquals("menos dez", converter.translateNumber("-10"));
		assertEquals("menos treze", converter.translateNumber("-13"));
		assertEquals("menos quatorze", converter.translateNumber("-14"));
		assertEquals("menos setenta", converter.translateNumber("-70"));
		assertEquals("menos setenta e nove", converter.translateNumber("-79"));
		assertEquals("menos oitenta", converter.translateNumber("-80"));
		assertEquals("menos oitenta e três", converter.translateNumber("-83"));
		assertEquals("menos noventa", converter.translateNumber("-90"));
		assertEquals("menos noventa e cinco", converter.translateNumber("-95"));
		assertEquals("menos cem", converter.translateNumber("-100"));
		assertEquals("menos cento e um", converter.translateNumber("-101"));
		assertEquals("menos cento e onze", converter.translateNumber("-111"));
		assertEquals("menos cento e cinquenta", converter.translateNumber("-150"));
		assertEquals("menos setecentos", converter.translateNumber("-700"));
		assertEquals("menos setecentos e setenta e sete", converter.translateNumber("-777"));
		assertEquals("menos mil", converter.translateNumber("-1000"));
		assertEquals("menos mil e um", converter.translateNumber("-1001"));
		assertEquals("menos mil e quatrocentos e vinte e seis", converter.translateNumber("-1426"));
		assertEquals("menos mil e quinhentos", converter.translateNumber("-1500"));
		assertEquals("menos quatro mil e quinhentos", converter.translateNumber("-4500"));
		assertEquals("menos sete mil e oitocentos e trinta e dois", converter.translateNumber("-7832"));
		assertEquals("menos nove mil", converter.translateNumber("-9000"));
		assertEquals("menos noventa e nove mil", converter.translateNumber("-99000"));
		assertEquals("menos noventa e nove mil e novecentos e noventa e nove", converter.translateNumber("-99999"));
	}
}
