//>>built
define("dojox/widget/FisheyeList",["dojo/_base/declare","dojo/_base/sniff","dojo/_base/lang","dojo/aspect","dojo/dom","dojo/dom-attr","dojo/dom-class","dojo/dom-geometry","dojo/dom-style","dojo/dom-construct","dojo/on","dojo/_base/window","dojo/mouse","dijit/_WidgetBase","dijit/_TemplatedMixin","dijit/_Container","./FisheyeListItem"],function(r,w,f,n,p,x,y,l,g,z,m,k,s,t,u,v,A){return r("dojox.widget.FisheyeList",[t,u,v],{constructor:function(){this.pos={x:-1,y:-1};this.timerScale=1},EDGE:{CENTER:0,LEFT:1,RIGHT:2,TOP:3,
BOTTOM:4},templateString:'\x3cdiv class\x3d"dojoxFisheyeListBar" data-dojo-attach-point\x3d"containerNode"\x3e\x3c/div\x3e',snarfChildDomOutput:!0,itemWidth:40,itemHeight:40,itemMaxWidth:150,itemMaxHeight:150,imgNode:null,orientation:"horizontal",isFixed:!1,conservativeTrigger:!1,effectUnits:2,itemPadding:10,attachEdge:"center",labelEdge:"bottom",postCreate:function(){var a=this.EDGE,c=this.isHorizontal="horizontal"==this.orientation;p.setSelectable(this.domNode,!1);this.selectedNode=-1;this.isOver=
!1;this.hitY2=this.hitX2=this.hitY1=this.hitX1=-1;this.anchorEdge=this._toEdge(this.attachEdge,a.CENTER);this.labelEdge=this._toEdge(this.labelEdge,a.TOP);this.labelEdge==a.CENTER&&(this.labelEdge=a.TOP);c?(this.anchorEdge==a.LEFT&&(this.anchorEdge=a.CENTER),this.anchorEdge==a.RIGHT&&(this.anchorEdge=a.CENTER),this.labelEdge==a.LEFT&&(this.labelEdge=a.TOP),this.labelEdge==a.RIGHT&&(this.labelEdge=a.TOP)):(this.anchorEdge==a.TOP&&(this.anchorEdge=a.CENTER),this.anchorEdge==a.BOTTOM&&(this.anchorEdge=
a.CENTER),this.labelEdge==a.TOP&&(this.labelEdge=a.LEFT),this.labelEdge==a.BOTTOM&&(this.labelEdge=a.LEFT));c=this.effectUnits;this.proximityLeft=this.itemWidth*(c-0.5);this.proximityRight=this.itemWidth*(c-0.5);this.proximityTop=this.itemHeight*(c-0.5);this.proximityBottom=this.itemHeight*(c-0.5);this.anchorEdge==a.LEFT&&(this.proximityLeft=0);this.anchorEdge==a.RIGHT&&(this.proximityRight=0);this.anchorEdge==a.TOP&&(this.proximityTop=0);this.anchorEdge==a.BOTTOM&&(this.proximityBottom=0);this.anchorEdge==
a.CENTER&&(this.proximityLeft/=2,this.proximityRight/=2,this.proximityTop/=2,this.proximityBottom/=2)},startup:function(){this.children=this.getChildren();this._initializePositioning();this._onMouseMoveHandle=m.pausable(k.doc.documentElement,"mousemove",f.hitch(this,"_onMouseMove"));this.conservativeTrigger&&this._onMouseMoveHandle.pause();this.isFixed&&this.own(m(k.doc,"scroll",f.hitch(this,this._onScroll)));this.own(m(k.doc.documentElement,s.leave,f.hitch(this,"_onBodyOut")),n.after(this,"addChild",
f.hitch(this,"_initializePositioning"),!0),n.after(k.global,"onresize",f.hitch(this,"_initializePositioning"),!0))},_initializePositioning:function(){this.itemCount=this.children.length;this.barWidth=(this.isHorizontal?this.itemCount:1)*this.itemWidth;this.barHeight=(this.isHorizontal?1:this.itemCount)*this.itemHeight;this.totalWidth=this.proximityLeft+this.proximityRight+this.barWidth;this.totalHeight=this.proximityTop+this.proximityBottom+this.barHeight;for(var a=0;a<this.children.length;a++){this.children[a].posX=
this.itemWidth*(this.isHorizontal?a:0);this.children[a].posY=this.itemHeight*(this.isHorizontal?0:a);this.children[a].cenX=this.children[a].posX+this.itemWidth/2;this.children[a].cenY=this.children[a].posY+this.itemHeight/2;var c=this.isHorizontal?this.itemWidth:this.itemHeight,d=this.effectUnits*c,b=this.isHorizontal?this.children[a].cenX:this.children[a].cenY,e=this.isHorizontal?this.proximityLeft:this.proximityTop,q=this.isHorizontal?this.proximityRight:this.proximityBottom,f=this.isHorizontal?
this.barWidth:this.barHeight,h=d;h>b+e&&(h=b+e);d>f-b+q&&(d=f-b+q);this.children[a].effectRangeLeft=h/c;this.children[a].effectRangeRght=d/c}g.set(this.domNode,{width:this.barWidth+"px",height:this.barHeight+"px"});for(a=0;a<this.children.length;a++)c=this.children[a],g.set(c.domNode,{left:c.posX+"px",top:c.posY+"px",width:this.itemWidth+"px",height:this.itemHeight+"px"}),g.set(c.imgNode,{left:this.itemPadding+"%",top:this.itemPadding+"%",width:100-2*this.itemPadding+"%",height:100-2*this.itemPadding+
"%"});this._calcHitGrid()},_overElement:function(a,c){a=p.byId(a);var d=c.pageX,b=c.pageY,e=l.position(a,!0),g=e.y,f=g+e.h,h=e.x,e=h+e.w;return d>=h&&d<=e&&b>=g&&b<=f},_onBodyOut:function(a){this._overElement(k.body(),a)||this._setDormant(a)},_setDormant:function(a){this.isOver&&(this.isOver=!1,this.conservativeTrigger&&this._onMouseMoveHandle.pause(),this._onGridMouseMove(-1,-1))},_setActive:function(a){this.isOver||(this.isOver=!0,this.conservativeTrigger&&(this._onMouseMoveHandle.resume(),this.timerScale=
0,this._onMouseMove(a),this._expandSlowly()))},_onMouseMove:function(a){a.pageX>=this.hitX1&&a.pageX<=this.hitX2&&a.pageY>=this.hitY1&&a.pageY<=this.hitY2?(this.isOver||this._setActive(a),this._onGridMouseMove(a.pageX-this.hitX1,a.pageY-this.hitY1)):this.isOver&&this._setDormant(a)},_onScroll:function(){this._calcHitGrid()},onResized:function(){this._calcHitGrid()},_onGridMouseMove:function(a,c){this.pos={x:a,y:c};this._paint()},_paint:function(){var a=this.pos.x,c=this.pos.y;if(!(0>=this.itemCount)){var d=
((this.isHorizontal?a:c)-(this.isHorizontal?this.proximityLeft:this.proximityTop))/(this.isHorizontal?this.itemWidth:this.itemHeight)-0.5,b=0;this.anchorEdge==this.EDGE.BOTTOM&&(b=(c-this.proximityTop)/this.itemHeight,b=0.5<b?1:c/(this.proximityTop+this.itemHeight/2));this.anchorEdge==this.EDGE.TOP&&(b=(c-this.proximityTop)/this.itemHeight,b=0.5>b?1:(this.totalHeight-c)/(this.proximityBottom+this.itemHeight/2));this.anchorEdge==this.EDGE.RIGHT&&(b=(a-this.proximityLeft)/this.itemWidth,b=0.5<b?1:a/
(this.proximityLeft+this.itemWidth/2));this.anchorEdge==this.EDGE.LEFT&&(b=(a-this.proximityLeft)/this.itemWidth,b=0.5>b?1:(this.totalWidth-a)/(this.proximityRight+this.itemWidth/2));this.anchorEdge==this.EDGE.CENTER&&(b=this.isHorizontal?c/this.totalHeight:a/this.totalWidth,0.5<b&&(b=1-b),b*=2);for(a=0;a<this.itemCount;a++)c=this._weighAt(d,a),0>c&&(c=0),this._setItemSize(a,c*b);b=Math.round(d);a=0;0>d?b=0:d>this.itemCount-1?b=this.itemCount-1:a=(d-b)*((this.isHorizontal?this.itemWidth:this.itemHeight)-
this.children[b].sizeMain);this._positionElementsFrom(b,a)}},_weighAt:function(a,c){var d=Math.abs(a-c),b=0<a-c?this.children[c].effectRangeRght:this.children[c].effectRangeLeft;return d>b?0:1-d/b},_setItemSize:function(a,c){if(this.children[a].scale!=c){this.children[a].scale=c;c*=this.timerScale;var d=Math.round(this.itemWidth+(this.itemMaxWidth-this.itemWidth)*c),b=Math.round(this.itemHeight+(this.itemMaxHeight-this.itemHeight)*c);if(this.isHorizontal){this.children[a].sizeW=d;this.children[a].sizeH=
b;this.children[a].sizeMain=d;this.children[a].sizeOff=b;var e=0,e=this.anchorEdge==this.EDGE.TOP?this.children[a].cenY-this.itemHeight/2:this.anchorEdge==this.EDGE.BOTTOM?this.children[a].cenY-(b-this.itemHeight/2):this.children[a].cenY-b/2;this.children[a].usualX=Math.round(this.children[a].cenX-d/2);g.set(this.children[a].domNode,{top:e+"px",left:this.children[a].usualX+"px"})}else this.children[a].sizeW=d,this.children[a].sizeH=b,this.children[a].sizeOff=d,this.children[a].sizeMain=b,e=0,e=this.anchorEdge==
this.EDGE.LEFT?this.children[a].cenX-this.itemWidth/2:this.anchorEdge==this.EDGE.RIGHT?this.children[a].cenX-(d-this.itemWidth/2):this.children[a].cenX-d/2,this.children[a].usualY=Math.round(this.children[a].cenY-b/2),g.set(this.children[a].domNode,{left:e+"px",top:this.children[a].usualY+"px"});g.set(this.children[a].domNode,{width:d+"px",height:b+"px"});this.children[a].svgNode&&this.children[a].svgNode.setSize(d,b)}},_positionElementsFrom:function(a,c){var d=0,b,e;this.isHorizontal?(b="usualX",
e="left"):(b="usualY",e="top");d=Math.round(this.children[a][b]+c);g.get(this.children[a].domNode,e)!=d+"px"&&(g.set(this.children[a].domNode,e,d+"px"),this._positionLabel(this.children[a]));var f=d;for(b=a-1;0<=b;b--)f-=this.children[b].sizeMain,g.get(this.children[a].domNode,e)!=f+"px"&&(g.set(this.children[b].domNode,e,f+"px"),this._positionLabel(this.children[b]));for(b=a+1;b<this.itemCount;b++)d+=this.children[b-1].sizeMain,g.get(this.children[a].domNode,e)!=d+"px"&&(g.set(this.children[b].domNode,
e,d+"px"),this._positionLabel(this.children[b]))},_positionLabel:function(a){var c=0,d=0,b=l.getMarginBox(a.lblNode);this.labelEdge==this.EDGE.TOP&&(c=Math.round(a.sizeW/2-b.w/2),d=-b.h);this.labelEdge==this.EDGE.BOTTOM&&(c=Math.round(a.sizeW/2-b.w/2),d=a.sizeH);this.labelEdge==this.EDGE.LEFT&&(c=-b.w,d=Math.round(a.sizeH/2-b.h/2));this.labelEdge==this.EDGE.RIGHT&&(c=a.sizeW,d=Math.round(a.sizeH/2-b.h/2));g.set(a.lblNode,{left:c+"px",top:d+"px"})},_calcHitGrid:function(){var a=l.position(this.domNode,
!0);this.hitX1=a.x-this.proximityLeft;this.hitY1=a.y-this.proximityTop;this.hitX2=this.hitX1+this.totalWidth;this.hitY2=this.hitY1+this.totalHeight},_toEdge:function(a,c){return this.EDGE[a.toUpperCase()]||c},_expandSlowly:function(){this.isOver&&(this.timerScale+=0.2,this._paint(),1>this.timerScale&&setTimeout(f.hitch(this,"_expandSlowly"),10))}})});
//@ sourceMappingURL=FisheyeList.js.map