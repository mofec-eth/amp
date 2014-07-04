//>>built
define("dijit/tree/dndSource",["dojo/_base/array","dojo/_base/connect","dojo/_base/declare","dojo/dom-class","dojo/dom-geometry","dojo/_base/lang","dojo/on","dojo/touch","dojo/topic","dojo/dnd/Manager","./_dndSelector"],function(p,t,u,n,v,l,x,y,m,k,w){return u("dijit.tree.dndSource",w,{isSource:!0,accept:["text","treeNode"],copyOnly:!1,dragThreshold:5,betweenThreshold:0,generateText:!0,constructor:function(a,g){g||(g={});l.mixin(this,g);var b=g.accept instanceof Array?g.accept:["text","treeNode"];this.accept=
null;if(b.length){this.accept={};for(var d=0;d<b.length;++d)this.accept[b[d]]=1}this.mouseDown=this.isDragging=!1;this.targetBox=this.targetAnchor=null;this.dropPosition="";this._lastY=this._lastX=0;this.sourceState="";this.isSource&&n.add(this.node,"dojoDndSource");this.targetState="";this.accept&&n.add(this.node,"dojoDndTarget");this.topics=[m.subscribe("/dnd/source/over",l.hitch(this,"onDndSourceOver")),m.subscribe("/dnd/start",l.hitch(this,"onDndStart")),m.subscribe("/dnd/drop",l.hitch(this,"onDndDrop")),
m.subscribe("/dnd/cancel",l.hitch(this,"onDndCancel"))]},checkAcceptance:function(){return!0},copyState:function(a){return this.copyOnly||a},destroy:function(){this.inherited(arguments);for(var a;a=this.topics.pop();)a.remove();this.targetAnchor=null},_onDragMouse:function(a,g){var b=k.manager(),d=this.targetAnchor,c=this.current,e=this.dropPosition,f="Over";if(c&&0<this.betweenThreshold){if(!this.targetBox||d!=c)this.targetBox=v.position(c.rowNode,!0);a.pageY-this.targetBox.y<=this.betweenThreshold?
f="Before":a.pageY-this.targetBox.y>=this.targetBox.h-this.betweenThreshold&&(f="After")}if(g||c!=d||f!=e){d&&this._removeItemClass(d.rowNode,e);c&&this._addItemClass(c.rowNode,f);if(c)if(c==this.tree.rootNode&&"Over"!=f)b.canDrop(!1);else{d=!1;if(b.source==this)for(var h in this.selection)if(this.selection[h].item===c.item){d=!0;break}d?b.canDrop(!1):this.checkItemAcceptance(c.rowNode,b.source,f.toLowerCase())&&!this._isParentChildDrop(b.source,c.rowNode)?b.canDrop(!0):b.canDrop(!1)}else b.canDrop(!1);
this.targetAnchor=c;this.dropPosition=f}},onMouseMove:function(a){if(!(this.isDragging&&"Disabled"==this.targetState)){this.inherited(arguments);var g=k.manager();if(this.isDragging)this._onDragMouse(a);else if(this.mouseDown&&this.isSource&&(Math.abs(a.pageX-this._lastX)>=this.dragThreshold||Math.abs(a.pageY-this._lastY)>=this.dragThreshold)){var b=this.getSelectedTreeNodes();if(b.length){if(1<b.length){var d=this.selection,c=0,e=[],f,h;a:for(;f=b[c++];){for(h=f.getParent();h&&h!==this.tree;h=h.getParent())if(d[h.id])continue a;
e.push(f)}b=e}b=p.map(b,function(a){return a.domNode});g.startDrag(this,b,this.copyState(t.isCopyKey(a)));this._onDragMouse(a,!0)}}}},onMouseDown:function(a){this.mouseDown=!0;this.mouseButton=a.button;this._lastX=a.pageX;this._lastY=a.pageY;this.inherited(arguments)},onMouseUp:function(a){this.mouseDown&&(this.mouseDown=!1,this.inherited(arguments))},onMouseOut:function(){this.inherited(arguments);this._unmarkTargetAnchor()},checkItemAcceptance:function(){return!0},onDndSourceOver:function(a){this!=
a?(this.mouseDown=!1,this._unmarkTargetAnchor()):this.isDragging&&k.manager().canDrop(!1)},onDndStart:function(a,g,b){this.isSource&&this._changeState("Source",this==a?b?"Copied":"Moved":"");g=this.checkAcceptance(a,g);this._changeState("Target",g?"":"Disabled");this==a&&k.manager().overSource(this);this.isDragging=!0},itemCreator:function(a){return p.map(a,function(a){return{id:a.id,name:a.textContent||a.innerText||""}})},onDndDrop:function(a,g,b){if("Over"==this.containerState){var d=this.tree,
c=d.model,e=this.targetAnchor;this.isDragging=!1;var f,h,k;f=e&&e.item||d.item;"Before"==this.dropPosition||"After"==this.dropPosition?(f=e.getParent()&&e.getParent().item||d.item,h=e.getIndexInParent(),"After"==this.dropPosition?(h=e.getIndexInParent()+1,k=e.getNextSibling()&&e.getNextSibling().item):k=e.item):f=e&&e.item||d.item;var l;p.forEach(g,function(d,m){var n=a.getItem(d.id);if(-1!=p.indexOf(n.type,"treeNode"))var q=n.data,r=q.item,s=q.getParent().item;a==this?("number"==typeof h&&f==s&&
q.getIndexInParent()<h&&(h-=1),c.pasteItem(r,s,f,b,h,k)):c.isItem(r)?c.pasteItem(r,s,f,b,h,k):(l||(l=this.itemCreator(g,e.rowNode,a)),c.newItem(l[m],f,h,k))},this);this.tree._expandNode(e)}this.onDndCancel()},onDndCancel:function(){this._unmarkTargetAnchor();this.mouseDown=this.isDragging=!1;delete this.mouseButton;this._changeState("Source","");this._changeState("Target","")},onOverEvent:function(){this.inherited(arguments);k.manager().overSource(this)},onOutEvent:function(){this._unmarkTargetAnchor();
var a=k.manager();this.isDragging&&a.canDrop(!1);a.outSource(this);this.inherited(arguments)},_isParentChildDrop:function(a,g){if(!a.tree||a.tree!=this.tree)return!1;for(var b=a.tree.domNode,d=a.selection,c=g.parentNode;c!=b&&!d[c.id];)c=c.parentNode;return c.id&&d[c.id]},_unmarkTargetAnchor:function(){this.targetAnchor&&(this._removeItemClass(this.targetAnchor.rowNode,this.dropPosition),this.dropPosition=this.targetBox=this.targetAnchor=null)},_markDndStatus:function(a){this._changeState("Source",
a?"Copied":"Moved")}})});
//@ sourceMappingURL=dndSource.js.map