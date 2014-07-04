//>>built
define("dojox/charting/action2d/MouseIndicator",["dojo/_base/lang","dojo/_base/declare","dojo/_base/connect","dojo/_base/window","dojo/sniff","./ChartAction","./_IndicatorElement","dojox/lang/utils","dojo/_base/event","dojo/_base/array"],function(f,g,b,c,d,h,k,e,l,m){return g("dojox.charting.action2d.MouseIndicator",h,{defaultParams:{series:"",vertical:!0,autoScroll:!0,fixed:!0,precision:0,lines:!0,labels:!0,markers:!0},optionalParams:{lineStroke:{},outlineStroke:{},shadowStroke:{},lineFill:{},stroke:{},outline:{},
shadow:{},fill:{},fillFunc:null,labelFunc:null,font:"",fontColor:"",markerStroke:{},markerOutline:{},markerShadow:{},markerFill:{},markerSymbol:"",offset:{},start:!1,mouseOver:!1},constructor:function(a,b,c){this.opt=f.clone(this.defaultParams);e.updateWithObject(this.opt,c);e.updateWithPattern(this.opt,c,this.optionalParams);this._listeners=this.opt.mouseOver?[{eventName:"onmousemove",methodName:"onMouseMove"}]:[{eventName:"onmousedown",methodName:"onMouseDown"}];this._uName="mouseIndicator"+this.opt.series;
this._handles=[];this.connect()},_disconnectHandles:function(){d("ie")&&this.chart.node.releaseCapture();m.forEach(this._handles,b.disconnect);this._handles=[]},connect:function(){this.inherited(arguments);this.chart.addPlot(this._uName,{type:k,inter:this})},disconnect:function(){if(this._isMouseDown)this.onMouseUp();this.chart.removePlot(this._uName);this.inherited(arguments);this._disconnectHandles()},onChange:function(a){},onMouseDown:function(a){this._isMouseDown=!0;d("ie")?(this._handles.push(b.connect(this.chart.node,
"onmousemove",this,"onMouseMove")),this._handles.push(b.connect(this.chart.node,"onmouseup",this,"onMouseUp")),this.chart.node.setCapture()):(this._handles.push(b.connect(c.doc,"onmousemove",this,"onMouseMove")),this._handles.push(b.connect(c.doc,"onmouseup",this,"onMouseUp")));this._onMouseSingle(a)},onMouseMove:function(a){(this._isMouseDown||this.opt.mouseOver)&&this._onMouseSingle(a)},_onMouseSingle:function(a){var b=this.chart.getPlot(this._uName);b.pageCoord={x:a.pageX,y:a.pageY};b.dirty=!0;
this.chart.render();l.stop(a)},onMouseUp:function(a){a=this.chart.getPlot(this._uName);a.stopTrack();this._isMouseDown=!1;this._disconnectHandles();a.pageCoord=null;a.dirty=!0;this.chart.render()}})});
//@ sourceMappingURL=MouseIndicator.js.map