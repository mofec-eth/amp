//>>built
define("dojo/dnd/autoscroll",["../_base/lang","../sniff","../_base/window","../dom-geometry","../dom-style","../window"],function(v,q,m,s,w,r){var a={};v.setObject("dojo.dnd.autoscroll",a);a.getViewport=r.getBox;a.V_TRIGGER_AUTOSCROLL=32;a.H_TRIGGER_AUTOSCROLL=32;a.V_AUTOSCROLL_VALUE=16;a.H_AUTOSCROLL_VALUE=16;var n,e=m.doc,t=Infinity,u=Infinity;a.autoScrollStart=function(a){e=a;n=r.getBox(e);a=m.body(e).parentNode;t=Math.max(a.scrollHeight-n.h,0);u=Math.max(a.scrollWidth-n.w,0)};a.autoScroll=function(k){var f=
n||r.getBox(e),l=m.body(e).parentNode,b=0,c=0;k.clientX<a.H_TRIGGER_AUTOSCROLL?b=-a.H_AUTOSCROLL_VALUE:k.clientX>f.w-a.H_TRIGGER_AUTOSCROLL&&(b=Math.min(a.H_AUTOSCROLL_VALUE,u-l.scrollLeft));k.clientY<a.V_TRIGGER_AUTOSCROLL?c=-a.V_AUTOSCROLL_VALUE:k.clientY>f.h-a.V_TRIGGER_AUTOSCROLL&&(c=Math.min(a.V_AUTOSCROLL_VALUE,t-l.scrollTop));window.scrollBy(b,c)};a._validNodes={div:1,p:1,td:1};a._validOverflow={auto:1,scroll:1};a.autoScrollNodes=function(k){for(var f,l,b,c,g,h,e=0,p=0,d=k.target;d;){if(1==
d.nodeType&&d.tagName.toLowerCase()in a._validNodes){b=w.getComputedStyle(d);c=b.overflow.toLowerCase()in a._validOverflow;g=b.overflowX.toLowerCase()in a._validOverflow;h=b.overflowY.toLowerCase()in a._validOverflow;if(c||g||h)f=s.getContentBox(d,b),l=s.position(d,!0);if(c||g){b=Math.min(a.H_TRIGGER_AUTOSCROLL,f.w/2);g=k.pageX-l.x;if(q("webkit")||q("opera"))g+=m.body().scrollLeft;e=0;0<g&&g<f.w&&(g<b?e=-b:g>f.w-b&&(e=b),d.scrollLeft+=e)}if(c||h){c=Math.min(a.V_TRIGGER_AUTOSCROLL,f.h/2);h=k.pageY-
l.y;if(q("webkit")||q("opera"))h+=m.body().scrollTop;p=0;0<h&&h<f.h&&(h<c?p=-c:h>f.h-c&&(p=c),d.scrollTop+=p)}if(e||p)return}try{d=d.parentNode}catch(n){d=null}}a.autoScroll(k)};return a});
//@ sourceMappingURL=autoscroll.js.map