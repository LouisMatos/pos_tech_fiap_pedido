package br.com.postechfiap.jlapppedido.shared.utils;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidaCPFTest {

  @Test
  @DisplayName("Should validate correct CPF")
  void shouldValidateCorrectCPF() {
    assertTrue(ValidaCPF.isValidCPF("12345678909"));
  }

  @Test
  @DisplayName("Should invalidate incorrect CPF")
  void shouldInvalidateIncorrectCPF() {
    assertFalse(ValidaCPF.isValidCPF("12345678900"));
  }

  @Test
  @DisplayName("Should invalidate CPF with length not equal to 11")
  void shouldInvalidateCPFWithLengthNotEqualTo11() {
    assertFalse(ValidaCPF.isValidCPF("123456789"));
  }

  @Test
  @DisplayName("Should invalidate null CPF")
  void shouldInvalidateNullCPF() {
    assertFalse(ValidaCPF.isValidCPF(null));
  }
}
