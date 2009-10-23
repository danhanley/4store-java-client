package uk.co.magus.fourstore.examples;

import java.io.IOException;
import java.net.MalformedURLException;

import uk.co.magus.fourstore.client.Store;

public class Delete {

	/**
	 * 4Store httpd returns HTTP 200 if you try to delete a non-existent resource so this 
	 * is a usage example, not a unit test :-)
	 * 
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.7 
	 * doesn't cover this case explicitly:
	 * 
	 * "The client cannot be guaranteed that the operation has been carried out, 
	 * even if the status code returned from the origin server indicates that the 
	 * action has been completed successfully. 
	 * 
	 * However, the server SHOULD NOT indicate success unless, at the time the response is given, 
	 * it intends to delete the resource or move it to an inaccessible location.
     *
     * A successful response SHOULD be 200 (OK)..."
	 *
     *
	 */
	public static void main(String[] args) {

		Store store;
		try {
			System.out.println("Start");
			store = new Store("http://193.131.98.57:8080");
			String response = store.delete("http://example.org/musicGraph");
			System.out.println(response);
			System.out.println("Done");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
