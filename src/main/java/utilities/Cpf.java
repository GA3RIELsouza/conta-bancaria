package utilities;

import java.util.InputMismatchException;

public final class Cpf
{
    public static final String regexAplicaMascara     = "(\\d{3})(\\d{3})(\\d{3})(\\d{2})";
    public static final String placementAplicaMascara = "$1.$2.$3-$4";
    public static final String regexRemoveMascara     = "[^0-9]";
    public static final String placementRemoveMascara = "";
    
    public static String aplicaMascara(String cpf)
    {
        while(cpf.length() < 11)
            cpf = "0" + cpf;
        
        return cpf.replaceAll(regexAplicaMascara, placementAplicaMascara);
    }
    
    public static String removeMascara(String cpf)
    {
        return cpf.replaceAll(regexRemoveMascara, placementRemoveMascara);
    }
    public static boolean isCpf(String cpf)
    {
        cpf = removeMascara(cpf);
        
        if(cpf.equals("00000000000") || cpf.equals("11111111111") ||
           cpf.equals("22222222222") || cpf.equals("33333333333") ||
           cpf.equals("44444444444") || cpf.equals("55555555555") ||
           cpf.equals("66666666666") || cpf.equals("77777777777") ||
           cpf.equals("88888888888") || cpf.equals("99999999999") ||
           cpf.length() != 11)
        	return false;

        char dig10, dig11;
        int soma, resto, num, peso, i;

        try
        {
            soma = 0;
            peso = 10;
            for(i = 0; i < 9; i++)
            {
                num = (int)(cpf.charAt(i) - 48);
                soma = soma + (num * peso);
                peso = peso - 1;
            }

            resto = 11 - (soma % 11);
            if((resto == 10) || (resto == 11))
                dig10 = '0';
            else
                dig10 = (char)(resto + 48);

            soma = 0;
            peso = 11;
            for(i = 0; i < 10; i++)
            {
                num = (int)(cpf.charAt(i) - 48);
                soma = soma + (num * peso);
                peso = peso - 1;
            }

            resto = 11 - (soma % 11);
            if((resto == 10) || (resto == 11))
                dig11 = '0';
            else
                dig11 = (char)(resto + 48);

            if((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                 return true;
            else
                return false;
        }
        catch(InputMismatchException erro)
        {
            return false;
        }
    }
}