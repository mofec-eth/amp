//>>built
define("dojox/charting/action2d/Magnify",["dojo/_base/connect","dojo/_base/declare","./PlotAction","dojox/gfx/matrix","dojox/gfx/fx","dojo/fx","dojo/fx/easing"],function(l,m,n,k,g,p,q){return m("dojox.charting.action2d.Magnify",n,{defaultParams:{duration:400,easing:q.backOut,scale:2},optionalParams:{},constructor:function(a,c,d){this.scale=d&&"number"==typeof d.scale?d.scale:2;this.connect()},process:function(a){if(a.shape&&(a.type in this.overOutEvents&&"cx"in a&&"cy"in a)&&!("spider_plot"==a.element||
"spider_poly"==a.element)){var c=a.run.name,d=a.index,f=[],e,b,h;c in this.anim?e=this.anim[c][d]:this.anim[c]={};e?e.action.stop(!0):this.anim[c][d]=e={};"onmouseover"==a.type?(b=k.identity,h=this.scale):(b=k.scaleAt(this.scale,a.cx,a.cy),h=1/this.scale);b={shape:a.shape,duration:this.duration,easing:this.easing,transform:[{name:"scaleAt",start:[1,a.cx,a.cy],end:[h,a.cx,a.cy]},b]};a.shape&&f.push(g.animateTransform(b));a.outline&&(b.shape=a.outline,f.push(g.animateTransform(b)));a.shadow&&(b.shape=
a.shadow,f.push(g.animateTransform(b)));f.length?(e.action=p.combine(f),"onmouseout"==a.type&&l.connect(e.action,"onEnd",this,function(){this.anim[c]&&delete this.anim[c][d]}),e.action.play()):delete this.anim[c][d]}}})});
//@ sourceMappingURL=Magnify.js.map