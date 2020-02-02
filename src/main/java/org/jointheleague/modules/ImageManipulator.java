package org.jointheleague.modules;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ImageManipulator extends CustomMessageCreateListener {

	URL url;
	boolean b = false;
	BufferedImage imagee;

	public ImageManipulator(String channelName) {
		super(channelName);

	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().startsWith("!image")) {

			String urll = event.getMessageContent().substring(6);
			try {
				url = new URL(urll);
				event.getChannel().sendMessage("Step 1 Complete");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				event.getChannel().sendMessage("Not an image!");
			}

			try {
				Image image = ImageIO.read(url);
				imagee = (BufferedImage) image;
				event.getChannel().sendMessage("Finalizing");
				System.out.println("Image height " + imagee.getHeight());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				event.getChannel().sendMessage("Not an image!");
			}
			checkChunks();
		}

	}
	public void checkChunks() {
		for (int z = 0; z < imagee.getHeight(); z += 3) {
			for (int m = 0; m < imagee.getWidth(); m += 3) {
				int chunkTotal = 0;

				for (int i = z; i < z + 3; i++) {
					for (int j = z; j < z + 3; j++) {
						Color color = new Color(imagee.getRGB(i, j));
						int red = color.getRed();
						int green = color.getGreen();
						int blue = color.getBlue();
						//System.out.println("Red " + red + "Green " + green + "Blue " + blue);
						//System.out.println(" Z " + z + " M " + m + " I " + i + " J " + j);
						int averageP = (red + green + blue) / 3;
						chunkTotal += averageP;
					}
				}
				int chunkAvg = chunkTotal / 9;
				System.out.println("Chunk average " + chunkAvg);
			}

		}
	}
}
