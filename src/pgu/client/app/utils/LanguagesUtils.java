package pgu.client.app.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map.Entry;

import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.HTMLPanel;

public class LanguagesUtils {

    private final EnumMap<LanguageLevel, ArrayList<String>> level2languages = new EnumMap<LanguageLevel, ArrayList<String>>(
            LanguageLevel.class);

    private static final String                             trophy          = " <i class=\"icon-trophy\"></i> ";

    private enum LanguageLevel {
        native_or_bilingual(4) //
        , full_professional(3) //
        , professional_working(2) //
        , limited_working(1) //
        , elementary(0) //
        ;

        private final int nbTrophies;

        LanguageLevel(final int nbTrophies) {
            this.nbTrophies = nbTrophies;
        }

        public int getNbTrophies() {
            return nbTrophies;
        }
    }

    private final HTMLPanel lgContainer;

    public LanguagesUtils(final HTMLPanel lgContainer) {
        this.lgContainer = lgContainer;
    }

    public static native void setProfileLanguages(LanguagesUtils languages_utils, JavaScriptObject profile) /*-{

        languages_utils.@pgu.client.app.utils.LanguagesUtils::clearProfileLanguages()();


        var languages = profile.languages || {};
        var language_values = languages.values || [];

        for ( var i in language_values) {

            var //
            language_value = language_values[i] //
            //
            , language = language_value.language || {} //
            , language_name = language.name || '' //
            //
            , language_proficiency = language_value.proficiency || {} //
            , language_level = language_proficiency.level || '' //
            ;

            languages_utils.@pgu.client.app.utils.LanguagesUtils::addProfileLanguage(Ljava/lang/String;Ljava/lang/String;)(language_name, language_level);
        }

        languages_utils.@pgu.client.app.utils.LanguagesUtils::showProfileLanguages()();
    }-*/;

    private void clearProfileLanguages() {
        lgContainer.clear();
        level2languages.clear();
    }

    private void addProfileLanguage(final String languageName, final String languageLevel) {

        final LanguageLevel level = getLanguageLevel(languageLevel);
        addLanguageAndLevelToCache(languageName, level);
    }

    private void addLanguageAndLevelToCache(final String languageName, final LanguageLevel level) {
        if (level2languages.containsKey(level)) {
            level2languages.get(level).add(languageName);
        } else {
            final ArrayList<String> names = new ArrayList<String>();
            names.add(languageName);
            level2languages.put(level, names);
        }
    }

    private LanguageLevel getLanguageLevel(final String languageLevel) {
        try {
            return LanguageLevel.valueOf(languageLevel);

        } catch (final IllegalArgumentException e) {
            return LanguageLevel.elementary;
        }
    }

    private static final Comparator<String> LEXICO = new Comparator<String>() {

        @Override
        public int compare(final String s1, final String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    };

    private void showProfileLanguages() {

        for (final Entry<LanguageLevel, ArrayList<String>> e : level2languages.entrySet()) {
            final LanguageLevel level = e.getKey();
            final ArrayList<String> names = e.getValue();

            final int nbTrophies = level.getNbTrophies();
            Collections.sort(names, LEXICO);

            for (final String name : names) {
                addLanguageRow(nbTrophies, name);
            }
        }
    }

    private void addLanguageRow(final int nbTrophies, final String name) {

        final StringBuilder trophies = new StringBuilder();
        for (int i = 0; i < nbTrophies; i++) {
            trophies.append(trophy);
        }

        final Column labelCol = new Column(3);
        final Column levelCol = new Column(3);

        labelCol.getElement().setInnerHTML(name);
        levelCol.getElement().setInnerHTML(trophies.toString());

        final FluidRow row = new FluidRow();
        row.add(labelCol);
        row.add(levelCol);

        lgContainer.add(row);
    }

}
