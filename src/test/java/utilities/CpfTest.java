package utilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CpfTest
{
	@Test
	void testAplicaMascara()
	{
		String retornoChamada = utilities.Cpf.aplicaMascara("13242872924");
		
		assertEquals(retornoChamada, "132.428.729-24");
	}
}