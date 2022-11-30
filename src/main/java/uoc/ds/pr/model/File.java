package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.List;
import uoc.ds.pr.SportEvents4Club;

import java.time.LocalDate;

public class File {

    private String fileId;
    private String eventId;
    private String description;
    private SportEvents4Club.Type type;
    private SportEvents4Club.Status status;
    private byte resources;
    private int max;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate statusUpdateDate;
    private OrganizingEntity organizingEntity;
    private List<Rating> ratings;

    public File(String id, String eventId, String description, SportEvents4Club.Type type, byte resources, int max, LocalDate startDate, LocalDate endDate, OrganizingEntity organizingEntity) {
        this.fileId = id;
        this.eventId = eventId;
        this.description = description;
        this.type = type;
        this.resources = resources;
        this.max = max;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = SportEvents4Club.Status.PENDING;
        this.organizingEntity = organizingEntity;
    }

    public String getFileId() {
        return fileId;
    }
    public String getEventId() {
        return eventId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SportEvents4Club.Type getType() {
        return type;
    }

    public void setType(SportEvents4Club.Type type) {
        this.type = type;
    }

    public SportEvents4Club.Status getStatus() {
        return status;
    }

    public void setStatus(SportEvents4Club.Status status) {
        this.status = status;
    }

    public void setStatusUpdateDate(LocalDate date){
        this.statusUpdateDate = date;
    }

    public byte getResources() {
        return resources;
    }

    public void setResources(byte resources) {
        this.resources = resources;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public void update(SportEvents4Club.Status status, LocalDate date, String description) {
        setStatus(status);
        setStatusUpdateDate(date);
        setDescription(description);
    }

    public boolean isEnabled(){
        return this.status == SportEvents4Club.Status.ENABLED;
    }

    public SportEvent newSportEvent(){
        SportEvent sportEvent = new SportEvent(this.eventId, this.description, this.type, this.resources, this.max, this.startDate, this.endDate, this);
        this.organizingEntity.addSportEvent(sportEvent);

        return sportEvent;
    }
}
