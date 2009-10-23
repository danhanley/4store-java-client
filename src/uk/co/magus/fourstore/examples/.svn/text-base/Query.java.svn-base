package uk.co.magus.fourstore.examples;

import java.io.IOException;
import java.net.MalformedURLException;

import uk.co.magus.fourstore.client.Store;

public class Query {

	private static String sparql = "SELECT * WHERE { ?s ?p ?o } LIMIT 10";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Store store;
		try {
			System.out.println("Start");
			store = new Store("http://193.131.98.57:8080");
			String response1 = store.query(sparql);
			String response2 = store.query(sparql,Store.OutputFormat.JSON);
			System.out.println(response1);
			System.out.println(response2);
			System.out.println("Done");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
