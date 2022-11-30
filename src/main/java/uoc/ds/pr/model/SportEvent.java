package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.SportEvents4Club;
import java.time.LocalDate;
import java.util.Comparator;


public class SportEvent  implements Comparable<SportEvent> {
    private String id;
    private String description;
    private SportEvents4Club.Type type;
    private byte resources;
    private int max;
    private LocalDate startDate;
    private LocalDate endDate;
    private File file;
    private QueueArrayImpl<Player> inscriptions;
    private LinkedList<Rating> ratings;
    private int ratingAccumulate;

    public static final Comparator<String> CMP_K = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
           return o1.compareTo(o2);
        }
    };
    public static final Comparator<SportEvent> CMP_V = new Comparator<SportEvent>() {
        @Override
        public int compare(SportEvent sportEvent1, SportEvent sportEvent2) {
            return Double.compare(sportEvent1.rating(), sportEvent2.rating());
        }
    };

    public SportEvent(String id, String description, SportEvents4Club.Type type, byte resources, int max, LocalDate startDate, LocalDate endDate, File file) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.resources = resources;
        this.max = max;
        this.startDate = startDate;
        this.endDate = endDate;
        this.file = file;
        this.inscriptions = new QueueArrayImpl<>();
        this.ratings = new LinkedList<>();
    }

    public String getEventId(){
        return id;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public QueueArrayImpl<Player> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(QueueArrayImpl<Player> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public int numPlayers(){
        return inscriptions.size();
    }
    public Iterator<Rating> getRatings() {
        return ratings.values();
    }

    public void setRatings(LinkedList<Rating> ratings) {
        this.ratings = ratings;
    }

    @Override
    public int compareTo( SportEvent se) {
        return id.compareTo(se.id);
    }

    public void addRating(SportEvents4Club.Rating rating, String message, Player player) {
        Rating rt = new Rating(rating, message, player);
        ratings.insertEnd(rt);
        ratingAccumulate += rating.getValue();
    }

    public double rating() {
        return (ratings.size()!= 0 ? (double) ratingAccumulate / ratings.size() : 0);
    }

    public boolean hasRatings() {
        return ratings.size() > 0;
    }

    public boolean hasInscriptionSlots() {
        return inscriptions.size() <= max;
    }

    public void inscribePlayer(Player player){
        inscriptions.add(player);
    }

    public int getMaxInscryptions(){
        return max;
    }


}
