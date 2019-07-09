package com.eh.restapi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void home(HttpServletRequest req, HttpServletResponse resp) {
        // get name from session, or return default
        String name = Optional.ofNullable(req.getSession(false))
                .map(session -> (String) session.getAttribute("name"))
                .orElse("Null session");
        // create a greeting with the name
        String greeting = String.format("Hello %s!", name);
 
        // write response
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf8");
        try (ServletOutputStream out = resp.getOutputStream()) {
            out.println("<html><body>");
            out.println(greeting);
            out.println("<form method='post' action='"+req.getContextPath()+"/logout'><input type='submit' value='logout'></input></form>");
            out.println("</body></html>");            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@RequestMapping(value = "/set", method = RequestMethod.GET)
	public void home(HttpServletRequest req, HttpServletResponse resp, @RequestParam String name) {
		req.getSession().setAttribute("name", name);
	}
	
}
