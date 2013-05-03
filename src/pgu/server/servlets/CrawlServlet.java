package pgu.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pgu.server.app.AppLog;

@SuppressWarnings("serial")
public class CrawlServlet extends HttpServlet {

    private final AppLog        log             = new AppLog();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        log.info(this, "** crawl SERVLET **");

        final String publicId = req.getRequestURI().split("/crawl/")[1];

        if (publicId == null || publicId.trim().isEmpty()) {
            resp.sendError(404);
            return;
        }

        resp.getWriter().print("Hello\n");
        resp.getWriter().print("We'll see the profile of \n");
        resp.getWriter().print(publicId);


    }

}
