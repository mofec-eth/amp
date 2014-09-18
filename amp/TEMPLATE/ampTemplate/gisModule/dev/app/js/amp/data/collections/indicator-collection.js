var _ = require('underscore');
var Backbone = require('backbone');
var RadioMixin = require('../../mixins/radio-mixin');
var JoinIndicator = require('../models/indicator-join-model');
var ArcGISIndicator = require('../models/indicator-arcgis-model');
var WMSIndicator = require('../models/indicator-wms-model');


module.exports = Backbone.Collection
.extend(RadioMixin).extend({

  url: '/rest/gis/indicator-layers',

  parse: function(data) {
    var parsedData = data;
    parsedData = _.filter(data, function(layer) {
      switch (layer.type) {
        case 'joinBoundaries':
        case 'wms':
        case 'arcgis':
          return true;
        default:
          return false;
      }
    });

    return parsedData;
  },

  model: function(attrs) {
    var typeName = attrs.type;

    switch (typeName) {
      case 'joinBoundaries':
        return new JoinIndicator(attrs);
      case 'wms':
        return new WMSIndicator(attrs);
      case 'arcgis':
        return new ArcGISIndicator(attrs);
      default:
        console.error('Unrecognized indicator type. Check parse function.: ' + attrs.type);
        return new Backbone.Model();
    }
  },

  initialize: function(models, options) {
    this.boundaries = options.boundaries;
  }

});