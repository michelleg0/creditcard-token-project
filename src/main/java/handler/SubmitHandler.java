package main.java.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import main.java.dao.CustomerDAO;
import main.java.dto.CustomerDTO;

public class SubmitHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            String formData = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            Map<String, String> params = parseFormData(formData);

            String firstName = params.get("firstName");
            String lastName = params.get("lastName");
            String email = params.get("email");

            CustomerDAO dao = new CustomerDAO();

            try (OutputStream os = exchange.getResponseBody()) {
                int id = dao.insertCustomer(new CustomerDTO(firstName, lastName, email));

                String response = "Customer with id " + id + " was successfully added";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                os.write(response.getBytes());
            } catch (SQLException e) {
//                TODO - add logging
                e.printStackTrace();

                String response = "Error saving data";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private Map<String, String> parseFormData(String formData) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = formData.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = java.net.URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                params.put(key, value);
            }
        }
        return params;
    }
}
