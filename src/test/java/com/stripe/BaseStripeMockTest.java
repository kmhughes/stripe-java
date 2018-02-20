package com.stripe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

public class BaseStripeMockTest {
	private static final String MOCK_MINIMUM_VERSION = "0.8.0";

	private static String port;

	private String origApiBase;
	private String origApiKey;

	@BeforeClass
	public static void checkStripeMock() throws Exception {
		port = System.getenv().get("STRIPE_MOCK_PORT");
		if (port == null) {
			port = "12111";
		}

		String urlString = "http://localhost:" + port;

		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");

		try {
			int _status = conn.getResponseCode();
		} catch (IOException e) {
			throw new RuntimeException(String.format(
				"Couldn't reach stripe-mock at `localhost:%s`. Is it " +
				"running? Please see README for setup instructions.",
				port
			));	
		}

		String version = conn.getHeaderField("Stripe-Mock-Version");
		if ((version != "master") && (compareVersions(version, MOCK_MINIMUM_VERSION) > 0)) {
			throw new RuntimeException(String.format(
				"Your version of stripe-mock (%s) is too old. The minimum " +
				"version to run this test suite is %s. Please see its " +
				"repository for upgrade instructions.",
				version, MOCK_MINIMUM_VERSION
			));
		}
	}

	/**
	 * Activates stripe-mock by overriding the API host and putting a test key
	 * into the environment.
	 */
	@Before
	public void setUpStripeMock() {
		this.origApiBase = Stripe.getApiBase();
		this.origApiKey = Stripe.apiKey;

		Stripe.overrideApiBase("http://localhost:" + port);
		Stripe.apiKey = "sk_test_myTestKey";
	}

	/**
	 * Deactivates stripe-mock by returning the API host to whatever it was
	 * before stripe-mock was activated.
	 */
	@After
	public void tearDownStripeMock() {
		Stripe.overrideApiBase(this.origApiBase);
		Stripe.apiKey = this.origApiKey;
	}

	/**
	 * Convenience method that extracts a subset of JSON data and returns it.
	 *
	 * <p>For example, if I know that my charge object has a customer under it,
	 * I can pass my charge JSON data and specify {@code field} as {@code
	 * customer}. This returns everything that had been under the {@code
	 * customer} key (encoded as JSON).
	 *
	 * @param data JSON encoded data.
	 * @param field Field under data which to extract.
	 * @return Extracted JSON encoded data.
	 */
	protected static String getDataAt(String data, String field) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		Map<String, Object> map = gson.fromJson(data, type);
		Object value = map.get(field);
		return gson.toJson(value);
	}

	/**
	 * Gets fixture data from stripe-mock for a resource expected to be at the
	 * given API path. stripe-mock ignores whether IDs are actually valid, so
	 * it's only important to make sure that the route exists, rather than the
	 * actual resource. It's common to use a symbolic ID stand-in like {@code
	 * ch_123}.
	 *
	 * <pre>
	 * getFixture("/v1/charges/ch_123")
	 * </pre>
	 *
	 * @param path API path to use to get a fixture for stripe-mock.
	 * @return Fixture data encoded as JSON.
	 */
	protected static String getFixture(String path) throws Exception, IOException, MalformedURLException, ProtocolException {
		return getFixture(path, null);
	}

	/**
	 * Gets fixture data with expansions specified. Expansions are specified
	 * the same way as they are in the normal API like {@code customer} or
	 * {@code data.customer}. Use the special {@code *} character to specify
	 * that all fields should be expanded.
	 *
	 * @param path API path to use to get a fixture for stripe-mock.
	 * @param expansions Set of expansions that should be applied.
	 * @return Fixture data encoded as JSON.
	 */
	protected static String getFixture(String path, String[] expansions) throws Exception, IOException, MalformedURLException, ProtocolException {
		int status;

		StringBuffer urlStringBuffer = new StringBuffer();
		urlStringBuffer.append("http://localhost:" + port + path);

		if (expansions != null) {
			urlStringBuffer.append("?");
			for (String expansion : expansions) {
				urlStringBuffer.append("expand[]=");
				urlStringBuffer.append(expansion);
				urlStringBuffer.append("&");
			}
		}
		URL url = new URL(urlStringBuffer.toString());

		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "Bearer sk_test_myTestKey");

		// This is the line that actually triggers the request.
		try {
			status = conn.getResponseCode();
		} catch (IOException e) {
			throw new RuntimeException(String.format(
				"Couldn't reach stripe-mock at `localhost:%s`. Is it " +
				"running? Please see README for setup instructions.",
				port
			));
		}

		if (status != 200) {
			throw new RuntimeException(String.format(
				"Connection to stripe-mock at : %d.",
				status
			));
		}

		return readUntilEnd(conn.getInputStream());
	}

	private static String readUntilEnd(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try {
			StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
			  buffer.append(line);
			  buffer.append("\r");
			}
			return buffer.toString();
		} finally {
			reader.close();
		}
	}

	/**
	 * Compares two version strings (e.g. "1.2.3").
	 *
	 * @param a First version string.
	 * @param b Second version string.
	 * @return -1 if a > b, 1 if a < b, 0 if a == b.
	 */
	protected static int compareVersions(String a, String b) {
		int ret = 0;

		String[] as = a.split("\\.");
		String[] bs = b.split("\\.");

		int loopMax = bs.length;
		if (as.length > bs.length) {
			loopMax = as.length;
		}

		for (int i = 0; i < loopMax; i++) {
			String x = "", y = "";
			if (as.length > i) {
				x = as[i];
			}
			if (bs.length > i) {
				y = bs[i];
			}

			int xi = Integer.parseInt(x);
			int yi = Integer.parseInt(y);

			if (xi > yi) {
				ret = -1;
			} else if (xi < yi) {
				ret = 1;
			}
			if (ret != 0) {
				break;
			}
		}

		return ret;
	}

	public static String getYear() {
		Date date = new Date(); //Get current date
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) + 1 + "";
	}
}
