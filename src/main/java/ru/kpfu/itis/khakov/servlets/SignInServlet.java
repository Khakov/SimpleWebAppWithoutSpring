package ru.kpfu.itis.khakov.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import ru.kpfu.itis.khakov.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.LogManager;

@WebServlet(name = "SignInServlet")
public class SignInServlet extends HttpServlet {
    /**
     * Обрабатываем данные со страницы входа
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession hs = request.getSession();
        if (UserRepository.authUser(request.getParameter("login").trim().toLowerCase(), request.getParameter("password")) != null) {
            if (request.getParameter("remember_me") != null) {
                Cookie cookie = new Cookie("login", UserRepository.md5Decoder(request.getParameter("login")));
                cookie.setMaxAge(3600);
                response.addCookie(cookie);
            }
            hs.setAttribute("current_user", UserRepository.md5Decoder(request.getParameter("login")));
            response.sendRedirect("/welcome");
        } else {
            response.sendRedirect("/sign-in?error=incorrect_user");
        }
    }

    /**
     * Отображаем данные для авторизации
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession hs = request.getSession();
            if (hs.getAttribute("current_user") == null) {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                request.setCharacterEncoding("UTF-8");
                Configuration cfg = ConfigSingleton.getConfig(getServletContext());
                Template tmp1 = cfg.getTemplate("login.ftl");
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("form_url", request.getRequestURI());
                if (request.getParameter("error") != null) {
                    if (request.getParameter("error").equals("incorrect_user"))
                        map.put("error", "неправильный логин или пароль");
                    else
                        map.put("error", "Необходимо ввести учетные данные");
                }
                if (request.getParameter("logout") != null) {
                    map.put("success", "Вы вышли из системы");
                }
                try {
                    tmp1.process(map, response.getWriter());
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            }else
                response.sendRedirect("/welcome");
        } catch (Exception e) {
            LogManager.getLogManager().readConfiguration(
                    MainServlet.class.getResourceAsStream("../../../../../log4j.properties"));
            Logger log = Logger.getLogger(MainServlet.class);
            log.debug("has error in proceed" + e.getMessage());
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
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
