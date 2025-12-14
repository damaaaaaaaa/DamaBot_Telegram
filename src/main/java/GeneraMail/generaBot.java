package GeneraMail;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class generaBot extends TelegramLongPollingBot {
	
	public void sendText (Long utente, String mess) {
		SendMessage sm = SendMessage.builder().chatId(utente.toString()).text(mess).build();
		
		try {
			execute (sm);
		}
		catch (TelegramApiException e){
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public void onUpdateReceived(Update update) {
		var msg = update.getMessage ();
		var user = msg.getFrom ();
		var id = user.getId();
		
		System.out.println (user.getFirstName() + " ha scritto " + msg.getText());
		
		JsonObject jobject = JsonParser.parseString(richiestaHTTP.richiesta().toString()).getAsJsonObject();
		
		String email = jobject.get ("email_addr").getAsString();
		String token = jobject.get ("sid_token").getAsString();
		
		
		if (msg.isCommand()) {
			switch (msg.getText()) {
				case "/generatemail": sendText (id, email); break;
				default: sendText (id, "Benvenuto in DamaBot! Usa il menu per vedere i comandi disponibili"); break;
			};
		}
		else {
			sendText (id, "Usa il menu per vedere i comandi disponibili");
		}
	}

	@Override
	public String getBotUsername() {
		return "rand_mail_bot";
	}

	@Override
	public String getBotToken() {
		return "TOKEN";
	}
	
	public static void main(String[] args) throws TelegramApiException {
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		generaBot bot = new generaBot ();
		botsApi.registerBot(bot);
	}
}
