//>>built
require({cache:{"url:dojox/calc/templates/GraphPro.html":'\x3cdiv class\x3d"dijitReset dijitInline dojoxCalc"\n\x3e\x3ctable class\x3d"dijitReset dijitInline dojoxCalcLayout" data-dojo-attach-point\x3d"calcTable" rules\x3d"none" cellspacing\x3d0 cellpadding\x3d0 border\x3d0\x3e\n\t\x3ctr\n\t\t\x3e\x3ctd colspan\x3d"4" class\x3d"dojoxCalcTextAreaContainer"\n\t\t\t\x3e\x3cdiv class\x3d"dijitTextBox dijitTextArea" style\x3d"height:10em;width:auto;max-width:15.3em;border-width:0px;" data-dojo-attach-point\x3d\'displayBox\'\x3e\x3c/div\n\t\t\x3e\x3c/td\n\t\x3e\x3c/tr\x3e\n\t\x3ctr\n\t\t\x3e\x3ctd colspan\x3d"4" class\x3d"dojoxCalcInputContainer"\n\t\t\t\x3e\x3cinput data-dojo-type\x3d"dijit.form.TextBox" data-dojo-attach-event\x3d"onBlur:onBlur,onKeyPress:onKeyPress" data-dojo-attach-point\x3d\'textboxWidget\'\n\t\t/\x3e\x3c/td\n\t\x3e\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"grapherMakerButton" label\x3d"Graph" data-dojo-attach-event\x3d\'onClick:makeGrapherWindow\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"functionMakerButton" label\x3d"Func" data-dojo-attach-event\x3d\'onClick:makeFunctionWindow\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"toFracButton" label\x3d"toFrac" value\x3d"toFrac(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd colspan\x3d"1" class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\x3c/td\x3e\n\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"seven" label\x3d"7" value\x3d\'7\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"eight" label\x3d"8" value\x3d\'8\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"nine" label\x3d"9" value\x3d\'9\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"divide" label\x3d"/" value\x3d\'/\' data-dojo-attach-event\x3d\'onClick:insertOperator\' /\x3e\n\t\t\x3c/td\x3e\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"four" label\x3d"4" value\x3d\'4\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"five" label\x3d"5" value\x3d\'5\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"six" label\x3d"6" value\x3d\'6\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"multiply" label\x3d"*" value\x3d\'*\' data-dojo-attach-event\x3d\'onClick:insertOperator\' /\x3e\n\t\t\x3c/td\x3e\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"one" label\x3d"1" value\x3d\'1\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"two" label\x3d"2" value\x3d\'2\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"three" label\x3d"3" value\x3d\'3\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"add" label\x3d"+" value\x3d\'+\' data-dojo-attach-event\x3d\'onClick:insertOperator\' /\x3e\n\t\t\x3c/td\x3e\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"decimal" label\x3d"." value\x3d\'.\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"zero" label\x3d"0" value\x3d\'0\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"equals" label\x3d"x\x3dy" value\x3d\'\x3d\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcMinusButtonContainer"\x3e\n\t\t\t\x3cspan data-dojo-type\x3d"dijit.form.ComboButton" data-dojo-attach-point\x3d"subtract" label\x3d\'-\' value\x3d\'-\' data-dojo-attach-event\x3d\'onClick:insertOperator\'\x3e\n\n\t\t\t\t\x3cdiv data-dojo-type\x3d"dijit.Menu" style\x3d"display:none;"\x3e\n\t\t\t\t\t\x3cdiv data-dojo-type\x3d"dijit.MenuItem" data-dojo-attach-event\x3d"onClick:insertMinus"\x3e\n\t\t\t\t\t\t(-)\n\t\t\t\t\t\x3c/div\x3e\n\t\t\t\t\x3c/div\x3e\n\t\t\t\x3c/span\x3e\n\t\t\x3c/td\x3e\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"clear" label\x3d"Clear" data-dojo-attach-event\x3d\'onClick:clearText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"sqrt" label\x3d"\x26#x221A;" value\x3d"\x26#x221A;" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"power" label\x3d"^" value\x3d"^" data-dojo-attach-event\x3d\'onClick:insertOperator\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"factorialButton" label\x3d"!" value\x3d"!" data-dojo-attach-event\x3d\'onClick:insertOperator\' /\x3e\n\t\t\x3c/td\x3e\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"pi" label\x3d"\x26#x03C0;" value\x3d"\x26#x03C0;" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"sin" label\x3d"sin" value\x3d"sin(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"cos" label\x3d"cos" value\x3d"cos(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"tan" label\x3d"tan" value\x3d"tan(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"e" label\x3d"\x26#x03F5;" value\x3d"\x26#x03F5;" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"log10" label\x3d"log" value\x3d"log(" value\x3d"log(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"lnE" label\x3d"ln" value\x3d"ln(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"round" label\x3d"Round" value\x3d"Round(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"intButton" label\x3d"Int" value\x3d"Int(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"PermutationButton" label\x3d"P" value\x3d"P(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"CombinationButton" label\x3d"C" value\x3d"C(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"comma" label\x3d"," value\x3d\',\' data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\n\t\x3c/tr\x3e\n\t\x3ctr\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"AnsButton" label\x3d"Ans" value\x3d"Ans" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"LeftParenButton" label\x3d"(" value\x3d"(" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"RightParenButton" label\x3d")" value\x3d")" data-dojo-attach-event\x3d\'onClick:insertText\' /\x3e\n\t\t\x3c/td\x3e\n\t\t\x3ctd class\x3d"dojoxCalcButtonContainer"\x3e\n\t\t\t\x3cbutton data-dojo-type\x3d"dijit.form.Button" data-dojo-attach-point\x3d"enter" label\x3d"Enter" data-dojo-attach-event\x3d\'onClick:parseTextbox\' /\x3e\n\t\t\x3c/td\x3e\n\t\x3c/tr\x3e\n\x3c/table\x3e\n\x3cspan data-dojo-attach-point\x3d"executor" data-dojo-type\x3d"dojox.calc._Executor" data-dojo-attach-event\x3d"onLoad:executorLoaded"\x3e\x3c/span\x3e\n\x3c/div\x3e\n'}});
define("dojox/calc/GraphPro",["dojo/_base/declare","dojo/_base/lang","dojo/_base/window","dojo/dom-style","dojo/dom-construct","dojo/dom-geometry","dojo/ready","dojox/calc/Standard","dojox/calc/Grapher","dojox/layout/FloatingPane","dojo/text!./templates/GraphPro.html","dojox/calc/_Executor","dijit/Menu","dijit/MenuItem","dijit/form/ComboButton","dijit/form/Button","dijit/form/TextBox"],function(l,e,g,m,c,h,n,p,f,r,q){return l("dojox.calc.GraphPro",p,{templateString:q,grapher:null,funcMaker:null,aFloatingPane:null,executorLoaded:function(){this.inherited(arguments);
n(e.hitch(this,function(){null==this.writeStore&&"functionMakerButton"in this&&m.set(this.functionMakerButton.domNode,{visibility:"hidden"})}))},makeFunctionWindow:function(){var b=g.body(),d=c.create("div");b.appendChild(d);this.aFloatingPane=new dojox.layout.FloatingPane({resizable:!1,dockable:!0,maxable:!1,closable:!0,duration:300,title:"Function Window",style:"position:absolute;left:10em;top:10em;width:50em;"},d);b=c.create("div");this.funcMaker=new f.FuncGen({writeStore:this.writeStore,readStore:this.readStore,
functions:this.functions,deleteFunction:this.executor.deleteFunction,onSaved:function(){var b,a;if(""==(b=this.combo.get("value")))this.status.set("value","The function needs a name");else if(""==(a=this.textarea.get("value")))this.status.set("value","The function needs a body");else{var k=this.args.get("value");b in this.functions||(this.combo.item=this.writeStore.put({name:b,args:k,body:a}));this.saveFunction(b,k,a);this.status.set("value","Function "+b+" was saved")}},saveFunction:e.hitch(this,
this.saveFunction)},b);this.aFloatingPane.set("content",this.funcMaker);this.aFloatingPane.startup();this.aFloatingPane.bringToTop()},makeGrapherWindow:function(){var b=g.body(),d=c.create("div");b.appendChild(d);this.aFloatingPane=new dojox.layout.FloatingPane({resizable:!1,dockable:!0,maxable:!1,closable:!0,duration:300,title:"Graph Window",style:"position:absolute;left:10em;top:5em;width:50em;"},d);var e=this,b=c.create("div");this.grapher=new f.Grapher({myPane:this.aFloatingPane,drawOne:function(a){this.array[a][this.chartIndex].resize(this.graphWidth.get("value"),
this.graphHeight.get("value"));this.array[a][this.chartIndex].axes.x.max=this.graphMaxX.get("value");if(""==this.array[a][this.expressionIndex].get("value"))this.setStatus(a,"Error");else{var b,d="y\x3d"==this.array[a][this.functionMode];this.array[a][this.expressionIndex].get("value")!=this.array[a][this.evaluatedExpression]?(b="x",d||(b="y"),b=e.executor.Function("",b,"return "+this.array[a][this.expressionIndex].get("value")),this.array[a][this.evaluatedExpression]=this.array[a][this.expressionIndex].value,
this.array[a][this.functionRef]=b):b=this.array[a][this.functionRef];var c=this.array[a][this.colorIndex].get("value");c||(c="black");f.draw(this.array[a][this.chartIndex],b,{graphNumber:this.array[a][this.funcNumberIndex],fOfX:d,color:{stroke:{color:c}}});this.setStatus(a,"Drawn")}},onDraw:function(){for(var a=0;a<this.rowCount;a++)!this.dirty&&this.array[a][this.checkboxIndex].get("checked")||this.dirty&&"Drawn"==this.array[a][this.statusIndex].innerHTML?this.drawOne(a):(this.array[a][this.chartIndex].resize(this.graphWidth.get("value"),
this.graphHeight.get("value")),this.array[a][this.chartIndex].axes.x.max=this.graphMaxX.get("value"));var a=h.position(this.outerDiv).y-h.position(this.myPane.domNode).y,a=Math.abs(2*a),a=""+Math.max(parseInt(this.graphHeight.get("value"))+50,this.outerDiv.scrollHeight+a),b=""+(parseInt(this.graphWidth.get("value"))+this.outerDiv.scrollWidth);this.myPane.resize({w:b,h:a})}},b);this.aFloatingPane.set("content",this.grapher);this.aFloatingPane.startup();this.aFloatingPane.bringToTop()}})});
//@ sourceMappingURL=GraphPro.js.map