package utilities;

public final class NumeroTelefone {
    public static final String REGEX_APLICA_MASCARA_COM_9 = "(\\d{2})(\\d{5})(\\d{4})";
    public static final String REGEX_APLICA_MASCARA_SEM_9 = "(\\d{2})(\\d{4})(\\d{4})";
    public static final String PLACEMENT_APLICA_MASCARA   = "($1) $2-$3";
    public static final String REGEX_REMOVE_MASCARA       = "[^0-9]";
    public static final String PLACEMENT_REMOVE_MASCARA   = "";
    public static final String[] DDDS_VALIDOS = {
        "11", "12", "13", "14", "15", "16", "17", "18",
        "19", "21", "22", "24", "27", "28", "31", "32",
        "33", "34", "35", "37", "41", "42", "43", "44",
        "45", "46", "47", "48", "49", "51", "53", "54",
        "55", "61", "62", "63", "64", "65", "66", "67",
        "68", "69", "71", "73", "74", "75", "77", "81",
        "82", "83", "84", "85", "86", "87", "88", "89",
        "91", "92", "93", "94", "95", "96", "97", "98",
        "99"
    };

    private NumeroTelefone(){}
    
    public static String aplicaMascara(String telefone) {
        return switch (telefone.length()) {
            case 11 -> telefone.replaceAll(REGEX_APLICA_MASCARA_COM_9, PLACEMENT_APLICA_MASCARA);
            case 10 -> telefone.replaceAll(REGEX_APLICA_MASCARA_SEM_9, PLACEMENT_APLICA_MASCARA);
            default -> null;
        };
    }
    
    public static String removeMascara(String telefone) {
        return telefone.replaceAll(REGEX_REMOVE_MASCARA, PLACEMENT_REMOVE_MASCARA);
    }
    
    public static boolean isTelefone(String telefone) {
        String ddd;
        boolean isDddValido = false;
        
        telefone = removeMascara(telefone);
        
        try {
            ddd = telefone.charAt(0) + "" + telefone.charAt(1);
        } catch (Exception ex) {
            return false;
        }
        
        for (String dddV : DDDS_VALIDOS)
            if (dddV.equals(ddd)) {
                isDddValido = true;
                break;
            }
        
        if (!isDddValido || (telefone.length() == 11 && telefone.charAt(2) != '9'))
            return false;
        else
            return true;
    }
}