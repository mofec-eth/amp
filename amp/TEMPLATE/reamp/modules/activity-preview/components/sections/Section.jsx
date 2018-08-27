import React, { Component } from 'react';
import SimpleField from '../fields/SimpleField';
import * as AC from '../../utils/ActivityConstants';
require('../../styles/ActivityView.css');

/**
 * @author Daniel Oliva
 */
const Section = (ComposedSection, SectionTitle = null, useEncapsulateHeader = true, sID) => class extends Component {

  buildSimpleField(activity, fieldPath, showIfNotAvailable, noDataValue, inline = false, options) {
    const field = activity[0][fieldPath];
    const options_ = options || {};
    //TODO
    const title_ = (field.field_label.en ? field.field_label.en : '');
    let value_ = field.value;
    
    if (value_ === '' || value_ === null) {
      value_ = noDataValue;
    }
    if (showIfNotAvailable === true || (value_ !== undefined && value_ !== null)) {
      const useInnerHTML = AC.RICH_TEXT_FIELDS.has(fieldPath);
      return (<SimpleField key={title_}
        title={title_} value={value_} 
        useInnerHTML={useInnerHTML}
        inline={inline}
        separator={false}
        fieldNameClass={options_.fieldNameClass || ''}
        fieldValueClass={options_.fieldValueClass || ''} />);
    }
    
  }

  render() {
    const composedSection = (<ComposedSection
      {...this.props} {...this.state} {...this.context} buildSimpleField={this.buildSimpleField.bind(this)} />);

    return (<div key={SectionTitle} className={this.props.groupClass} id={sID}>
      <div className={this.props.titleClass}>
        <span>{SectionTitle} </span><span>{this.props.titleDetails}</span>
      </div>
      <div className={this.props.composedClass}>
        {composedSection}
      </div>
    </div>);
  }
};



export default Section;
