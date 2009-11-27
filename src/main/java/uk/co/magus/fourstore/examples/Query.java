/**
 * Copyright (c) 2009, Magus Ltd
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package uk.co.magus.fourstore.examples;

import java.io.IOException;
import java.net.MalformedURLException;

import uk.co.magus.fourstore.client.Store;

/**
 * Example of querying
 * @author Dan Hanley - dan.hanley @ magus.co.uk
 */
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
			//simple query
			String response1 = store.query(sparql);
			System.out.println(response1);
			//specifying outputformat
			String response2 = store.query(sparql,Store.OutputFormat.JSON);
			System.out.println(response2);
			//specifying softlimit and default output format
			String response3 = store.query(sparql,5);
			System.out.println(response3);
			//specifying outputformat and soft limit
			String response4 = store.query(sparql,Store.OutputFormat.TAB_SEPARATED, 1);
			System.out.println(response4);
			System.out.println("Done");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
