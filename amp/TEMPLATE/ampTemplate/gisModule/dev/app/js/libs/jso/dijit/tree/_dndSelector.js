//>>built
define("dijit/tree/_dndSelector",["dojo/_base/array","dojo/_base/connect","dojo/_base/declare","dojo/_base/kernel","dojo/_base/lang","dojo/dom","dojo/mouse","dojo/on","dojo/touch","../a11yclick","./_dndContainer"],function(e,h,l,m,f,n,p,d,g,k,q){return l("dijit.tree._dndSelector",q,{constructor:function(){this.selection={};this.anchor=null;this.events.push(d(this.tree.domNode,g.press,f.hitch(this,"onMouseDown")),d(this.tree.domNode,g.release,f.hitch(this,"onMouseUp")),d(this.tree.domNode,g.move,f.hitch(this,
"onMouseMove")),d(this.tree.domNode,k.press,f.hitch(this,"onClickPress")),d(this.tree.domNode,k.release,f.hitch(this,"onClickRelease")))},singular:!1,getSelectedTreeNodes:function(){var a=[],b=this.selection,c;for(c in b)a.push(b[c]);return a},selectNone:function(){this.setSelection([]);return this},destroy:function(){this.inherited(arguments);this.selection=this.anchor=null},addTreeNode:function(a,b){this.setSelection(this.getSelectedTreeNodes().concat([a]));b&&(this.anchor=a);return a},removeTreeNode:function(a){var b=
e.filter(this.getSelectedTreeNodes(),function(b){return!n.isDescendant(b.domNode,a.domNode)});this.setSelection(b);return a},isTreeNodeSelected:function(a){return a.id&&!!this.selection[a.id]},setSelection:function(a){var b=this.getSelectedTreeNodes();e.forEach(this._setDifference(b,a),f.hitch(this,function(a){a.setSelected(!1);this.anchor==a&&delete this.anchor;delete this.selection[a.id]}));e.forEach(this._setDifference(a,b),f.hitch(this,function(a){a.setSelected(!0);this.selection[a.id]=a}));this._updateSelectionProperties()},
_setDifference:function(a,b){e.forEach(b,function(a){a.__exclude__=!0});var c=e.filter(a,function(a){return!a.__exclude__});e.forEach(b,function(a){delete a.__exclude__});return c},_updateSelectionProperties:function(){var a=this.getSelectedTreeNodes(),b=[],c=[],f=[];e.forEach(a,function(a){var d=a.getTreePath(),g=this.tree.model;c.push(a);b.push(d);d=e.map(d,function(a){return g.getIdentity(a)},this);f.push(d.join("/"))},this);a=e.map(c,function(a){return a.item});this.tree._set("paths",b);this.tree._set("path",
b[0]||[]);this.tree._set("selectedNodes",c);this.tree._set("selectedNode",c[0]||null);this.tree._set("selectedItems",a);this.tree._set("selectedItem",a[0]||null)},onClickPress:function(a){if(!this.current||!this.current.isExpandable||!this.tree.isExpandoNode(a.target,this.current)){p.isLeft(a)&&a.preventDefault();var b="keydown"==a.type?this.tree.focusedChild:this.current;if(b){var c=h.isCopyKey(a),d=b.id;!this.singular&&!a.shiftKey&&this.selection[d]?this._doDeselect=!0:(this._doDeselect=!1,this.userSelect(b,
c,a.shiftKey))}}},onClickRelease:function(a){this._doDeselect&&(this._doDeselect=!1,this.userSelect("keyup"==a.type?this.tree.focusedChild:this.current,h.isCopyKey(a),a.shiftKey))},onMouseMove:function(){this._doDeselect=!1},onMouseDown:function(){},onMouseUp:function(){},_compareNodes:function(a,b){if(a===b)return 0;if("sourceIndex"in document.documentElement)return a.sourceIndex-b.sourceIndex;if("compareDocumentPosition"in document.documentElement)return a.compareDocumentPosition(b)&2?1:-1;if(document.createRange){var c=
doc.createRange();c.setStartBefore(a);var d=doc.createRange();d.setStartBefore(b);return c.compareBoundaryPoints(c.END_TO_END,d)}throw Error("dijit.tree._compareNodes don't know how to compare two different nodes in this browser");},userSelect:function(a,b,c){if(this.singular)this.anchor==a&&b?this.selectNone():(this.setSelection([a]),this.anchor=a);else if(c&&this.anchor){b=this._compareNodes(this.anchor.rowNode,a.rowNode);c=this.anchor;0>b?b=c:(b=a,a=c);for(c=[];b!=a;)c.push(b),b=this.tree._getNext(b);
c.push(a);this.setSelection(c)}else this.selection[a.id]&&b?this.removeTreeNode(a):b?this.addTreeNode(a,!0):(this.setSelection([a]),this.anchor=a)},getItem:function(a){return{data:this.selection[a],type:["treeNode"]}},forInSelectedItems:function(a,b){b=b||m.global;for(var c in this.selection)a.call(b,this.getItem(c),c,this)}})});
//@ sourceMappingURL=_dndSelector.js.map