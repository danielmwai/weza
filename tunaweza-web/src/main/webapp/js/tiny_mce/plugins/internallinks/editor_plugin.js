(function() {
	tinymce.PluginManager.requireLangPack("internallinks");
	tinymce
			.create(
					"tinymce.plugins.InternallinksPlugin",
					{
						init : function(a, b) {
							a.addCommand("mceInternallinks", function() {
								a.windowManager.open({
									file :  b + ".htm",
									width : 320 + parseInt(a.getLang(
											"internallinks.delta_width", 0)),
									height : 120 + parseInt(a.getLang(
											"internallinks.delta_height", 0)),
									inline : 1
								}, {
									plugin_url : b,
									some_custom_arg : "custom arg"
								})
							});
							a.addButton("internallinks", {
								title : "JJTeach Internal Links",
								cmd : "mceInternallinks",
								image : b + "/img/internal.png"
							});
							a.onNodeChange.add(function(d, c, e) {
								c.setActive("internallinks",
										e.nodeName == "A")
							})
						},
						createControl : function(b, a) {
							return null
						},
						getInfo : function() {
							return {
								longname : "Internallinks plugin",
								author : "Samuel Waweru",
								authorurl : "https://www.facebook.com/#!/githengi",
								infourl : "http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/internallinks",
								version : "1.0"
							}
						}
					});
	tinymce.PluginManager.add("internallinks",
			tinymce.plugins.InternallinksPlugin)
})();