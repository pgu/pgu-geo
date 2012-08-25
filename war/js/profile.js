
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
	var info_xp_id = "info_xp_" + index;
	var locations = createListLocations(info_xp_id, position);
	var dates = labelDates(position);
	var title = labelTitle(position);
	var summary = labelSummary(position);
	
	return '' 
	+ '<tr>                                   '
	+ '  <td>                                 '
	+ '    <ul class="nav nav-pills">         '
	+ locations
	+ '    </ul>                              '
	+ '  </td>                                '
	+ '  <td>' + dates + '</td>               '
	+ '  <td>' + title + '</td>               '
	+ '  <td style="cursor:pointer"           '
	+ '      onclick="javascript:$(\'#' + info_xp_id + '\').popover(\'toggle\');"'
	+ '      >                                '
	+ '    <i id="' + info_xp_id + '" class="icon-info-sign icon-large"  '
	+ '      data-animation="true"                            '
	+ '      data-html="true"                                 '
	+ '      data-placement="left"                            '
	+ '      data-title="Experience"                          '
	+ '      data-content="' + summary + '"                   '
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

function createListLocations(info_id, position) {
	var loc = position.location || '';
	var names = loc.name || '';
	var locations = names.split(";");

	var list = [];
	for (var i = 0, len = locations.length; i < len; i++) {
		var id = "a_" +info_id + "_" + i;
		var location = locations[i];
		
		var el = ''
		+ '      <li>                             '
		+ '        <a id="' + id + '"             '
		+ '           href="javascript:;"         '
		+ '           onclick="javascript:searchMapFor(\''+ id +'\', \''+ location +'\');return false;"'
		+ '           >                           '
		+ '           <b>' + location + '</b>     '
		+ '        </a>                           '
		+ '      </li>                            '
		+ '      <br/>                            '
		+ '';
		
		list.push(el);
	}
	
	return list.join('');
}


function labelSummary(position) { // http://softwaremaniacs.org/playground/showdown-highlight/
	 var summary = position.summary || '';
	 
	 var html = getMarkdownConverter().makeHtml(summary);
	 return html;
}

var _showdown_converter;

function getMarkdownConverter() {
	if (_showdown_converter) {
		return _showdown_converter;
	}
	
	_showdown_converter = new Showdown.converter();
	return _showdown_converter;
}

function createEduTable(table_id, educations, id2loc) {
	
	var edu_table = [];
	
	edu_table.push(createEduTableHead());
	
	for ( var i = 0, len = educations.length; i < len; i++) {
		
		edu_table.push(createEduTableRow(i, educations[i], id2loc));
	}

	edu_table.push(createEduTableFoot());

	document.getElementById(table_id).innerHTML = edu_table.join('');
	
}

function createEduTableHead() {
	return ''
	+ '<table class="table table-bordered table-striped"> '
	+ '  <thead>                                          '
	+ '    <tr>                                           '
	+ '      <th>Location</th>                            '
	+ '      <th>Dates</th>                               '
	+ '      <th>Education</th>                           '
	+ '      <th></th>                                    '
	+ '    </tr>                                          '
	+ '  </thead>                                         '
	+ '<tbody>                                            '
	+ '';
	
}

function createEduTableFoot() {
	return ''
	+ '  </tbody>                                        '
	+ '</table>                                          '
	+ '';
	
}

function createEduTableRow(index, position, id2loc) {
	
	var info_edu_id = position.id || "info_edu_" + index;
	var locations = createEduListLocations(info_edu_id, position, id2loc);
	var dates = labelDates(position); // 2005<br/>2005
//	var title = labelEduTitle(position); // Universität Rostock<br/>International Trade
	var title = "todo";
//	var notes = labelNotes(position);
	var notes = "todo";
	
	return '' 
	+ '<tr>                                   '
	+ '  <td>                                 '
	+ '    <ul class="nav nav-pills">         '
	+ locations
	+ '    </ul>                              '
	+ '  </td>                                '
	+ '  <td>' + dates + '</td>               '
	+ '  <td>' + title + '</td>               '
	+ '  <td style="cursor:pointer"           '
	+ '      onclick="javascript:$(\'#' + info_edu_id + '\').popover(\'toggle\');"'
	+ '      >                                '
	+ '    <i id="' + info_edu_id + '" class="icon-info-sign icon-large"  '
	+ '      data-animation="true"                            '
	+ '      data-html="true"                                 '
	+ '      data-placement="left"                            '
	+ '      data-title="Education"                          '
	+ '      data-content="' + notes + '"                   '
	+ '     ></i>                                             '
	+ '   </td>                                               '
	+ '</tr>                                                  '
	+ '';
	
}

function createEduListLocations(info_edu_id, position, id2loc) {
	
	console.log(id2loc);
	
	var id = "a_" + info_edu_id;
	var location = id2loc[position.id];
	
	return ''
	+ '      <li>                             '
	+ '        <a id="' + id + '"             '
	+ '           href="javascript:;"         '
	+ '           onclick="javascript:searchMapFor(\''+ id +'\', \''+ location +'\');return false;"'
	+ '           >                           '
	+ '           <b>' + location + '</b>     '
	+ '        </a>                           '
	+ '      </li>                            '
	+ '      <br/>                            '
	+ '';
}



