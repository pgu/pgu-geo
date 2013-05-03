package pgu.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pgu.server.app.AppLog;
import pgu.server.service.PublicProfileServiceImpl;
import pgu.server.utils.AppUtils;
import pgu.shared.dto.FullPublicProfile;
import pgu.shared.dto.PublicContacts;
import pgu.shared.model.PublicLocations;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;

@SuppressWarnings("serial")
public class CrawlServlet extends HttpServlet {

    private final PublicProfileServiceImpl service = new PublicProfileServiceImpl();
    private final AppUtils      u               = new AppUtils();
    private final AppLog        log             = new AppLog();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        log.info(this, "** crawl SERVLET **");

        final String publicId = req.getRequestURI().split("/crawl/")[1];

        log.info(this, "publicId\n%s", publicId);

        if (publicId == null || publicId.trim().isEmpty()) {
            resp.sendError(404);
            return;
        }

        final FullPublicProfile publicProfile = service.fetchPublicProfileByUrl(publicId);
        final PublicContacts contacts = service.fetchPublicContacts(publicId);

        final String jsonProfile = publicProfile.getBasePublicProfile().getValue();
        final HashMap profile = new Gson().fromJson(jsonProfile, HashMap.class);

        final String profileFullName = (profile.get("firstName") + " " + profile.get("lastName")).trim();
        final String headline = (String) profile.get("headline");
        final String currentLocationName = (String) profile.get("location");
        final String specialties = (String) profile.get("specialties");
        final String summary = (String) profile.get("summary");
        final String languages = (String) profile.get("languages");
        final ArrayList<StringMap> positions = (ArrayList<StringMap>) profile.get("positions");
        final ArrayList<StringMap> educations = (ArrayList<StringMap>) profile.get("educations");

        final PublicLocations publicLocations = publicProfile.getPublicLocations();
        final HashMap<String, Object> refLocations = new Gson().fromJson(publicLocations.getReferentialLocations(), HashMap.class);

        // html
        final StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>Pgu-geo / " + profileFullName + " / " + headline + "</title>");
        sb.append("</head>");
        sb.append("<body>");
        addParagraph("Full name", profileFullName, sb);
        addParagraph("Headline", headline, sb);
        addParagraph("Current location", currentLocationName, sb);
        addParagraph("Specialties", specialties, sb);
        addParagraph("Summary", summary, sb);
        addParagraph("Languages", languages, sb);

        if (positions != null) {
            sb.append("<p><div><strong>Positions</strong></div>");
            for (final StringMap position : positions) {
                sb.append("<p>");
                sb.append("<div>" + position.get("dates") + "</div>");
                sb.append("<div>" + position.get("short_content") + "</div>");
                sb.append("<div>" + position.get("long_content") + "</div>");
                sb.append("</p>");
            }
            sb.append("</p>");
        }

        if (educations != null) {
            sb.append("<p><div><strong>Educations</strong></div>");
            for (final StringMap education : educations) {
                sb.append("<p>");
                sb.append("<div>" + education.get("dates") + "</div>");
                sb.append("<div>" + education.get("short_content") + "</div>");
                sb.append("<div>" + education.get("long_content") + "</div>");
                sb.append("</p>");
            }
            sb.append("</p>");
        }

        if (refLocations != null) {
            sb.append("<p><div><strong>Locations</strong></div>");
            for (final String refLocation : refLocations.keySet()) {
                sb.append("<div>" + refLocation + "</div>");
            }
            sb.append("</p>");
        }

        final String htmlContacts = "Total: " + contacts.getTotalNbOfContacts() + "<br/>" + contacts.getContactsNumberByCountry();
        addParagraph("Contacts", htmlContacts, sb);

        sb.append("</body>");
        sb.append("</html>");

        // response
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        final PrintWriter writer = resp.getWriter();
        writer.print(sb.toString());

    }

    private void addParagraph(final String title, final Object value, final StringBuilder sb) {
        if (value == null) {
            return;
        }

        sb.append("<p><div><strong>" + title + "</strong></div><div>" + value + "</div></p>");
    }

}
