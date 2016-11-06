package ru.kpfu.itis.khakov.servlets;

import org.json.JSONException;
import org.json.JSONObject;
import ru.kpfu.itis.khakov.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ValidateServlet")
public class ValidateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Сервлет для обработки ajax запроса из javaScript для проверки существования пользователя с данным логином в базе данных
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        try {
            if (login != null) {
                JSONObject jo = new JSONObject();
                jo.put("results", UserRepository.getLogin(login));
                response.setContentType("text/json");
                response.getWriter().print(jo.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
