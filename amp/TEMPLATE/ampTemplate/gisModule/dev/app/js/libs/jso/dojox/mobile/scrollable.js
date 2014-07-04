//>>built
define("dojox/mobile/scrollable",["dojo/_base/kernel","dojo/_base/connect","dojo/_base/event","dojo/_base/lang","dojo/_base/window","dojo/dom-class","dojo/dom-construct","dojo/dom-style","dojo/dom-geometry","dojo/touch","./sniff","./_css3","./_maskUtils"],function(q,p,t,u,h,n,r,l,v,s,g,f,x){var m=u.getObject("dojox.mobile",!0);g.add("translate3d",function(){if(g("css3-animations")){var a=h.doc.createElement("div");a.style[f.name("transform")]="translate3d(0px,1px,0px)";h.doc.documentElement.appendChild(a);var c=
h.doc.defaultView.getComputedStyle(a,"")[f.name("transform",!0)],c=c&&0===c.indexOf("matrix");h.doc.documentElement.removeChild(a);return c}});var w=function(){};u.extend(w,{fixedHeaderHeight:0,fixedFooterHeight:0,isLocalFooter:!1,scrollBar:!0,scrollDir:"v",weight:0.6,fadeScrollBar:!0,disableFlashScrollBar:!1,threshold:4,constraint:!0,touchNode:null,propagatable:!0,dirLock:!1,height:"",scrollType:0,_parentPadBorderExtentsBottom:0,init:function(a){if(a)for(var c in a)a.hasOwnProperty(c)&&(this[c]=
("domNode"==c||"containerNode"==c)&&"string"==typeof a[c]?h.doc.getElementById(a[c]):a[c]);"undefined"!=typeof this.domNode.style.msTouchAction&&(this.domNode.style.msTouchAction="none");this.touchNode=this.touchNode||this.containerNode;this._v=-1!=this.scrollDir.indexOf("v");this._h=-1!=this.scrollDir.indexOf("h");this._f="f"==this.scrollDir;this._ch=[];this._ch.push(p.connect(this.touchNode,s.press,this,"onTouchStart"));if(g("css3-animations"))if(this._useTopLeft=this.scrollType?2===this.scrollType:
3>g("android"),this._useTopLeft||(this._useTransformTransition=this.scrollType?3===this.scrollType:6<=g("ios")),this._useTopLeft)this._ch.push(p.connect(this.domNode,f.name("transitionEnd"),this,"onFlickAnimationEnd")),this._ch.push(p.connect(this.domNode,f.name("transitionStart"),this,"onFlickAnimationStart"));else{if(this._useTransformTransition)this._ch.push(p.connect(this.domNode,f.name("transitionEnd"),this,"onFlickAnimationEnd")),this._ch.push(p.connect(this.domNode,f.name("transitionStart"),
this,"onFlickAnimationStart"));else{this._ch.push(p.connect(this.domNode,f.name("animationEnd"),this,"onFlickAnimationEnd"));this._ch.push(p.connect(this.domNode,f.name("animationStart"),this,"onFlickAnimationStart"));for(a=0;3>a;a++)this.setKeyframes(null,null,a)}g("translate3d")&&l.set(this.containerNode,f.name("transform"),"translate3d(0,0,0)")}this._speed={x:0,y:0};this._appFooterHeight=0;this.isTopLevel()&&!this.noResize&&this.resize();var b=this;setTimeout(function(){b.flashScrollBar()},600);
h.global.addEventListener&&(this._onScroll=function(a){if(b.domNode&&"none"!==b.domNode.style.display){a=b.domNode.scrollTop;var c=b.domNode.scrollLeft,k;if(0<a||0<c)k=b.getPos(),b.domNode.scrollLeft=0,b.domNode.scrollTop=0,b.scrollTo({x:k.x-c,y:k.y-a})}},h.global.addEventListener("scroll",this._onScroll,!0));!this.disableTouchScroll&&this.domNode.addEventListener&&(this._onFocusScroll=function(a){if(b.domNode&&"none"!==b.domNode.style.display){a=h.doc.activeElement;var c,k;a&&(c=a.getBoundingClientRect(),
k=b.domNode.getBoundingClientRect(),c.height<b.getDim().d.h&&(c.top<k.top+b.fixedHeaderHeight?b.scrollIntoView(a,!0):c.top+c.height>k.top+k.height-b.fixedFooterHeight&&b.scrollIntoView(a,!1)))}},this.domNode.addEventListener("focus",this._onFocusScroll,!0))},isTopLevel:function(){return!0},cleanup:function(){if(this._ch){for(var a=0;a<this._ch.length;a++)p.disconnect(this._ch[a]);this._ch=null}this._onScroll&&h.global.removeEventListener&&(h.global.removeEventListener("scroll",this._onScroll,!0),
this._onScroll=null);this._onFocusScroll&&this.domNode.removeEventListener&&(this.domNode.removeEventListener("focus",this._onFocusScroll,!0),this._onFocusScroll=null)},findDisp:function(a){if(!a.parentNode)return null;if(1===a.nodeType&&n.contains(a,"mblSwapView")&&"none"!==a.style.display)return a;for(var c=a.parentNode.childNodes,b=0;b<c.length;b++){var e=c[b];if(1===e.nodeType&&n.contains(e,"mblView")&&"none"!==e.style.display)return e}return a},getScreenSize:function(){return{h:h.global.innerHeight||
h.doc.documentElement.clientHeight||h.doc.documentElement.offsetHeight,w:h.global.innerWidth||h.doc.documentElement.clientWidth||h.doc.documentElement.offsetWidth}},resize:function(a){this._appFooterHeight=this._fixedAppFooter?this._fixedAppFooter.offsetHeight:0;this.isLocalHeader&&(this.containerNode.style.marginTop=this.fixedHeaderHeight+"px");var c=0;for(a=this.domNode;a&&"BODY"!=a.tagName;a=a.offsetParent){a=this.findDisp(a);if(!a)break;c+=a.offsetTop+v.getBorderExtents(a).h}var b;a=this.getScreenSize().h;
c=a-c-this._appFooterHeight;if("inherit"===this.height)this.domNode.offsetParent&&(b=v.getContentBox(this.domNode.offsetParent).h-v.getBorderExtents(this.domNode).h+"px");else if("auto"===this.height){if(b=this.domNode.offsetParent){this.domNode.style.height="0px";b=b.getBoundingClientRect();var c=this.domNode.getBoundingClientRect(),e=b.bottom-this._appFooterHeight-this._parentPadBorderExtentsBottom,c=c.bottom>=e?a-(c.top-b.top)-this._appFooterHeight-this._parentPadBorderExtentsBottom:e-c.bottom}a=
Math.max(this.domNode.scrollHeight,this.containerNode.scrollHeight);b=(a?Math.min(a,c):c)+"px"}else this.height&&(b=this.height);b||(b=c+"px");"-"!==b.charAt(0)&&"default"!==b&&(this.domNode.style.height=b);if(!this._conn)this.onTouchEnd()},onFlickAnimationStart:function(a){t.stop(a)},onFlickAnimationEnd:function(a){g("ios")&&this._keepInputCaretInActiveElement();if(a){var c=a.animationName;if(c&&-1===c.indexOf("scrollableViewScroll2")){-1!==c.indexOf("scrollableViewScroll0")?this._scrollBarNodeV&&
n.remove(this._scrollBarNodeV,"mblScrollableScrollTo0"):-1!==c.indexOf("scrollableViewScroll1")?this._scrollBarNodeH&&n.remove(this._scrollBarNodeH,"mblScrollableScrollTo1"):(this._scrollBarNodeV&&(this._scrollBarNodeV.className=""),this._scrollBarNodeH&&(this._scrollBarNodeH.className=""));return}if(this._useTransformTransition||this._useTopLeft)if(c=a.target,c===this._scrollBarV||c===this._scrollBarH){a="mblScrollableScrollTo"+(c===this._scrollBarV?"0":"1");n.contains(c,a)?n.remove(c,a):c.className=
"";return}a.srcElement&&t.stop(a)}this.stopAnimation();if(this._bounce){var b=this,e=b._bounce;setTimeout(function(){b.slideTo(e,0.3,"ease-out")},0);b._bounce=void 0}else this.hideScrollBar(),this.removeCover()},isFormElement:function(a){a&&1!==a.nodeType&&(a=a.parentNode);if(!a||1!==a.nodeType)return!1;a=a.tagName;return"SELECT"===a||"INPUT"===a||"TEXTAREA"===a||"BUTTON"===a},onTouchStart:function(a){!this.disableTouchScroll&&!(this._conn&&500>(new Date).getTime()-this.startTime)&&(this._conn||(this._conn=
[],this._conn.push(p.connect(h.doc,s.move,this,"onTouchMove")),this._conn.push(p.connect(h.doc,s.release,this,"onTouchEnd"))),this._aborted=!1,n.contains(this.containerNode,"mblScrollableScrollTo2")?this.abort():(this._scrollBarNodeV&&(this._scrollBarNodeV.className=""),this._scrollBarNodeH&&(this._scrollBarNodeH.className="")),this.touchStartX=a.touches?a.touches[0].pageX:a.clientX,this.touchStartY=a.touches?a.touches[0].pageY:a.clientY,this.startTime=(new Date).getTime(),this.startPos=this.getPos(),
this._dim=this.getDim(),this._time=[0],this._posX=[this.touchStartX],this._posY=[this.touchStartY],this._locked=!1,this.isFormElement(a.target)||(this.propagatable?a.preventDefault():t.stop(a)))},onTouchMove:function(a){if(!this._locked){var c=a.touches?a.touches[0].pageX:a.clientX;a=a.touches?a.touches[0].pageY:a.clientY;var b=c-this.touchStartX,e=a-this.touchStartY,d={x:this.startPos.x+b,y:this.startPos.y+e},k=this._dim,b=Math.abs(b),e=Math.abs(e);if(1==this._time.length){if(this.dirLock&&(this._v&&
!this._h&&b>=this.threshold&&b>=e||(this._h||this._f)&&!this._v&&e>=this.threshold&&e>=b)){this._locked=!0;return}if(this._v&&this._h){if(e<this.threshold&&b<this.threshold)return}else if(this._v&&e<this.threshold||(this._h||this._f)&&b<this.threshold)return;this.addCover();this.showScrollBar()}b=this.weight;this._v&&this.constraint&&(0<d.y?d.y=Math.round(d.y*b):d.y<-k.o.h&&(d.y=k.c.h<k.d.h?Math.round(d.y*b):-k.o.h-Math.round((-k.o.h-d.y)*b)));if((this._h||this._f)&&this.constraint)0<d.x?d.x=Math.round(d.x*
b):d.x<-k.o.w&&(d.x=k.c.w<k.d.w?Math.round(d.x*b):-k.o.w-Math.round((-k.o.w-d.x)*b));this.scrollTo(d);d=this._time.length;if(2<=d){var f,g;this._v&&!this._h?(f=this._posY[d-1]-this._posY[d-2],g=a-this._posY[d-1]):!this._v&&this._h&&(f=this._posX[d-1]-this._posX[d-2],g=c-this._posX[d-1]);0>f*g&&(this._time=[this._time[d-1]],this._posX=[this._posX[d-1]],this._posY=[this._posY[d-1]],d=1)}10==d&&(this._time.shift(),this._posX.shift(),this._posY.shift());this._time.push((new Date).getTime()-this.startTime);
this._posX.push(c);this._posY.push(a)}},_keepInputCaretInActiveElement:function(){var a=h.doc.activeElement,c;if(a&&("INPUT"==a.tagName||"TEXTAREA"==a.tagName))c=a.value,a.value="number"==a.type||"week"==a.type?c?a.value+1:"week"==a.type?"2013-W10":1:a.value+" ",a.value=c},_fingerMovedSinceTouchStart:function(){var a=this._time.length;return 1>=a||2==a&&4>Math.abs(this._posY[1]-this._posY[0])&&g("touch")?!1:!0},onTouchEnd:function(a){if(!this._locked){var c=this._speed={x:0,y:0},b=this._dim,e=this.getPos(),
d={};if(a){if(!this._conn)return;for(c=0;c<this._conn.length;c++)p.disconnect(this._conn[c]);this._conn=null;c=!1;!this._aborted&&!this._fingerMovedSinceTouchStart()&&(c=!0);if(c){this.hideScrollBar();this.removeCover();if(g("touch")&&g("clicks-prevented")&&!this.isFormElement(a.target)){var k=a.target;1!=k.nodeType&&(k=k.parentNode);setTimeout(function(){m._sendClick(k,a)})}return}c=this._speed=this.getSpeed()}else{if(0==e.x&&0==e.y)return;b=this.getDim()}this._v&&(d.y=e.y+c.y);if(this._h||this._f)d.x=
e.x+c.x;if(!1!==this.adjustDestination(d,e,b)){if(this.constraint){if("v"==this.scrollDir&&b.c.h<b.d.h){this.slideTo({y:0},0.3,"ease-out");return}if("h"==this.scrollDir&&b.c.w<b.d.w){this.slideTo({x:0},0.3,"ease-out");return}if(this._v&&this._h&&b.c.h<b.d.h&&b.c.w<b.d.w){this.slideTo({x:0,y:0},0.3,"ease-out");return}}var f,h="ease-out",l={};this._v&&this.constraint&&(0<d.y?0<e.y?(f=0.3,d.y=0):(d.y=Math.min(d.y,20),h="linear",l.y=0):-c.y>b.o.h- -e.y&&(e.y<-b.o.h?(f=0.3,d.y=b.c.h<=b.d.h?0:-b.o.h):(d.y=
Math.max(d.y,-b.o.h-20),h="linear",l.y=-b.o.h)));if((this._h||this._f)&&this.constraint)0<d.x?0<e.x?(f=0.3,d.x=0):(d.x=Math.min(d.x,20),h="linear",l.x=0):-c.x>b.o.w- -e.x&&(e.x<-b.o.w?(f=0.3,d.x=b.c.w<=b.d.w?0:-b.o.w):(d.x=Math.max(d.x,-b.o.w-20),h="linear",l.x=-b.o.w));this._bounce=void 0!==l.x||void 0!==l.y?l:void 0;if(void 0===f){var n,q;this._v&&this._h?(q=Math.sqrt(c.x*c.x+c.y*c.y),n=Math.sqrt(Math.pow(d.y-e.y,2)+Math.pow(d.x-e.x,2))):this._v?(q=c.y,n=d.y-e.y):this._h&&(q=c.x,n=d.x-e.x);if(0===
n&&!a)return;f=0!==q?Math.abs(n/q):0.01}this.slideTo(d,f,h)}}},adjustDestination:function(a,c,b){return!0},abort:function(){this._aborted=!0;this.scrollTo(this.getPos());this.stopAnimation()},_forceRendering:function(a){if(4.1<=g("android")){var c=a.style.display;a.style.display="none";a.offsetHeight;a.style.display=c}},stopAnimation:function(){this._forceRendering(this.containerNode);n.remove(this.containerNode,"mblScrollableScrollTo2");this._scrollBarV&&(this._scrollBarV.className="",this._forceRendering(this._scrollBarV));
this._scrollBarH&&(this._scrollBarH.className="",this._forceRendering(this._scrollBarH));if(this._useTransformTransition||this._useTopLeft)this.containerNode.style[f.name("transition")]="",this._scrollBarV&&(this._scrollBarV.style[f.name("transition")]=""),this._scrollBarH&&(this._scrollBarH.style[f.name("transition")]="")},scrollIntoView:function(a,c,b){if(this._v){for(var e=this.containerNode,d=this.getDim().d.h,f=0,g=a;g!==e;g=g.offsetParent){if(!g||"BODY"===g.tagName)return;f+=g.offsetTop}a=c?
Math.max(d-e.offsetHeight,-f):Math.min(0,d-f-a.offsetHeight);b&&"number"===typeof b?this.slideTo({y:a},b,"ease-out"):this.scrollTo({y:a})}},getSpeed:function(){var a=0,c=0,b=this._time.length;if(2<=b&&500>(new Date).getTime()-this.startTime-this._time[b-1])var a=this._posX[b-(3<b?2:1)]-this._posX[0<=b-6?b-6:0],e=this._time[b-(3<b?2:1)]-this._time[0<=b-6?b-6:0],c=this.calcSpeed(this._posY[b-(3<b?2:1)]-this._posY[0<=b-6?b-6:0],e),a=this.calcSpeed(a,e);return{x:a,y:c}},calcSpeed:function(a,c){return 4*
Math.round(100*(a/c))},scrollTo:function(a,c,b){var e,d;d=!0;!this._aborted&&this._conn&&(this._dim||(this._dim=this.getDim()),e=0<a.y?a.y:0,d=0>this._dim.o.h+a.y?-1*(this._dim.o.h+a.y):0,e={bubbles:!1,cancelable:!1,x:a.x,y:a.y,beforeTop:0<e,beforeTopHeight:e,afterBottom:0<d,afterBottomHeight:d},d=this.onBeforeScroll(e));if(d){b=(b||this.containerNode).style;if(g("css3-animations"))if(this._useTopLeft){if(b[f.name("transition")]="",this._v&&(b.top=a.y+"px"),this._h||this._f)b.left=a.x+"px"}else this._useTransformTransition&&
(b[f.name("transition")]=""),b[f.name("transform")]=this.makeTranslateStr(a);else if(this._v&&(b.top=a.y+"px"),this._h||this._f)b.left=a.x+"px";g("ios")&&this._keepInputCaretInActiveElement();c||this.scrollScrollBarTo(this.calcScrollBarPos(a));if(e)this.onAfterScroll(e)}},onBeforeScroll:function(a){return!0},onAfterScroll:function(a){},slideTo:function(a,c,b){this._runSlideAnimation(this.getPos(),a,c,b,this.containerNode,2);this.slideScrollBarTo(a,c,b)},makeTranslateStr:function(a){var c=this._v&&
"number"==typeof a.y?a.y+"px":"0px";a=(this._h||this._f)&&"number"==typeof a.x?a.x+"px":"0px";return g("translate3d")?"translate3d("+a+","+c+",0px)":"translate("+a+","+c+")"},getPos:function(){if(g("css3-animations")){var a=h.doc.defaultView.getComputedStyle(this.containerNode,"");if(this._useTopLeft)return{x:parseInt(a.left)||0,y:parseInt(a.top)||0};var c=a[f.name("transform")];return c&&0===c.indexOf("matrix")?(a=c.split(/[,\s\)]+/),c=0===c.indexOf("matrix3d")?12:4,{y:a[c+1]-0,x:a[c]-0}):{x:0,y:0}}return{y:parseInt(this.containerNode.style.top)||
0,x:this.containerNode.offsetLeft}},getDim:function(){var a={};a.c={h:this.containerNode.offsetHeight,w:this.containerNode.offsetWidth};a.v={h:this.domNode.offsetHeight+this._appFooterHeight,w:this.domNode.offsetWidth};a.d={h:a.v.h-this.fixedHeaderHeight-this.fixedFooterHeight-this._appFooterHeight,w:a.v.w};a.o={h:a.c.h-a.v.h+this.fixedHeaderHeight+this.fixedFooterHeight+this._appFooterHeight,w:a.c.w-a.v.w};return a},showScrollBar:function(){if(this.scrollBar){var a=this._dim;if(!("v"==this.scrollDir&&
a.c.h<=a.d.h)&&!("h"==this.scrollDir&&a.c.w<=a.d.w)&&(!this._v||!this._h||!(a.c.h<=a.d.h&&a.c.w<=a.d.w)))a=function(a,b){var e=a["_scrollBarNode"+b];if(!e){var e=r.create("div",null,a.domNode),d={position:"absolute",overflow:"hidden"};"V"==b?(d.right="2px",d.width="5px"):(d.bottom=(a.isLocalFooter?a.fixedFooterHeight:0)+2+"px",d.height="5px");l.set(e,d);e.className="mblScrollBarWrapper";a["_scrollBarWrapper"+b]=e;e=r.create("div",null,e);l.set(e,f.add({opacity:0.6,position:"absolute",backgroundColor:"#606060",
fontSize:"1px",MozBorderRadius:"2px",zIndex:2147483647},{borderRadius:"2px",transformOrigin:"0 0"}));l.set(e,"V"==b?{width:"5px"}:{height:"5px"});a["_scrollBarNode"+b]=e}return e},this._v&&!this._scrollBarV&&(this._scrollBarV=a(this,"V")),this._h&&!this._scrollBarH&&(this._scrollBarH=a(this,"H")),this.resetScrollBar()}},hideScrollBar:function(){if(this.fadeScrollBar&&g("css3-animations")&&!m._fadeRule){var a=r.create("style",null,h.doc.getElementsByTagName("head")[0]);a.textContent=".mblScrollableFadeScrollBar{  "+
f.name("animation-duration",!0)+": 1s;  "+f.name("animation-name",!0)+": scrollableViewFadeScrollBar;}@"+f.name("keyframes",!0)+" scrollableViewFadeScrollBar{  from { opacity: 0.6; }  to { opacity: 0; }}";m._fadeRule=a.sheet.cssRules[1]}this.scrollBar&&(a=function(a,b){l.set(a,f.add({opacity:0},{animationDuration:""}));if(!b._useTopLeft||!g("android"))a.className="mblScrollableFadeScrollBar"},this._scrollBarV&&(a(this._scrollBarV,this),this._scrollBarV=null),this._scrollBarH&&(a(this._scrollBarH,
this),this._scrollBarH=null))},calcScrollBarPos:function(a){var c={},b=this._dim,e=function(a,b,c,e,f){c=Math.round((e-b-8)/(e-f)*c);c<-b+5&&(c=-b+5);c>a-5&&(c=a-5);return c};"number"==typeof a.y&&this._scrollBarV&&(c.y=e(this._scrollBarWrapperV.offsetHeight,this._scrollBarV.offsetHeight,a.y,b.d.h,b.c.h));"number"==typeof a.x&&this._scrollBarH&&(c.x=e(this._scrollBarWrapperH.offsetWidth,this._scrollBarH.offsetWidth,a.x,b.d.w,b.c.w));return c},scrollScrollBarTo:function(a){this.scrollBar&&(this._v&&
(this._scrollBarV&&"number"==typeof a.y)&&(g("css3-animations")?this._useTopLeft?l.set(this._scrollBarV,f.add({top:a.y+"px"},{transition:""})):(this._useTransformTransition&&(this._scrollBarV.style[f.name("transition")]=""),this._scrollBarV.style[f.name("transform")]=this.makeTranslateStr({y:a.y})):this._scrollBarV.style.top=a.y+"px"),this._h&&(this._scrollBarH&&"number"==typeof a.x)&&(g("css3-animations")?this._useTopLeft?l.set(this._scrollBarH,f.add({left:a.x+"px"},{transition:""})):(this._useTransformTransition&&
(this._scrollBarH.style[f.name("transition")]=""),this._scrollBarH.style[f.name("transform")]=this.makeTranslateStr({x:a.x})):this._scrollBarH.style.left=a.x+"px"))},slideScrollBarTo:function(a,c,b){if(this.scrollBar){var e=this.calcScrollBarPos(this.getPos());a=this.calcScrollBarPos(a);this._v&&this._scrollBarV&&this._runSlideAnimation({y:e.y},{y:a.y},c,b,this._scrollBarV,0);this._h&&this._scrollBarH&&this._runSlideAnimation({x:e.x},{x:a.x},c,b,this._scrollBarH,1)}},_runSlideAnimation:function(a,
c,b,e,d,k){if(g("css3-animations"))if(this._useTopLeft)l.set(d,f.add({},{transitionProperty:"top, left",transitionDuration:b+"s",transitionTimingFunction:e})),setTimeout(function(){l.set(d,{top:(c.y||0)+"px",left:(c.x||0)+"px"})},0),n.add(d,"mblScrollableScrollTo"+k);else if(this._useTransformTransition)if(void 0===c.x&&(c.x=a.x),void 0===c.y&&(c.y=a.y),c.x!==a.x||c.y!==a.y){l.set(d,f.add({},{transitionProperty:f.name("transform"),transitionDuration:b+"s",transitionTimingFunction:e}));var h=this.makeTranslateStr(c);
setTimeout(function(){l.set(d,f.add({},{transform:h}))},0);n.add(d,"mblScrollableScrollTo"+k)}else this.hideScrollBar(),this.removeCover();else this.setKeyframes(a,c,k),l.set(d,f.add({},{animationDuration:b+"s",animationTimingFunction:e})),n.add(d,"mblScrollableScrollTo"+k),2==k?this.scrollTo(c,!0,d):this.scrollScrollBarTo(c);else q.fx&&q.fx.easing&&b?(a=q.fx.slideTo({node:d,duration:1E3*b,left:c.x,top:c.y,easing:"ease-out"==e?q.fx.easing.quadOut:q.fx.easing.linear}).play(),2==k&&p.connect(a,"onEnd",
this,"onFlickAnimationEnd")):2==k?(this.scrollTo(c,!1,d),this.onFlickAnimationEnd()):this.scrollScrollBarTo(c)},resetScrollBar:function(){var a=function(a,c,d,f,g,h){if(c){var m={};m[h?"top":"left"]=g+4+"px";g=0>=d-8?1:d-8;m[h?"height":"width"]=g+"px";l.set(a,m);a=Math.round(d*d/f);a=Math.min(Math.max(a-8,5),g);c.style[h?"height":"width"]=a+"px";l.set(c,{opacity:0.6})}},c=this.getDim();a(this._scrollBarWrapperV,this._scrollBarV,c.d.h,c.c.h,this.fixedHeaderHeight,!0);a(this._scrollBarWrapperH,this._scrollBarH,
c.d.w,c.c.w,0);this.createMask()},createMask:function(){if(g("webkit")||g("svg"))this._scrollBarWrapperV&&x.createRoundMask(this._scrollBarWrapperV,0,0,0,0,5,this._scrollBarWrapperV.offsetHeight,2,2,0.5),this._scrollBarWrapperH&&x.createRoundMask(this._scrollBarWrapperH,0,0,0,0,this._scrollBarWrapperH.offsetWidth,5,2,2,0.5)},flashScrollBar:function(){if(!this.disableFlashScrollBar&&this.domNode&&(this._dim=this.getDim(),!(0>=this._dim.d.h))){this.showScrollBar();var a=this;setTimeout(function(){a.hideScrollBar()},
300)}},addCover:function(){!g("touch")&&!this.noCover&&(m._cover?m._cover.style.display="":(m._cover=r.create("div",null,h.doc.body),m._cover.className="mblScrollableCover",l.set(m._cover,{backgroundColor:"#ffff00",opacity:0,position:"absolute",top:"0px",left:"0px",width:"100%",height:"100%",zIndex:2147483647}),this._ch.push(p.connect(m._cover,s.press,this,"onTouchEnd"))),this.setSelectable(m._cover,!1),this.setSelectable(this.domNode,!1))},removeCover:function(){!g("touch")&&m._cover&&(m._cover.style.display=
"none",this.setSelectable(m._cover,!0),this.setSelectable(this.domNode,!0))},setKeyframes:function(a,c,b){m._rule||(m._rule=[]);if(!m._rule[b]){var e=r.create("style",null,h.doc.getElementsByTagName("head")[0]);e.textContent=".mblScrollableScrollTo"+b+"{"+f.name("animation-name",!0)+": scrollableViewScroll"+b+";}@"+f.name("keyframes",!0)+" scrollableViewScroll"+b+"{}";m._rule[b]=e.sheet.cssRules[1]}if(b=m._rule[b])a&&(b.deleteRule(g("webkit")?"from":0),(b.insertRule||b.appendRule).call(b,"from { "+
f.name("transform",!0)+": "+this.makeTranslateStr(a)+"; }")),c&&(void 0===c.x&&(c.x=a.x),void 0===c.y&&(c.y=a.y),b.deleteRule(g("webkit")?"to":1),(b.insertRule||b.appendRule).call(b,"to { "+f.name("transform",!0)+": "+this.makeTranslateStr(c)+"; }"))},setSelectable:function(a,c){a.style.KhtmlUserSelect=c?"auto":"none";a.style.MozUserSelect=c?"":"none";a.onselectstart=c?null:function(){return!1};if(g("ie")){a.unselectable=c?"":"on";for(var b=a.getElementsByTagName("*"),e=0;e<b.length;e++)b[e].unselectable=
c?"":"on"}}});u.setObject("dojox.mobile.scrollable",w);return w});
//@ sourceMappingURL=scrollable.js.map