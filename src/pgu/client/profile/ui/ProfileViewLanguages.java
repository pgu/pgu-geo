package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileViewLanguages {

    public native String createLanguagesHtml(JavaScriptObject language_values) /*-{

        var cache_lg = {};
        for (var i = 0, len = language_values.length; i < len; i++) {

            var //
            language_value = language_values[i] //
            //
            , language = language_value.language || {} //
            , language_name = language.name || '' //
            //
            , language_proficiency = language_value.proficiency || {} //
            , language_level = language_proficiency.level || '' //
            ;

            if (cache_lg.hasOwnProperty(language_level)) {
                cache_lg.get(language_level).push(language_name);

            } else {
                cache_lg[language_level] = [].concat(language_name);

            }
        }

        // sort language names
        for (var key in cache_lg) {
            if ('__gwt_ObjectId' === key) {
                continue;
            }
            if (cache_lg.hasOwnProperty(key)) {
                cache_lg[key].sort();
            }
        }

        var lg_levels = [
              {lvl: 'native_or_bilingual', nb: 4}
            , {lvl: 'full_professional', nb: 3}
            , {lvl: 'professional_working', nb: 2}
            , {lvl: 'limited_working', nb: 1}
            , {lvl: 'elementary', nb: 0}
        ];

        var lg_rows = [];

        for (var i = 0, len = lg_levels.length; i < len; i++) {
            var lg_level = lg_levels[i];

            if (cache_lg.hasOwnProperty(lg_level.lvl)) {
                //
                var trophies = [];
                var trophies_nb = lg_level.nb;

                for (var k = 0; k < trophies_nb; k++) {
                    trophies.push('<i class=\"icon-trophy\"></i>');
                }
                var trophies_html = trophies.join('');

                //
                var lg_names = cache_lg[lg_level.lvl];

                for (var j = 0, lenN = lg_names.length; j < lenN; j++) {
                    var name = lg_names[j];

                    var row = '<div>' + name + '</div><div>' + trophies_html + '</div>';
                    lg_rows.push(row);
                }
            }
        }

        return lg_rows.join('');
    }-*/;

}
