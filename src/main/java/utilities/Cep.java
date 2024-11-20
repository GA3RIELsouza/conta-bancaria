package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import enums.Estado;
import models.Localidade;

public final class Cep {
    private Cep(){}

    public static JSONObject consultarCep(long cep) {
        final String urlViaCEP = "https://viacep.com.br/ws/" + cep + "/json/";

        try  {
            URL url = new URL(urlViaCEP);

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setConnectTimeout(5000);
            http.setReadTimeout(5000);

            int responseCode = http.getResponseCode();
            if (responseCode != 200) 
                throw new RuntimeException("Problemas ao consultar o CEP:\n" + responseCode);

            BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String         Linha;
            StringBuffer   bufferSaida = new StringBuffer();

            while ((Linha = bufferEntrada.readLine()) != null) {
            	bufferSaida.append(Linha);
            }
            bufferEntrada.close();

            return new JSONObject(bufferSaida.toString());
        } catch (Exception e) {
            throw new RuntimeException("Problemas ao consultar o CEP:\n" + e.getMessage());
        }
	}

	public static Localidade buscarDadosCep(long cep) {
		Localidade localidade = null;
		
		JSONObject retornoViaCEP = consultarCep(cep);
		
		if (retornoViaCEP != null) {
			localidade = new Localidade();
			
			localidade.setId((long) -1);
			localidade.setCep(Integer.parseInt(retornoViaCEP.getString("cep").replaceAll("[^0-9]", "")));
			localidade.setEstado(Estado.fromNome(retornoViaCEP.getString("estado")));
			localidade.setCidade(retornoViaCEP.getString("localidade"));
			localidade.setBairro(retornoViaCEP.getString("bairro"));
			localidade.setLogradouro(retornoViaCEP.getString("logradouro"));
		}
		
		return localidade;
	}
}
