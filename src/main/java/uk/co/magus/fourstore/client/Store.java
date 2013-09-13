/**
 * Copyright (c) 2009, Magus Ltd
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.
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
package uk.co.magus.fourstore.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Provides a basic client wrapper for the 4store httpd service
 * 
 * @author Dan Hanley - dan.hanley @ magus.co.uk
 * 
 */
public class Store {

	/**
	 * Enumeration of allowed import formats and their mime types
	 * 
	 * @author Dan
	 * 
	 */
	public static enum InputFormat {
		TURTLE("application/x-turtle"), 
		XML("application/rdf+xml"), 
		N3("text/rdf+n3"), 
		NT("text/rdf+nt"), 
		TriG("application/x-trig");

		private final String mimeType;

		InputFormat(String mimeType) {
			this.mimeType = mimeType;
		}

		public String getMimeType() {
			return mimeType;
		};
	};

	/**
	 * Enumeration of available result formats and their mime types
	 * 
	 * @author Dan
	 * 
	 */
	public static enum OutputFormat {
		TAB_SEPARATED("text/tab-separated-values"), 
		JSON("application/sparql-results+json"), 
		SPARQL_XML("application/sparql-results+xml");

		private final String mimeType;

		OutputFormat(String mimeType) {
			this.mimeType = mimeType;
		}

		public String getMimeType() {
			return mimeType;
		};
	};

	private URL baseURL;
	private URL statusURL;
	private URL sizeURL;
	private URL dataURL;
	private URL sparqlURL;
	private URL updateURL;

	/**
	 * Creates a client driver for the 4Store server identified
	 * 
	 * @param u
	 *            String representation of the base URL of the 4Store httpd
	 *            server
	 * @throws MalformedURLException
	 */

	public Store(String u) throws MalformedURLException {
		baseURL = new URL(u);
		init();
	}

	/**
	 * Creates a client driver for the 4Store server identified
	 * 
	 * @param u
	 *            base URL of the 4Store httpd server
	 * @throws MalformedURLException
	 */
	public Store(URL u) throws MalformedURLException {
		baseURL = u;
		init();
	}

	private void init() throws MalformedURLException {
		statusURL = new URL(baseURL + "/status");
		sizeURL = new URL(statusURL + "/size/");
		dataURL = new URL(baseURL + "/data/");
		sparqlURL = new URL(baseURL + "/sparql/");
		updateURL = new URL(baseURL + "/update/");
	}

	/**
	 * Gets the size of the repository
	 * 
	 * @return the HTML string returned for the server - could be made prettier.
	 * @throws IOException
	 */
	public String getSize() throws IOException {
		return readResponse(sizeURL);
	}

	/**
	 * Gets the status of the repository
	 * 
	 * @return the HTML string returned for the server - could be made prettier.
	 * @throws IOException
	 */
	public String getStatus() throws IOException {
		return readResponse(statusURL);
	}

	/**
	 * Appends the given graph to the default graph
	 * 
	 * @param graphData
	 *            - the graph to append
	 * @param format
	 *            - the format of the graph to append
	 * @return the HTML string returned for the server - could be made prettier.
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public String append(String graphData, InputFormat format)
			throws MalformedURLException, ProtocolException, IOException {
		return append("", graphData, format);
	}

	/**
	 * Appends the given graph to a named graph
	 * 
	 * @param graphName
	 *            - name of the graph to append to
	 * @param graphData
	 *            - the graph to append
	 * @param format
	 *            - the format of the graph to append
	 * @return the HTML string returned for the server - could be made prettier.
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public String append(String graphName, String graphData, InputFormat format)
			throws MalformedURLException, ProtocolException, IOException {

		HttpURLConnection connection = (HttpURLConnection) dataURL
				.openConnection();

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		DataOutputStream ps = new DataOutputStream(connection.getOutputStream());
		ps.writeBytes("mime-type=" + format.getMimeType() + "&graph="
				+ URLEncoder.encode(graphName, "UTF-8") + "&data="
				+ URLEncoder.encode(graphData, "UTF-8"));
		ps.flush();
		ps.close();

		return readResponse(connection);
	}

	/**
	 * Deletes a named graph from the repository
	 * 
	 * @param graph
	 *            - the graph to delete
	 * @return the HTML string returned for the server - could be made prettier.
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public String delete(String graph) throws MalformedURLException,
			ProtocolException, IOException {

		URL deleteURL = new URL(dataURL + URLEncoder.encode(graph, "UTF-8"));
		HttpURLConnection connection = (HttpURLConnection) deleteURL
				.openConnection();

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("DELETE");

		return readResponse(connection);
	}

	/**
	 * adds (or replaces) a named graph to the repository
	 * 
	 * @param graphName
	 * @param graphData
	 * @param format
	 * @return the HTML string returned for the server - could be made prettier.
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public String add(String graphName, String graphData, InputFormat format)
			throws MalformedURLException, ProtocolException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(dataURL
				+ graphName).openConnection();

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", format.getMimeType());

		DataOutputStream ps = new DataOutputStream(connection.getOutputStream());
		ps.writeBytes(graphData);
		ps.flush();
		ps.close();

		return readResponse(connection);
	}

	/**
	 * Queries the repository
	 * 
	 * @param sparql
	 * @return the result in SparQL-XML - could be rewritten to pass back a
	 *         proper Document
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public String query(String sparql) throws MalformedURLException,
			ProtocolException, IOException {
			//Integer.MIN_VALUE means no soft-limit specified
		return query(sparql, OutputFormat.SPARQL_XML, Integer.MIN_VALUE);
	}
	
	/**
	 * Queries the repository
	 * 
	 * @param sparql
	 * @param softLimit
	 * @return the result in SparQL-XML - could be rewritten to pass back a
	 *         proper Document
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public String query(String sparql, int softLimit) throws MalformedURLException,
			ProtocolException, IOException {
			//Integer.MIN_VALUE means no soft-limit specified
		return query(sparql, OutputFormat.SPARQL_XML, softLimit);
	}

	/**
	 * Queries the repository and returns the result in the requested format
	 * 
	 * @param sparql
	 * @param format
	 * @return the result in the requested format
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public String query(String sparql, OutputFormat format)
			throws MalformedURLException, ProtocolException, IOException {
			//Integer.MIN_VALUE means no soft-limit specified
		return query(sparql, format, Integer.MIN_VALUE);
	}

	/**
	 * Queries the repository and returns the result in the requested format
	 * 
	 * @param sparql
	 * @param format
	 * @param softLimit
	 * @return the result in the requested format
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws IOException
	 */
	public String query(String sparql, OutputFormat format, int softLimit)
			throws MalformedURLException, ProtocolException, IOException {
		HttpURLConnection connection = (HttpURLConnection) sparqlURL
				.openConnection();

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Accept", format.getMimeType());

		DataOutputStream ps = new DataOutputStream(connection.getOutputStream());
		if(softLimit != Integer.MIN_VALUE){  //a soft limit has been set
			ps.writeBytes("&query=" + URLEncoder.encode(sparql, "UTF-8") + "&soft-limit=" + softLimit);
		} else {  //no soft limit specified
			ps.writeBytes("&query=" + URLEncoder.encode(sparql, "UTF-8"));
		}
		ps.flush();
		ps.close();

		return readResponse(connection);
	}



	private String readResponse(HttpURLConnection connection) 
	          throws MalformedURLException, ProtocolException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(connection
				.getInputStream()));

		StringBuilder responseBuilder = new StringBuilder();
		String str;
		while (null != ((str = in.readLine()))) {
			responseBuilder.append(str + System.getProperty("line.separator")); 
		}
		in.close();
		return responseBuilder.toString();
	}
	
	private String readResponse(URL u) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(u
				.openStream()));
		String response;
		while ((response = in.readLine()) != null)
			System.out.println(response);
		in.close();
		return response;
	}
}
