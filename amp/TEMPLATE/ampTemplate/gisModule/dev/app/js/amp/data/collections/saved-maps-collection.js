var Deferred = require('jquery').Deferred;
var Backbone = require('backbone');
var SavedMapModel = require('../models/saved-map-model');

module.exports = Backbone.Collection.extend({

  url: '/rest/gis/saved-maps',
  model: SavedMapModel,

  load: function(stateId) {
    var deferred = new Deferred();
    var loaded = this.get(stateId);
    if (loaded) {
      deferred.resolve(loaded);
    } else {
      var model = this.model.fromId(stateId);
      this.add(model);  // sets up collection so the model can find a URL
      model.fetch().done(function() {
        deferred.resolve(model);
      });
    }

    return deferred.promise();
  }

});