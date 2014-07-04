//>>built
define("dojox/widget/UpgradeBar",["dojo/_base/kernel","dojo/_base/array","dojo/_base/connect","dojo/_base/declare","dojo/_base/fx","dojo/_base/lang","dojo/_base/sniff","dojo/_base/window","dojo/dom-attr","dojo/dom-class","dojo/dom-construct","dojo/dom-geometry","dojo/dom-style","dojo/cache","dojo/cookie","dojo/domReady","dojo/fx","dojo/window","dijit/_WidgetBase","dijit/_TemplatedMixin"],function(e,l,m,n,c,f,p,d,q,g,r,s,b,t,h,u,k,v,w,x){e.experimental("dojox.widget.UpgradeBar");return n("dojox.widget.UpgradeBar",[w,x],{notifications:[],
buttonCancel:"Close for now",noRemindButton:"Don't Remind Me Again",templateString:t("dojox.widget","UpgradeBar/UpgradeBar.html"),constructor:function(a,b){!a.notifications&&b&&l.forEach(b.childNodes,function(a){if(1==a.nodeType){var b=q.get(a,"validate");this.notifications.push({message:a.innerHTML,validate:function(){var a=!0;try{a=e.eval(b)}catch(c){}return a}})}},this)},checkNotifications:function(){if(this.notifications.length)for(var a=0;a<this.notifications.length;a++)if(this.notifications[a].validate()){this.notify(this.notifications[a].message);
break}},postCreate:function(){this.inherited(arguments);this.domNode.parentNode&&b.set(this.domNode,"display","none");f.mixin(this.attributeMap,{message:{node:"messageNode",type:"innerHTML"}});this.noRemindButton||r.destroy(this.dontRemindButtonNode);if(6==p("ie")){var a=this,c=function(){var c=v.getBox();b.set(a.domNode,"width",c.w+"px")};this.connect(window,"resize",function(){c()});c()}u(f.hitch(this,"checkNotifications"))},notify:function(a){h("disableUpgradeReminders")||((!this.domNode.parentNode||
!this.domNode.parentNode.innerHTML)&&document.body.appendChild(this.domNode),b.set(this.domNode,"display",""),a&&this.set("message",a))},show:function(){this._bodyMarginTop=b.get(d.body(),"marginTop");this._size=s.getContentBox(this.domNode).h;b.set(this.domNode,{display:"block",height:0,opacity:0});this._showAnim||(this._showAnim=k.combine([c.animateProperty({node:d.body(),duration:500,properties:{marginTop:this._bodyMarginTop+this._size}}),c.animateProperty({node:this.domNode,duration:500,properties:{height:this._size,
opacity:1}})]));this._showAnim.play()},hide:function(){this._hideAnim||(this._hideAnim=k.combine([c.animateProperty({node:d.body(),duration:500,properties:{marginTop:this._bodyMarginTop}}),c.animateProperty({node:this.domNode,duration:500,properties:{height:0,opacity:0}})]),m.connect(this._hideAnim,"onEnd",this,function(){b.set(this.domNode,{display:"none",opacity:1})}));this._hideAnim.play()},_onDontRemindClick:function(){h("disableUpgradeReminders",!0,{expires:3650});this.hide()},_onCloseEnter:function(){g.add(this.closeButtonNode,
"dojoxUpgradeBarCloseIcon-hover")},_onCloseLeave:function(){g.remove(this.closeButtonNode,"dojoxUpgradeBarCloseIcon-hover")}})});
//@ sourceMappingURL=UpgradeBar.js.map