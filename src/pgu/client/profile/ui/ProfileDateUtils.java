package pgu.client.profile.ui;

import com.google.gwt.core.client.JavaScriptObject;

public class ProfileDateUtils {

    public static native String labelDates(JavaScriptObject item) /*-{

		//  October 2011<br/>August 2011<br/>(3 months)
		var dates = [];
		var endDate = item.endDate;
		var startDate = item.startDate;

		if (endDate) {

			var end = '';
			if (endDate.month) {
				end += @pgu.client.profile.ui.ProfileDateUtils::tslMonth(I)(endDate.month)
						+ ' ';
			}
			if (endDate.year) {
				end += endDate.year;
			}
			dates.push(end);

		} else {

			dates.push('Present');

			var now = new Date();
			endDate = {};
			endDate.month = now.getMonth() + 1;
			endDate.year = now.getFullYear();
		}

		if (startDate) {

			var start = '';
			if (endDate) {
				start += '<br/>';
			}
			if (startDate.month) {
				start += @pgu.client.profile.ui.ProfileDateUtils::tslMonth(I)(startDate.month)
						+ ' ';
			}
			if (startDate.year) {
				start += startDate.year;
			}
			dates.push(start);
		}

		if (startDate && endDate) {

			var diffD = '';
			if (startDate.year && startDate.month && endDate.year
					&& endDate.month) {

				var endD = new Date(endDate.year, endDate.month - 1, 1);
				var startD = new Date(startDate.year, startDate.month - 1, 1);

				var diffDtime = @pgu.client.profile.ui.ProfileDateUtils::monthDiff(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(startD, endD);

				if (diffDtime > 0) {
					var years = Math.floor(diffDtime / 12);
					var months = diffDtime % 12;

					diffD += '<br/>(';
					diffD += years > 0 ? (years + ' year') : '';
					if (years > 0) {
						diffD += years > 1 ? 's ' : ' ';
					}

					diffD += months > 0 ? (months + ' month') : '';
					if (months > 0) {
						diffD += months > 1 ? 's' : '';
					}

					diffD += ')';
				}
			}
			dates.push(diffD);
		}

		return dates.join('');

    }-*/;

    public static final String[] months = { //
                                        //
            "January" //
            , "February" //
            , "March" //
            , "April" //
            , "May" //
            , "June" //
            , "July" //
            , "August" //
            , "September" //
            , "October" //
            , "November" //
            , "December" //
                                        };

    public static String tslMonth(final int monthNb) {
        final Integer monthIdx = monthNb - 1;
        return months[monthIdx];
    }

    // http://stackoverflow.com/questions/2536379/difference-in-months-between-two-dates-in-javascript
    public static native int monthDiff(JavaScriptObject d1, JavaScriptObject d2) /*-{
		var months;
		months = (d2.getFullYear() - d1.getFullYear()) * 12;
		months -= d1.getMonth();
		months += d2.getMonth() + 1;
		return months;
    }-*/;

}
