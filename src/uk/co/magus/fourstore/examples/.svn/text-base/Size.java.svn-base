package uk.co.magus.fourstore.examples;

import java.io.IOException;
import java.net.MalformedURLException;

import uk.co.magus.fourstore.client.Store;



public class Size {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Store store;
		try {
			store = new Store("http://193.131.98.57:8080");
			String size = store.getSize();
			System.out.println(size);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
