//>>built
define("dijit/form/MultiSelect",["dojo/_base/array","dojo/_base/declare","dojo/dom-geometry","dojo/has","dojo/query","./_FormValueWidget"],function(e,f,h,g,d,c){c=f("dijit.form.MultiSelect"+(g("dojo-bidi")?"_NoBidi":""),c,{size:7,baseClass:"dijitMultiSelect",templateString:"\x3cselect multiple\x3d'true' ${!nameAttrSetting} data-dojo-attach-point\x3d'containerNode,focusNode' data-dojo-attach-event\x3d'onchange: _onChange'\x3e\x3c/select\x3e",addSelected:function(a){a.getSelected().forEach(function(b){this.containerNode.appendChild(b);
this.domNode.scrollTop=this.domNode.offsetHeight;b=a.domNode.scrollTop;a.domNode.scrollTop=0;a.domNode.scrollTop=b},this);this._set("value",this.get("value"))},getSelected:function(){return d("option",this.containerNode).filter(function(a){return a.selected})},_getValueAttr:function(){return e.map(this.getSelected(),function(a){return a.value})},multiple:!0,_setValueAttr:function(a,b){d("option",this.containerNode).forEach(function(b){b.selected=-1!=e.indexOf(a,b.value)});this.inherited(arguments)},
invertSelection:function(a){var b=[];d("option",this.containerNode).forEach(function(a){a.selected||b.push(a.value)});this._setValueAttr(b,!(!1===a||null==a))},_onChange:function(){this._handleOnChange(this.get("value"),!0)},resize:function(a){a&&h.setMarginBox(this.domNode,a)},postCreate:function(){this._set("value",this.get("value"));this.inherited(arguments)}});g("dojo-bidi")&&(c=f("dijit.form.MultiSelect",c,{addSelected:function(a){a.getSelected().forEach(function(a){a.text=this.enforceTextDirWithUcc(this.restoreOriginalText(a),
a.text)},this);this.inherited(arguments)},_setTextDirAttr:function(a){if((this.textDir!=a||!this._created)&&this.enforceTextDirWithUcc)this._set("textDir",a),d("option",this.containerNode).forEach(function(a){!this._created&&a.value===a.text&&(a.value=a.text);a.text=this.enforceTextDirWithUcc(a,a.originalText||a.text)},this)}}));return c});
//@ sourceMappingURL=MultiSelect.js.map