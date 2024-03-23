function loadListingAll() {

    var functionSuccess = function(list, textStatus, jQxhr) {
        // ApiEvent[]

        var content = createListingContent(list);
        var table = createListingTable(content);

        $(".event_listing").append(table);
    };

    $(".event_listing").html(null);

    readListing(getServerUrl() + "listAll", "", functionSuccess);
}

function loadListingGrouped() {

    var functionSuccess = function(mapped, textStatus, jQxhr) {
        // String -> ApiEvent[]

        const keys = Object.keys(mapped);

        var html = "";
        for (var i=0; i<keys.length; i++) {
            var key = keys[i];
            var list = mapped[key];

            html += "<h3>"+ key +"</h3>";

            var content = createListingContent(list);
            var table = createListingTable(content);
            html += table;
        }

        $(".event_listing").append(html);
    };

    $(".event_listing").html(null);

    readListingGrouped(getServerUrl() + "listGrouped", "", functionSuccess);
}

function createListingContent(list) {
    var html = "";
    for (var i=0; i<list.length; i++) {
        html += createRowOfListing(list[i]);
    }
    return html;
}

function createRowOfListing(apiEvent) {
    // ApiEvent

    const dateCreated = new Date(apiEvent.created);

    const options = {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
      //second: "2-digit",
      //timeZoneName: "short"
    };
    const formattedDate = dateCreated.toLocaleString("en-US", options);

    var html = "";
    html += "<tr>";

    // Type
    html += "<td>";
    html += apiEvent.type;
    html += "</td>";

    // Location
    html += "<td>";
    html += apiEvent.location;
    html += "</td>";

    // Date
    html += "<td>";
    html += formattedDate;
    html += "</td>";

    // Title
    html += "<td>";
    html += apiEvent.title;
    html += "</td>";

    // Description
    html += "<td>";
    html += apiEvent.description;
    html += "</td>";

    html += "</tr>";

    return html;
}

function createListingTable(content) {
    var html = "";
    html += "<table class='table table-striped'>";

    html += "<thead>";
    html += "<tr>";
    html += "<th>&nbsp;</th>";
    html += "<th>Location</th>";
    html += "<th>Created</th>";
    html += "<th>Title</th>";
    html += "<th>Info</th>";
    html += "</tr>";
    html += "</thead>";

    html += "<tbody>";
    html += content;
    html += "<tbody>";
    html += "</table>";
    return html;
}

function readListing(url, location, functionSuccess) {
	var params = "?";
	//params += "location=" + encodeURIComponent(location);

	$.ajax({
		type: 'get',
		url: url + params,
		success: functionSuccess,
		error: function( jqXhr, textStatus, errorThrown ){
			console.log( errorThrown );
		}
	}).fail(function() {
		console.log("Get listing failed: " + url);
	});
}

function readListingGrouped(url, location, functionSuccess) {
	var params = "?";
	//params += "location=" + encodeURIComponent(location);

	$.ajax({
		type: 'get',
		url: url + params,
		success: functionSuccess,
		error: function( jqXhr, textStatus, errorThrown ){
			console.log( errorThrown );
		}
	}).fail(function() {
		console.log("Get listing failed: " + url);
	});
}