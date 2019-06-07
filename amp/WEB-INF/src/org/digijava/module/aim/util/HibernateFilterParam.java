package org.digijava.module.aim.util;

import org.hibernate.Query;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;

import java.util.List;

/**
 * Class to generalize setting values for hibernate filters
 * if FilterName is null the same as fieldName will be used
 */
public class HibernateFilterParam {
    private String fieldName;
    private String filterName;
    private Object value;
    private AbstractSingleColumnStandardBasicType hibernateType;
    private String operator;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public AbstractSingleColumnStandardBasicType getHibernateType() {
        return hibernateType;
    }

    public void setHibernateType(AbstractSingleColumnStandardBasicType hibernateType) {
        this.hibernateType = hibernateType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public HibernateFilterParam(String fieldName, String filtername, Object value,
                                AbstractSingleColumnStandardBasicType hibernateType) {
        this(fieldName, filtername, value, hibernateType, "=");
    }

    public HibernateFilterParam(String fieldName, String filtername, Object value,
                                AbstractSingleColumnStandardBasicType hibernateType, String operator) {
        this.fieldName = fieldName;
        this.filterName = filtername;
        this.value = value;
        this.hibernateType = hibernateType;
        this.operator = operator;

    }

    public static void getQueryStringFromFilterParam(List<HibernateFilterParam> paramList, StringBuffer hqlQuery) {
        paramList.forEach(filterParam -> {
            String filterName = filterParam.getFilterName() != null ? filterParam.getFilterName()
                    : filterParam.getFieldName();
            hqlQuery.append(" and " + filterParam.getFieldName() + " " + filterParam.getOperator() + ":" + filterName);
        });
    }

    public static void setQueryParams(List<HibernateFilterParam> paramList, Query query) {
        paramList.forEach(filterParam -> {
            String filterName = filterParam.getFilterName() != null ? filterParam.getFilterName()
                    : filterParam.getFieldName();
            query.setParameter(filterName, filterParam.getValue(), filterParam.hibernateType);
        });
    }
}
