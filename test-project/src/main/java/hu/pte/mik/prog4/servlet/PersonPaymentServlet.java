package hu.pte.mik.prog4.servlet;

import hu.pte.mik.prog4.model.Person;
import hu.pte.mik.prog4.service.IdProvider;
import hu.pte.mik.prog4.service.PersonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Stream;

public class PersonPaymentServlet extends HttpServlet {

    private final IdProvider idProvider = IdProvider.getInstance();
    private final PersonService personService = new PersonService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession();
        PrintWriter writer = resp.getWriter();

//        String name = null;
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (int i = 0; i < cookies.length; i++) {
//                if ("name".equals(cookies[i].getName())) {
//                    name = URLDecoder.decode(cookies[i].getValue(), StandardCharsets.UTF_8);
//                    break;
//                }
//            }
//        }

        String name = Optional.ofNullable(req.getCookies())
                .map(Stream::of)
                .orElse(Stream.empty())
                .filter(cookie -> "name".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8))
                .orElse(null);

        createResponse(writer, name);
    }

    private static void createResponse(PrintWriter writer, String name) {
        writer.println("<html><head><meta charset=\"UTF-8\"></head><body>");
        if (name != null && !name.isEmpty()) {
            writer.println("<h1>Hello " + name + "!</h1>");
        }
        writer.println("<form method=\"POST\">");
        writer.println("Name <input type=\"text\" name=\"name\" />");
        writer.println("Address <input type=\"text\" name=\"address\" />");
        writer.println("ID number <input type=\"text\" name=\"idNumber\" />");
        writer.println("<input type=\"submit\" />");
        writer.println("</bod>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession();
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String idNumber = req.getParameter("idNumber");
        Person person = new Person(idProvider.nextId(), name, address, idNumber);

        Cookie cookie = new Cookie("name", URLEncoder.encode(name, StandardCharsets.UTF_8));
        resp.addCookie(cookie);

        this.personService.pay(person);
        createResponse(resp.getWriter(), name);
    }
}
