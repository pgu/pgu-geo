var delay_to_call_geocoder = 5000;
var cache_name2itemLocation = {};
var cache_itemId2locations = {};
var lastCall = 0 ;
var _showdown_converter;

function RowConfig(item_id) {
	this.item_id = item_id;
	this.info_id = "info_" + item_id;
	this.locations_cell_id = "locations_" + item_id;
}

function createTable(type, items, empty_message) {
	
	var _items = items || {};
	if (_items.values) {

		var values = _items.values;
		var table = [];
		
		table.push(createTableHead(type));
		
		for ( var i = 0, len = values.length; i < len; i++) {
			
			table.push(createTableRow(type, values[i]));
		}

		table.push(createTableFoot());

		return table.join('');

	} else {
		return empty_message;
	}
	
}

function isEdu(type) {
	return type == 'edu';
}
function isXp(type) {
	return type == 'xp';
}

function createTableHead(type) {
	var title = '';
	
	if (isEdu(type)) {
		title = 'Education';
		
	} else if (isXp(type)) {
		title = 'Position';
		
	}
	
	return ''
	+ '<table class="table table-bordered table-striped"> '
	+ '   <thead>                                         '
	+ '      <tr>                                         '
	+ '          <th>Location</th>                        '
	+ '          <th>Dates</th>                           '
	+ '          <th>' + title + '</th>                   '
	+ '          <th></th>                                '
	+ '      </tr>                                        '
	+ '  </thead>                                         '
	+ '  <tbody>                                          '
	+ '';

}

function createTableRow(type, item) {
	
	var rowConfig = new RowConfig(item.id, type);
	rowConfig.locations = createListLocations(item.id);
	rowConfig.dates = labelDates(item);
	
	if (isEdu(type)) {
		
		rowConfig.short_content = labelEduTitle(item);
		rowConfig.content_title = "Education";
		rowConfig.long_content = labelMkdown(item.notes);
		
	} else if (isXp(type)) {
		
		rowConfig.short_content = labelXpTitle(item);
		rowConfig.content_title = "Experience";
		rowConfig.long_content = labelMkdown(item.summary);
		
	} else {
		rowConfig = {};
	}
	
	
	return '' 
	+ '<tr>                                                                              '
	+ '  <td>                                                                            '
	+ '    <ul id="' + rowConfig.locations_cell_id + '" class="nav nav-pills">           '
	+ rowConfig.locations
	+ '    </ul>                                                                         '
	+ '    <i class="icon-plus-sign icon-large add-location"                             '
	+ '      onclick="javascript:addNewLocation(\'' + rowConfig.item_id + '\');"         '
	+ '      >                                                                           '
	+ '    </i>                                                                          '
	+ '  </td>                                                                           '
	+ '  <td>' + rowConfig.dates + '</td>                                                '
	+ '  <td>' + rowConfig.short_content + '</td>                                        '
	+ '  <td style="cursor:pointer"                                                      '
	+ '      onclick="javascript:$(\'#' + rowConfig.info_id + '\').popover(\'toggle\');" '
	+ '      >                                                                           '
	+ '    <i id="' + rowConfig.info_id + '" class="icon-info-sign icon-large"           '
	+ '      data-animation="true"                                                       '
	+ '      data-html="true"                                                            '
	+ '      data-placement="left"                                                       '
	+ '      data-title="' + rowConfig.content_title + '"                                '
	+ '      data-content="' + rowConfig.long_content + '"                               '
	+ '     ></i>                                                                        '
	+ '   </td>                                                                          '
	+ '</tr>                                                                             '
	+ '';
	
}

function createTableFoot() {
	return ''
		+ '  </tbody>                                        '
		+ '</table>                                          '
		+ '';
	
}

function labelDates(item) {
//	October 2011<br/>August 2011<br/>(3 months)
	var dates = [];
	var endDate = item.endDate;
	var startDate = item.startDate;
	
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

function labelXpTitle(position) {
//	SFEIR<br/>Senior Web Java J2EE Engineer Developer
	var title = [];
	
	if (position.company
			&& position.company.name) {
		
		title.push(position.company.name);
	}
	
	if (position.title) {
		title.push(position.title);
	}
	
	return title.join('<br/>');
}

function getMarkdownConverter() {
	if (_showdown_converter) {
		return _showdown_converter;
	}
	
	_showdown_converter = new Showdown.converter();
	return _showdown_converter;
}

function labelMkdown(text) { // http://softwaremaniacs.org/playground/showdown-highlight/
	return getMarkdownConverter().makeHtml(text || '');
}

function createListLocations(item_id) {
	
	var itemLocations = cache_itemId2locations[item_id] || ''; 
	
	var list = [];
	for (var i = 0, len = itemLocations.length; i < len; i++) {
		
		var itemLocation = itemLocations[i];
		
		var anchor_id = "loc_" + item_id + "_" + i;
		
		var el = ''
		+ '      <li class="locationLi">          '
		+ '        <a id="' + anchor_id + '"             '
		+ '           href="javascript:;"         '
		+ '           onclick="javascript:searchMapFor(\''+ item_id +'\', \'' + anchor_id +'\', \''+ itemLocation.name +'\');return false;"'
		+ '           >                           '
		+ '           ' + itemLocation.name + '   '
		+ '        </a>                           '
		+ '      </li>                            '
		+ '';
		
		list.push(el);
		
		if (!(itemLocation.lat && itemLocation.lng)) {
			
			setTimeout(function() { searchLatLng(itemLocation, anchor_id); }, delay_to_call_geocoder);
			delay_to_call_geocoder += 500;
			
		} else {
			
			updateCache_name2itemLocation(itemLocation);
		}
	}
	
	return list.join('');
	
}

function updateCache_name2itemLocation(itemLocation) {
	
	if (cache_name2itemLocation[itemLocation.name] == undefined) {
		
		var cacheItem = {};
		cacheItem.name = itemLocation.name;
		cacheItem.lat = itemLocation.lat;
		cacheItem.lng = itemLocation.lng;
		
		cache_name2itemLocation[itemLocation.name] = cacheItem;
	}
	
	// update items' locations
	for (var key in cache_itemId2locations) {
		if (cache_itemId2locations.hasOwnProperty(key)) {
			
			var itemLocations = cache_itemId2locations[key];
			for (var i = 0, len = itemLocations.length; i < len; i++) {
				var loc = itemLocations[i];
				if (loc.name == itemLocation.name) {
					loc.lat = itemLocation.lat;
					loc.lng = itemLocation.lng;
				}
			}
		}
	}
}

function labelEduTitle(education) {
	// Universität Rostock<br/>International Trade	
	var title = [];
	
	if (education.schoolName) {
		title.push(education.schoolName);
	}
	
	if (education.fieldOfStudy) {
		title.push(education.fieldOfStudy);
	}
	
	return title.join('<br/>');
}

function searchLatLng(itemLocation, anchor_id) {

	if (geocoder == undefined) {
		setTimeout(function() { searchLatLng(itemLocation, anchor_id); }, 1000);
		return;
	}
	
//	var currentCall = Math.round(Date.now() / 1000);
//	console.log("call at " + (currentCall - lastCall) + "s, " + itemLocation.name);
//	lastCall = currentCall;
	
	var cacheItem = cache_name2itemLocation[itemLocation.name];
	if (cacheItem) {
		
		itemLocation.lat = cacheItem.lat;
		itemLocation.lng = cacheItem.lng;
		return;
	}
	
	geocoder.geocode(
			{
				'address' : itemLocation.name
			},
			function(results, status) {
				
				
				if (status == google.maps.GeocoderStatus.OK) {
					
					var loc = results[0].geometry.location;
//					console.log(itemLocation.name + ": " + loc);
					
					itemLocation.lat = loc.lat() + '';
					itemLocation.lng = loc.lng() + '';

					updateCache_name2itemLocation(itemLocation);

				} else if (status == google.maps.GeocoderStatus.ZERO_RESULTS) {
					
					var anchor = $('#' + anchor_id);
					anchor.addClass('locationNotFound');
					anchor.attr('title', 'Unknown location');
//					console.log("Oups: " + status);
					
				} else if (status == google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
					
					setTimeout(function() { searchLatLng(itemLocation, anchor_id); }, 1000);			
//					console.log("over_query_limit... " + itemLocation.name);
					
				} else {
					
					var anchor = $('#' + anchor_id);
					anchor.addClass('locationNotFound_technicalError');
					anchor.attr('title', 'Location not found because of a technical exception: ' + status);
//					console.log("Oups: " + status);
				}
				
			}
	);
}



















