//>>built
define("dojox/mobile/FixedSplitter",["dojo/_base/array","dojo/_base/declare","dojo/_base/window","dojo/dom-class","dojo/dom-geometry","dijit/_Contained","dijit/_Container","dijit/_WidgetBase","dojo/has"],function(m,r,n,h,k,s,t,u,v){return r("dojox.mobile.FixedSplitter",[u,t,s],{orientation:"H",variablePane:-1,screenSizeAware:!1,screenSizeAwareClass:"dojox/mobile/ScreenSizeAware",baseClass:"mblFixedSplitter",startup:function(){if(!this._started){h.add(this.domNode,this.baseClass+this.orientation);var a=this.getParent(),
c;if(!a||!a.resize){var e=this;c=function(){e.defer(function(){e.resize()})}}this.screenSizeAware?require([this.screenSizeAwareClass],function(a){a.getInstance();c&&c()}):c&&c();this.inherited(arguments)}},resize:function(){var a="H"===this.orientation?"w":"h",c="H"===this.orientation?"l":"t",e={},h={},b,g,l=[],f=0,p=0,d=m.filter(this.domNode.childNodes,function(a){return 1==a.nodeType}),q=-1==this.variablePane?d.length-1:this.variablePane;for(b=0;b<d.length;b++)b!=q&&(l[b]=k.getMarginBox(d[b])[a],
p+=l[b]);"V"==this.orientation&&"BODY"==this.domNode.parentNode.tagName&&1==m.filter(n.body().childNodes,function(a){return 1==a.nodeType}).length&&(g=n.global.innerHeight||n.doc.documentElement.clientHeight);g=(g||k.getMarginBox(this.domNode)[a])-p;h[a]=l[q]=g;a=d[q];k.setMarginBox(a,h);a.style["H"===this.orientation?"height":"width"]="";if(v("dojo-bidi")&&"H"==this.orientation&&!this.isLeftToRight()){f=g;for(b=d.length-1;0<=b;b--)a=d[b],e[c]=g-f,k.setMarginBox(a,e),a.style["H"===this.orientation?
"top":"left"]="",f-=l[b]}else for(b=0;b<d.length;b++)a=d[b],e[c]=f,k.setMarginBox(a,e),a.style["H"===this.orientation?"top":"left"]="",f+=l[b];m.forEach(this.getChildren(),function(a){a.resize&&a.resize()})},_setOrientationAttr:function(a){var c=this.baseClass;h.replace(this.domNode,c+a,c+this.orientation);this.orientation=a;this._started&&this.resize()}})});
//@ sourceMappingURL=FixedSplitter.js.map