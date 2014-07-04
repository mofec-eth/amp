//>>built
define("dojox/mobile/_ScrollableMixin",["dojo/_base/kernel","dojo/_base/config","dojo/_base/declare","dojo/_base/lang","dojo/_base/window","dojo/dom","dojo/dom-class","dijit/registry","./scrollable"],function(m,h,k,n,e,d,f,g,l){return k("dojox.mobile._ScrollableMixin",l,{fixedHeader:"",fixedFooter:"",_fixedAppFooter:"",scrollableParams:null,allowNestedScrolls:!0,appBars:!0,constructor:function(){this.scrollableParams={}},destroy:function(){this.cleanup();this.inherited(arguments)},startup:function(){if(!this._started){this._fixedAppFooter&&
(this._fixedAppFooter=d.byId(this._fixedAppFooter));this.findAppBars();var a,b=this.scrollableParams;this.fixedHeader&&(a=d.byId(this.fixedHeader),a.parentNode==this.domNode&&(this.isLocalHeader=!0),b.fixedHeaderHeight=a.offsetHeight);this.fixedFooter&&(a=d.byId(this.fixedFooter),a.parentNode==this.domNode&&(this.isLocalFooter=!0,a.style.bottom="0px"),b.fixedFooterHeight=a.offsetHeight);this.scrollType=this.scrollType||h.mblScrollableScrollType||0;this.init(b);if(this.allowNestedScrolls)for(a=this.getParent();a;a=
a.getParent())if(a&&a.scrollableParams){this.dirLock=!0;a.dirLock=!0;break}this._resizeHandle=this.subscribe("/dojox/mobile/afterResizeAll",function(){if("none"!==this.domNode.style.display){var a=e.doc.activeElement;this.isFormElement(a)&&d.isDescendant(a,this.containerNode)&&this.scrollIntoView(a)}});this.inherited(arguments)}},findAppBars:function(){if(this.appBars){var a,b,c;a=0;for(b=e.body().childNodes.length;a<b;a++)c=e.body().childNodes[a],this.checkFixedBar(c,!1);if(this.domNode.parentNode){a=
0;for(b=this.domNode.parentNode.childNodes.length;a<b;a++)c=this.domNode.parentNode.childNodes[a],this.checkFixedBar(c,!1)}this.fixedFooterHeight=this.fixedFooter?this.fixedFooter.offsetHeight:0}},checkFixedBar:function(a,b){if(1===a.nodeType){var c=a.getAttribute("fixed")||a.getAttribute("data-mobile-fixed")||g.byNode(a)&&g.byNode(a).fixed;if("top"===c)return f.add(a,"mblFixedHeaderBar"),b&&(a.style.top="0px",this.fixedHeader=a),c;if("bottom"===c)return f.add(a,"mblFixedBottomBar"),b?this.fixedFooter=
a:this._fixedAppFooter=a,c}return null}})});
//@ sourceMappingURL=_ScrollableMixin.js.map