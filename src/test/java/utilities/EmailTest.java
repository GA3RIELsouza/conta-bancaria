package utilities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmailTest
{
	@Test
	void testIsEmailTrue()
	{
		boolean retornoChamada = utilities.Email.isEmail("email.teste123@gmail.com");
		
		assertEquals(retornoChamada, true);
	}
	
	@Test
	void testIsEmailFalse()
	{
		boolean retornoChamada = utilities.Email.isEmail("asdada");
		
		assertEquals(retornoChamada, false);
	}
}