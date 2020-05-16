package org.jointheleague.modules;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;

public class AdvancedHangmanListener extends CustomMessageCreateListener {
	String helpCommand = "!help";
	String uncoverCommand = "!uncover";
	String guessLetter = "!guessl";
	String guessPhrase = "!guessp";
	String startCommand = "!start";
	String phrase = "";
	String begspaces = "";
	int pointTotal = 10;

	public AdvancedHangmanListener(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
	
		if (event.getMessageContent().toLowerCase().startsWith(startCommand)){
			begspaces = "";
			pointTotal = 10;
			event.getChannel().sendMessage(
					"Welcome to Advanced Hangman! Here is how you play: \n 1) You will be presented with a random cliche phrase and given the same amount of underscores as the letters in each word.");
			event.getChannel().sendMessage(
					"2)Comment '!help' to view commands!");

			event.getChannel().sendMessage("Here is your phrase:");
			int r = new Random().nextInt();
			try {
				BufferedReader br = new BufferedReader(
						new FileReader("src/main/java/org/jointheleague/modules/cliches"));
				int numLines = 0;
				String line = br.readLine();
				while (line != null) {
					numLines++;
					line = br.readLine();
				}
				BufferedReader br2 = new BufferedReader(
						new FileReader("src/main/java/org/jointheleague/modules/cliches"));
				int R = new Random().nextInt(numLines);
				for (int i = 0; i < R; i++) {
					phrase = br2.readLine();
				}
				
				
				for (int i = 0; i < phrase.length(); i++) {
					if (phrase.charAt(i) == ' ') {
						begspaces = begspaces + "  ";
					} else {
						begspaces = begspaces + "_ ";
					}
				}
				event.getChannel().sendMessage("`"+ begspaces + "`");
				event.getChannel().sendMessage("Your points: "+ pointTotal);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if(event.getMessageContent().toLowerCase().startsWith(helpCommand)){
			event.getChannel().sendMessage(
					"Here are your commands: \n '!start'-starts a new game \n '!help'-shows all commands for reference \n '!uncover-use to uncover a letter; enter guesses in format Word#,Letter# \n '!guessl'-guess a letter; enter guesses in format Word#,Letter#,guess \n '!guessp' -guess a phrase");
		}
		if(event.getMessageContent().toLowerCase().startsWith(uncoverCommand)) {
			int numLetters = 0;
			String uncoverletter = "";
			try {
			int word = Integer.parseInt(event.getMessageContent().substring(9,10));
			int letter = Integer.parseInt(event.getMessageContent().substring(11,12));
			int currentword = 1;
			for(int i = 0; i<phrase.length();i++) {
				numLetters++;
				if(phrase.charAt(i)==' ') {
					currentword++;
				}
				if(currentword == word) {
						if(word == 1) {
							numLetters = letter;
							if(letter == 1) {
							uncoverletter = phrase.substring(i,i+1);
							numLetters = 1;
							}else {
							uncoverletter = phrase.substring(i+letter-1,i+letter);
							}
						}else {
							uncoverletter = phrase.substring(i+letter,i+letter+1);
							numLetters = numLetters + letter;
							
						}
						event.getChannel().sendMessage(uncoverletter);
						break;
				}
				
			}
			pointTotal = pointTotal-1;
			event.getChannel().sendMessage("Your points: " + pointTotal);
			int cl = 0;
			String newString = "";
			for(int i = 0; i<begspaces.length()-1; i++) {
				if(i%2 == 0) {
				cl++;
					if(cl==numLetters) {
					newString = newString + uncoverletter + " ";
					}else{
						if(i == 1) {
							newString = newString + (begspaces.substring(i+1,i+2));
						}else {
							newString = newString + (begspaces.substring(i,i+2));	
						}
					}
				}
			}
			event.getChannel().sendMessage("`" + newString + "`");
			begspaces = newString;
			}catch(Exception e) {
				event.getChannel().sendMessage("Remember to enter your commands in the format: !uncover word#,letter# (it's really not that difficult to remember so please try)");
			}
		

	}
	if(event.getMessageContent().toLowerCase().startsWith(guessLetter)) {
		try {
		int word = Integer.parseInt(event.getMessageContent().substring(8,9));
		int letter = Integer.parseInt(event.getMessageContent().substring(10,11));
		String guessletter = event.getMessageContent().substring(12,13);
		String uncoverletter = "";
		int currentword = 1;
		int numLetters = 0;
		for(int i = 0; i<phrase.length();i++) {
			numLetters++;
			if(phrase.charAt(i)==' ') {
				currentword++;
			}
			if(currentword == word) {
					if(word == 1) {
						numLetters = letter;
						if(letter == 1) {
						uncoverletter = phrase.substring(i,i+1);
						numLetters = 1;
						}else {
						uncoverletter = phrase.substring(i+letter-1,i+letter);
						}
					}else {
						uncoverletter = phrase.substring(i+letter,i+letter+1);
						numLetters = numLetters + letter;
						
					}
					break;
			}
			
		}
		if(uncoverletter.equals(guessletter)){	
			pointTotal = pointTotal + 2;
			event.getChannel().sendMessage("You guessed a letter! Congratulations! You have proved that you have the intellectual capabilities of a 5 year old!");
			event.getChannel().sendMessage("Your points: " + pointTotal);
			int cl = 0;
			String newString = "";
			for(int i = 0; i<begspaces.length()-1; i++) {
				if(i%2 == 0) {
				cl++;
					if(cl==numLetters) {
					newString = newString + uncoverletter + " ";
					}else{
						if(i == 1) {
							newString = newString + (begspaces.substring(i+1,i+2));
						}else {
							newString = newString + (begspaces.substring(i,i+2));	
						}
					}
				}
			}
			event.getChannel().sendMessage("`" + newString + "`");
			begspaces = newString;
		
		}else {
			event.getChannel().sendMessage("Wow, you really are as stupid as you look. WRONG LETTER FOOL!");
			pointTotal = pointTotal-2;
			event.getChannel().sendMessage("Your points: " + pointTotal);
			event.getChannel().sendMessage("`" + begspaces + "`");
		}
	}catch(Exception e) {
		event.getChannel().sendMessage("Remember to enter your commands in the format: !uncover word#,letter# (it's really not that difficult to remember so please try)");
	}


}
		
	
	if(event.getMessageContent().toLowerCase().startsWith(guessPhrase)) {
		try {
		String guessmessage = event.getMessageContent().substring(8,event.getMessageContent().length());
		boolean failed = false;
		if(guessmessage.length()!= phrase.length()) {
			event.getChannel().sendMessage("Ok seriously dude. You just failed. Good luck coming back from that!");
			pointTotal = pointTotal-5;
			event.getChannel().sendMessage("Your points: " + pointTotal);
			event.getChannel().sendMessage("`" + begspaces + "`");	
			failed = true;
		}else {
			for(int i = 0; i<phrase.length();i++) {
				if(phrase.charAt(i)!=guessmessage.charAt(i)) {
					event.getChannel().sendMessage("Ok seriously dude. You just failed. Good luck coming back from that!");	
					pointTotal = pointTotal-5;
					event.getChannel().sendMessage("Your points: " + pointTotal);
					event.getChannel().sendMessage("`" + begspaces + "`");
					failed = true;
					break;
				}
			}}
			if(failed ==false) {
				event.getChannel().sendMessage("Wow. Great job. You got it.");
				pointTotal = pointTotal + 10;
				event.getChannel().sendMessage("You ended with " + pointTotal + " points.");
			}
		
	}catch(Exception e) {
	event.getChannel().sendMessage("PLEASE ACTUALLY TYPE IN A VALID PHRASE!!! STOP BEING DIFFICULT!!!")	;
	}
	
	}}}
	