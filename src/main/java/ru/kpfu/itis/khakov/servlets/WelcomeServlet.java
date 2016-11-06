package ru.kpfu.itis.khakov.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import ru.kpfu.itis.khakov.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.LogManager;


public class WelcomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    /**
     * Отображение приветствия
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getSession().getAttribute("current_user") != null) {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                request.setCharacterEncoding("UTF-8");
                Configuration cfg = ConfigSingleton.getConfig(getServletContext());
                Template tmp1 = cfg.getTemplate("welcome.ftl");
                HashMap<String, Object> model = new HashMap<String, Object>();
                model.put("user", UserRepository.getLoginBySession((String) request.getSession().getAttribute("current_user")));
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                String day = "ое утро";
                if (hour >= 10 && hour < 18)
                    day = "ый день";
                else {
                    if (hour >= 18 && hour < 22)
                        day = "ый вечер";
                    else if (hour >= 22 || hour < 6)
                        day = "ой ночи";
                }
                model.put("time", day);
                try {
                    tmp1.process(model, response.getWriter());
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else
                response.sendRedirect("/sign-in?error=need_sign-in");
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
