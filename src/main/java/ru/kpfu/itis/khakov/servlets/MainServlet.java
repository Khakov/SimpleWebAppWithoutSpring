package ru.kpfu.itis.khakov.servlets;

import freemarker.template.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.LogManager;

public class MainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    /**
     * Отображение главной страницы
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException{
        try {
            response.sendRedirect("/welcome");
        }
        catch (Exception e) {
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
