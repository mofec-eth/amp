import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as reportsActions from '../actions/ReportsActions';
import * as commonListsActions from '../actions/CommonListsActions';
import * as startUp from '../actions/StartUpAction';
import * as Constants from '../common/Constants';
import Utils from '../common/Utils';
import PagingSection from './PagingSection';
import YearsFilterSection from './YearsFilterSection';
import RemarksPopup from './RemarksPopup';
import ToolBar from './ToolBar';
import { Modal } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
export default class Report5a extends Component {
    constructor( props, context ) {
        super( props, context );
        this.state = { recordsPerPage: 150, hierarchy: 'donor-agency', selectedYear: null, selectedDonor: "", remarksUrl:null, showRemarks: false};
        this.showFilters = this.showFilters.bind( this );
        this.showSettings = this.showSettings.bind( this );        
        this.onDonorFilterChange = this.onDonorFilterChange.bind( this );
        this.toggleHierarchy = this.toggleHierarchy.bind( this );  
      }

    componentDidMount() {
        this.initializeFiltersAndSettings();
    }

    initializeFiltersAndSettings() {
        this.filter = Utils.initializeFilterWidget();
        this.settingsWidget = Utils.initializeSettingsWidget()
        this.props.actions.getYears()
        this.props.actions.getOrgList(false);
        this.fetchReportData();
        
    }

    showFilters() {
        Utils.showFilters(this.refs.filterPopup, this.filter, this.onFilterApply.bind(this), this.onFilterCancel.bind(this));
    }
    
    onFilterApply() {
        this.resetQuickFilters();
        this.fetchReportData();
        $( this.refs.filterPopup ).hide();
    }
    
    onFilterCancel() {
        $( this.refs.filterPopup ).hide(); 
    }

    showSettings() {
       Utils.showSettings(this.refs.settingsPopup, this.settingsWidget, this.onSettingsApply.bind(this), this.onSettingsCancel.bind(this));       
    }
    
    onSettingsCancel() {
        $( this.refs.settingsPopup ).hide();
    }

    onSettingsApply(){
        this.fetchReportData();
        $( this.refs.settingsPopup ).hide();
    }
    
    getRecordsPerPage(recordsPerPage) {               
        return (this.props.mainReport && this.props.mainReport.page) ? this.props.mainReport.page.recordsPerPage : this.state.recordsPerPage;           
    }
    
    getRequestData() {
        let requestData = {
            "hierarchy": this.state.hierarchy,
            "page": 1,
            "recordsPerPage": this.getRecordsPerPage()
        };

        requestData.filters = this.filter.serialize().filters;        
        requestData.settings = this.settingsWidget.toAPIFormat();        
        if(this.state.hierarchy === 'donor-agency'){
            requestData.filters[this.state.hierarchy] = requestData.filters[this.state.hierarchy] || [];
            if (this.state.selectedDonor && requestData.filters[this.state.hierarchy].indexOf(this.state.selectedDonor) == -1) {
                requestData.filters[this.state.hierarchy].push(this.state.selectedDonor); 
            }
        }
        
        //TODO: to be removed - hack for BE bug 
        if(!requestData.filters.date){
            requestData.filters.date = {
                    'start': 1970 + '-01-01',
                    'end': 2030 + '-12-31'
                };  
        }        
          
        return requestData
    } 

    fetchReportData( data ) {
        let requestData = data || this.getRequestData();
        this.props.actions.fetchReport5aMainReport( requestData, '5a' );
    }
    
    onDonorFilterChange( e ) {
        this.setState( { selectedDonor: parseInt( e.target.value ) }, function() {
            let filters = this.filter.serialize().filters;
            delete filters['donor-group'];
            delete filters['donor-agency'];
            filters[this.state.hierarchy] = [];
            filters[this.state.hierarchy].push( this.state.selectedDonor);
            this.filter.deserialize({filters: filters});
            this.fetchReportData();
        }.bind( this ) );
    }

    onYearClick( selectedYear ) {
        this.setState( { selectedYear: selectedYear }, function() {                      
            let filters = this.filter.serialize().filters;
            filters.date = {};
            if (this.state.selectedYear) {
                filters.date = {
                        'start': this.state.selectedYear + '-01-01',
                        'end': this.state.selectedYear + '-12-31'
                    };  
            }           
            this.filter.deserialize({filters: filters});            
            this.fetchReportData();
        }.bind( this ) );

    }

    resetQuickFilters() {
        let filters = this.filter.serialize().filters;
        if (filters.date) {
            this.setState( { selectedYear: null });
        }

        if (( filters['donor-group'] && filters['donor-group'].length > 0 ) || ( filters['donor-agency'] && filters['donor-agency'].length > 0 ) ) {
            this.setState( { selectedDonor: "" });
        }
    }

    toggleHierarchy( event ) {
        this.setState( { hierarchy: $( event.target ).data( "hierarchy" ), selectedDonor: ''}, function() {
            this.props.actions.getOrgList(( this.state.hierarchy === 'donor-group' ) );
            let filters = this.filter.serialize().filters;
            delete filters['donor-group'];
            delete filters['donor-agency'];
            filters[this.state.hierarchy] = [];
            filters[this.state.hierarchy].push( this.state.selectedDonor);
            this.filter.deserialize({filters: filters});            
            this.fetchReportData();                     
        }.bind( this ) );
    }

    getLocalizedColumnName( originalColumnName ) {
        let name = originalColumnName;
        if ( this.props.mainReport && this.props.mainReport.page && this.props.mainReport.page.headers ) {
            let header = this.props.mainReport.page.headers.filter( header => header.originalColumnName === originalColumnName )[0]
            if ( header ) {
                name = header.columnName;
            }
        }
        return name;
    }

    getYearCell( addedGroups, row ) {
        if ( addedGroups.indexOf( row[Constants.YEAR] ) === -1 ) {
            addedGroups.push( row[Constants.YEAR] );
            let matches = this.props.mainReport.page.contents.filter( content => content[Constants.YEAR] === row[Constants.YEAR] );
            return ( <td className="year-col" rowSpan={matches.length}>{row[Constants.YEAR]}</td> )
        }
    }  
    
    goToPage( pageNumber ) {
        let requestData = this.getRequestData();
        requestData.page = pageNumber;
        this.props.actions.fetchReport5aMainReport( requestData, '5a' );
    }
    
    updateRecordsPerPage(recordsPerPage) {
        let requestData = this.getRequestData();
        requestData.recordsPerPage = recordsPerPage;
        this.props.actions.fetchReport5aMainReport( requestData, '5a' );
    }
    
    closeRemarksModal() {
        this.setState({showRemarks: false, remarksUrl: null});
    }
    
    showRemarksModal(event) {       
        this.setState({showRemarks: true, remarksUrl: $( event.target ).data("url")});
    }
    
    render() {
        if ( this.props.mainReport && this.props.mainReport.page ) {
            let addedGroups = [];
            let years = this.props.years.slice();
            return (
                <div>
                    <div id="filter-popup" ref="filterPopup"> </div>
                    <div id="amp-settings" ref="settingsPopup"> </div>
                    <ToolBar showFilters={this.showFilters} showSettings={this.showSettings}/>
                    <div className="section-divider"></div>
                    {this.props.mainReport && this.props.mainReport.summary &&
                        <div className="container-fluid indicator-stats no-padding">
                        <div className="col-md-3 reduced-padding">
                          <div className="indicator-stat-wrapper">
                            <div className="stat-value">{this.props.mainReport.summary[Constants.DISBURSEMENTS_AS_SCHEDULED]}</div>
                            <div className="stat-label">{this.getLocalizedColumnName( Constants.DISBURSEMENTS_AS_SCHEDULED )}</div>
                          </div>
                        </div>
                        <div className="col-md-3 reduced-padding">
                          <div className="indicator-stat-wrapper">
                            <div className="stat-value">{this.props.mainReport.summary[Constants.OVER_DISBURSED]}</div>
                            <div className="stat-label">{this.getLocalizedColumnName(Constants.OVER_DISBURSED)}</div>
                          </div>
                        </div>
                        <div className="col-md-3 reduced-padding">
                        </div>
                        <div className="col-md-3 reduced-padding">
                        </div>
                      </div>                        
                    }
                    <YearsFilterSection onYearClick={this.onYearClick.bind(this)} years={this.props.years} selectedYear={this.state.selectedYear} mainReport={this.props.mainReport}/>                    
                    <div className="container-fluid no-padding">
                        <div className="dropdown">
                            <select name="donorAgency" className="form-control donor-dropdown" value={this.state.selectedDonor} onChange={this.onDonorFilterChange}>
                                <option value="">{this.props.translations['amp.gpi-reports:all-donors']}</option>
                                {this.props.orgList.map( org =>
                                    <option value={org.id} key={org.id} >{org.name}</option>
                                )}
                            </select>
                        </div>
                        <div className="pull-right"><h4>{this.props.translations['amp.gpi-reports:currency']} {this.props.mainReport.settings['currency-code']}</h4></div>
                    </div>                                       
                    <div className="section-divider"></div>     
                        {this.state.showRemarks &&
                             <RemarksPopup showRemarks={this.state.showRemarks} closeRemarksModal={this.closeRemarksModal.bind(this)} remarksUrl={this.state.remarksUrl} code="5a" />                                                  
                        }                        
                        <table className="table table-bordered table-striped indicator-table">
                        <thead>
                        <tr>
                          <th className="col-md-1">{this.getLocalizedColumnName(Constants.YEAR)}</th>
                          <th className="col-md-3">
                          <img src="images/blue_radio_on.png" className={this.state.hierarchy === 'donor-agency' ? 'donor-toggle' : 'donor-toggle donor-toggle-unselected'} onClick={this.toggleHierarchy} data-hierarchy="donor-agency" /><span className="donor-header-text" onClick={this.toggleHierarchy} data-hierarchy="donor-agency">{this.props.translations['amp.gpi-reports:donor-agency']}</span><br />
                          <img src="images/blue_radio_on.png" className={this.state.hierarchy === 'donor-group' ? 'donor-toggle' : 'donor-toggle donor-toggle-unselected'} onClick={this.toggleHierarchy} data-hierarchy="donor-group" /><span className="donor-header-text" onClick={this.toggleHierarchy} data-hierarchy="donor-group">{this.props.translations['amp.gpi-reports:donor-group']}</span>
                          </th>
                          <th>{this.getLocalizedColumnName(Constants.TOTAL_ACTUAL_DISBURSEMENTS)}<span className="light-weight">(Q1)</span></th>
                          <th className="col-md-1">{this.getLocalizedColumnName(Constants.CONCESSIONAL)}? <span className="light-weight">(Yes=1/ No=0)</span></th>
                          <th>{this.getLocalizedColumnName(Constants.ACTUAL_DISBURSEMENTS)} <span className="light-weight">(Q2)</span></th>
                          <th>{this.getLocalizedColumnName(Constants.PLANNED_DISBURSEMENTS)}<span className="light-weight">(Q3)</span></th>
                          <th>{this.getLocalizedColumnName(Constants.DISBURSEMENTS_THROUGH_OTHER_PROVIDERS)}</th>
                          <th>{this.getLocalizedColumnName(Constants.DISBURSEMENTS_AS_SCHEDULED)}</th>
                          <th>{this.getLocalizedColumnName(Constants.OVER_DISBURSED)}</th>
                          <th>
                            <div className="popup">
                              <a data-container="body" data-toggle="popover" data-placement="top" data-content={this.getLocalizedColumnName(Constants.REMARK)} data-original-title="" title="">
                                <img className="table-icon" src="images/remarks-heading-icon.svg"/>
                              </a>
                            </div>
                          </th>
                        </tr>
                      </thead>
                      <tbody>
                          {this.props.mainReport && this.props.mainReport.page && this.props.mainReport.page.contents.map(( row, i ) =>
                          <tr key={i} >
                              {this.getYearCell( addedGroups, row )}
                              <td>{row[Constants.DONOR_AGENCY] || row[Constants.DONOR_GROUP]}</td>
                              <td className="number-column">{row[Constants.TOTAL_ACTUAL_DISBURSEMENTS]}</td>
                              <td className="number-column">{row[Constants.CONCESSIONAL]}</td>
                              <td className="number-column">{row[Constants.ACTUAL_DISBURSEMENTS]}</td>
                              <td className="number-column">{row[Constants.PLANNED_DISBURSEMENTS]}</td>
                              <td className="number-column">{row[Constants.DISBURSEMENTS_THROUGH_OTHER_PROVIDERS]}</td>
                              <td className="number-column">{row[Constants.DISBURSEMENTS_AS_SCHEDULED]}</td>
                              <td className="number-column">{row[Constants.OVER_DISBURSED]}</td>
                              <td className="number-column"><img className="table-icon" src="images/remarks-icon.svg" data-url={row[Constants.REMARK]} onClick={this.showRemarksModal.bind(this)}/></td>
                          </tr>
                      )}                      
                      </tbody>
                      </table>                             
                    <div>                 
                         <PagingSection mainReport={this.props.mainReport} goToPage={this.goToPage.bind(this)} updateRecordsPerPage={this.updateRecordsPerPage.bind(this)}/>
                    </div>
                </div>
            );
        }

        return ( <div></div> );
    }

}

function mapStateToProps( state, ownProps ) {
    return {
        mainReport: state.reports['5a'].mainReport,
        orgList: state.commonLists.orgList,
        years: state.commonLists.years,
        translations: state.startUp.translations,
        translate: state.startUp.translate
    }
}

function mapDispatchToProps( dispatch ) {
    return { actions: bindActionCreators( Object.assign( {}, reportsActions, commonListsActions ), dispatch ) }
}

export default connect( mapStateToProps, mapDispatchToProps )( Report5a );
