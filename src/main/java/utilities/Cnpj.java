package utilities;

import java.util.InputMismatchException;

public final class Cnpj {
    public static final String REGEX_APLICA_MASCARA     = "(\\d{2})(\\d{3})(\\d{3})/(\\d{4})-(\\d{2})";
    public static final String PLACEMENT_APLICA_MASCARA = "$1.$2.$3/$4-$5";
    public static final String REGEX_REMOVE_MASCARA     = "[^0-9]";
    public static final String PLACEMENT_REMOVE_MASCARA = "";

    private Cnpj(){}

    public static String aplicaMascara(String cnpj) {
        while (cnpj.length() < 14)
            cnpj = "0" + cnpj;
        
        return cnpj.replaceAll(REGEX_APLICA_MASCARA, PLACEMENT_APLICA_MASCARA);
    }

    public static String removeMascara(String cnpj) {
        return cnpj.replaceAll(REGEX_REMOVE_MASCARA, PLACEMENT_REMOVE_MASCARA);
    }

    public static boolean isCnpj(String cnpj) {
        cnpj = removeMascara(cnpj);

        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
            cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
            cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
            cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
            cnpj.equals("88888888888888") || cnpj.equals("99999999999999") ||
            cnpj.length() != 14)
            return false;

        char dig13, dig14;
        int soma, resto, num, peso, i;

        try {
            soma = 0;
            peso = 5;
            for (i = 0; i < 12; i++) {
                num = (int)(cnpj.charAt(i) - 48);
                soma = soma + (num * peso);
                peso = (peso == 2) ? 9 : peso - 1;
            }

            resto = soma % 11;
            dig13 = (resto < 2) ? '0' : (char)(11 - resto + 48);

            soma = 0;
            peso = 6;
            for (i = 0; i < 13; i++) {
                num = (int)(cnpj.charAt(i) - 48);
                soma = soma + (num * peso);
                peso = (peso == 2) ? 9 : peso - 1;
            }

            resto = soma % 11;
            dig14 = (resto < 2) ? '0' : (char)(11 - resto + 48);

            if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
                return true;
            else
                return false;
        } catch (InputMismatchException erro) {
            return false;
        }
    }
}
