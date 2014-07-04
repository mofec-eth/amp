//>>built
define("dojox/mvc/_DataBindingMixin",["dojo/_base/kernel","dojo/_base/lang","dojo/_base/array","dojo/_base/declare","dojo/Stateful","dijit/registry"],function(h,e,g,k,l,f){h.deprecated("dojox.mvc._DataBindingMixin","Use dojox/mvc/at for data binding.");return k("dojox.mvc._DataBindingMixin",null,{ref:null,isValid:function(){var a=this.get("valid");return"undefined"!=typeof a?a:this.get("binding")?this.get("binding").get("valid"):!0},_dbstartup:function(){this._databound||(this._unwatchArray(this._viewWatchHandles),
this._viewWatchHandles=[this.watch("ref",function(a,b,c){this._databound&&b!==c&&this._setupBinding()}),this.watch("value",function(a,b,c){this._databound&&(a=this.get("binding"))&&(c&&b&&b.valueOf()===c.valueOf()||a.set("value",c))})],this._beingBound=!0,this._setupBinding(),delete this._beingBound,this._databound=!0)},_setupBinding:function(a){if(this.ref){var b=this.ref,c;if(b&&e.isFunction(b.toPlainObject))c=b;else if(/^\s*expr\s*:\s*/.test(b))b=b.replace(/^\s*expr\s*:\s*/,""),c=e.getObject(b);
else if(/^\s*rel\s*:\s*/.test(b))b=b.replace(/^\s*rel\s*:\s*/,""),(a=a||this._getParentBindingFromDOM())&&(c=e.getObject(""+b,!1,a));else if(/^\s*widget\s*:\s*/.test(b))b=b.replace(/^\s*widget\s*:\s*/,""),c=b.split("."),1==c.length?c=f.byId(b).get("binding"):(a=f.byId(c.shift()).get("binding"),c=e.getObject(c.join("."),!1,a));else if(a=a||this._getParentBindingFromDOM())c=e.getObject(""+b,!1,a);else try{var d=e.getObject(""+b)||{};e.isFunction(d.set)&&e.isFunction(d.watch)&&(c=d)}catch(m){-1==b.indexOf("${")&&
console.warn("dojox/mvc/_DataBindingMixin: '"+this.domNode+"' widget with illegal ref not evaluating to a dojo/Stateful node: '"+b+"'")}if(c)if(e.isFunction(c.toPlainObject)){this.binding=c;if(this[this._relTargetProp||"target"]!==c)this.set(this._relTargetProp||"target",c);this._updateBinding("binding",null,c)}else console.warn("dojox/mvc/_DataBindingMixin: '"+this.domNode+"' widget with illegal ref not evaluating to a dojo/Stateful node: '"+b+"'")}},_isEqual:function(a,b){return a===b||isNaN(a)&&
"number"===typeof a&&isNaN(b)&&"number"===typeof b},_updateBinding:function(a,b,c){this._unwatchArray(this._modelWatchHandles);if((a=this.get("binding"))&&e.isFunction(a.watch)){var d=this;this._modelWatchHandles=[a.watch("value",function(a,b,c){d._isEqual(b,c)||d._isEqual(d.get("value"),c)||d.set("value",c)}),a.watch("valid",function(a,b,c){d._updateProperty(a,b,c,!0);c!==d.get(a)&&d.validate&&e.isFunction(d.validate)&&d.validate()}),a.watch("required",function(a,b,c){d._updateProperty(a,b,c,!1,
a,c)}),a.watch("readOnly",function(a,b,c){d._updateProperty(a,b,c,!1,a,c)}),a.watch("relevant",function(a,b,c){d._updateProperty(a,b,c,!1,"disabled",!c)})];a=a.get("value");null!=a&&this.set("value",a)}this._updateChildBindings()},_updateProperty:function(a,b,c,d,e,f){b!==c&&(null===c&&void 0!==d&&(c=d),c!==this.get("binding").get(a)&&this.get("binding").set(a,c),e&&this.set(e,f))},_updateChildBindings:function(a){var b=this.get("binding")||a;b&&!this._beingBound&&g.forEach(f.findWidgets(this.domNode),
function(a){a.ref&&a._setupBinding?a._setupBinding(b):a._updateChildBindings(b)})},_getParentBindingFromDOM:function(){for(var a=this.domNode.parentNode,b;a;){if(a=f.getEnclosingWidget(a))if((b=a.get("binding"))&&e.isFunction(b.toPlainObject))break;a=a?a.domNode.parentNode:null}return b},_unwatchArray:function(a){g.forEach(a,function(a){a.unwatch()})}})});
//@ sourceMappingURL=_DataBindingMixin.js.map