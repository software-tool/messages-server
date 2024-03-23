function clearAllEvents() {
    var functionSuccess = function(result, textStatus, jQxhr) {
        loadListingAll();
    };

    $(".event_listing").html(null);

    applyClearAllEvents(getServerUrl() + "clear", "", functionSuccess);
}

function applyClearAllEvents(url, location, functionSuccess) {
	var params = "?";
	params += "location=" + encodeURIComponent(location);

	$.ajax({
		type: 'post',
		url: url + params,
		success: functionSuccess,
		error: function( jqXhr, textStatus, errorThrown ){
			console.log( errorThrown );
		}
	}).fail(function() {
		console.log("Clear listing failed: " + url);
	});
}