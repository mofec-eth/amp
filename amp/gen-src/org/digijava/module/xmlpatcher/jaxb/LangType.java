//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.23 at 07:03:33 PM EEST 
//


package org.digijava.module.xmlpatcher.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for langType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="langType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="sql"/>
 *     &lt;enumeration value="hql"/>
 *     &lt;enumeration value="mysql"/>
 *     &lt;enumeration value="oracle"/>
 *     &lt;enumeration value="postgres"/>
 *     &lt;enumeration value="bsh"/>
 *     &lt;whiteSpace value="collapse"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "langType")
@XmlEnum
public enum LangType {

    @XmlEnumValue("sql")
    SQL("sql"),
    @XmlEnumValue("hql")
    HQL("hql"),
    @XmlEnumValue("mysql")
    MYSQL("mysql"),
    @XmlEnumValue("oracle")
    ORACLE("oracle"),
    @XmlEnumValue("postgres")
    POSTGRES("postgres"),
    @XmlEnumValue("bsh")
    BSH("bsh");
    private final String value;

    LangType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LangType fromValue(String v) {
        for (LangType c: LangType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
