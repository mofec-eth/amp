/**
 * Event.java
 * (c) 2007 Development Gateway Foundation
 */
package org.digijava.module.message.helper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event.java
 * Object instantiated by a Trigger object, encapsulating the event information. 
 * This object will be processed by the messaging system. If an alert has been saved with
 * the same trigger its message will be sent after replacing the parameterNames with the values
 * in {@link #getParameters()} 
 * @author mihai
 * @package org.digijava.module.message.helper
 * @since 02.05.2008
 * @see Trigger
 */
public class Event {
    
    /**
     * Creates an Event object that references the creator trigger and also sets the
     * creation date of the event
     * @param generator the trigger that generated the event
     */
    public Event(Class trigger) {
	this.trigger=trigger;
	this.creationDate=new Date(System.currentTimeMillis());
	this.parameters=new HashMap<String,Object>();
    }
    
    /**
     * the creation date when the event was generated
     */
    private Date creationDate;
    
    /**
     * This may contain extra recipients, created dynamically. The messaging system
     * will process this and append those to the final message destination. 
     * This list can hold Teams or Team Members or both. The messaging system needs
     * to handle this separately, it needs to know how to send a message to several
     * team members or to an entire Team (which means it will extract the members 
     * of that team)
     */
    private List extraRecipients;
    
    /**
     * the trigger object that generated this event. useful to see who wants to receive
     * events generated by this trigger
     */
    private Class trigger;
    
    /**
     * the list of parameterNames values as they were extracted by the trigger from the
     * source (triggered) object like AmpActivity. The map holds the names of the properties
     * as keys plus the values of the properties as values. This is used in the alert templates
     * to replace each parameterName with the actual value of it
     */
    private Map<String,Object> parameters;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

   

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Class getTrigger() {
        return trigger;
    }

    public void setTrigger(Class trigger) {
        this.trigger = trigger;
    }

    public List getExtraRecipients() {
        return extraRecipients;
    }

    public void setExtraRecipients(List extraRecipients) {
        this.extraRecipients = extraRecipients;
    }
}
