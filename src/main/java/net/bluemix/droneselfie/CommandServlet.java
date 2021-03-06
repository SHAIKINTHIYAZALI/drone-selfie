/*
 * Copyright IBM Corp. 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.bluemix.droneselfie;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.paho.client.mqttv3.MqttException;

@WebServlet("/navigate")
public class CommandServlet extends HttpServlet {

	private static final long serialVersionUID = -1623116324694499889L;

	public CommandServlet() {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		boolean inputok = true;
		String command = request.getParameter("c");
		if (command == null)
			inputok = false;
		if (command.equals(""))
			inputok = false;
		
		if (inputok == false)
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		else {
			try {
				MQTTUtilities.sendMQTTMessage(command, null);
				response.setStatus(HttpServletResponse.SC_OK);
			}
			catch (MqttException me) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
}
