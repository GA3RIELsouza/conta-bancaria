package utilities;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class Email {
	public static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	private Email(){}
	 
	public static boolean isEmail(String email) {
		Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches())
            return true;
        else
        	return false;
	}
}