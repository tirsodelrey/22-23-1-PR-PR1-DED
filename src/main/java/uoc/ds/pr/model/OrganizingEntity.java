package uoc.ds.pr.model;

import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.adt.sequential.LinkedList;

public class OrganizingEntity {
    private int id;
    private String name;
    private String description;
    private LinkedList<SportEvent> sportEvents;

    public OrganizingEntity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sportEvents = new LinkedList<SportEvent>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Iterator<SportEvent> getSportEvents() {
        return this.sportEvents.values();
    }

    public boolean hasSportEvents(){
        return(sportEvents.size() > 0);
    }

    public void setSportEvents(LinkedList<SportEvent> sportEvents) {
        this.sportEvents = sportEvents;
    }

    public void addSportEvent(SportEvent sportEvent) {
        sportEvents.insertEnd(sportEvent);
    }

    public int numSportEvents(){
        return sportEvents.size();
    }
}
