//>>built
define("dojox/embed/Quicktime",["dojo/_base/kernel","dojo/_base/lang","dojo/_base/sniff","dojo/_base/window","dojo/dom","dojo/dom-construct","dojo/domReady!"],function(h,m,f,n,p,k){function q(a){a=h.mixin(m.clone(u),a||{});if(!("path"in a)&&!a.testing)return console.error("dojox.embed.Quicktime(ctor):: no path reference to a QuickTime movie was provided."),null;a.testing&&(a.path="");"id"in a||(a.id=v+w++);return a}var g,r={major:0,minor:0,rev:0},e,u={width:320,height:240,redirect:null},v="dojox-embed-quicktime-",
w=0;h.getObject("dojox.embed",!0);f("ie")?(e=function(){try{var a=new ActiveXObject("QuickTimeCheckObject.QuickTimeCheck.1");if(void 0!==a){var b=a.QuickTimeVersion.toString(16);r={major:b.substring(0,1)-0||0,minor:b.substring(1,2)-0||0,rev:b.substring(2,3)-0||0};return a.IsQuickTimeAvailable(0)}}catch(c){}return!1}(),g=function(a){if(!e)return{id:null,markup:'This content requires the \x3ca href\x3d"http://www.apple.com/quicktime/download/" title\x3d"Download and install QuickTime."\x3eQuickTime plugin\x3c/a\x3e.'};
a=q(a);if(!a)return null;var b='\x3cobject classid\x3d"clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase\x3d"http://www.apple.com/qtactivex/qtplugin.cab#version\x3d6,0,2,0" id\x3d"'+a.id+'" width\x3d"'+a.width+'" height\x3d"'+a.height+'"\x3e\x3cparam name\x3d"src" value\x3d"'+a.path+'"/\x3e',c;for(c in a.params||{})b+='\x3cparam name\x3d"'+c+'" value\x3d"'+a.params[c]+'"/\x3e';return{id:a.id,markup:b+"\x3c/object\x3e"}}):(e=function(){for(var a=0,b=navigator.plugins,c=b.length;a<c;a++)if(-1<b[a].name.indexOf("QuickTime"))return!0;
return!1}(),g=function(a){if(!e)return{id:null,markup:'This content requires the \x3ca href\x3d"http://www.apple.com/quicktime/download/" title\x3d"Download and install QuickTime."\x3eQuickTime plugin\x3c/a\x3e.'};a=q(a);if(!a)return null;var b='\x3cembed type\x3d"video/quicktime" src\x3d"'+a.path+'" id\x3d"'+a.id+'" name\x3d"'+a.id+'" pluginspage\x3d"www.apple.com/quicktime/download" enablejavascript\x3d"true" width\x3d"'+a.width+'" height\x3d"'+a.height+'"',c;for(c in a.params||{})b+=" "+c+'\x3d"'+
a.params[c]+'"';return{id:a.id,markup:b+"\x3e\x3c/embed\x3e"}});var d=function(a,b){return d.place(a,b)};h.mixin(d,{minSupported:6,available:e,supported:e,version:r,initialized:!1,onInitialize:function(){d.initialized=!0},place:function(a,b){var c=g(a);if(!(b=p.byId(b)))b=k.create("div",{id:c.id+"-container"},n.body());return c&&(b.innerHTML=c.markup,c.id)?f("ie")?dom.byId(c.id):document[c.id]:null}});if(f("ie"))f("ie")&&e&&setTimeout(function(){d.onInitialize()},10);else{var s=g({testing:!0,width:4,
height:4}),l=10,t=function(){setTimeout(function(){var a=document[s.id],b=p.byId("-qt-version-test");if(a)try{var c=a.GetQuickTimeVersion().split(".");d.version={major:parseInt(c[0]||0),minor:parseInt(c[1]||0),rev:parseInt(c[2]||0)};if(d.supported=c[0])d.onInitialize();l=0}catch(e){l--&&t()}!l&&b&&k.destroy(b)},20)};k.create("div",{innerHTML:s.markup,id:"-qt-version-test",style:{top:"-1000px",left:0,width:"1px",height:"1px",overflow:"hidden",position:"absolute"}},n.body());t()}m.setObject("dojox.embed.Quicktime",
d);return d});
//@ sourceMappingURL=Quicktime.js.map