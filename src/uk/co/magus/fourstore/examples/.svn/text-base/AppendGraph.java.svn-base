package uk.co.magus.fourstore.examples;

import java.io.IOException;
import java.net.MalformedURLException;

import uk.co.magus.fourstore.client.Store;

public class AppendGraph {

	private static String exampleRDFGraph = "<?xml version=\"1.0\"?>" 
	        + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
			+ " xmlns:si=\"http://www.w3schools.com/rdf/\">" 
			+ "<rdf:Description rdf:about=\"http://www.w3schools.com\">"
			+ "  <si:title>How to operate with a blown mind</si:title>"
			+ "  <si:author>Lo Fidelity Allstars</si:author>"
			+ " </rdf:Description>" 
			+ "</rdf:RDF>";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Store store;
		try {
			System.out.println("Start");
			store = new Store("http://193.131.98.57:8080");
			String response = store.append("http://example.org/musicGraph",
					exampleRDFGraph, Store.InputFormat.XML);
			System.out.println(response);
			System.out.println("Done");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
