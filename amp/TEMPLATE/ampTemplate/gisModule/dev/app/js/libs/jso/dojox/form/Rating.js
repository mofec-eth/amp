//>>built
define("dojox/form/Rating",["dojo/_base/declare","dojo/_base/lang","dojo/dom-attr","dojo/dom-class","dojo/mouse","dojo/on","dojo/string","dojo/query","dijit/form/_FormWidget"],function(k,f,g,e,l,d,h,m,n){return k("dojox.form.Rating",n,{templateString:null,numStars:3,value:0,buildRendering:function(a){for(var b="",c=0;c<this.numStars;c++)b+=h.substitute('\x3cli class\x3d"dojoxRatingStar dijitInline" value\x3d"${value}"\x3e\x3c/li\x3e',{value:c+1});this.templateString=h.substitute('\x3cdiv dojoAttachPoint\x3d"domNode" class\x3d"dojoxRating dijitInline"\x3e\x3cinput type\x3d"hidden" value\x3d"0" dojoAttachPoint\x3d"focusNode" /\x3e\x3cul data-dojo-attach-point\x3d"list"\x3e${stars}\x3c/ul\x3e\x3c/div\x3e',
{stars:b});this.inherited(arguments)},postCreate:function(){this.inherited(arguments);this._renderStars(this.value);this.own(d(this.list,d.selector(".dojoxRatingStar","mouseover"),f.hitch(this,"_onMouse")),d(this.list,d.selector(".dojoxRatingStar","click"),f.hitch(this,"onStarClick")),d(this.list,l.leave,f.hitch(this,function(){this._renderStars(this.value)})))},_onMouse:function(a){var b=+g.get(a.target,"value");this._renderStars(b,!0);this.onMouseOver(a,b)},_renderStars:function(a,b){m(".dojoxRatingStar",
this.domNode).forEach(function(c,d){d+1>a?(e.remove(c,"dojoxRatingStarHover"),e.remove(c,"dojoxRatingStarChecked")):(e.remove(c,"dojoxRatingStar"+(b?"Checked":"Hover")),e.add(c,"dojoxRatingStar"+(b?"Hover":"Checked")))})},onStarClick:function(a){a=+g.get(a.target,"value");this.setAttribute("value",a==this.value?0:a);this._renderStars(this.value);this.onChange(this.value)},onMouseOver:function(){},setAttribute:function(a,b){this.set(a,b)},_setValueAttr:function(a){this.focusNode.value=a;this._set("value",
a);this._renderStars(a);this.onChange(a)}})});
//@ sourceMappingURL=Rating.js.map