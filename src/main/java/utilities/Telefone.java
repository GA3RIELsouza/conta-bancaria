package utilities;

public abstract class Telefone
{
    public static final String regexAplicaMascaraCom9 = "(\\d{2})(\\d{5})(\\d{4})";
    public static final String regexAplicaMascaraSem9 = "(\\d{2})(\\d{4})(\\d{4})";
    public static final String placementAplicaMascara = "($1) $2-$3";
    public static final String regexRemoveMascara     = "[^0-9]";
    public static final String placementRemoveMascara = "";
    public static final String[] dddsValidos =
    {
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
    
    public static String aplicaMascara(String telefone)
    {
        return switch(telefone.length())
        {
            case 11 -> telefone.replaceAll(regexAplicaMascaraCom9, placementAplicaMascara);
            case 10 -> telefone.replaceAll(regexAplicaMascaraSem9, placementAplicaMascara);
            default -> null;
        };
    }
    
    public static String removeMascara(String telefone)
    {
        return telefone.replaceAll(regexRemoveMascara, placementRemoveMascara);
    }
    
    public static boolean isTelefone(String telefone)
    {
        String ddd;
        boolean isDddValido = false;
        
        telefone = removeMascara(telefone);
        
        try
        {
            ddd = telefone.charAt(0) + "" + telefone.charAt(1);
        }
        catch(Exception ex)
        {
            return false;
        }
        
        for(String dddV : dddsValidos)
            if(dddV.equals(ddd))
            {
                isDddValido = true;
                break;
            }
        
        if(!isDddValido || (telefone.length() == 11 && telefone.charAt(2) != '9'))
            return false;
        else
            return true;
    }
}