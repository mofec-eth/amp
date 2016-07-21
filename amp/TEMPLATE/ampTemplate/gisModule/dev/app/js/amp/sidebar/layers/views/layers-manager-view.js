var Backbone = require('backbone');
var _ = require('underscore');
var fs = require('fs');
var Template = fs.readFileSync(__dirname + '/../templates/layers-manager-template.html', 'utf8');
var IndicatorLayerManager = require('gis-layers-manager/src/index');

module.exports = Backbone.View.extend({
    id: 'tool-layers-manager',
    title: 'Statistical Layer Manager',
    iconClass: 'ampicon-layers',
    template: _.template(Template),
    events: {
        'click .btn-layers-manager': 'showLayersManager'
    },

    initialize: function(options) {
    	this.sections = options.sections;
    },
    render: function() {
    	var self = this;
        this.$el.html(this.template({title: this.title}));
        var self = this;
        this.layersManager = new IndicatorLayerManager({
            draggable: true,
            caller: 'GIS',
            el: this.$('#layers-manager-popup')
        });
        this.layersManager.on('cancel', function() {        	 
            self.$('#layers-manager-popup').hide();
            _.each(self.sections, function(section) {
            	section.reloadData();              	
       	    });
        });
        this.$('#layers-manager-popup').hide();
        return this;
    },

    showLayersManager: function (e) {
        this.layersManager.show();
        this.$('#layers-manager-popup').show();
    }

});