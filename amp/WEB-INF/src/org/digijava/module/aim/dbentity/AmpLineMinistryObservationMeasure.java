package org.digijava.module.aim.dbentity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AmpLineMinistryObservationMeasure implements Serializable, Cloneable {
	private Long ampLineMinistryObservationMeasureId;
	private String name;
	private AmpLineMinistryObservation lineMinistryObservation;
	private Set<AmpLineMinistryObservationActor> actors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAmpLineMinistryObservationMeasureId() {
		return ampLineMinistryObservationMeasureId;
	}

	public void setAmpLineMinistryObservationMeasureId(
			Long ampLineMinistryObservationMeasureId) {
		this.ampLineMinistryObservationMeasureId = ampLineMinistryObservationMeasureId;
	}

	public AmpLineMinistryObservation getLineMinistryObservation() {
		return lineMinistryObservation;
	}

	public void setLineMinistryObservation(
			AmpLineMinistryObservation lineMinistryObservation) {
		this.lineMinistryObservation = lineMinistryObservation;
	}

	public void setActors(Set<AmpLineMinistryObservationActor> actors) {
		this.actors = actors;
	}
	
	public Set<AmpLineMinistryObservationActor> getActors() {
		return actors;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		AmpLineMinistryObservationMeasure aux = (AmpLineMinistryObservationMeasure) super.clone();
		aux.setAmpLineMinistryObservationMeasureId(null);
		
		if (aux.actors != null && aux.actors.size() > 0){
			Set<AmpLineMinistryObservationActor> set = new HashSet<AmpLineMinistryObservationActor>();
			Iterator<AmpLineMinistryObservationActor> i = aux.actors.iterator();
			while (i.hasNext()) {
				AmpLineMinistryObservationActor newActor = (AmpLineMinistryObservationActor) i.next().clone();
				newActor.setAmpLineMinistryObservationActorId(null);
				newActor.setMeasure(aux);
				set.add(newActor);
			}
			aux.actors = set;
		}
		else
			aux.actors = null;
		
		return aux;
	}
}