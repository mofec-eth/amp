//>>built
define("dojox/date/timezone",["dojo","dojo/date","dojo/date/locale","dojo/_base/array","dojo/_base/xhr"],function(k,n,r){function D(a){a=a||{};l=k.mixin(l,a.zones||{});v=k.mixin(v,a.rules||{})}function w(a){x[a]=!0;k.xhrGet({url:require.toUrl((y.timezoneFileBasePath||"dojox/date/zoneinfo")+"/"+a),sync:!0,handleAs:"olson-zoneinfo",load:D,error:function(a){console.error("Error loading zone file:",a);throw a;}})}function E(a){throw Error('Timezone "'+a+'" is either incorrect, or not loaded in the timezone registry.');
}function z(a){var c=M[a];if(!c&&(c=a.split("/")[0],c=N[c],!c)){var e=l[a];if("string"==typeof e)return z(e);if(x.backward)E(a);else return w("backward"),z(a)}return c}function A(a){a=a.match(/(\d+)(?::0*(\d*))?(?::0*(\d*))?([su])?$/);if(!a)return null;a[1]=parseInt(a[1],10);a[2]=a[2]?parseInt(a[2],10):0;a[3]=a[3]?parseInt(a[3],10):0;return a}function u(a,c,e,d,b,f,g){return Date.UTC(a,c,e,d,b,f)+6E4*(g||0)}function s(a){var c=A(a);if(null===c)return 0;c=(0===a.indexOf("-")?-1:1)*1E3*(60*(60*c[1]+
c[2])+c[3]);return-c/60/1E3}function F(a,c,e){var d=G[a[3].substr(0,3).toLowerCase()],b=a[4],f=A(a[5]);"u"==f[4]&&(e=0);if(isNaN(b)){if("last"==b.substr(0,4))return b=H[b.substr(4,3).toLowerCase()],a=new Date(u(c,d+1,1,f[1]-24,f[2],f[3],e)),e=n.add(a,"minute",-e).getUTCDay(),b=b>e?b-e-7:b-e,0!==b&&(a=n.add(a,"hour",24*b)),a;b=H[b.substr(0,3).toLowerCase()];if("undefined"!=b){if("\x3e\x3d"==a[4].substr(3,2))return a=new Date(u(c,d,parseInt(a[4].substr(5),10),f[1],f[2],f[3],e)),e=n.add(a,"minute",-e).getUTCDay(),
b=b<e?b-e+7:b-e,0!==b&&(a=n.add(a,"hour",24*b)),a;if("\x3c\x3d"==b.substr(3,2))return a=new Date(u(c,d,parseInt(a[4].substr(5),10),f[1],f[2],f[3],e)),e=n.add(a,"minute",-e).getUTCDay(),b=b>e?b-e-7:b-e,0!==b&&(a=n.add(a,"hour",24*b)),a}}else return a=new Date(u(c,d,parseInt(b,10),f[1],f[2],f[3],e));return null}function O(a,c){var e=[];k.forEach(v[a[1]]||[],function(d){for(var b=0;2>b;b++)switch(d[b]){case "min":d[b]=I;break;case "max":d[b]=B;break;case "only":break;default:if(d[b]=parseInt(d[b],10),
isNaN(d[b]))throw Error("Invalid year found on rule");}"string"==typeof d[6]&&(d[6]=s(d[6]));(d[0]<=c&&d[1]>=c||d[0]==c&&"only"==d[1])&&e.push({r:d,d:F(d,c,a[0])})});return e}function P(a,c){for(var e=C[a]=[],d=0;d<c.length;d++){var b=c[d],f=e[d]=[],g=null,p=null,t=[];"string"==typeof b[0]&&(b[0]=s(b[0]));0===d?f[0]=Date.UTC(I,0,1,0,0,0,0):(f[0]=e[d-1][1],g=c[d-1],p=e[d-1],t=p[2]);for(var q=(new Date(f[0])).getUTCFullYear(),l=b[3]?parseInt(b[3],10):B,h=[];q<=l;q++)h=h.concat(O(b,q));h.sort(function(a,
b){return n.compare(a.d,b.d)});var m,q=0;for(m;m=h[q];q++)l=0<q?h[q-1]:null,0>m.r[5].indexOf("u")&&0>m.r[5].indexOf("s")&&(0===q&&0<d?t.length?m.d=n.add(m.d,"minute",t[t.length-1].r[6]):0===n.compare(new Date(p[1]),m.d,"date")?m.d=new Date(p[1]):m.d=n.add(m.d,"minute",s(g[1])):0<q&&(m.d=n.add(m.d,"minute",l.r[6])));f[2]=h;if(b[3]){var p=parseInt(b[3],10),t=G[(b[4]||"Jan").substr(0,3).toLowerCase()],l=parseInt(b[5]||"1",10),g=A(b[6]||"0"),r=f[1]=u(p,t,l,g[1],g[2],g[3],"u"==g[4]?0:b[0]);isNaN(r)&&(r=
f[1]=F([0,0,0,b[4],b[5],b[6]||"0"],p,"u"==g[4]?0:b[0]).getTime());p=k.filter(h,function(a,b){var c=0<b?6E4*h[b-1].r[6]:0;return a.d.getTime()<r+c});"u"!=g[4]&&"s"!=g[4]&&(f[1]=p.length?f[1]+6E4*p[p.length-1].r[6]:f[1]+6E4*s(b[1]))}else f[1]=Date.UTC(B,11,31,23,59,59,999)}}function J(a,c){for(var e=c,d=l[e];"string"==typeof d;)e=d,d=l[e];if(!d){if(!x.backward)return w("backward",!0),J(a,c);E(e)}C[c]||P(c,d);for(var e=C[c],b=a.getTime(),f=0,g;g=e[f];f++)if(b>=g[0]&&b<g[1])return{zone:d[f],range:e[f],
idx:f};throw Error('No Zone found for "'+c+'" on '+a);}k.experimental("dojox.date.timezone");k.getObject("date.timezone",!0,dojox);var y=k.config,h="africa antarctica asia australasia backward etcetera europe northamerica pacificnew southamerica".split(" "),I=1835,B=2038,x={},l={},C={},v={},K=y.timezoneLoadingScheme||"preloadAll",h=y.defaultZoneFile||("preloadAll"==K?h:"northamerica");k._contentHandlers["olson-zoneinfo"]=function(a){a=k._contentHandlers.text(a).split("\n");for(var c=[],e="",d=null,
e=null,b={zones:{},rules:{}},f=0;f<a.length;f++)if(c=a[f],c.match(/^\s/)&&(c="Zone "+d+c),c=c.split("#")[0],3<c.length)switch(c=c.split(/\s+/),e=c.shift(),e){case "Zone":d=c.shift();c[0]&&(b.zones[d]||(b.zones[d]=[]),b.zones[d].push(c));break;case "Rule":e=c.shift();b.rules[e]||(b.rules[e]=[]);b.rules[e].push(c);break;case "Link":if(b.zones[c[1]])throw Error("Error with Link "+c[1]);b.zones[c[1]]=c[0]}return b};var G={jan:0,feb:1,mar:2,apr:3,may:4,jun:5,jul:6,aug:7,sep:8,oct:9,nov:10,dec:11},H={sun:0,
mon:1,tue:2,wed:3,thu:4,fri:5,sat:6},N={EST:"northamerica",MST:"northamerica",HST:"northamerica",EST5EDT:"northamerica",CST6CDT:"northamerica",MST7MDT:"northamerica",PST8PDT:"northamerica",America:"northamerica",Pacific:"australasia",Atlantic:"europe",Africa:"africa",Indian:"africa",Antarctica:"antarctica",Asia:"asia",Australia:"australasia",Europe:"europe",WET:"europe",CET:"europe",MET:"europe",EET:"europe"},M={"Pacific/Honolulu":"northamerica","Atlantic/Bermuda":"northamerica","Atlantic/Cape_Verde":"africa",
"Atlantic/St_Helena":"africa","Indian/Kerguelen":"antarctica","Indian/Chagos":"asia","Indian/Maldives":"asia","Indian/Christmas":"australasia","Indian/Cocos":"australasia","America/Danmarkshavn":"europe","America/Scoresbysund":"europe","America/Godthab":"europe","America/Thule":"europe","Asia/Yekaterinburg":"europe","Asia/Omsk":"europe","Asia/Novosibirsk":"europe","Asia/Krasnoyarsk":"europe","Asia/Irkutsk":"europe","Asia/Yakutsk":"europe","Asia/Vladivostok":"europe","Asia/Sakhalin":"europe","Asia/Magadan":"europe",
"Asia/Kamchatka":"europe","Asia/Anadyr":"europe","Africa/Ceuta":"europe","America/Argentina/Buenos_Aires":"southamerica","America/Argentina/Cordoba":"southamerica","America/Argentina/Tucuman":"southamerica","America/Argentina/La_Rioja":"southamerica","America/Argentina/San_Juan":"southamerica","America/Argentina/Jujuy":"southamerica","America/Argentina/Catamarca":"southamerica","America/Argentina/Mendoza":"southamerica","America/Argentina/Rio_Gallegos":"southamerica","America/Argentina/Ushuaia":"southamerica",
"America/Aruba":"southamerica","America/La_Paz":"southamerica","America/Noronha":"southamerica","America/Belem":"southamerica","America/Fortaleza":"southamerica","America/Recife":"southamerica","America/Araguaina":"southamerica","America/Maceio":"southamerica","America/Bahia":"southamerica","America/Sao_Paulo":"southamerica","America/Campo_Grande":"southamerica","America/Cuiaba":"southamerica","America/Porto_Velho":"southamerica","America/Boa_Vista":"southamerica","America/Manaus":"southamerica",
"America/Eirunepe":"southamerica","America/Rio_Branco":"southamerica","America/Santiago":"southamerica","Pacific/Easter":"southamerica","America/Bogota":"southamerica","America/Curacao":"southamerica","America/Guayaquil":"southamerica","Pacific/Galapagos":"southamerica","Atlantic/Stanley":"southamerica","America/Cayenne":"southamerica","America/Guyana":"southamerica","America/Asuncion":"southamerica","America/Lima":"southamerica","Atlantic/South_Georgia":"southamerica","America/Paramaribo":"southamerica",
"America/Port_of_Spain":"southamerica","America/Montevideo":"southamerica","America/Caracas":"southamerica"},L={US:"S",Chatham:"S",NZ:"S",NT_YK:"S",Edm:"S",Salv:"S",Canada:"S",StJohns:"S",TC:"S",Guat:"S",Mexico:"S",Haiti:"S",Barb:"S",Belize:"S",CR:"S",Moncton:"S",Swift:"S",Hond:"S",Thule:"S",NZAQ:"S",Zion:"S",ROK:"S",PRC:"S",Taiwan:"S",Ghana:"GMT",SL:"WAT",Chicago:"S",Detroit:"S",Vanc:"S",Denver:"S",Halifax:"S",Cuba:"S",Indianapolis:"S",Starke:"S",Marengo:"S",Pike:"S",Perry:"S",Vincennes:"S",Pulaski:"S",
Louisville:"S",CA:"S",Nic:"S",Menominee:"S",Mont:"S",Bahamas:"S",NYC:"S",Regina:"S",Resolute:"ES",DR:"S",Toronto:"S",Winn:"S"};k.setObject("dojox.date.timezone",{getTzInfo:function(a,c){if("lazyLoad"==K){var e=z(c);if(e)x[e]||w(e);else throw Error("Not a valid timezone ID.");}var d=J(a,c),e=d.zone[0],b;var f=-1;b=d.range[2]||[];for(var g=a.getTime(),h=0,k;k=b[h];h++)g>=k.d.getTime()&&(f=h);b=0<=f?b[f].r:null;e=b?e+b[6]:v[d.zone[1]]&&0<d.idx?e+s(l[c][d.idx-1][1]):e+s(d.zone[1]);g=d.zone;f=g[2];-1<
f.indexOf("%s")?(b?(d=b[7],"-"==d&&(d="")):g[1]in L?d=L[g[1]]:0<d.idx?(d=l[c][d.idx-1][2],d=0>d.indexOf("%s")?f.replace("%s","S")==d?"S":"":""):d="",d=f.replace("%s",d)):-1<f.indexOf("/")?(d=f.split("/"),d=b?d[0===b[6]?0:1]:d[0]):d=f;return{tzOffset:e,tzAbbr:d}},loadZoneData:function(a){D(a)},getAllZones:function(){var a=[],c;for(c in l)a.push(c);a.sort();return a}});"string"==typeof h&&h&&(h=[h]);k.isArray(h)&&k.forEach(h,w);var Q=r.format,R=r._getZone;r.format=function(a,c){c=c||{};c.timezone&&
!c._tzInfo&&(c._tzInfo=dojox.date.timezone.getTzInfo(a,c.timezone));if(c._tzInfo){var e=a.getTimezoneOffset()-c._tzInfo.tzOffset;a=new Date(a.getTime()+6E4*e)}return Q.call(this,a,c)};r._getZone=function(a,c,e){return e._tzInfo?c?e._tzInfo.tzAbbr:e._tzInfo.tzOffset:R.call(this,a,c,e)}});
//@ sourceMappingURL=timezone.js.map