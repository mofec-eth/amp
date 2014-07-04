//>>built
define("dojox/mobile/_EditableListMixin",["dojo/_base/array","dojo/_base/connect","dojo/_base/declare","dojo/_base/event","dojo/_base/window","dojo/dom-class","dojo/dom-geometry","dojo/dom-style","dojo/touch","dojo/dom-attr","dijit/registry","./ListItem"],function(e,l,p,q,r,f,h,m,k,d,n,s){return p("dojox.mobile._EditableListMixin",null,{rightIconForEdit:"mblDomButtonGrayKnob",deleteIconForEdit:"mblDomButtonRedCircleMinus",isEditing:!1,destroy:function(){this._blankItem&&this._blankItem.destroy();this.inherited(arguments)},
_setupMoveItem:function(a){m.set(a,{width:h.getContentBox(a).w+"px",top:a.offsetTop+"px"});f.add(a,"mblListItemFloat")},_resetMoveItem:function(a){this.defer(function(){f.remove(a,"mblListItemFloat");m.set(a,{width:"",top:""})})},_onClick:function(a){if((!a||!("keydown"===a.type&&13!==a.keyCode))&&!1!==this.onClick(a)){var b=n.getEnclosingWidget(a.target);for(a=a.target;a!==b.domNode;a=a.parentNode)if(a===b.deleteIconNode){l.publish("/dojox/mobile/deleteListItem",[b]);this.onDeleteItem(b);break}}},
onClick:function(){},_onTouchStart:function(a){if(!(1>=this.getChildren().length)){this._blankItem||(this._blankItem=new s);var b=this._movingItem=n.getEnclosingWidget(a.target);this._startIndex=this.getIndexOfChild(b);for(var c=!1,g=a.target;g!==b.domNode;g=g.parentNode)if(g===b.rightIconNode){c=!0;d.set(b.rightIconNode,"aria-grabbed","true");d.set(this.domNode,"aria-dropeffect","move");break}c&&(c=(c=b.getNextSibling())?c.domNode:null,this.containerNode.insertBefore(this._blankItem.domNode,c),this._setupMoveItem(b.domNode),
this.containerNode.appendChild(b.domNode),this._conn||(this._conn=[this.connect(this.domNode,k.move,"_onTouchMove"),this.connect(r.doc,k.release,"_onTouchEnd")]),this._pos=[],e.forEach(this.getChildren(),function(a,b){this._pos.push(h.position(a.domNode,!0).y)},this),this.touchStartY=a.touches?a.touches[0].pageY:a.pageY,this._startTop=h.getMarginBox(b.domNode).t,q.stop(a))}},_onTouchMove:function(a){a=a.touches?a.touches[0].pageY:a.pageY;for(var b=this._pos.length-1,c=1;c<this._pos.length;c++)if(a<
this._pos[c]){b=c-1;break}b=this.getChildren()[b];c=this._blankItem;if(b!==c){var d=b.domNode.parentNode;b.getIndexInParent()<c.getIndexInParent()?d.insertBefore(c.domNode,b.domNode):d.insertBefore(b.domNode,c.domNode)}this._movingItem.domNode.style.top=this._startTop+(a-this.touchStartY)+"px"},_onTouchEnd:function(a){a=this._startIndex;var b=this.getIndexOfChild(this._blankItem),c=this._blankItem.getNextSibling(),c=c?c.domNode:null;null===c&&b--;this.containerNode.insertBefore(this._movingItem.domNode,
c);this.containerNode.removeChild(this._blankItem.domNode);this._resetMoveItem(this._movingItem.domNode);e.forEach(this._conn,l.disconnect);this._conn=null;this.onMoveItem(this._movingItem,a,b);d.set(this._movingItem.rightIconNode,"aria-grabbed","false");d.remove(this.domNode,"aria-dropeffect")},startEdit:function(){this.isEditing=!0;f.add(this.domNode,"mblEditableRoundRectList");e.forEach(this.getChildren(),function(a){a.deleteIconNode||(a.set("rightIcon",this.rightIconForEdit),a.rightIconNode&&
(d.set(a.rightIconNode,"role","button"),d.set(a.rightIconNode,"aria-grabbed","false")),a.set("deleteIcon",this.deleteIconForEdit),a.deleteIconNode.tabIndex=a.tabIndex,a.deleteIconNode&&d.set(a.deleteIconNode,"role","button"));a.rightIconNode.style.display="";a.deleteIconNode.style.display="";"undefined"!=typeof a.rightIconNode.style.msTouchAction&&(a.rightIconNode.style.msTouchAction="none")},this);this._handles||(this._handles=[this.connect(this.domNode,k.press,"_onTouchStart"),this.connect(this.domNode,
"onclick","_onClick"),this.connect(this.domNode,"onkeydown","_onClick")]);this.onStartEdit()},endEdit:function(){f.remove(this.domNode,"mblEditableRoundRectList");e.forEach(this.getChildren(),function(a){a.rightIconNode.style.display="none";a.deleteIconNode.style.display="none";"undefined"!=typeof a.rightIconNode.style.msTouchAction&&(a.rightIconNode.style.msTouchAction="auto")});this._handles&&(e.forEach(this._handles,this.disconnect,this),this._handles=null);this.isEditing=!1;this.onEndEdit()},
onDeleteItem:function(a){},onMoveItem:function(a,b,c){},onStartEdit:function(){},onEndEdit:function(){}})});
//@ sourceMappingURL=_EditableListMixin.js.map