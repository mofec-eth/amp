//>>built
define("dojox/highlight/widget/Code",["dojo/_base/declare","dojo/_base/lang","dojo/_base/array","dojo/query","dojo/dom-class","dojo/dom-attr","dojo/dom-construct","dojo/request/xhr","dijit/_Widget","dijit/_Templated","dojox/highlight"],function(f,c,g,e,h,k,l,m,n,p){return f("dojox.highlight.widget.Code",[n,p],{url:"",range:null,style:"",listType:"1",lang:"",templateString:'\x3cdiv class\x3d"formatted" style\x3d"${style}"\x3e\x3cdiv class\x3d"titleBar"\x3e\x3c/div\x3e\x3col type\x3d"${listType}" dojoAttachPoint\x3d"codeList" class\x3d"numbers"\x3e\x3c/ol\x3e\x3cdiv style\x3d"display:none" dojoAttachPoint\x3d"containerNode"\x3e\x3c/div\x3e\x3c/div\x3e',
postCreate:function(){this.inherited(arguments);this.url?m(this.url,{}).then(c.hitch(this,"_populate"),c.hitch(this,"_loadError")):this._populate(this.containerNode.innerHTML)},_populate:function(a){this.containerNode.innerHTML="\x3cpre\x3e\x3ccode class\x3d'"+this.lang+"'\x3e"+a.replace(/\</g,"\x26lt;")+"\x3c/code\x3e\x3c/pre\x3e";e("pre \x3e code",this.containerNode).forEach(dojox.highlight.init);a=this.containerNode.innerHTML.split("\n");g.forEach(a,function(a,b){var d=l.create("li");h.add(d,0!==
b%2?"even":"odd");a=("\x3cpre\x3e\x3ccode\x3e"+a+"\x26nbsp;\x3c/code\x3e\x3c/pre\x3e").replace(/\t/g," \x26nbsp; ");d.innerHTML=a;this.codeList.appendChild(d)},this);this._lines=e("li",this.codeList);this._updateView()},setRange:function(a){a instanceof Array&&(this.range=a,this._updateView())},_updateView:function(){if(this.range){var a=this.range;this._lines.style({display:"none"}).filter(function(c,b){return b+1>=a[0]&&b+1<=a[1]}).style({display:""});k.set(this.codeList,"start",a[0])}},_loadError:function(a){console.warn("loading: ",
this.url," FAILED",a)}})});
//@ sourceMappingURL=Code.js.map