//>>built
define("esri/map",["require","dojo/_base/kernel","dojo/_base/declare","dojo/_base/connect","dojo/_base/lang","dojo/_base/array","dojo/_base/event","dojo/on","dojo/aspect","dojo/dom","dojo/dom-class","dojo/dom-construct","dojo/dom-geometry","dojo/dom-style","dijit/registry","esri/kernel","esri/config","esri/sniff","esri/lang","esri/_coremap","esri/MapNavigationManager"],function(x,r,K,B,p,s,C,D,L,E,h,F,M,N,O,u,G,d,t,P,Q){var y={up:"panUp",right:"panRight",down:"panDown",left:"panLeft"},H={upperRight:"panUpperRight",lowerRight:"panLowerRight",
lowerLeft:"panLowerLeft",upperLeft:"panUpperLeft"},g=B.connect,m=B.disconnect,n=F.create,q=N.set,z=p.hitch,w=M.getMarginBox,I=r.deprecated,A=p.mixin,J=0;r=K(P,{declaredClass:"esri.Map",constructor:function(a,c){A(this,{_slider:null,_navDiv:null,_mapParams:A({attributionWidth:0.45,slider:!0,nav:!1,logo:!0,sliderStyle:"small",sliderPosition:"top-left",sliderOrientation:"vertical",autoResize:!0},c||{})});A(this,{isDoubleClickZoom:!1,isShiftDoubleClickZoom:!1,isClickRecenter:!1,isScrollWheelZoom:!1,isPan:!1,
isRubberBandZoom:!1,isKeyboardNavigation:!1,isPanArrows:!1,isZoomSlider:!1});p.isFunction(u._css)&&(u._css=u._css(this._mapParams.force3DTransforms),this.force3DTransforms=this._mapParams.force3DTransforms);var b=d("esri-transforms")&&d("esri-transitions");this.navigationMode=this._mapParams.navigationMode||b&&"css-transforms"||"classic";"css-transforms"===this.navigationMode&&!b&&(this.navigationMode="classic");this.fadeOnZoom=t.isDefined(this._mapParams.fadeOnZoom)?this._mapParams.fadeOnZoom:"css-transforms"===
this.navigationMode;"css-transforms"!==this.navigationMode&&(this.fadeOnZoom=!1);this.setMapCursor("default");this.smartNavigation=c&&c.smartNavigation;if(!t.isDefined(this.smartNavigation)&&d("mac")&&!d("esri-touch")&&!d("esri-pointer")&&!(3.5>=d("ff"))){var e=navigator.userAgent.match(/Mac\s+OS\s+X\s+([\d]+)(\.|\_)([\d]+)\D/i);e&&(t.isDefined(e[1])&&t.isDefined(e[3]))&&(b=parseInt(e[1],10),e=parseInt(e[3],10),this.smartNavigation=10<b||10===b&&6<=e)}this.showAttribution=t.isDefined(this._mapParams.showAttribution)?
this._mapParams.showAttribution:!0;this._onLoadHandler_connect=g(this,"onLoad",this,"_onLoadInitNavsHandler");var k=n("div",{"class":"esriControlsBR"+(this._mapParams.nav?" withPanArrows":"")},this.root);if(this.showAttribution)if(b=p.getObject("esri.dijit.Attribution",!1))this._initAttribution(b,k);else{var l=J++,f=this;this._rids&&this._rids.push(l);x(["esri/dijit/Attribution"],function(a){var b=f._rids?s.indexOf(f._rids,l):-1;-1!==b&&(f._rids.splice(b,1),f._initAttribution(a,k))})}this._mapParams.logo&&
(b={},6===d("ie")&&(b.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled\x3d'true', sizingMethod\x3d'crop', src\x3d'"+x.toUrl("esri")+"/images/map/logo-med.png')"),b=this._ogol=n("div",{style:b},k),25E4>this.root.clientWidth*this.root.clientHeight?h.add(b,"logo-sm"):h.add(b,"logo-med"),!d("esri-touch")&&!d("esri-pointer")&&(this._ogol_connect=g(b,"onclick",this,"_openLogoLink")));this.navigationManager=new Q(this);c&&c.basemap&&(this._onLoadFix=!0,this.setBasemap(c.basemap),this._onLoadFix=
!1);if(this.autoResize=this._mapParams.autoResize)b=(b=O.getEnclosingWidget(this.container))&&b.resize?b:window,e=z(this,this.resize),this._rszSignal=D.pausable(b,"resize",e),this._oriSignal=D.pausable(window,"orientationchange",e),L.after(b,"resize",e,!0)},_initAttribution:function(a,c){var b=n("span",{"class":"esriAttribution"},c,"first");q(b,"maxWidth",Math.floor(this.width*this._mapParams.attributionWidth)+"px");this._connects.push(g(b,"onclick",function(){h.contains(this,"esriAttributionOpen")?
h.remove(this,"esriAttributionOpen"):this.scrollWidth>this.clientWidth&&h.add(this,"esriAttributionOpen")}));this.attribution=new a({map:this},b)},_cleanUp:function(){this.disableMapNavigation();this.navigationManager.destroy();var a=this._slider;a&&(a.destroy&&!a._destroyed)&&a.destroy();var a=this._navDiv,c=this.attribution;a&&F.destroy(a);c&&c.destroy();this._connects.push(this._slider_connect,this._ogol_connect,this._rszSignal,this._oriSignal);s.forEach(this._connects,m);this.attribution=this.navigationManager=
this._rids=this._connects=this._slider_connect=this._ogol_connect=this._rszSignal=this._oriSignal=null;this.inherited("_cleanUp",arguments)},_isPanningOrZooming:function(){return this.__panning||this.__zooming},_canZoom:function(a){var c=this.getLevel();return!this.__tileInfo||!(c===this.getMinZoom()&&0>a||c===this.getMaxZoom()&&0<a)},_onLoadInitNavsHandler:function(){this.enableMapNavigation();this._createNav();if("small"===this._mapParams.sliderStyle||!this._createSlider)this._createSimpleSlider();
else if(this._mapParams.slider){var a=-1!==this._getSliderClass(!0).indexOf("Horizontal"),a=[a?"dijit.form.HorizontalSlider":"dijit.form.VerticalSlider",a?"dijit.form.HorizontalRule":"dijit.form.VerticalRule",a?"dijit.form.HorizontalRuleLabels":"dijit.form.VerticalRuleLabels"];if(s.some(a,function(a){return!p.getObject(a,!1)})){var a=s.map(a,function(a){return a.replace(/\./g,"/")}),c=J++,b=this;this._rids&&this._rids.push(c);x(a,function(){var a=b._rids?s.indexOf(b._rids,c):-1;-1!==a&&(b._rids.splice(a,
1),b._createSlider.apply(b,arguments))})}else a=s.map(a,function(a){return p.getObject(a,!1)}),this._createSlider.apply(this,a)}m(this._onLoadHandler_connect)},_createNav:function(){if(this._mapParams.nav){var a,c,b,e=h.add,k=this.id;this._navDiv=n("div",{id:k+"_navdiv"},this.root);e(this._navDiv,"navDiv");var l=this.width/2,f=this.height/2,d;for(b in y)c=y[b],a=n("div",{id:k+"_pan_"+b},this._navDiv),e(a,"fixedPan "+c),"up"===b||"down"===b?(d=parseInt(w(a).w,10)/2,q(a,{left:l-d+"px",zIndex:30})):
(d=parseInt(w(a).h,10)/2,q(a,{top:f-d+"px",zIndex:30})),this._connects.push(g(a,"onclick",z(this,this[c])));this._onMapResizeNavHandler_connect=g(this,"onResize",this,"_onMapResizeNavHandler");for(b in H)c=H[b],a=n("div",{id:k+"_pan_"+b,style:{zIndex:30}},this._navDiv),e(a,"fixedPan "+c),this._connects.push(g(a,"onclick",z(this,this[c])));this.isPanArrows=!0}},_onMapResizeNavHandler:function(a,c,b){a=this.id;c/=2;b/=2;var e=E.byId,k,d,f;for(k in y)d=e(a+"_pan_"+k),"up"===k||"down"===k?(f=parseInt(w(d).w,
10)/2,q(d,"left",c-f+"px")):(f=parseInt(w(d).h,10)/2,q(d,"top",b-f+"px"))},_createSimpleSlider:function(){if(this._mapParams.slider){var a=this._slider=n("div",{id:this.id+"_zoom_slider","class":this._getSliderClass(),style:{zIndex:30}}),c=d("esri-touch")&&!d("ff")?"touchstart":d("esri-pointer")?navigator.msPointerEnabled?"MSPointerDown":"pointerdown":"onclick",b=n("div",{"class":"esriSimpleSliderIncrementButton"},a),e=n("div",{"class":"esriSimpleSliderDecrementButton"},a);this._incButton=b;this._decButton=
e;b.innerHTML="+";e.innerHTML="\x26ndash;";8>d("ie")&&h.add(e,"dj_ie67Fix");this._connects.push(g(b,c,this,this._simpleSliderChangeHandler));this._connects.push(g(e,c,this,this._simpleSliderChangeHandler));"touchstart"==c&&(this._connects.push(g(b,"onclick",this,this._simpleSliderChangeHandler)),this._connects.push(g(e,"onclick",this,this._simpleSliderChangeHandler)));(-1<this.getMaxZoom()||-1<this.getMinZoom())&&this._connects.push(g(this,"onZoomEnd",this,this._simpleSliderZoomHandler));10>d("ie")&&
E.setSelectable(a,!1);this.root.appendChild(a);this.isZoomSlider=!0}},_simpleSliderChangeHandler:function(a){C.stop(a);a=-1!==a.currentTarget.className.indexOf("IncrementButton")?!0:!1;this._extentUtil({numLevels:a?1:-1})},_simpleSliderZoomHandler:function(a,c,b,e){var d;a=this._incButton;c=this._decButton;e===this.getMaxZoom()?d=a:e===this.getMinZoom()&&(d=c);d?(h.add(d,"esriSimpleSliderDisabledButton"),h.remove(d===a?c:a,"esriSimpleSliderDisabledButton")):(h.remove(a,"esriSimpleSliderDisabledButton"),
h.remove(c,"esriSimpleSliderDisabledButton"))},_getSliderClass:function(a){a=a?"Large":"Simple";var c=this._mapParams.sliderOrientation,b=this._mapParams.sliderPosition||"",c=c&&"horizontal"===c.toLowerCase()?"esri"+a+"SliderHorizontal":"esri"+a+"SliderVertical";if(b)switch(b.toLowerCase()){case "top-left":b="esri"+a+"SliderTL";break;case "top-right":b="esri"+a+"SliderTR";break;case "bottom-left":b="esri"+a+"SliderBL";break;case "bottom-right":b="esri"+a+"SliderBR"}return"esri"+a+"Slider "+c+" "+
b},_createSlider:function(a,c,b){if(this._mapParams.slider){var e=n("div",{id:this.id+"_zoom_slider"},this.root),k=G.defaults.map,l=this._getSliderClass(!0),f=-1!==l.indexOf("Horizontal");-1!==l.indexOf("SliderTL")||l.indexOf("SliderBL");-1!==l.indexOf("SliderTL")||l.indexOf("SliderTR");var h=this.getNumLevels();if(0<h){var m,p,v=this._mapParams.sliderLabels,t=!!v;if(k=!1!==v){var r=f?"bottomDecoration":"rightDecoration";if(!v){v=[];for(f=0;f<h;f++)v[f]=""}s.forEach([{"class":"esriLargeSliderTicks",
container:r,count:h,dijitClass:c},{"class":t&&"esriLargeSliderLabels",container:r,count:h,labels:v,dijitClass:b}],function(a){var b=n("div"),d=a.dijitClass;delete a.dijitClass;e.appendChild(b);d===c?m=new d(a,b):p=new d(a,b)})}a=this._slider=new a({id:e.id,"class":l,minimum:this.getMinZoom(),maximum:this.getMaxZoom(),discreteValues:h,value:this.getLevel(),clickSelect:!0,intermediateChanges:!0,style:"z-index:30;"},e);a.startup();k&&(m.startup(),p.startup());this._slider_connect=g(a,"onChange",this,
"_onSliderChangeHandler");this._connects.push(g(this,"onExtentChange",this,"_onExtentChangeSliderHandler"));this._connects.push(g(a._movable,"onFirstMove",this,"_onSliderMoveStartHandler"))}else{a=this._slider=new a({id:e.id,"class":l,minimum:0,maximum:2,discreteValues:3,value:1,clickSelect:!0,intermediateChanges:k.sliderChangeImmediate,style:"height:50px; z-index:30;"},e);b=a.domNode.firstChild.childNodes;for(f=1;3>=f;f++)q(b[f],"visibility","hidden");a.startup();this._slider_connect=g(a,"onChange",
this,"_onDynSliderChangeHandler");this._connects.push(g(this,"onExtentChange",this,"_onExtentChangeDynSliderHandler"))}b=a.decrementButton;a.incrementButton.style.outline="none";b.style.outline="none";a.sliderHandle.style.outline="none";a._onKeyPress=function(){};if(a=a._movable){var u=a.onMouseDown;a.onMouseDown=function(a){9>d("ie")&&1!==a.button||u.apply(this,arguments)}}this.isZoomSlider=!0}},_onSliderMoveStartHandler:function(){m(this._slider_connect);m(this._slidermovestop_connect);this._slider_connect=
g(this._slider,"onChange",this,"_onSliderChangeDragHandler");this._slidermovestop_connect=g(this._slider._movable,"onMoveStop",this,"_onSliderMoveEndHandler")},_onSliderChangeDragHandler:function(a){this._extentUtil({targetLevel:a})},_onSliderMoveEndHandler:function(){m(this._slider_connect);m(this._slidermovestop_connect)},_onSliderChangeHandler:function(a){this.setLevel(a)},_updateSliderValue:function(a,c){m(this._slider_connect);var b=this._slider,d=b._onChangeActive;b._onChangeActive=!1;b.set("value",
a);b._onChangeActive=d;this._slider_connect=g(b,"onChange",this,c)},_onExtentChangeSliderHandler:function(a,c,b,d){m(this._slidermovestop_connect);this._updateSliderValue(d.level,"_onSliderChangeHandler")},_onDynSliderChangeHandler:function(a){this._extentUtil({numLevels:0<a?1:-1})},_onExtentChangeDynSliderHandler:function(){this._updateSliderValue(1,"_onDynSliderChangeHandler")},_openLogoLink:function(a){window.open(G.defaults.map.logoLink,"_blank");C.stop(a)},enableMapNavigation:function(){this.navigationManager.enableNavigation()},
disableMapNavigation:function(){this.navigationManager.disableNavigation()},enableDoubleClickZoom:function(){this.isDoubleClickZoom||(this.navigationManager.enableDoubleClickZoom(),this.isDoubleClickZoom=!0)},disableDoubleClickZoom:function(){this.isDoubleClickZoom&&(this.navigationManager.disableDoubleClickZoom(),this.isDoubleClickZoom=!1)},enableShiftDoubleClickZoom:function(){this.isShiftDoubleClickZoom||(I(this.declaredClass+": Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.",
null,"v2.0"),this.navigationManager.enableShiftDoubleClickZoom(),this.isShiftDoubleClickZoom=!0)},disableShiftDoubleClickZoom:function(){this.isShiftDoubleClickZoom&&(I(this.declaredClass+": Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.",null,"v2.0"),this.navigationManager.disableShiftDoubleClickZoom(),this.isShiftDoubleClickZoom=!1)},enableClickRecenter:function(){this.isClickRecenter||(this.navigationManager.enableClickRecenter(),this.isClickRecenter=
!0)},disableClickRecenter:function(){this.isClickRecenter&&(this.navigationManager.disableClickRecenter(),this.isClickRecenter=!1)},enablePan:function(){this.isPan||(this.navigationManager.enablePan(),this.isPan=!0)},disablePan:function(){this.isPan&&(this.navigationManager.disablePan(),this.isPan=!1)},enableRubberBandZoom:function(){this.isRubberBandZoom||(this.navigationManager.enableRubberBandZoom(),this.isRubberBandZoom=!0)},disableRubberBandZoom:function(){this.isRubberBandZoom&&(this.navigationManager.disableRubberBandZoom(),
this.isRubberBandZoom=!1)},enableKeyboardNavigation:function(){this.isKeyboardNavigation||(this.navigationManager.enableKeyboardNavigation(),this.isKeyboardNavigation=!0)},disableKeyboardNavigation:function(){this.isKeyboardNavigation&&(this.navigationManager.disableKeyboardNavigation(),this.isKeyboardNavigation=!1)},enableScrollWheelZoom:function(){this.isScrollWheelZoom||(this.navigationManager.enableScrollWheelZoom(),this.isScrollWheelZoom=!0)},disableScrollWheelZoom:function(){this.isScrollWheelZoom&&
(this.navigationManager.disableScrollWheelZoom(),this.isScrollWheelZoom=!1)},showPanArrows:function(){this._navDiv&&(this._navDiv.style.display="block",this.isPanArrows=!0)},hidePanArrows:function(){this._navDiv&&(this._navDiv.style.display="none",this.isPanArrows=!1)},showZoomSlider:function(){this._slider&&(q(this._slider.domNode||this._slider,"visibility","visible"),this.isZoomSlider=!0)},hideZoomSlider:function(){this._slider&&(q(this._slider.domNode||this._slider,"visibility","hidden"),this.isZoomSlider=
!1)}});d("extend-esri")&&(u.Map=r);return r});
//@ sourceMappingURL=map.js.map