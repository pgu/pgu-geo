package pgu.server.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pgu.shared.utils.DateUtils;

import com.google.appengine.api.utils.SystemProperty;

public class AppUtils {

    public boolean eq(final Object a, final Object b) {
        return a == b || a != null && a.equals(b);
    }

    public boolean isVoid(final String str) {
        return null == str || str.trim().isEmpty();
    }

    public boolean isEnvProd() {
        return SystemProperty.environment.value() == SystemProperty.Environment.Value.Production;
    }

    public String now() {
        return new SimpleDateFormat(DateUtils.FULL_DOT_FMT).format(new Date());
    }

    public ArrayList<String> list(final String... args) {

        if (args == null) {
            return new ArrayList<String>();
        }

        final ArrayList<String> a = new ArrayList<String>(args.length);
        for (final String string : args) {
            a.add(string);
        }
        return a;
    }

}
