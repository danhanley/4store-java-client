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
 * Example adding (or overwriting) a graph
 * @author Dan Hanley - dan.hanley @ magus.co.uk
 */
public class AddGraph {

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
			store = new Store("http://localhost:8080");
			String response = store.add("http://example.org/musicGraph",
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
