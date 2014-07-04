//>>built
define("dojox/mobile/lazyLoadUtils",["dojo/_base/kernel","dojo/_base/array","dojo/_base/config","dojo/_base/window","dojo/_base/Deferred","dojo/ready"],function(l,e,p,q,r,n){return new function(){this._lazyNodes=[];var m=this;p.parseOnLoad&&n(90,function(){var c=e.filter(q.body().getElementsByTagName("*"),function(a){return"true"===a.getAttribute("lazy")||(a.getAttribute("data-dojo-props")||"").match(/lazy\s*:\s*true/)}),a,b,g,d;for(a=0;a<c.length;a++)e.forEach(["dojoType","data-dojo-type"],function(f){g=
e.filter(c[a].getElementsByTagName("*"),function(a){return a.getAttribute(f)});for(b=0;b<g.length;b++)d=g[b],d.setAttribute("__"+f,d.getAttribute(f)),d.removeAttribute(f),m._lazyNodes.push(d)})});n(function(){for(var c=0;c<m._lazyNodes.length;c++){var a=m._lazyNodes[c];e.forEach(["dojoType","data-dojo-type"],function(b){a.getAttribute("__"+b)&&(a.setAttribute(b,a.getAttribute("__"+b)),a.removeAttribute("__"+b))})}delete m._lazyNodes});this.instantiateLazyWidgets=function(c,a,b){var g=new r;a=a?a.split(/,/):
[];for(var d=c.getElementsByTagName("*"),f=d.length,k=0;k<f;k++){var h=d[k].getAttribute("dojoType")||d[k].getAttribute("data-dojo-type");h&&(a.push(h),h=(h=d[k].getAttribute("data-dojo-mixins"))?h.split(/, */):[],a=a.concat(h))}if(0===a.length)return!0;if(l.require)return e.forEach(a,function(a){l.require(a)}),l.parser.parse(c),b&&b(c),!0;a=e.map(a,function(a){return a.replace(/\./g,"/")});require(a,function(){l.parser.parse(c);b&&b(c);g.resolve(!0)});return g}}});
//@ sourceMappingURL=lazyLoadUtils.js.map