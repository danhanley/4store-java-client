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
	 * @author Dan Hanley - dan.hanley @ magus.co.uk
     *
	 */
	public static void main(String[] args) {

		Store store;
		try {
			System.out.println("Start");
			store = new Store("http://localhost:8080");
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
