package pgu.client.profile.ui;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import pgu.client.profile.ProfilePresenter;
import pgu.client.profile.ProfileView;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Popover;
import com.github.gwtbootstrap.client.ui.Section;
import com.github.gwtbootstrap.client.ui.constants.Placement;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

public class ProfileViewImpl extends Composite implements ProfileView {

    private static ProfileViewImplUiBinder uiBinder = GWT.create(ProfileViewImplUiBinder.class);

    interface ProfileViewImplUiBinder extends UiBinder<Widget, ProfileViewImpl> {
    }

    @UiField
    Heading                  nameBasic;
    @UiField
    Paragraph                headlineBasic;
    @UiField
    Popover                  summaryBasic;
    @UiField
    Button                   summaryBasicBtn;

    @UiField(provided = true)
    Section                  overviewSection, experienceSection, educationSection;
    @UiField
    HTMLPanel                lgContainer, spContainer, locContainer;
    @UiField
    NavLink                  positionLocation;
    @UiField(provided = true)
    CellTable<ExperienceDto> experiencesTable;

    public class ExperienceDto implements IsSerializable {

        private String location;
        private String dates;
        private String experience;

        public String getLocation() {
            return location;
        }

        public String getDates() {
            return dates;
        }

        public String getExperience() {
            return experience;
        }

    }

    public ProfileViewImpl() {

        experiencesTable = new CellTable<ExperienceDto>();

        final TextColumn<ExperienceDto> locationCol = new TextColumn<ExperienceDto>() {

            @Override
            public String getValue(final ExperienceDto xp) {
                return xp.getLocation();
            }
        };
        final TextColumn<ExperienceDto> datesCol = new TextColumn<ExperienceDto>() {

            @Override
            public String getValue(final ExperienceDto xp) {
                return xp.getDates();
            }
        };
        final TextColumn<ExperienceDto> experienceCol = new TextColumn<ExperienceDto>() {

            @Override
            public String getValue(final ExperienceDto xp) {
                return xp.getExperience();
            }
        };
        final TextColumn<ExperienceDto> moreCol = new TextColumn<ExperienceDto>() {

            @Override
            public String getValue(final ExperienceDto xp) {
                return "";
            }
        };
        experiencesTable.addColumn(locationCol, "Location");
        experiencesTable.addColumn(datesCol, "Dates");
        experiencesTable.addColumn(experienceCol, "Position");
        experiencesTable.addColumn(moreCol, "");
        // experiencesTable.setColumnWidth(firstNameColumn, 20, Unit.PCT);

        overviewSection = new Section("profile:overview");
        experienceSection = new Section("profile:experience");
        educationSection = new Section("profile:education");

        initWidget(uiBinder.createAndBindUi(this));

        // http://www.linkedin.com/pub/pascal-guilcher/2/3b1/955
        nameBasic.setText("Pascal Guilcher");
        headlineBasic.setText("Senior Web Java J2EE Engineer Developer at SFEIR");

        final String trophy = " <i class=\"icon-trophy\"></i> ";

        final LinkedHashMap<String, Integer> lg2level = new LinkedHashMap<String, Integer>();
        lg2level.put("French", 4);
        lg2level.put("English", 3);
        lg2level.put("Spanish", 3);
        lg2level.put("German", 2);

        for (final Entry<String, Integer> e : lg2level.entrySet()) {

            final Column labelCol = new Column(3);
            final Column levelCol = new Column(3);

            labelCol.getElement().setInnerHTML(e.getKey());

            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < e.getValue(); i++) {
                sb.append(trophy);
            }
            levelCol.getElement().setInnerHTML(sb.toString());

            final FluidRow row = new FluidRow();
            row.add(labelCol);
            row.add(levelCol);

            lgContainer.add(row);
        }

        // <p>Ajax, J2EE, Java, Javascript, Eclipse</p>
        final String specialties = "Ajax, J2EE, Java, Javascript, Eclipse";
        for (final String sp : specialties.split(", ")) {
            spContainer.add(new HTML(sp));
        }

        // <p>Paris Area, France</p>
        final String location = "Paris Area, France";
        for (final String loc : location.split(", ")) {
            locContainer.add(new HTML(loc));
        }

        summaryBasic.setAnimation(true);
        summaryBasic.setPlacement(Placement.LEFT);
        summaryBasic.setHeading("Summary");
        summaryBasic
                .setText("A senior Java J2EE web developer with broad experience in web-based development, mainly with AJAX interface and Java-based frameworks such as J2EE, Spring and Hibernate. Resourceful, opened to different technologies, willing to join a state of the art web project to build up a successful career.");

        final ListDataProvider<ExperienceDto> dataProvider = new ListDataProvider<ExperienceDto>();
        dataProvider.addDataDisplay(experiencesTable);
        final ExperienceDto xp = new ExperienceDto();
        xp.location = "Paris, France";
        xp.dates = "Present<br/>November 2011<br/>(10 months)";
        xp.experience = "SFEIR<br/>Senior Web Java J2EE Engineer Developer";
        dataProvider.getList().add(xp);
    }

    @UiHandler("positionLocation")
    public void clickPositionLocation(final ClickEvent e) {
        positionLocation.setActive(!positionLocation.isActive());

        if (positionLocation.isActive()) {
            Window.scrollTo(0, 0);
            presenter.searchForPosition("positionId", positionLocation.getText());
        }
    }

    @UiHandler("summaryBasicBtn")
    public void clickSummaryBasic(final ClickEvent e) {
        summaryBasic.toggle();
    }

    private ProfilePresenter presenter;

    @Override
    public void setPresenter(final ProfilePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HasText getProfileBoard() {
        return new HasText() {

            @Override
            public String getText() {
                return headlineBasic.getText();
            }

            @Override
            public void setText(final String text) {
                // profileBoard.setText(text);
                // TODO PGU Aug 20, 2012 enable it
            }

        };
    }

}
