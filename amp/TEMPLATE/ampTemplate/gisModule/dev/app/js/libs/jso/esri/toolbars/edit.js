//>>built
define("esri/toolbars/edit",["require","dojo/_base/declare","dojo/_base/lang","dojo/_base/connect","dojo/_base/array","dojo/_base/Color","dojo/has","dojo/dom-construct","dojo/dom-style","esri/kernel","esri/lang","esri/sniff","esri/toolbars/_toolbar","esri/toolbars/_Box","esri/toolbars/_GraphicMover","esri/toolbars/_VertexEditor","esri/toolbars/TextEditor","esri/symbols/SimpleMarkerSymbol","esri/symbols/SimpleLineSymbol","esri/symbols/TextSymbol","esri/graphic"],function(v,w,m,l,k,g,n,t,x,y,u,G,z,A,B,C,D,h,f,E,F){var d=w(z,{declaredClass:"esri.toolbars.Edit",
constructor:function(a,b){this._map=a;this._tool=0;if(this._map.loaded)this._scratchGL=a.graphics;else var e=l.connect(this._map,"onLoad",this,function(){l.disconnect(e);e=null;this._scratchGL=this._map.graphics});var c=n("esri-touch")||n("esri-pointer");this._defaultOptions=m.mixin({vertexSymbol:new h(h.STYLE_CIRCLE,c?20:12,new f(f.STYLE_SOLID,new g([0,0,0,0.5]),1),new g([128,128,128])),ghostVertexSymbol:new h(h.STYLE_CIRCLE,c?18:10,new f(f.STYLE_SOLID,new g([0,0,0,0.5]),1),new g([255,255,255,0.75])),
ghostLineSymbol:new f(f.STYLE_DOT,new g([128,128,128]),2),allowDeleteVertices:!0,allowAddVertices:!0,rotateHandleOffset:c?24:16,boxLineSymbol:new f(f.STYLE_DASH,new g([64,64,64]),1),boxHandleSymbol:new h(h.STYLE_SQUARE,c?16:9,new f(f.STYLE_SOLID,new g([0,0,0,0.5]),1),new g([255,255,255,0.75])),textAnchorSymbol:new h(h.STYLE_CIRCLE,10,null,new g([255,0,0]))},b||{})},activate:function(a,b,e){this.deactivate();this._graphic=b;this._options=m.mixin(m.mixin({},this._defaultOptions),e||{});var c=d.MOVE;
e=d.EDIT_VERTICES;var f=d.SCALE,p=d.ROTATE,g=d.EDIT_TEXT,h=!1,k=!1,n=!1,q=this._map,r=q.spatialReference,s=b.geometry.spatialReference;this._geo=!(!r||!s||r.equals(s)||!(r.isWebMercator()&&4326===s.wkid));this._isTextPoint=this._prepareTextSymbolEditing(b,a);(a&c)===c&&(h=this._enableMove(b));c=(a&f)===f;p=(a&p)===p;if(c||p)n=this._enableBoxEditing(b,c,p);(a&e)===e&&(k=this._enableVertexEditing(b));(a&g)===g&&this._enableTextEditing(b);if(!h&&!k&&!n)throw Error("[esri.toolbars.Edit::activate] Unable to activate the tool. Check if the tool is valid for the given geometry type.");
if(this._tool=a)this._mapPanEndHandle=l.connect(q,"onPanEnd",this,this._mapPanEndHandler),this._mapExtChgHandle=l.connect(q,"onExtentChange",this,this._mapExtentChangeHandler),this.onActivate(this._tool,b);q.snappingManager&&(h||k)&&q.snappingManager._startSelectionLayerQuery()},deactivate:function(){this._isTextPoint=null;var a=this._tool,b=this._graphic;if(a){var e=!!this._modified;this._clear();l.disconnect(this._mapPanEndHandle);l.disconnect(this._mapExtChgHandle);this._graphic=this._geo=this._mapPanEndHandle=
this._mapExtChgHandle=null;this.onDeactivate(a,b,{isModified:e});this._map.snappingManager&&this._map.snappingManager._stopSelectionLayerQuery()}},refresh:function(){this._refreshMoveables(!0)},getCurrentState:function(){return{tool:this._tool,graphic:this._graphic,isModified:!!this._modified}},onActivate:function(a,b){},onDeactivate:function(a,b,e){},onGraphicMoveStart:function(a){},onGraphicFirstMove:function(a){this._modified=!0},onGraphicMove:function(a,b){},onGraphicMoveStop:function(a,b){},
onGraphicClick:function(a,b){},onVertexMoveStart:function(a,b){},onVertexFirstMove:function(a,b){this._modified=!0},onVertexMove:function(a,b,e){},onVertexMoveStop:function(a,b,e){},onVertexAdd:function(a,b){this._modified=!0},onVertexClick:function(a,b){},onVertexMouseOver:function(a,b){},onVertexMouseOut:function(a,b){},onVertexDelete:function(a,b){this._modified=!0},onTextEditStart:function(a,b){},onTextEditEnd:function(a){},onScaleStart:function(a){},onScaleFirstMove:function(a){this._modified=
!0},onScale:function(a,b){},onScaleStop:function(a,b){},onRotateStart:function(a){},onRotateFirstMove:function(a){this._modified=!0},onRotate:function(a,b){},onRotateStop:function(a,b){},_eventMap:{activate:["tool","graphic"],deactivate:["tool","graphic","info"],"graphic-move-start":["graphic"],"graphic-first-move":["graphic"],"graphic-move":["graphic","transform"],"graphic-move-stop":["graphic","transform"],"graphic-click":["graphic","info"],"vertex-move-start":["graphic","vertexinfo"],"vertex-first-move":["graphic",
"vertexinfo"],"vertex-move":["graphic","vertexinfo","transform"],"vertex-move-stop":["graphic","vertexinfo","transform"],"vertex-add":["graphic","vertexinfo"],"vertex-click":["graphic","vertexinfo"],"vertex-mouse-over":["graphic","vertexinfo"],"vertex-mouse-out":["graphic","vertexinfo"],"vertex-delete":["graphic","vertexinfo"],"scale-start":["graphic"],"scale-first-move":["graphic"],scale:["graphic","info"],"scale-stop":["graphic","info"],"rotate-start":["graphic"],"rotate-first-move":["graphic"],
rotate:["graphic","info"],"rotate-stop":["graphic","info"]},_prepareTextSymbolEditing:function(a,b){if("point"===a.geometry.type||"multipoint"===a.geometry.type){var e=a.symbol||a.getLayer()._getSymbol(a);if("textsymbol"===e.type){if((b&d.SCALE)===d.SCALE||(b&d.ROTATE)===d.ROTATE||(b&d.EDIT_TEXT)===d.EDIT_TEXT){a.setSymbol(new E(e.toJson()));var c=this;if(this._textSymbolEditor)this._textSymbolEditor.createForm(a),this._textSymbolEditor.show();else if(this._options&&this._options.textSymbolEditor)this._textSymbolEditor=
this._options.textSymbolEditor,this._textSymbolEditor.on("symbol-change",function(){c._boxEditor&&c._boxEditor.refresh()});else{var f;f=this._options.textSymbolEditorHolder?t.create("div",null,this._options.textSymbolEditorHolder):t.create("div",null,this._map.root);v(["esri/dijit/SymbolEditor"],function(b){c._textSymbolEditor=new b({graphic:a},f);b=c._textSymbolEditor.domNode.parentNode.id;x.set(c._textSymbolEditor.domNode,{position:"map_root"===b?"absolute":"relative",left:"map_root"===b?c._map.width/
2-100+"px":"5px",top:"20px","z-index":50});c._textSymbolEditor.startup();c._textSymbolEditor.createForm(a);c._textSymbolEditor.show();c._textSymbolEditor.on("symbol-change",function(){c._boxEditor&&c._boxEditor.refresh()})})}}if((b&d.MOVE)===d.MOVE||(b&d.ROTATE)===d.ROTATE||(b&d.SCALE)===d.SCALE)this._textAnchor=new F(a.geometry,this._options.textAnchorSymbol),this._scratchGL.add(this._textAnchor);return!0}}return!1},_enableMove:function(a){var b=this._map;switch(a.geometry.type){case "point":case "polyline":case "polygon":return this._graphicMover=
new B(a,b,this,this._textAnchor),!0}return!1},_enableVertexEditing:function(a){var b=this._map;switch(a.geometry.type){case "multipoint":case "polyline":case "polygon":return this._vertexEditor=C.create(a,b,this),!0}return!1},_enableBoxEditing:function(a,b,e){var c=this._map,d=a.geometry.type;return"polyline"===d||"polygon"===d||this._isTextPoint?(this._boxEditor=new A(a,c,this,b,e,this._options.uniformScaling,this._isTextPoint),!0):!1},_enableTextEditing:function(a){return this._isTextPoint?(this._textEditor=
new D(a,this._map,this),l.connect(this._textEditor,"onEditStart",m.hitch(this,function(){this._textAnchor&&(this._textAnchor.getLayer().remove(this._textAnchor),this._textAnchor=null);this._disableMove();this._disableBoxEditing()})),!0):!1},_disableMove:function(){var a=this._graphicMover;a&&(a.destroy(),this._graphicMover=null)},_disableVertexEditing:function(){var a=this._vertexEditor;a&&(a.destroy(),this._vertexEditor=null)},_disableBoxEditing:function(){var a=this._boxEditor;a&&(a.destroy(),this._boxEditor=
null)},_disableTextEditing:function(){this._textEditor&&(this._textEditor.destroy(),this._textEditor=null)},_disableSymbolEditing:function(){this._textSymbolEditor&&this._textSymbolEditor.hide()},_clear:function(){this._disableMove();this._disableVertexEditing();this._disableBoxEditing();this._disableTextEditing();this._disableSymbolEditing();this._textAnchor&&(this._textAnchor.getLayer().remove(this._textAnchor),this._textAnchor=null);this._tool=0;this._modified=!1},_mapPanEndHandler:function(){this._refreshMoveables()},
_mapExtentChangeHandler:function(a,b,d){d&&this._refreshMoveables()},_refreshMoveables:function(a){var b=k.filter([this._graphicMover,this._vertexEditor,this._boxEditor],u.isDefined);k.forEach(b,function(b){b.refresh(a)})},_beginOperation:function(a){k.forEach(this._getAffectedTools(a),function(a){a.suspend()})},_endOperation:function(a){k.forEach(this._getAffectedTools(a),function(a){a.resume()})},_getAffectedTools:function(a){var b=[];switch(a){case "MOVE":b=[this._vertexEditor,this._boxEditor];
break;case "VERTICES":b=[this._boxEditor];break;case "BOX":b=[this._vertexEditor]}return b=k.filter(b,u.isDefined)}});m.mixin(d,{MOVE:1,EDIT_VERTICES:2,SCALE:4,ROTATE:8,EDIT_TEXT:16});n("extend-esri")&&m.setObject("toolbars.Edit",d,y);return d});
//@ sourceMappingURL=edit.js.map