var _ = require('underscore');
var Backbone = require('backbone');

// classes for instanceof checks
var IndicatorJoin = require('../../data/models/indicator-join-model');
var IndicatorWMS = require('../../data/models/indicator-wms-model');
var IndicatorArcGIS = require('../../data/models/indicator-arcgis-model');
var ProjectSites = require('../../data/models/structures-collection-model');
var ADMClusters = require('../../data/models/adm-cluster-model');

// model-specific item views
var IndicatorJoinView = require('./legend-item-indicator-join');
var IndicatorArcGISView = require('./legend-item-indicator-arcgis');
var IndicatorWMSView = require('./legend-item-indicator-wms');
var ProjectSitesView = require('./legend-item-structures');
var ADMClustersView = require('./legend-item-adm-clusters');


var Unknown = Backbone.View.extend({

  template: _.template('<h3><%= title %></h3>' +
                       '<em>legend not yet configured for this layer type</em>'),

  render: function() {
    this.$el.html(this.template(this.model.toJSON()));
    return this;
  }

});


module.exports = function(options) {
  if (options.model instanceof IndicatorJoin) {
    return new IndicatorJoinView(options);
  } else if (options.model instanceof IndicatorArcGIS) {
    return new IndicatorArcGISView(options);
  } else if (options.model instanceof IndicatorWMS) {
    return new IndicatorWMSView(options);
  } else if (options.model instanceof ProjectSites) {
    return new ProjectSitesView(options);
  } else if (options.model instanceof ADMClusters) {
    return new ADMClustersView(options);
  } else {
    console.error('legend for layer type not implemented: ', options.model);
    return new Unknown(options);
  }
};
