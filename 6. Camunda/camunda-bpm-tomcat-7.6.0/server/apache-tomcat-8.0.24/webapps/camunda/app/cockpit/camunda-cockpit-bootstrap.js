window.__define("camunda-cockpit-bootstrap",["./scripts/camunda-cockpit-ui"],function(){"use strict";var a=window.CamundaCockpitUi;requirejs.config({baseUrl:"../../../lib"});var b=window;a.exposePackages(b),window.define=window.__define,window.require=window.__require,requirejs(["globalize"],function(c){c(requirejs,["angular","camunda-commons-ui","camunda-bpm-sdk-js","jquery","angular-data-depend","moment","events"],b);var d=window.PLUGIN_PACKAGES||[],e=window.PLUGIN_DEPENDENCIES||[];d.forEach(function(a){var b=document.createElement("link");b.setAttribute("rel","stylesheet"),b.setAttribute("href",a.location+"/plugin.css"),document.head.appendChild(b)}),requirejs.config({packages:d,baseUrl:"../",paths:{ngDefine:"../../lib/ngDefine"}});var f=["angular","ngDefine"].concat(e.map(function(a){return a.requirePackageName}));requirejs(f,function(b){if("undefined"!=typeof window.camCockpitConf&&window.camCockpitConf.customScripts){var c=window.camCockpitConf.customScripts||{},d={};["baseUrl","paths","bundles","shim","map","config","packages","waitSeconds","context","callback","enforceDefine","xhtml","urlArgs","scriptType"].forEach(function(a){c[a]&&(d[a]=c[a])}),requirejs.config(d),requirejs(c.deps||[],function(){b.module("cam.cockpit.custom",c.ngDeps),window.define=void 0,window.require=void 0,a(e)})}else b.module("cam.cockpit.custom",[]),require([],function(){window.define=void 0,window.require=void 0,a(e)})})})}),requirejs(["camunda-cockpit-bootstrap"],function(){});