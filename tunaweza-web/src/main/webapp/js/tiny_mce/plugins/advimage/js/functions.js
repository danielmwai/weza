function ts_insert_image(url, alt_text) {
	 var formObj = formElement(); 
	 formObj.src.value = url;
	 formObj.alt.value = alt_text;
	ImageDialog.insertAndClose();
}

function ts_ce(tag, name) {

	if (name && window.ActiveXObject) {

		element = document.createElement('<' + tag + ' name="' + name + '">');

	} else {

		element = document.createElement(tag);

		element.setAttribute('name', name);

	}

	return element;

}

function ts_onload() {

	var iframe1 = ts_ce('iframe', 'html_editor_image_upload_frame');

	iframe1.setAttribute('src', 'about:blank');

	iframe1.style.border = "0px none";

	iframe1.style.position = "absolute";

	iframe1.style.width = "1px";

	iframe1.style.height = "1px";

	iframe1.style.visibility = "hidden";

	iframe1.setAttribute('id', 'html_editor_image_upload_frame');

	$('#image-upload').append(iframe1);

	$('#image_upload_form').attr("action", ts_upload_image_path());

}

function ts_upload_image_path() {

	path_prefix = window.parent.location.pathname.split("/")[1];

	to_path = "/" + path_prefix + "/js/tiny_mce/plugins/advimage/image.htm";

	return to_path;

}
