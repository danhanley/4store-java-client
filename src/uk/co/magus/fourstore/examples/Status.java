package uk.co.magus.fourstore.examples;

import java.io.IOException;
import java.net.MalformedURLException;

import uk.co.magus.fourstore.client.Store;



public class Status {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Store store;
		try {
			store = new Store("http://193.131.98.57:8080");
			String status = store.getStatus();
			System.out.println(status);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
