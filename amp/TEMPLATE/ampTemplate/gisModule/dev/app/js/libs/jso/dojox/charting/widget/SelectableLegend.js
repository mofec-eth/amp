//>>built
define("dojox/charting/widget/SelectableLegend",["dojo/_base/array","dojo/_base/declare","dojo/query","dojo/_base/connect","dojo/_base/Color","./Legend","dijit/form/CheckBox","../action2d/Highlight","dojox/lang/functional","dojox/gfx/fx","dojo/keys","dojo/dom-construct","dojo/dom-prop"],function(k,n,e,m,r,s,t,u,p,v,g,w,x){var y=n(null,{constructor:function(a){this.legend=a;this.index=0;this.horizontalLength=this._getHrizontalLength();k.forEach(a.legends,function(a,b){0<b&&e("input",a).attr("tabindex",-1)});this.firstLabel=
e("input",a.legends[0])[0];m.connect(this.firstLabel,"focus",this,function(){this.legend.active=!0});m.connect(this.legend.domNode,"keydown",this,"_onKeyEvent")},_getHrizontalLength:function(){var a=this.legend.horizontal;return"number"==typeof a?Math.min(a,this.legend.legends.length):a?this.legend.legends.length:1},_onKeyEvent:function(a){if(this.legend.active)if(a.keyCode==g.TAB)this.legend.active=!1;else{var c=this.legend.legends.length;switch(a.keyCode){case g.LEFT_ARROW:this.index--;0>this.index&&
(this.index+=c);break;case g.RIGHT_ARROW:this.index++;this.index>=c&&(this.index-=c);break;case g.UP_ARROW:0<=this.index-this.horizontalLength&&(this.index-=this.horizontalLength);break;case g.DOWN_ARROW:this.index+this.horizontalLength<c&&(this.index+=this.horizontalLength);break;default:return}this._moveToFocus();Event.stop(a)}},_moveToFocus:function(){e("input",this.legend.legends[this.index])[0].focus()}});return n("dojox.charting.widget.SelectableLegend",s,{outline:!1,transitionFill:null,transitionStroke:null,
postCreate:function(){this.legends=[];this.legendAnim={};this._cbs=[];this.inherited(arguments)},refresh:function(){this.legends=[];this._clearLabels();this.inherited(arguments);this._applyEvents();new y(this)},_clearLabels:function(){for(var a=this._cbs;a.length;)a.pop().destroyRecursive()},_addLabel:function(a,c){this.inherited(arguments);var b=e("td",this.legendBody),d=b[b.length-1];this.legends.push(d);b=new t({checked:!0});this._cbs.push(b);w.place(b.domNode,d,"first");d=e("label",d)[0];x.set(d,
"for",b.id)},_applyEvents:function(){this.chart.dirty||k.forEach(this.legends,function(a,c){var b,d=[],l,h;this._isPie()?(b=this.chart.stack[0],d.push(b.group.children[c]),l=b.name,h=this.chart.series[0].name):(b=this.chart.series[c],d=b.group.children,l=b.plot,h=b.name);var q={fills:p.map(d,"x.getFill()"),strokes:p.map(d,"x.getStroke()")};b=e(".dijitCheckBox",a)[0];m.connect(b,"onclick",this,function(b){this._toggle(d,c,a.vanished,q,h,l);a.vanished=!a.vanished;b.stopPropagation()});var z=e(".dojoxLegendIcon",
a)[0],f=this._getFilledShape(this._surfaces[c].children);k.forEach(["onmouseenter","onmouseleave"],function(b){m.connect(z,b,this,function(b){this._highlight(b,f,d,c,a.vanished,q,h,l)})},this)},this)},_toggle:function(a,c,b,d,e,h){k.forEach(a,function(a,c){var f=d.fills[c],e=this._getTransitionFill(h),l=d.strokes[c],k=this.transitionStroke;f&&(e&&("string"==typeof f||f instanceof r)?v.animateFill({shape:a,color:{start:b?e:f,end:b?f:e}}).play():a.setFill(b?f:e));l&&!this.outline&&a.setStroke(b?l:k)},
this)},_highlight:function(a,c,b,d,e,h,g,m){if(!e){var f=this._getAnim(m),n=this._isPie(),p="mouseenter"==a.type?"onmouseover":"mouseleave"==a.type?"onmouseout":"on"+a.type;f.process({shape:c,index:n?"legend"+d:"legend",run:{name:g},type:p});k.forEach(b,function(a,b){a.setFill(h.fills[b]);var c={shape:a,index:n?d:b,run:{name:g},type:p};f.duration=100;f.process(c)})}},_getAnim:function(a){this.legendAnim[a]||(this.legendAnim[a]=new u(this.chart,a),this.chart.getPlot(a).dirty=!1);return this.legendAnim[a]},
_getTransitionFill:function(a){return-1!=this.chart.stack[this.chart.plots[a]].declaredClass.indexOf("dojox.charting.plot2d.Stacked")?this.chart.theme.plotarea.fill:null},_getFilledShape:function(a){for(var c=0;a[c];){if(a[c].getFill())return a[c];c++}return null},_isPie:function(){return"dojox.charting.plot2d.Pie"==this.chart.stack[0].declaredClass},destroy:function(){this._clearLabels();this.inherited(arguments)}})});
//@ sourceMappingURL=SelectableLegend.js.map