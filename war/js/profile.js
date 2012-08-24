
function createXpTable(table_id, positions) {
	
	var xp_table = [];
	
	xp_table.push(createXpTableHead());
	
	for ( var i = 0, len = positions.length; i < len; i++) {
		
		xp_table.push(createXpTableRow(i, positions[i]));
	}

	xp_table.push(createXpTableFoot());

	document.getElementById(table_id).innerHTML = xp_table.join('');
}

function createXpTableHead() {
	return ''
	+ '<table class="table table-bordered table-striped"> '
	+ '   <thead>                                         '
	+ '      <tr>                                         '
	+ '          <th>Location</th>                        '
	+ '          <th>Dates</th>                           '
	+ '          <th>Position</th>                        '
	+ '          <th></th>                                '
	+ '      </tr>                                        '
	+ '  </thead>                                         '
	+ '  <tbody>                                          '
	+ '';

}

function createXpTableRow(index, position) {
	var id = "position_" + index;
	var info_id = "info_" + id;
	var loc = labelLocations(position);
	var dates = labelDates(position);
	var title = labelTitle(position);
	var summary = position.summary || '';
	
	return '' 
	+ '<tr>                                   '
	+ '  <td>                                 '
	+ '    <ul class="nav nav-pills">         '
	+ '      <li>                             '
	+ '        <a id="' + id + '"             '
	+ '           href="javascript:;"         '
	+ '           onclick="javascript:searchMapFor("'+ id +'", "'+ loc +'");return false;"'
	+ '           >                           '
	+ '           <b>' + loc + '</b>          '
	+ '        </a>                           '
	+ '      </li>                            '
	+ '    </ul>                              '
	+ '  </td>                                '
	+ '  <td>' + dates + '</td>               '
	+ '  <td>' + title + '</td>               '
	+ '  <td style="cursor:pointer"           '
	+ '      onclick="javascript:$("#' + info_id + '").popover("toggle");"'
	+ '      >                                '
	+ '    <i id="' + info_id + '" class="icon-info-sign icon-large"  '
	+ '      data-title="Experience"                          '
	+ '      data-content="' + summary + '"                   '
	+ '      data-placement="left"                            '
	+ '     ></i>                                             '
	+ '   </td>                                               '
	+ '</tr>                                                  '
	+ '';
	
}

function createXpTableFoot() {
	return ''
		+ '  </tbody>                                        '
		+ '</table>                                          '
		+ '';
	
}

function labelDates(position) {
//	October 2011<br/>August 2011<br/>(3 months)
	var dates = [];
	var endDate = position.endDate;
	var startDate = position.startDate;
	
	if (endDate) {
		var end = '';
		if (endDate.month) {
			end += tslMonth(endDate.month) + ' ';
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
			start += tslMonth(startDate.month) + ' ';
		}
		if (startDate.year) {
			start += startDate.year;
		}
		dates.push(start);
	}

	console.log('---');
	console.log(startDate);
	console.log(endDate);
	console.log(startDate.year);
	console.log(startDate.month);
	console.log(endDate.year);
	console.log(endDate.month);
	
	if (startDate && endDate) {
		var diffD = '';
		if (startDate.year 
				&& startDate.month
				&& endDate.year 
				&& endDate.month) {
			
			var endD = new Date(endDate.year, endDate.month - 1, 1);
			var startD = new Date(startDate.year, startDate.month - 1, 1);

			var diffDtime = monthDiff(startD, endD);
			
			if (diffDtime > 0) {
				var years = Math.floor(diffDtime / 12);
				var months = diffDtime % 12;
				
				diffD += '<br/>(';
				diffD += years > 0 ? (years + ' year'): '';
				if (years > 0) {
					diffD += years > 1 ? 's ' : ' ';
				}
				
				diffD += months > 0 ? (months + ' month'): '';
				if (months > 0) {
					diffD += months > 1 ? 's' : '';
				}
				
				diffD += ')';
			}
		}
		dates.push(diffD);
	}
	
	return dates.join('');
}

// http://stackoverflow.com/questions/2536379/difference-in-months-between-two-dates-in-javascript
function monthDiff(d1, d2) {
    var months;
    months = (d2.getFullYear() - d1.getFullYear()) * 12;
    months -= d1.getMonth();
    months += d2.getMonth() + 1;
    return months;
}

function tslMonth(monthNb) { // temporary...
	var month=new Array();
	month[0]="January";
	month[1]="February";
	month[2]="March";
	month[3]="April";
	month[4]="May";
	month[5]="June";
	month[6]="July";
	month[7]="August";
	month[8]="September";
	month[9]="October";
	month[10]="November";
	month[11]="December";
	return month[monthNb - 1];
}

function labelTitle(position) {
//	SFEIR<br/>Senior Web Java J2EE Engineer Developer
	var title = [];
	
	if (position.company
			&& position.company.name) {
		
		title.push(position.company.name)
	}
	
	if (position.title) {
		if (title.length > 0) {
			title.push('<br/>');
		}
		title.push(position.title);
	}
	
	return title.join('');
}

function labelLocations(position) {
	var loc = position.location || '';
	var names = loc.name || '';
	
	if (names.indexOf(";") == -1) {
		return names;
	}
	
	var parts = names.split(";");
	var results = []; 
	for (var i = 0, len = parts.length; i < len; i++) {
		if (i > 0) {
			results.push("<br/>");
		}
		results.push(parts[i].trim());
	}
	return results.join('');
}

