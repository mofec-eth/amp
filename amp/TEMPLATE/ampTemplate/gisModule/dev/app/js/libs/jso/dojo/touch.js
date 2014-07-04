//>>built
define("dojo/touch",["./_base/kernel","./aspect","./dom","./dom-class","./_base/lang","./on","./has","./mouse","./domReady","./_base/window"],function(C,r,D,E,F,e,m,n,s,d){function g(a,p,b){return t&&b?function(a,h){return e(a,b,h)}:q?function(b,h){var d=e(b,p,h),c=e(b,a,function(a){(!k||(new Date).getTime()>k+1E3)&&h.call(this,a)});return{remove:function(){d.remove();c.remove()}}}:function(b,h){return e(b,a,h)}}function G(a){do if(a.dojoClick)return a.dojoClick;while(a=a.parentNode)}function u(a,p,b){if(c=
!a.target.disabled&&G(a.target))v=a.target,w=a.touches?a.touches[0].pageX:a.clientX,x=a.touches?a.touches[0].pageY:a.clientY,y=("object"==typeof c?c.x:"number"==typeof c?c:0)||4,z=("object"==typeof c?c.y:"number"==typeof c?c:0)||4,A||(A=!0,d.doc.addEventListener(p,function(a){c=c&&a.target==v&&Math.abs((a.touches?a.touches[0].pageX:a.clientX)-w)<=y&&Math.abs((a.touches?a.touches[0].pageY:a.clientY)-x)<=z},!0),d.doc.addEventListener(b,function(a){if(c){B=(new Date).getTime();var b=a.target;"LABEL"===
b.tagName&&(b=D.byId(b.getAttribute("for"))||b);setTimeout(function(){e.emit(b,"click",{bubbles:!0,cancelable:!0,_dojo_click:!0})})}},!0),a=function(a){d.doc.addEventListener(a,function(b){!b._dojo_click&&((new Date).getTime()<=B+1E3&&!("INPUT"==b.target.tagName&&E.contains(b.target,"dijitOffScreen")))&&(b.stopPropagation(),b.stopImmediatePropagation&&b.stopImmediatePropagation(),"click"==a&&(("INPUT"!=b.target.tagName||"radio"==b.target.type||"checkbox"==b.target.type)&&"TEXTAREA"!=b.target.tagName&&
"AUDIO"!=b.target.tagName&&"VIDEO"!=b.target.tagName)&&b.preventDefault())},!0)},a("click"),a("mousedown"),a("mouseup"))}var q=m("touch"),l=5>m("ios"),t=navigator.msPointerEnabled,A,c,v,w,x,y,z,B,k,f;q&&(t?s(function(){d.doc.addEventListener("MSPointerDown",function(a){u(a,"MSPointerMove","MSPointerUp")},!0)}):s(function(){function a(a){var b=F.delegate(a,{bubbles:!0});6<=m("ios")&&(b.touches=a.touches,b.altKey=a.altKey,b.changedTouches=a.changedTouches,b.ctrlKey=a.ctrlKey,b.metaKey=a.metaKey,b.shiftKey=
a.shiftKey,b.targetTouches=a.targetTouches);return b}f=d.body();d.doc.addEventListener("touchstart",function(a){k=(new Date).getTime();var b=f;f=a.target;e.emit(b,"dojotouchout",{relatedTarget:f,bubbles:!0});e.emit(f,"dojotouchover",{relatedTarget:b,bubbles:!0});u(a,"touchmove","touchend")},!0);e(d.doc,"touchmove",function(c){k=(new Date).getTime();var b=d.doc.elementFromPoint(c.pageX-(l?0:d.global.pageXOffset),c.pageY-(l?0:d.global.pageYOffset));b&&(f!==b&&(e.emit(f,"dojotouchout",{relatedTarget:b,
bubbles:!0}),e.emit(b,"dojotouchover",{relatedTarget:f,bubbles:!0}),f=b),e.emit(b,"dojotouchmove",a(c)))});e(d.doc,"touchend",function(c){k=(new Date).getTime();var b=d.doc.elementFromPoint(c.pageX-(l?0:d.global.pageXOffset),c.pageY-(l?0:d.global.pageYOffset))||d.body();e.emit(b,"dojotouchend",a(c))})}));r={press:g("mousedown","touchstart","MSPointerDown"),move:g("mousemove","dojotouchmove","MSPointerMove"),release:g("mouseup","dojotouchend","MSPointerUp"),cancel:g(n.leave,"touchcancel",q?"MSPointerCancel":
null),over:g("mouseover","dojotouchover","MSPointerOver"),out:g("mouseout","dojotouchout","MSPointerOut"),enter:n._eventHandler(g("mouseover","dojotouchover","MSPointerOver")),leave:n._eventHandler(g("mouseout","dojotouchout","MSPointerOut"))};return C.touch=r});
//@ sourceMappingURL=touch.js.map