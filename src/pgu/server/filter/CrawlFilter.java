package pgu.server.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CrawlFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException,
    ServletException {

        final String paramOfFragment = request.getParameter("_escaped_fragment_");

        if (paramOfFragment == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //
        // is being crawled
        // ex: http://pgu-contacts.appspot.com/?_escaped_fragment_=public:pub/pascal-guilcher/2/3b1/955
        //
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String requestURI = httpRequest.getRequestURI();

        String baseURI = requestURI.split("?")[0];
        if (!baseURI.endsWith("/")) {
            baseURI = baseURI + "/";
        }

        final String publicId = paramOfFragment.replaceFirst("public:", "");;

        final String newURI = baseURI + "crawl/" + publicId;
        request.getRequestDispatcher(newURI).forward(request, response);
    }

    @Override
    public void init(final FilterConfig arg0) throws ServletException {
        // no-op
    }

    @Override
    public void destroy() {
        // no-op
    }

}
