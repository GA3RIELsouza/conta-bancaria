package utilities;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Email
{
	static final String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	 
	static public boolean isEmail(String email)
	{
		Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches())
            return true;
        return false;
	}
}