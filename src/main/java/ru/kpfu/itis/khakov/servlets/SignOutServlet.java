package ru.kpfu.itis.khakov.servlets;

import freemarker.template.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.LogManager;

public class SignOutServlet extends HttpServlet {
    /**
     * Выход из учетной записи
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession hs = request.getSession();
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login")) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setValue(null);
                    response.addCookie(cookie);
                }
            }
            hs.setAttribute("current_user", null);
            response.sendRedirect("/sign-in?logout=logout");
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
