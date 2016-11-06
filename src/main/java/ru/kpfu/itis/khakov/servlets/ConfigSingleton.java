package ru.kpfu.itis.khakov.servlets;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletContext;
/**
 * Настраиваем Freemarker
 */
public class ConfigSingleton {
    private static Configuration cfg = null;
    public static Configuration getConfig(ServletContext sc){
        if (cfg==null){
            cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setServletContextForTemplateLoading(sc,"/WEB-INF/templates");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        }
        return cfg;
    }
}
