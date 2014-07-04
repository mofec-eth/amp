//>>built
define("dojox/mobile/_compat",["dojo/_base/array","dojo/_base/config","dojo/_base/connect","dojo/_base/fx","dojo/_base/lang","dojo/sniff","dojo/_base/window","dojo/dom-class","dojo/dom-construct","dojo/dom-geometry","dojo/dom-style","dojo/dom-attr","dojo/fx","dojo/fx/easing","dojo/ready","dojo/uacss","dijit/registry","dojox/fx","dojox/fx/flip","./EdgeToEdgeList","./IconContainer","./ProgressIndicator","./RoundRect","./RoundRectList","./ScrollableView","./Switch","./View","./Heading","require"],function(z,v,p,w,f,g,k,u,q,r,l,A,m,J,B,K,C,D,L,x,
s,E,F,n,G,H,I,t,y){var c=f.getObject("dojox.mobile",!0);g("webkit")||10<=g("ie")||(f.extend(I,{_doTransition:function(a,d,b,e){this.wakeUp(d);var c;if(!b||"none"==b)d.style.display="",a.style.display="none",d.style.left="0px",this.invokeCallback();else{if("slide"==b||"cover"==b||"reveal"==b){var h=a.offsetWidth;b=m.slideTo({node:a,duration:400,left:-h*e,top:l.get(a,"top")});c=m.slideTo({node:d,duration:400,left:0,top:l.get(d,"top")});d.style.position="absolute";d.style.left=h*e+"px";d.style.display=
"";e=m.combine([b,c]);p.connect(e,"onEnd",this,function(){if(this._inProgress){a.style.display="none";a.style.left="0px";d.style.position="relative";var b=C.byNode(d);b&&!u.contains(b.domNode,"out")&&(b.containerNode.style.paddingTop="");this.invokeCallback()}})}else"slidev"==b||"coverv"==b||"reavealv"==b?(h=a.offsetHeight,b=m.slideTo({node:a,duration:400,left:0,top:-h*e}),c=m.slideTo({node:d,duration:400,left:0,top:0}),d.style.position="absolute",d.style.top=h*e+"px",d.style.left="0px",d.style.display=
"",e=m.combine([b,c]),p.connect(e,"onEnd",this,function(){this._inProgress&&(a.style.display="none",d.style.position="relative",this.invokeCallback())})):"flip"==b?(e=D.flip({node:a,dir:"right",depth:0.5,duration:400}),d.style.position="absolute",d.style.left="0px",p.connect(e,"onEnd",this,function(){this._inProgress&&(a.style.display="none",d.style.position="relative",d.style.display="",this.invokeCallback())})):(e=m.chain([w.fadeOut({node:a,duration:600}),w.fadeIn({node:d,duration:600})]),d.style.position=
"absolute",d.style.left="0px",d.style.display="",l.set(d,"opacity",0),p.connect(e,"onEnd",this,function(){this._inProgress&&(a.style.display="none",d.style.position="relative",l.set(a,"opacity",1),this.invokeCallback())}));e.play()}},wakeUp:function(a){if(g("ie")&&!a._wokeup){a._wokeup=!0;var d=a.style.display;a.style.display="";for(var b=a.getElementsByTagName("*"),e=0,c=b.length;e<c;e++){var h=b[e].style.display;b[e].style.display="none";b[e].style.display="";b[e].style.display=h}a.style.display=
d}}}),f.extend(H,{_changeState:function(a,d){var b="on"===a,e;e=b?this.isLeftToRight()?0:-l.get(this.right,"left"):this.isLeftToRight()?-l.get(this.right,"left"):0;this.left.style.display="";this.right.style.display="";var c=this,h=function(){u.remove(c.domNode,b?"mblSwitchOff":"mblSwitchOn");u.add(c.domNode,b?"mblSwitchOn":"mblSwitchOff");c.left.style.display=b?"":"none";c.right.style.display=!b?"":"none";A.set(c.domNode,"aria-checked",b?"true":"false")};if(d)m.slideTo({node:this.inner,duration:300,
left:e,onEnd:h}).play();else{if((this.isLeftToRight()?b:!b)||e)this.inner.style.left=e+"px";h()}}}),f.extend(E,{scale:function(a){if(g("ie"))a={w:a,h:a},r.setMarginBox(this.domNode,a),r.setMarginBox(this.containerNode,a);else if(g("ff")){var d=a/40;l.set(this.containerNode,{MozTransform:"scale("+d+")",MozTransformOrigin:"0 0"});r.setMarginBox(this.domNode,{w:a,h:a});r.setMarginBox(this.containerNode,{w:a/d,h:a/d})}}}),g("ie")&&(f.extend(F,{buildRendering:function(){c.createRoundRect(this);this.domNode.className=
"mblRoundRect"}}),n._addChild=n.prototype.addChild,n._postCreate=n.prototype.postCreate,f.extend(n,{buildRendering:function(){c.createRoundRect(this,!0);this.domNode.className="mblRoundRectList";g("ie")&&(g("dojo-bidi")&&!this.isLeftToRight())&&(this.domNode.className="mblRoundRectList mblRoundRectListRtl")},postCreate:function(){n._postCreate.apply(this,arguments);this.redrawBorders()},addChild:function(a,d){n._addChild.apply(this,arguments);this.redrawBorders();c.applyPngFilter&&c.applyPngFilter(a.domNode)},
redrawBorders:function(){if(!(this instanceof x))for(var a=!1,d=this.containerNode.childNodes.length-1;0<=d;d--){var b=this.containerNode.childNodes[d];"LI"==b.tagName&&(b.style.borderBottomStyle=a?"solid":"none",a=!0)}}}),f.extend(x,{buildRendering:function(){this.domNode=this.containerNode=this.srcNodeRef||k.doc.createElement("ul");this.domNode.className="mblEdgeToEdgeList"}}),s._addChild=s.prototype.addChild,f.extend(s,{addChild:function(a,d){s._addChild.apply(this,arguments);c.applyPngFilter&&
c.applyPngFilter(a.domNode)}}),f.mixin(c,{createRoundRect:function(a,d){var b,e;a.domNode=k.doc.createElement("div");a.domNode.style.padding="0px";a.domNode.style.backgroundColor="transparent";a.domNode.style.border="none";a.containerNode=k.doc.createElement(d?"ul":"div");a.containerNode.className="mblRoundRectContainer";if(a.srcNodeRef){a.srcNodeRef.parentNode.replaceChild(a.domNode,a.srcNodeRef);b=0;for(e=a.srcNodeRef.childNodes.length;b<e;b++)a.containerNode.appendChild(a.srcNodeRef.removeChild(a.srcNodeRef.firstChild));
a.srcNodeRef=null}a.domNode.appendChild(a.containerNode);for(b=0;5>=b;b++)e=q.create("div"),e.className="mblRoundCorner mblRoundCorner"+b+"T",a.domNode.insertBefore(e,a.containerNode),e=q.create("div"),e.className="mblRoundCorner mblRoundCorner"+b+"B",a.domNode.appendChild(e)}}),f.extend(G,{postCreate:function(){var a=q.create("div",{className:"mblDummyForIE",innerHTML:"\x26nbsp;"},this.containerNode,"first");l.set(a,{position:"relative",marginBottom:"-2px",fontSize:"1px"})}}),t._buildRendering=t.prototype.buildRendering,
f.extend(t,{buildRendering:function(){t._buildRendering.apply(this);for(var a=this.domNode.getElementsByTagName("INPUT"),d=a.length;d--;)a[d].removeAttribute("unselectable")}})),6>=g("ie")&&(c.applyPngFilter=function(a){a=a||k.body();a=a.getElementsByTagName("IMG");for(var d=y.toUrl("dojo/resources/blank.gif"),b=0,e=a.length;b<e;b++){var c=a[b],h=c.offsetWidth,f=c.offsetHeight;if(0===h||0===f){if("none"!=l.get(c,"display"))continue;c.style.display="";h=c.offsetWidth;f=c.offsetHeight;c.style.display=
"none";if(0===h||0===f)continue}var g=c.src;-1==g.indexOf("resources/blank.gif")&&(c.src=d,c.runtimeStyle.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src\x3d'"+g+"')",c.style.width=h+"px",c.style.height=f+"px")}},!c._disableBgFilter&&c.createDomButton&&(c._createDomButton_orig=c.createDomButton,c.createDomButton=function(a,d,b){var e=c._createDomButton_orig.apply(this,arguments);if(e&&e.className&&-1!==e.className.indexOf("mblDomButton")){var f=function(){if(e.currentStyle&&e.currentStyle.backgroundImage.match(/url.*(mblDomButton.*\.png)/)){var a=
RegExp.$1,a=y.toUrl("dojox/mobile/themes/common/domButtons/compat/")+a;e.runtimeStyle.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src\x3d'"+a+"',sizingMethod\x3d'crop')";e.style.background="none"}};setTimeout(f,1E3);setTimeout(f,5E3)}return e})),c.loadCssFile=function(a){c.loadedCssFiles||(c.loadedCssFiles=[]);k.doc.createStyleSheet?setTimeout(function(a){return function(){var b=k.doc.createStyleSheet(a);b&&c.loadedCssFiles.push(b.owningElement)}}(a),0):c.loadedCssFiles.push(q.create("link",
{href:a,type:"text/css",rel:"stylesheet"},k.doc.getElementsByTagName("head")[0]))},c.loadCss=function(a){if(!c._loadedCss){var d={};z.forEach(c.getCssPaths(),function(a){d[a]=!0});c._loadedCss=d}f.isArray(a)||(a=[a]);for(var b=0;b<a.length;b++){var e=a[b];c._loadedCss[e]||(c._loadedCss[e]=!0,c.loadCssFile(e))}},c.getCssPaths=function(){var a=[],d,b,c=k.doc.styleSheets;for(d=0;d<c.length;d++)if(!c[d].href){var f=c[d].cssRules||c[d].imports;if(f)for(b=0;b<f.length;b++)f[b].href&&a.push(f[b].href)}c=
k.doc.getElementsByTagName("link");d=0;for(b=c.length;d<b;d++)c[d].href&&a.push(c[d].href);return a},c.loadCompatPattern=/\/mobile\/themes\/.*\.css$/,c.loadCompatCssFiles=function(a){if(g("ie")&&!a)setTimeout(function(){c.loadCompatCssFiles(!0)},0);else{c._loadedCss=void 0;a=c.getCssPaths();g("dojo-bidi")&&(a=c.loadRtlCssFiles(a));for(var d=0;d<a.length;d++){var b=a[d];if((b.match(v.mblLoadCompatPattern||c.loadCompatPattern)||-1!==location.href.indexOf("mobile/tests/"))&&-1===b.indexOf("-compat.css"))b=
b.substring(0,b.length-4)+"-compat.css",c.loadCss(b)}}},g("dojo-bidi")&&(c.loadRtlCssFiles=function(a){for(var d=0;d<a.length;d++){var b=a[d];if(-1==b.indexOf("_rtl")){var e=b.substr(b.lastIndexOf("/")+1);-1!="android.css blackberry.css custom.css iphone.css holodark.css base.css Carousel.css ComboBox.css IconContainer.css IconMenu.css ListItem.css RoundRectCategory.css SpinWheel.css Switch.css TabBar.css ToggleButton.css ToolBarButton.css".indexOf(e)&&(b=b.replace(".css","_rtl.css"),a.push(b),c.loadCss(b))}}return a}),
c.hideAddressBar=function(a,d){!1!==d&&c.resizeAll()},B(function(){!1!==v.mblLoadCompatCssFiles&&c.loadCompatCssFiles();c.applyPngFilter&&c.applyPngFilter()}));return c});
//@ sourceMappingURL=_compat.js.map