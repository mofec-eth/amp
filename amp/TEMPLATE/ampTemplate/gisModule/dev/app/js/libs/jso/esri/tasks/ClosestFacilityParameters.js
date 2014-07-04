//>>built
define("esri/tasks/ClosestFacilityParameters",["dojo/_base/declare","dojo/_base/lang","dojo/_base/json","dojo/has","esri/kernel","esri/lang","esri/graphicsUtils","esri/tasks/NATypes"],function(e,g,c,h,k,l,f,m){e=e(null,{declaredClass:"esri.tasks.ClosestFacilityParameters",accumulateAttributes:null,attributeParameterValues:null,defaultCutoff:null,defaultTargetFacilityCount:null,directionsLanguage:null,directionsLengthUnits:null,directionsOutputType:null,directionsStyleName:null,directionsTimeAttribute:null,
doNotLocateOnRestrictedElements:!0,facilities:null,impedanceAttribute:null,incidents:null,outputGeometryPrecision:null,outputGeometryPrecisionUnits:null,outputLines:null,outSpatialReference:null,pointBarriers:null,polygonBarriers:null,polylineBarriers:null,restrictionAttributes:null,restrictUTurns:null,returnDirections:!1,returnFacilities:!1,returnIncidents:!1,returnPointBarriers:!1,returnPolylgonBarriers:!1,returnPolylineBarriers:!1,returnRoutes:!0,travelDirection:null,useHierarchy:!1,timeOfDay:null,
timeOfDayUsage:null,toJson:function(d){var b={returnDirections:this.returnDirections,returnFacilities:this.returnFacilities,returnIncidents:this.returnIncidents,returnBarriers:this.returnPointBarriers,returnPolygonBarriers:this.returnPolygonBarriers,returnPolylineBarriers:this.returnPolylineBarriers,returnCFRoutes:this.returnRoutes,useHierarchy:this.useHierarchy,attributeParameterValues:this.attributeParameterValues&&c.toJson(this.attributeParameterValues),defaultCutoff:this.defaultCutoff,defaultTargetFacilityCount:this.defaultTargetFacilityCount,
directionsLanguage:this.directionsLanguage,directionsLengthUnits:m.LengthUnit[this.directionsLengthUnits],directionsTimeAttributeName:this.directionsTimeAttribute,impedanceAttributeName:this.impedanceAttribute,outputGeometryPrecision:this.outputGeometryPrecision,outputGeometryPrecisionUnits:this.outputGeometryPrecisionUnits,outputLines:this.outputLines,outSR:this.outSpatialReference?this.outSpatialReference.wkid||c.toJson(this.outSpatialReference.toJson()):null,restrictionAttributeNames:this.restrictionAttributes?
this.restrictionAttributes.join(","):null,restrictUTurns:this.restrictUTurns,accumulateAttributeNames:this.accumulateAttributes?this.accumulateAttributes.join(","):null,travelDirection:this.travelDirection,timeOfDay:this.timeOfDay&&this.timeOfDay.getTime(),directionsStyleName:this.directionsStyleName};if(this.directionsOutputType)switch(this.directionsOutputType.toLowerCase()){case "complete":b.directionsOutputType="esriDOTComplete";break;case "complete-no-events":b.directionsOutputType="esriDOTCompleteNoEvents";
break;case "instructions-only":b.directionsOutputType="esriDOTInstructionsOnly";break;case "standard":b.directionsOutputType="esriDOTStandard";break;case "summary-only":b.directionsOutputType="esriDOTSummaryOnly";break;default:b.directionsOutputType=this.directionsOutputType}if(this.timeOfDayUsage){var a;switch(this.timeOfDayUsage.toLowerCase()){case "start":a="esriNATimeOfDayUseAsStartTime";break;case "end":a="esriNATimeOfDayUseAsEndTime";break;default:a=this.timeOfDayUsage}b.timeOfDayUsage=a}a=
this.incidents;"esri.tasks.FeatureSet"===a.declaredClass&&0<a.features.length?b.incidents=c.toJson({type:"features",features:f._encodeGraphics(a.features,d&&d["incidents.features"]),doNotLocateOnRestrictedElements:this.doNotLocateOnRestrictedElements}):"esri.tasks.DataLayer"===a.declaredClass?b.incidents=a:"esri.tasks.DataFile"===a.declaredClass&&(b.incidents=c.toJson({type:"features",url:a.url,doNotLocateOnRestrictedElements:this.doNotLocateOnRestrictedElements}));a=this.facilities;"esri.tasks.FeatureSet"===
a.declaredClass&&0<a.features.length?b.facilities=c.toJson({type:"features",features:f._encodeGraphics(a.features,d&&d["facilities.features"]),doNotLocateOnRestrictedElements:this.doNotLocateOnRestrictedElements}):"esri.tasks.DataLayer"===a.declaredClass?b.facilities=a:"esri.tasks.DataFile"===a.declaredClass&&(b.facilities=c.toJson({type:"features",url:a.url,doNotLocateOnRestrictedElements:this.doNotLocateOnRestrictedElements}));a=function(a,b){return!a?null:"esri.tasks.FeatureSet"===a.declaredClass?
0<a.features.length?c.toJson({type:"features",features:f._encodeGraphics(a.features,d&&d[b])}):null:"esri.tasks.DataLayer"===a.declaredClass?a:"esri.tasks.DataFile"===a.declaredClass?c.toJson({type:"features",url:a.url}):c.toJson(a)};this.pointBarriers&&(b.barriers=a(this.pointBarriers,"pointBarriers.features"));this.polygonBarriers&&(b.polygonBarriers=a(this.polygonBarriers,"polygonBarriers.features"));this.polylineBarriers&&(b.polylineBarriers=a(this.polylineBarriers,"polylineBarriers.features"));
return l.filter(b,function(a){if(null!==a)return!0})}});h("extend-esri")&&g.setObject("tasks.ClosestFacilityParameters",e,k);return e});
//@ sourceMappingURL=ClosestFacilityParameters.js.map