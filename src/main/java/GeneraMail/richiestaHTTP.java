package GeneraMail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class richiestaHTTP {
	public static StringBuilder richiesta () {
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		StringBuilder buffer = new StringBuilder();
		try {
			URL url = new URL("https://api.guerrillamail.com/ajax.php?f=get_email_address");
			
			urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            
            InputStream MailRisultato = urlConnection.getInputStream();
            
            if (MailRisultato == null) {
                return null;
            }
            
            reader = new BufferedReader(new InputStreamReader(MailRisultato));
            
            String line;
            while ((line = reader.readLine()) != null) {
            	buffer.append(line + "\n");
            }
            
            if (buffer.length() == 0) {
            	return null;
            }          
		}
		catch (IOException e){
			throw new RuntimeException(e);
		}
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return buffer;
	}
}






