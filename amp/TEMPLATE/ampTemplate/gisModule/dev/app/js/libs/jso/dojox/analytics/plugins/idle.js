//>>built
define("dojox/analytics/plugins/idle",["dojo/_base/lang","../_base","dojo/_base/config","dojo/ready","dojo/aspect","dojo/_base/window"],function(b,a,d,e,f,g){return a.plugins.idle=new function(){this.addData=b.hitch(a,"addData","idle");this.idleTime=d.idleTime||6E4;this.idle=!0;this.setIdle=function(){this.addData("isIdle");this.idle=!0};e(b.hitch(this,function(){for(var a=["onmousemove","onkeydown","onclick","onscroll"],c=0;c<a.length;c++)f.after(g.doc,a[c],b.hitch(this,function(a){this.idle?(this.idle=
!1,this.addData("isActive")):clearTimeout(this.idleTimer);this.idleTimer=setTimeout(b.hitch(this,"setIdle"),this.idleTime)}),!0)}))}});
//@ sourceMappingURL=idle.js.map