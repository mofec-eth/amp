//>>built
define("dgrid/Keyboard",["dojo/_base/declare","dojo/aspect","dojo/on","dojo/_base/lang","dojo/has","put-selector/put","dojo/_base/Deferred","dojo/_base/sniff"],function(t,p,n,q,m,u,v){function w(a){a.preventDefault()}var C={checkbox:1,radio:1,button:1},D=/\bdgrid-cell\b/,E=/\bdgrid-row\b/;m.add("dom-contains",function(a,b,c){return!!c.contains});var e=t("dgrid.Keyboard",null,{pageSkip:10,tabIndex:0,keyMap:null,headerKeyMap:null,postMixInProperties:function(){this.inherited(arguments);this.keyMap||(this.keyMap=
q.mixin({},e.defaultKeyMap));this.headerKeyMap||(this.headerKeyMap=q.mixin({},e.defaultHeaderKeyMap))},postCreate:function(){function a(a){var c=a.target;return c.type&&(!C[c.type]||32==a.keyCode)}function b(b){function d(){if(c._focusedHeaderNode=l=e?c.headerNode.getElementsByTagName("th")[0]:c.headerNode)l.tabIndex=c.tabIndex}var e=c.cellNavigation,h=e?D:E,f=b===c.headerNode,l=b;f?(d(),p.after(c,"renderHeader",d,!0)):p.after(c,"renderArray",function(a){return v.when(a,function(a){var d=c._focusedNode||
l,f;if(f=h.test(d.className))f=d,f=m("dom-contains")?b.contains(f):b.compareDocumentPosition(f)&8;if(f)return a;f=0;for(var e=b.getElementsByTagName("*"),k;k=e[f];++f)if(h.test(k.className)){d=c._focusedNode=k;break}d.tabIndex=c.tabIndex;return a})});c._listeners.push(n(b,"mousedown",function(b){a(b)||c._focusOnNode(b.target,f,b)}));c._listeners.push(n(b,"keydown",function(b){if(!b.metaKey&&!b.altKey){var d=c[f?"headerKeyMap":"keyMap"][b.keyCode];d&&!a(b)&&d.call(c,b)}}))}this.inherited(arguments);
var c=this;this.tabableHeader&&(b(this.headerNode),n(this.headerNode,"dgrid-cellfocusin",function(){c.scrollTo({x:this.scrollLeft})}));b(this.contentNode)},removeRow:function(a){if(!this._focusedNode)return this.inherited(arguments);var b=this,c=document.activeElement===this._focusedNode,e=this[this.cellNavigation?"cell":"row"](this._focusedNode),d=e.row||e,g;a=a.element||a;if(a===d.element){g=this.down(d,!0);if(!g||g.element===a)g=this.up(d,!0);this._removedFocus={active:c,rowId:d.id,columnId:e.column&&
e.column.id,siblingId:!g||g.element===a?void 0:g.id};setTimeout(function(){b._removedFocus&&b._restoreFocus(d.id)},0);this._focusedNode=null}this.inherited(arguments)},insertRow:function(a){var b=this.inherited(arguments);this._removedFocus&&!this._removedFocus.wait&&this._restoreFocus(b);return b},_restoreFocus:function(a){var b=this._removedFocus;if((a=(a=a&&this.row(a))&&a.element&&a.id===b.rowId?a:"undefined"!==typeof b.siblingId&&this.row(b.siblingId))&&a.element){if(!a.element.parentNode.parentNode){b.wait=
!0;return}a="undefined"!==typeof b.columnId?this.cell(a,b.columnId):a;b.active?this.focus(a):(u(a.element,".dgrid-focus"),a.element.tabIndex=this.tabIndex)}delete this._removedFocus},addKeyHandler:function(a,b,c){return p.after(this[c?"headerKeyMap":"keyMap"],a,b,!0)},_focusOnNode:function(a,b,c){b="_focused"+(b?"Header":"")+"Node";var e=this[b],d=this.cellNavigation?"cell":"row",g=this[d](a),h,f,l,k,r;if(a=g&&g.element){if(this.cellNavigation){h=a.getElementsByTagName("input");r=0;for(l=h.length;r<
l;r++)if(f=h[r],(-1!=f.tabIndex||"lastValue"in f)&&!f.disabled){8>m("ie")&&(f.style.position="relative");f.focus();8>m("ie")&&(f.style.position="");k=!0;break}}c=q.mixin({grid:this},c);c.type&&(c.parentType=c.type);c.bubbles||(c.bubbles=!0);e&&(u(e,"!dgrid-focus[!tabIndex]"),8>m("ie")&&(e.style.position=""),c[d]=this[d](e),n.emit(a,"dgrid-cellfocusout",c));e=this[b]=a;c[d]=g;k||(8>m("ie")&&(a.style.position="relative"),a.tabIndex=this.tabIndex,a.focus());u(a,".dgrid-focus");n.emit(e,"dgrid-cellfocusin",
c)}},focusHeader:function(a){this._focusOnNode(a||this._focusedHeaderNode,!0)},focus:function(a){this._focusOnNode(a||this._focusedNode,!1)}}),s=e.moveFocusVertical=function(a,b){var c=this.cellNavigation,e=this[c?"cell":"row"](a),e=c&&e.column.id,d=this.down(this._focusedNode,b,!0);c&&(d=this.cell(d,e));this._focusOnNode(d,!1,a);a.preventDefault()};t=e.moveFocusUp=function(a){s.call(this,a,-1)};var F=e.moveFocusDown=function(a){s.call(this,a,1)},G=e.moveFocusPageUp=function(a){s.call(this,a,-this.pageSkip)},
H=e.moveFocusPageDown=function(a){s.call(this,a,this.pageSkip)},x=e.moveFocusHorizontal=function(a,b){if(this.cellNavigation){var c=!this.row(a);this._focusOnNode(this.right(this["_focused"+(c?"Header":"")+"Node"],b),c,a);a.preventDefault()}},y=e.moveFocusLeft=function(a){x.call(this,a,-1)},z=e.moveFocusRight=function(a){x.call(this,a,1)},A=e.moveHeaderFocusEnd=function(a,b){var c;this.cellNavigation&&(c=this.headerNode.getElementsByTagName("th"),this._focusOnNode(c[b?0:c.length-1],!0,a));a.preventDefault()},
I=e.moveHeaderFocusHome=function(a){A.call(this,a,!0)},B=e.moveFocusEnd=function(a,b){var c=this,e=this.cellNavigation,d=this.contentNode,g=d.scrollTop+(b?0:d.scrollHeight),d=d[b?"firstChild":"lastChild"],h=-1<d.className.indexOf("dgrid-preload"),f=h?d[(b?"next":"previous")+"Sibling"]:d,l=f.offsetTop+(b?0:f.offsetHeight),k;if(h){for(;f&&0>f.className.indexOf("dgrid-row");)f=f[(b?"next":"previous")+"Sibling"];if(!f)return}!h||1>d.offsetHeight?(e&&(f=this.cell(f,this.cell(a).column.id)),this._focusOnNode(f,
!1,a)):(m("dom-addeventlistener")||(a=q.mixin({},a)),k=p.after(this,"renderArray",function(d){k.remove();return v.when(d,function(d){d=d[b?0:d.length-1];e&&(d=c.cell(d,c.cell(a).column.id));c._focusOnNode(d,!1,a)})}));g===l&&a.preventDefault()},J=e.moveFocusHome=function(a){B.call(this,a,!0)};e.defaultKeyMap={32:w,33:G,34:H,35:B,36:J,37:y,38:t,39:z,40:F};e.defaultHeaderKeyMap={32:w,35:A,36:I,37:y,39:z};return e});
//@ sourceMappingURL=Keyboard.js.map