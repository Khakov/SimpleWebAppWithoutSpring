package ru.kpfu.itis.khakov.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import ru.kpfu.itis.khakov.entity.User;
import ru.kpfu.itis.khakov.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.LogManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpServlet extends HttpServlet {
    /**
     * Регистрация пользователя
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        System.out.println(login + "  " + password + " ");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Pattern loginPattern = Pattern.compile("(\\d|[a-z]|[A-Z]){4,}");
        Pattern passwordPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})");
        Matcher loginMatcher = loginPattern.matcher(login);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (!loginMatcher.matches())
            response.sendRedirect("/sign-up?error=login_error");
        else {
            if (!passwordMatcher.matches())
                response.sendRedirect("/sign-up?error=password_error");
            else {
                if (UserRepository.getLogin(login.trim().toLowerCase()))
                    response.sendRedirect("/sign-up?error=have_user");
                else {
                    if (!password.equals(confirmPassword))
                        response.sendRedirect("/sign-up?error=password_incorrect");
                    else {
                        UserRepository.addUser(new User(login, password));
                        request.getSession().setAttribute("current_user", UserRepository.md5Decoder(login.trim().toLowerCase()));
                        response.sendRedirect("/welcome");
                    }
                }
            }
        }
    }
    /**
     * Отображение страницы регистрации пользователя
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getSession().getAttribute("current_user") != null) {
                response.sendRedirect("/welcome");
            } else {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                request.setCharacterEncoding("UTF-8");
                Configuration cfg = ConfigSingleton.getConfig(getServletContext());
                Template tmp1 = cfg.getTemplate("registration.ftl");
                HashMap<String, Object> model = new HashMap<String, Object>();
                if (request.getParameter("error") != null) {
                    switch (request.getParameter("error")) {
                        case "have_user":
                            model.put("error", "такой пользователь уже существует");
                            break;
                        case "login_error":
                            model.put("error", "логин должен быть от 4 символов и содержать латинские буквы или цифры");
                            break;
                        case "password_error":
                            model.put("error", "Пароль недостаточно сложен: должны быть цифры, заглавные и строчные буквы и длина минимум 8 символов");
                            break;
                        default:
                            model.put("error", "пароли не совпадают!");
                            break;
                    }
                }
                try {
                    tmp1.process(model, response.getWriter());
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            LogManager.getLogManager().readConfiguration(
                    MainServlet.class.getResourceAsStream("../../../../../log4j.properties"));
            Logger log = Logger.getLogger(MainServlet.class);
            log.debug("has error in proceed" + e.getMessage());
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            Configuration cfg = ConfigSingleton.getConfig(getServletContext());
            Template tmp1 = cfg.getTemplate("error.ftl");
            HashMap<String, Object> map = new HashMap<String, Object>();
            try {
                tmp1.process(map, response.getWriter());
            } catch (TemplateException e1) {
                e1.printStackTrace();
            }
        }
    }
}
