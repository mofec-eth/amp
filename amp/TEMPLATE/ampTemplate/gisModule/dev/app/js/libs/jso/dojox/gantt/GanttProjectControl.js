//>>built
define("dojox/gantt/GanttProjectControl",["./GanttTaskItem","./GanttTaskControl","dijit/focus","dojo/_base/declare","dojo/_base/array","dojo/_base/lang","dojo/date/locale","dojo/request","dojo/on","dojo/dom","dojo/dom-class","dojo/dom-construct","dojo/dom-style","dojo/dom-attr","dojo/dom-geometry","dojo/keys","dojo/domReady!"],function(t,u,v,w,s,n,y,z,q,A,p,d,l,x,B,r){return w("dojox.gantt.GanttProjectControl",[],{constructor:function(a,c){this.project=c;this.ganttChart=a;this.projectNameItem=this.projectItem=this.descrProject=
null;this.posX=this.posY=0;this.previousProject=this.nextProject=null;this.arrTasks=[];this.duration=this.percentage=0},checkWidthProjectNameItem:function(){if(this.projectNameItem.offsetWidth+this.projectNameItem.offsetLeft>this.ganttChart.maxWidthTaskNames){var a=Math.round((this.projectNameItem.offsetWidth+this.projectNameItem.offsetLeft-this.ganttChart.maxWidthTaskNames)/(this.projectNameItem.offsetWidth/this.projectNameItem.firstChild.length)),a=this.project.name.substring(0,this.projectNameItem.firstChild.length-
a-3);this.projectNameItem.innerHTML=a+"..."}},refreshProjectItem:function(a){this.percentage=this.getPercentCompleted();l.set(a,{left:this.posX+"px",width:this.duration*this.ganttChart.pixelsPerWorkHour+"px"});var c=a.firstChild,b=this.duration*this.ganttChart.pixelsPerWorkHour;c.width=(0==b?1:b)+"px";c.style.width=(0==b?1:b)+"px";c=c.rows[0];-1!=this.percentage?(0!=this.percentage&&(b=c.firstChild,b.width=this.percentage+"%",b=b.firstChild,l.set(b,{width:(!this.duration?1:this.percentage*this.duration*
this.ganttChart.pixelsPerWorkHour/100)+"px",height:this.ganttChart.heightTaskItem+"px"})),100!=this.percentage&&(b=c.lastChild,b.width=100-this.percentage+"%",b=b.firstChild,l.set(b,{width:(!this.duration?1:(100-this.percentage)*this.duration*this.ganttChart.pixelsPerWorkHour/100)+"px",height:this.ganttChart.heightTaskItem+"px"}))):(b=c.firstChild,b.width="1px",b=b.firstChild,l.set(b,{width:"1px",height:this.ganttChart.heightTaskItem+"px"}));c=a.lastChild.firstChild;l.set(c,{height:this.ganttChart.heightTaskItem+
"px",width:(!this.duration?1:this.duration*this.ganttChart.pixelsPerWorkHour)+"px"});c.rows[0].firstChild.height=this.ganttChart.heightTaskItem+"px";0==this.project.parentTasks.length&&(a.style.display="none");return a},refreshDescrProject:function(a){l.set(a,{left:this.posX+this.duration*this.ganttChart.pixelsPerWorkHour+10+"px"});0==this.project.parentTasks.length&&(this.descrProject.style.visibility="hidden");return a},postLoadData:function(){},refresh:function(){this.posX=(this.project.startDate-
this.ganttChart.startDate)/36E5*this.ganttChart.pixelsPerHour;this.refreshProjectItem(this.projectItem[0]);this.refreshDescrProject(this.projectItem[0].nextSibling);return this},create:function(){var a=this.ganttChart.contentData.firstChild;this.posX=(this.project.startDate-this.ganttChart.startDate)/36E5*this.ganttChart.pixelsPerHour;if(this.previousProject)if(0<this.previousProject.arrTasks.length){var c=this.ganttChart.getLastChildTask(this.previousProject.arrTasks[this.previousProject.arrTasks.length-
1]);this.posY=parseInt(c.cTaskItem[0].style.top)+this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra}else this.posY=parseInt(this.previousProject.projectItem[0].style.top)+this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra;else this.posY=6;c=this.ganttChart.panelNames.firstChild;this.projectNameItem=this.createProjectNameItem();c.appendChild(this.projectNameItem);this.checkWidthProjectNameItem();this.projectItem=[this.createProjectItem(),[]];a.appendChild(this.projectItem[0]);
a.appendChild(this.createDescrProject());this.adjustPanelTime()},getTaskById:function(a){for(var c=0;c<this.arrTasks.length;c++){var b=this.searchTaskInTree(this.arrTasks[c],a);if(b)return b}return null},searchTaskInTree:function(a,c){if(a.taskItem.id==c)return a;for(var b=0;b<a.childTask.length;b++){var e=a.childTask[b];if(e.taskItem.id==c||0<e.childTask.length&&(e=this.searchTaskInTree(e,c)))return e}return null},shiftProjectItem:function(){for(var a=null,c=null,b=parseInt(this.projectItem[0].style.left),
e=0;e<this.arrTasks.length;e++){var m=this.arrTasks[e],f=parseInt(m.cTaskItem[0].style.left),m=parseInt(m.cTaskItem[0].style.left)+parseInt(m.cTaskItem[0].firstChild.firstChild.width);a||(a=f);c||(c=m);a>f&&(a=f);c<m&&(c=m)}a!=b&&(this.project.startDate=new Date(this.ganttChart.startDate),this.project.startDate.setHours(this.project.startDate.getHours()+a/this.ganttChart.pixelsPerHour));this.projectItem[0].style.left=a+"px";this.resizeProjectItem(c-a);this.duration=Math.round(parseInt(this.projectItem[0].firstChild.width)/
this.ganttChart.pixelsPerWorkHour);this.shiftDescrProject();this.adjustPanelTime()},adjustPanelTime:function(){var a=this.projectItem[0],a=parseInt(a.style.left)+parseInt(a.firstChild.style.width)+this.ganttChart.panelTimeExpandDelta,a=a+this.descrProject.offsetWidth;this.ganttChart.adjustPanelTime(a)},resizeProjectItem:function(a){var c=this.percentage,b=this.projectItem[0];if(0<c&&100>c){b.firstChild.style.width=a+"px";b.firstChild.width=a+"px";b.style.width=a+"px";var e=b.firstChild.rows[0];e.cells[0].firstChild.style.width=
Math.round(a*c/100)+"px";e.cells[0].firstChild.style.height=this.ganttChart.heightTaskItem+"px";e.cells[1].firstChild.style.width=Math.round(a*(100-c)/100)+"px";e.cells[1].firstChild.style.height=this.ganttChart.heightTaskItem+"px";b.lastChild.firstChild.width=a+"px"}else if(0==c||100==c)b.firstChild.style.width=a+"px",b.firstChild.width=a+"px",b.style.width=a+"px",e=b.firstChild.rows[0],e.cells[0].firstChild.style.width=a+"px",e.cells[0].firstChild.style.height=this.ganttChart.heightTaskItem+"px",
b.lastChild.firstChild.width=a+"px"},shiftDescrProject:function(){var a=parseInt(this.projectItem[0].style.left)+this.duration*this.ganttChart.pixelsPerWorkHour+10;this.descrProject.style.left=a+"px";this.descrProject.innerHTML=this.getDescStr()},showDescrProject:function(){var a=parseInt(this.projectItem[0].style.left)+this.duration*this.ganttChart.pixelsPerWorkHour+10;this.descrProject.style.left=a+"px";this.descrProject.style.visibility="visible";this.descrProject.innerHTML=this.getDescStr()},
hideDescrProject:function(){this.descrProject.style.visibility="hidden"},getDescStr:function(){return this.duration/this.ganttChart.hsPerDay+" days,  "+this.duration+" hours"},createDescrProject:function(){var a=this.posX+this.duration*this.ganttChart.pixelsPerWorkHour+10,c=d.create("div",{innerHTML:this.getDescStr(),className:"ganttDescProject"});l.set(c,{left:a+"px",top:this.posY+"px"});this.descrProject=c;0==this.project.parentTasks.length&&(this.descrProject.style.visibility="hidden");return c},
createProjectItem:function(){this.percentage=this.getPercentCompleted();this.duration=this.getDuration();var a=d.create("div",{id:this.project.id,className:"ganttProjectItem"});l.set(a,{left:this.posX+"px",top:this.posY+"px",width:this.duration*this.ganttChart.pixelsPerWorkHour+"px"});var c=d.create("table",{cellPadding:"0",cellSpacing:"0",className:"ganttTblProjectItem"},a),b=this.duration*this.ganttChart.pixelsPerWorkHour;c.width=(0==b?1:b)+"px";c.style.width=(0==b?1:b)+"px";c=c.insertRow(c.rows.length);
-1!=this.percentage?(0!=this.percentage&&(b=d.create("td",{width:this.percentage+"%"},c),b.style.lineHeight="1px",b=d.create("div",{className:"ganttImageProgressFilled"},b),l.set(b,{width:this.percentage*this.duration*this.ganttChart.pixelsPerWorkHour/100+"px",height:this.ganttChart.heightTaskItem+"px"})),100!=this.percentage&&(b=d.create("td",{width:100-this.percentage+"%"},c),b.style.lineHeight="1px",b=d.create("div",{className:"ganttImageProgressBg"},b),l.set(b,{width:(100-this.percentage)*this.duration*
this.ganttChart.pixelsPerWorkHour/100+"px",height:this.ganttChart.heightTaskItem+"px"}))):(b=d.create("td",{width:"1px"},c),b.style.lineHeight="1px",b=d.create("div",{className:"ganttImageProgressBg"},b),l.set(b,{width:"1px",height:this.ganttChart.heightTaskItem+"px"}));c=d.create("div",{className:"ganttDivTaskInfo"});b=d.create("table",{cellPadding:"0",cellSpacing:"0",height:this.ganttChart.heightTaskItem+"px",width:(0==this.duration*this.ganttChart.pixelsPerWorkHour?1:this.duration*this.ganttChart.pixelsPerWorkHour)+
"px"},c).insertRow(0);d.create("td",{align:"center",vAlign:"top",height:this.ganttChart.heightTaskItem+"px",className:"ganttMoveInfo"},b);a.appendChild(c);0==this.project.parentTasks.length&&(a.style.display="none");return a},createProjectNameItem:function(){var a=d.create("div",{className:"ganttProjectNameItem",innerHTML:this.project.name,title:this.project.name});l.set(a,{left:"5px",top:this.posY+"px"});x.set(a,"tabIndex",0);this.ganttChart.isShowConMenu&&(this.ganttChart._events.push(q(a,"mouseover",
n.hitch(this,function(c){p.add(a,"ganttProjectNameItemHover");clearTimeout(this.ganttChart.menuTimer);this.ganttChart.tabMenu.clear();this.ganttChart.tabMenu.show(c.target,this)}))),this.ganttChart._events.push(q(a,"keydown",n.hitch(this,function(a){a.keyCode==r.ENTER&&(this.ganttChart.tabMenu.clear(),this.ganttChart.tabMenu.show(a.target,this));this.ganttChart.tabMenu.isShow&&(a.keyCode==r.LEFT_ARROW||a.keyCode==r.RIGHT_ARROW)&&v(this.ganttChart.tabMenu.menuPanel.firstChild.rows[0].cells[0]);this.ganttChart.tabMenu.isShow&&
a.keyCode==r.ESCAPE&&this.ganttChart.tabMenu.hide()}))),this.ganttChart._events.push(q(a,"mouseout",n.hitch(this,function(){p.remove(a,"ganttProjectNameItemHover");clearTimeout(this.ganttChart.menuTimer);this.ganttChart.menuTimer=setTimeout(n.hitch(this,function(){this.ganttChart.tabMenu.hide()}),200)}))),this.ganttChart._events.push(q(this.ganttChart.tabMenu.menuPanel,"mouseover",n.hitch(this,function(){clearTimeout(this.ganttChart.menuTimer)}))),this.ganttChart._events.push(q(this.ganttChart.tabMenu.menuPanel,
"keydown",n.hitch(this,function(){this.ganttChart.tabMenu.isShow&&event.keyCode==r.ESCAPE&&this.ganttChart.tabMenu.hide()}))),this.ganttChart._events.push(q(this.ganttChart.tabMenu.menuPanel,"mouseout",n.hitch(this,function(){clearTimeout(this.ganttChart.menuTimer);this.ganttChart.menuTimer=setTimeout(n.hitch(this,function(){this.ganttChart.tabMenu.hide()}),200)}))));return a},getPercentCompleted:function(){var a=0;s.forEach(this.project.parentTasks,function(c){a+=parseInt(c.percentage)},this);return 0!=
this.project.parentTasks.length?Math.round(a/this.project.parentTasks.length):-1},getDuration:function(){var a=0,c=0;return 0<this.project.parentTasks.length?(s.forEach(this.project.parentTasks,function(b){c=24*b.duration/this.ganttChart.hsPerDay+(b.startTime-this.ganttChart.startDate)/36E5;c>a&&(a=c)},this),(a-this.posX)/24*this.ganttChart.hsPerDay):0},deleteTask:function(a){if(a=this.getTaskById(a))this.deleteChildTask(a),this.ganttChart.checkPosition()},setName:function(a){a&&(this.project.name=
a,this.projectNameItem.innerHTML=a,this.projectNameItem.title=a,this.checkWidthProjectNameItem(),this.descrProject.innerHTML=this.getDescStr(),this.adjustPanelTime())},setPercentCompleted:function(a){a=parseInt(a);if(isNaN(a)||100<a||0>a)return!1;var c=this.projectItem[0].firstChild.rows[0],b=c.cells[0],e=c.cells[1];0<a&&100>a&&0<this.percentage&&100>this.percentage?(b.width=parseInt(a)+"%",b.firstChild.style.width=a*this.duration*this.ganttChart.pixelsPerWorkHour/100+"px",e.width=100-parseInt(a)+
"%",e.firstChild.style.width=(100-a)*this.duration*this.ganttChart.pixelsPerWorkHour/100+"px"):(0==a||100==a)&&0<this.percentage&&100>this.percentage?0==a?(b.parentNode.removeChild(b),e.width="100%",e.firstChild.style.width=this.duration*this.ganttChart.pixelsPerWorkHour+"px"):100==a&&(e.parentNode.removeChild(e),b.width="100%",b.firstChild.style.width=this.duration*this.ganttChart.pixelsPerWorkHour+"px"):(0==a||100==a)&&(0==this.percentage||100==this.percentage)?0==a&&100==this.percentage?(p.remove(b.firstChild,
"ganttImageProgressFilled"),p.add(b.firstChild,"ganttImageProgressBg")):100==a&&0==this.percentage&&(p.remove(b.firstChild,"ganttImageProgressBg"),p.add(b.firstChild,"ganttImageProgressFilled")):(0<a||100>a)&&(0==this.percentage||100==this.percentage)?(b.parentNode.removeChild(b),b=d.create("td",{width:a+"%"},c),b.style.lineHeight="1px",b=d.create("div",{className:"ganttImageProgressFilled"},b),l.set(b,{width:a*this.duration*this.ganttChart.pixelsPerWorkHour/100+"px",height:this.ganttChart.heightTaskItem+
"px"}),b=d.create("td",{width:100-a+"%"},c),b.style.lineHeight="1px",b=d.create("div",{className:"ganttImageProgressBg"},b),l.set(b,{width:(100-a)*this.duration*this.ganttChart.pixelsPerWorkHour/100+"px",height:this.ganttChart.heightTaskItem+"px"})):-1==this.percentage&&(100==a?(p.remove(b.firstChild,"ganttImageProgressBg"),p.add(b.firstChild,"ganttImageProgressFilled")):100>a&&0<a&&(b.parentNode.removeChild(b),b=d.create("td",{width:a+"%"},c),b.style.lineHeight="1px",b=d.create("div",{className:"ganttImageProgressFilled"},
b),l.set(b,{width:a*this.duration*this.ganttChart.pixelsPerWorkHour/100+"px",height:this.ganttChart.heightTaskItem+"px"}),b=d.create("td",{width:100-a+"%"},c),b.style.lineHeight="1px",b=d.create("div",{className:"ganttImageProgressBg"},b),l.set(b,{width:(100-a)*this.duration*this.ganttChart.pixelsPerWorkHour/100+"px",height:this.ganttChart.heightTaskItem+"px"})));this.percentage=a;this.descrProject.innerHTML=this.getDescStr();return!0},deleteChildTask:function(a){if(a){var c=a.cTaskItem[0],b=a.cTaskNameItem[0],
e=a.cTaskItem[1],m=a.cTaskNameItem[1],f=a.cTaskNameItem[2];"none"==c.style.display&&this.ganttChart.openTree(a.parentTask);if(0<a.childPredTask.length)for(var g=0;g<a.childPredTask.length;g++){for(var d=a.childPredTask[g],h=0;h<d.cTaskItem[1].length;h++)d.cTaskItem[1][h].parentNode.removeChild(d.cTaskItem[1][h]);d.cTaskItem[1]=[];d.predTask=null}if(0<a.childTask.length)for(;0<a.childTask.length;)this.deleteChildTask(a.childTask[0]);g=this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra;
"none"!=c.style.display&&a.shiftCurrentTasks(a,-g);this.project.deleteTask(a.taskItem.id);c&&c.parentNode.removeChild(c);a.descrTask.parentNode.removeChild(a.descrTask);if(0<e.length)for(g=0;g<e.length;g++)e[g].parentNode.removeChild(e[g]);b&&b.parentNode.removeChild(b);if(a.cTaskNameItem[1])for(g=0;g<m.length;g++)m[g].parentNode.removeChild(m[g]);f&&f.parentNode&&f.parentNode.removeChild(f);a.taskIdentifier&&(a.taskIdentifier.parentNode.removeChild(a.taskIdentifier),a.taskIdentifier=null);if(a.parentTask){a.previousChildTask&&
(a.previousChildTask.nextChildTask=a.nextChildTask?a.nextChildTask:null);b=a.parentTask;for(g=0;g<b.childTask.length;g++)if(b.childTask[g].taskItem.id==a.taskItem.id){b.childTask[g]=null;b.childTask.splice(g,1);break}0==b.childTask.length&&b.cTaskNameItem[2]&&(b.cTaskNameItem[2].parentNode.removeChild(b.cTaskNameItem[2]),b.cTaskNameItem[2]=null)}else{a.previousParentTask&&(a.previousParentTask.nextParentTask=a.nextParentTask?a.nextParentTask:null);b=a.project;for(g=0;g<b.arrTasks.length;g++)b.arrTasks[g].taskItem.id==
a.taskItem.id&&b.arrTasks.splice(g,1)}if(a.predTask){b=a.predTask;for(g=0;g<b.childPredTask.length;g++)b.childPredTask[g].taskItem.id==a.taskItem.id&&(b.childPredTask[g]=null,b.childPredTask.splice(g,1))}0!=a.project.arrTasks.length?a.project.shiftProjectItem():(a.project.projectItem[0].style.display="none",this.hideDescrProject());this.ganttChart.contentDataHeight-=this.ganttChart.heightTaskItemExtra+this.ganttChart.heightTaskItem}},insertTask:function(a,c,b,e,d,f,g,l){var h=null,k=null;if(this.project.getTaskById(a))return!1;
if(!e||e<this.ganttChart.minWorkLength)e=this.ganttChart.minWorkLength;if(!c||""==c)c=a;if(!d||""==d)d=0;else if(d=parseInt(d),0>d||100<d)return!1;if(l&&""!=l){k=this.project.getTaskById(l);if(!k)return!1;b=b||k.startTime;if(b<k.startTime)return!1;h=new t({id:a,name:c,startTime:b,duration:e,percentage:d,previousTaskId:f,taskOwner:g});if(!this.ganttChart.checkPosParentTask(k,h))return!1;h.parentTask=k;a=this.getTaskById(k.id);c=!1;"none"==a.cTaskItem[0].style.display?c=!0:a.cTaskNameItem[2]&&(a.isExpanded||
(c=!0));c&&(0==a.childTask.length?this.ganttChart.openTree(a.parentTask):this.ganttChart.openTree(a));if(""!=f){if((f=this.project.getTaskById(f))&&f.parentTask){if(f.parentTask.id!=h.parentTask.id)return!1}else return!1;this.ganttChart.checkPosPreviousTask(f,h)||this.ganttChart.correctPosPreviousTask(f,h);h.previousTask=f}0<k.cldTasks.length&&(k.cldTasks[k.cldTasks.length-1].nextChildTask=h,h.previousChildTask=k.cldTasks[k.cldTasks.length-1]);k.cldTasks.push(h);1==k.cldTasks.length&&(a.cTaskNameItem[2]=
a.createTreeImg());k=new u(h,this,this.ganttChart);k.create();h.nextChildTask&&(k.nextChildTask=k.project.getTaskById(h.nextChildTask.id));k.adjustPanelTime();h=this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra;k.shiftCurrentTasks(k,h)}else{b=b||this.project.startDate;h=new t({id:a,name:c,startTime:b,duration:e,percentage:d,previousTaskId:f,taskOwner:g});if(h.startTime<=this.ganttChart.startDate)return!1;if(""!=f){f=this.project.getTaskById(f);if(!f)return!1;this.ganttChart.checkPosPreviousTask(f,
h)||this.ganttChart.correctPosPreviousTask(f,h);if(f.parentTask)return!1;h.previousTask=f}0<this.project.parentTasks.length&&(this.project.parentTasks[this.project.parentTasks.length-1].nextParentTask=h,h.previousParentTask=this.project.parentTasks[this.project.parentTasks.length-1]);this.project.parentTasks.push(h);k=new u(h,this,this.ganttChart);k.create();h.nextParentTask&&(k.nextParentTask=k.project.getTaskById(h.nextParentTask.id));k.adjustPanelTime();this.arrTasks.push(k);h=this.ganttChart.heightTaskItem+
this.ganttChart.heightTaskItemExtra;k.shiftCurrentTasks(k,h);this.projectItem[0].style.display="inline";this.setPercentCompleted(this.getPercentCompleted());this.shiftProjectItem();this.showDescrProject()}this.ganttChart.checkHeighPanelTasks();this.ganttChart.checkPosition();return k},shiftNextProject:function(a,c){a.nextProject&&(a.nextProject.shiftProject(c),this.shiftNextProject(a.nextProject,c))},shiftProject:function(a){this.posY+=a;this.projectItem[0].style.top=parseInt(this.projectItem[0].style.top)+
a+"px";this.descrProject.style.top=parseInt(this.descrProject.style.top)+a+"px";this.projectNameItem.style.top=parseInt(this.projectNameItem.style.top)+a+"px";0<this.arrTasks.length&&this.shiftNextParentTask(this.arrTasks[0],a)},shiftTask:function(a,c){a.posY+=c;var b=a.cTaskNameItem[0],e=a.cTaskNameItem[1],d=a.cTaskNameItem[2],f=a.cTaskItem[1];b.style.top=parseInt(b.style.top)+c+"px";d&&(d.style.top=parseInt(d.style.top)+c+"px");a.parentTask&&(e[0].style.top=parseInt(e[0].style.top)+c+"px",e[1].style.top=
parseInt(e[1].style.top)+c+"px");a.cTaskItem[0].style.top=parseInt(a.cTaskItem[0].style.top)+c+"px";a.descrTask.style.top=parseInt(a.descrTask.style.top)+c+"px";f[0]&&(f[0].style.top=parseInt(f[0].style.top)+c+"px",f[1].style.top=parseInt(f[1].style.top)+c+"px",f[2].style.top=parseInt(f[2].style.top)+c+"px")},shiftNextParentTask:function(a,c){this.shiftTask(a,c);this.shiftChildTasks(a,c);a.nextParentTask&&this.shiftNextParentTask(a.nextParentTask,c)},shiftChildTasks:function(a,c){s.forEach(a.childTask,
function(a){this.shiftTask(a,c);0<a.childTask.length&&this.shiftChildTasks(a,c)},this)}})});
//@ sourceMappingURL=GanttProjectControl.js.map