package utilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CpfTest {
	@Test
	void testAplicaMascara() {
		String retornoChamada = utilities.Cpf.aplicaMascara("13242872924");
		
		assertEquals(retornoChamada, "132.428.729-24");
	}
	
	@Test
	void testRemoveMascara() {
		String retornoChamada = utilities.Cpf.removeMascara("132.428.729-24");
		
		assertEquals(retornoChamada, "13242872924");
	}
	
	@Test
	void testIsCpfTrue() {
		boolean retornoChamada = utilities.Cpf.isCpf("132.428.729-24");
		
		assertEquals(retornoChamada, true);
	}
	
	@Test
	void testIsCpfFalse() {
		boolean retornoChamada = utilities.Cpf.isCpf("111.222.333-44");
		
		assertEquals(retornoChamada, false);
	}
}