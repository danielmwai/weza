tinyMCEPopup.requireLangPack();

var InternallinksDialog = {
	init : function() {
		var f = document.forms[0];

		// Get the selected contents as text and place it in the input
		//f.someval.value = tinyMCEPopup.editor.selection.getContent({format : 'text'});
		//f.somearg.value = tinyMCEPopup.getWindowArg('some_custom_arg');
	},

	insert : function() {
		// Insert the contents from the input into the document
		///tinyMCEPopup.editor.execCommand('mceInsertContent', false, document.forms[0].someval.value);
		var module= 1;
		var topic =$("#topic").val();
		var args= $("#somearg").val();
		var topicText =$("#topic option:selected").text();
		var link="<a href='#' class='internalLink' onclick='JJTEACH.selectTreeNode("+topic+");return false;' "+args+">"+topicText+"</a>";
		tinyMCEPopup.editor.execCommand('mceInsertContent', false,link );
		tinyMCEPopup.close();
	}
};

tinyMCEPopup.onInit.add(InternallinksDialog.init, InternallinksDialog);