package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.traversal.Iterator;

import java.time.LocalDate;
import java.util.Date;

public class Player {
    private String id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private LinkedList<SportEvent> sportEvents;

    public Player(String id, String name, String surname, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.sportEvents = new LinkedList<SportEvent>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Iterator<SportEvent> sportEvents(){
        return this.sportEvents.values();
    }
    public int numEvents(){
       return sportEvents.size();
    }

    public Iterator<SportEvent> getSportEvents(){
        return sportEvents.values();
    }
    public boolean isInEvent(String eventId) {
        Iterator<SportEvent> iterator = sportEvents.values();
        boolean found = false;
        SportEvent sportEvent = null;

        while (!found && iterator.hasNext()) {
            sportEvent = iterator.next();
            found = sportEvent.getEventId().equals(eventId);
        }
        return found;
    }

    public boolean hasSportEvents() {
        return(sportEvents.size() > 0);
    }

    public void addSportEvent(SportEvent sportEvent) {
        sportEvents.insertEnd(sportEvent);
    }
}
