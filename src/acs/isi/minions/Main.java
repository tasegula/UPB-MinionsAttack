package acs.isi.minions;

import java.awt.EventQueue;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JFrame;

import acs.isi.util.JStuff;

public class Main {
	String title;

	public Main(String title) {
		this.title = title;
		MapWrapper.setup();
		MapWrapper.setDefaultLayer(LayerWrapperEnum.MAGNITUDE);
		MapWrapper.bind();
	}
	
	public void run() {
		// create the UI, including the map, for the application.
		JFrame window = JStuff.createWindow(title);
		window.add(MapWrapper.contentPane);
		window.setVisible(true);
	}

	/**
	 * Starting point of this application.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					Main application = new Main(acs.isi.util.Utils.App.title);
					application.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
