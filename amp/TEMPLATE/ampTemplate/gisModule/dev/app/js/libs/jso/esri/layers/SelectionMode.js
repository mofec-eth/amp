//>>built
define("esri/layers/SelectionMode",["dojo/_base/declare","dojo/_base/lang","dojo/has","esri/kernel","esri/layers/RenderMode"],function(a,b,c,d,e){a=a([e],{declaredClass:"esri.layers._SelectionMode",constructor:function(a){this.featureLayer=a;this._featureMap={}},propertyChangeHandler:function(a){this._init&&0===a&&this._applyTimeFilter()},resume:function(){this.propertyChangeHandler(0)}});c("extend-esri")&&b.setObject("layers._SelectionMode",a,d);return a});
//@ sourceMappingURL=SelectionMode.js.map