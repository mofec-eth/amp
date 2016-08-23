//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.23 at 07:03:30 PM EEST 
//


package org.digijava.module.dataExchangeIATI.iatiSchema.jaxb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{}activity-website"/>
 *         &lt;element ref="{}reporting-org"/>
 *         &lt;element ref="{}participating-org"/>
 *         &lt;element ref="{}recipient-country"/>
 *         &lt;element ref="{}recipient-region"/>
 *         &lt;element ref="{}collaboration-type"/>
 *         &lt;element ref="{}default-flow-type"/>
 *         &lt;element ref="{}default-aid-type"/>
 *         &lt;element ref="{}implementation-level"/>
 *         &lt;element ref="{}default-finance-type"/>
 *         &lt;element ref="{}iati-identifier"/>
 *         &lt;element ref="{}other-identifier"/>
 *         &lt;element ref="{}title"/>
 *         &lt;element ref="{}description"/>
 *         &lt;element ref="{}sector"/>
 *         &lt;element ref="{}activity-date"/>
 *         &lt;element ref="{}activity-status"/>
 *         &lt;element ref="{}contact-info"/>
 *         &lt;element ref="{}default-tied-status"/>
 *         &lt;element ref="{}policy-marker"/>
 *         &lt;element ref="{}location"/>
 *         &lt;element ref="{}transaction"/>
 *         &lt;element ref="{}result"/>
 *         &lt;element ref="{}conditions"/>
 *         &lt;element ref="{}budget"/>
 *         &lt;element ref="{}planned-disbursement"/>
 *         &lt;element ref="{}related-activity"/>
 *         &lt;element ref="{}document-link"/>
 *         &lt;element ref="{}legacy-data"/>
 *         &lt;any processContents='lax' namespace='##other'/>
 *       &lt;/choice>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="last-updated-datetime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/>
 *       &lt;attribute name="default-currency" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="hierarchy" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "activityWebsiteOrReportingOrgOrParticipatingOrg"
})
@XmlRootElement(name = "iati-activity")
public class IatiActivity {

    @XmlElementRefs({
        @XmlElementRef(name = "planned-disbursement", type = PlannedDisbursement.class),
        @XmlElementRef(name = "transaction", type = Transaction.class),
        @XmlElementRef(name = "location", type = Location.class),
        @XmlElementRef(name = "description", type = Description.class),
        @XmlElementRef(name = "activity-date", type = ActivityDate.class),
        @XmlElementRef(name = "policy-marker", type = PolicyMarker.class),
        @XmlElementRef(name = "default-tied-status", type = JAXBElement.class),
        @XmlElementRef(name = "related-activity", type = RelatedActivity.class),
        @XmlElementRef(name = "iati-identifier", type = IatiIdentifier.class),
        @XmlElementRef(name = "other-identifier", type = OtherIdentifier.class),
        @XmlElementRef(name = "legacy-data", type = LegacyData.class),
        @XmlElementRef(name = "default-aid-type", type = JAXBElement.class),
        @XmlElementRef(name = "title", type = JAXBElement.class),
        @XmlElementRef(name = "activity-status", type = JAXBElement.class),
        @XmlElementRef(name = "default-flow-type", type = JAXBElement.class),
        @XmlElementRef(name = "reporting-org", type = ReportingOrg.class),
        @XmlElementRef(name = "collaboration-type", type = JAXBElement.class),
        @XmlElementRef(name = "budget", type = Budget.class),
        @XmlElementRef(name = "document-link", type = DocumentLink.class),
        @XmlElementRef(name = "activity-website", type = ActivityWebsite.class),
        @XmlElementRef(name = "recipient-country", type = RecipientCountry.class),
        @XmlElementRef(name = "implementation-level", type = JAXBElement.class),
        @XmlElementRef(name = "sector", type = Sector.class),
        @XmlElementRef(name = "participating-org", type = ParticipatingOrg.class),
        @XmlElementRef(name = "default-finance-type", type = JAXBElement.class),
        @XmlElementRef(name = "conditions", type = Conditions.class),
        @XmlElementRef(name = "contact-info", type = ContactInfo.class),
        @XmlElementRef(name = "recipient-region", type = RecipientRegion.class),
        @XmlElementRef(name = "result", type = Result.class)
    })
    @XmlAnyElement(lax = true)
    protected List<Object> activityWebsiteOrReportingOrgOrParticipatingOrg;
    @XmlAttribute(name = "version")
    protected BigDecimal version;
    @XmlAttribute(name = "last-updated-datetime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdatedDatetime;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;
    @XmlAttribute(name = "default-currency")
    protected String defaultCurrency;
    @XmlAttribute(name = "hierarchy")
    protected String hierarchy;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the activityWebsiteOrReportingOrgOrParticipatingOrg property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activityWebsiteOrReportingOrgOrParticipatingOrg property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivityWebsiteOrReportingOrgOrParticipatingOrg().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlannedDisbursement }
     * {@link Transaction }
     * {@link Location }
     * {@link Description }
     * {@link ActivityDate }
     * {@link PolicyMarker }
     * {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}
     * {@link Object }
     * {@link Element }
     * {@link RelatedActivity }
     * {@link IatiIdentifier }
     * {@link OtherIdentifier }
     * {@link LegacyData }
     * {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}
     * {@link JAXBElement }{@code <}{@link TextType }{@code >}
     * {@link JAXBElement }{@code <}{@link CodeType }{@code >}
     * {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}
     * {@link ReportingOrg }
     * {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}
     * {@link Budget }
     * {@link DocumentLink }
     * {@link ActivityWebsite }
     * {@link RecipientCountry }
     * {@link JAXBElement }{@code <}{@link CodeType }{@code >}
     * {@link Sector }
     * {@link ParticipatingOrg }
     * {@link JAXBElement }{@code <}{@link CodeReqType }{@code >}
     * {@link Conditions }
     * {@link ContactInfo }
     * {@link RecipientRegion }
     * {@link Result }
     * 
     * 
     */
    public List<Object> getActivityWebsiteOrReportingOrgOrParticipatingOrg() {
        if (activityWebsiteOrReportingOrgOrParticipatingOrg == null) {
            activityWebsiteOrReportingOrgOrParticipatingOrg = new ArrayList<Object>();
        }
        return this.activityWebsiteOrReportingOrgOrParticipatingOrg;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }

    /**
     * Gets the value of the lastUpdatedDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdatedDatetime() {
        return lastUpdatedDatetime;
    }

    /**
     * Sets the value of the lastUpdatedDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdatedDatetime(XMLGregorianCalendar value) {
        this.lastUpdatedDatetime = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the defaultCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    /**
     * Sets the value of the defaultCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultCurrency(String value) {
        this.defaultCurrency = value;
    }

    /**
     * Gets the value of the hierarchy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHierarchy() {
        return hierarchy;
    }

    /**
     * Sets the value of the hierarchy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHierarchy(String value) {
        this.hierarchy = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
