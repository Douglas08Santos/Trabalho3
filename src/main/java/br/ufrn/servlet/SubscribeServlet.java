package br.ufrn.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class SubscribeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Do nothing
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String json = getBody(req);
		
		System.out.println(json);

	}

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		
		try {
			JSONObject  json = new JSONObject(body);
			String subs_id = json.getString("subscriptionId");
			/*customizavel os subscriptionId devem ser configurados da sua maquina
			 * para nosso exemplo:
			 * subscribe1 = 5fd185f8476313a5e43b1b9f
			 * subscribe2 = 5fd185ef476313a5e43b1b9e
			 * subscribe3 =5fd185ff476313a5e43b1ba0
			 * */
			if(subs_id.equals("5fd185f8476313a5e43b1b9f")) {
				System.out.println("**** Notificação caminhão 1 ****");
			}else if (subs_id.equals("5fd185ef476313a5e43b1b9e")) {
				System.out.println("**** Notificação caminhão 2 ****");
			}else if (subs_id.equals("5fd185ff476313a5e43b1ba0")) {
				System.out.println("**** Notificação caminhão 3 ****");
			}else {
				System.out.println("configure os subscriptionId");
			}
			return json.toString(4);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return body;
	}

}
