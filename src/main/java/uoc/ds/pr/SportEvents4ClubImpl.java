package uoc.ds.pr;

import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.File;
import uoc.ds.pr.model.OrganizingEntity;
import uoc.ds.pr.model.Player;
import uoc.ds.pr.model.SportEvent;
import uoc.ds.pr.util.DictionaryOrderedVector;
import uoc.ds.pr.util.OrderedVector;

import java.time.LocalDate;

public class SportEvents4ClubImpl implements SportEvents4Club {

    private Player[] players;
    private int numPlayers;
    private QueueArrayImpl<File> files;
    private int totalFiles;
    private int numRejectedFiles;
    private OrganizingEntity[] organizingEntities;
    private int numOrganizingEntities;
    private DictionaryOrderedVector<String, SportEvent> sportEvents;
    private OrderedVector<SportEvent> sportEventsByRating;
    private Player mostActivePlayer;


    public SportEvents4ClubImpl() {
        players = new Player[MAX_NUM_PLAYER];
        numPlayers = 0;
        files = new QueueArrayImpl<>();
        totalFiles = 0;
        numRejectedFiles = 0;
        organizingEntities = new OrganizingEntity[20];
        numOrganizingEntities = 0;
        sportEvents = new DictionaryOrderedVector<>(MAX_NUM_SPORT_EVENTS, SportEvent.CMP_K);
        sportEventsByRating = new OrderedVector<>(MAX_NUM_SPORT_EVENTS, SportEvent.CMP_V);
        this.mostActivePlayer = null;
    }

    @Override
    public void addPlayer(String id, String name, String surname, LocalDate dateOfBirth) {
        Player player = getPlayer(id);
        if(player != null){
            player.setName(name);
            player.setSurname(surname);
            player.setDateOfBirth(dateOfBirth);
        }else{
            addPlayer(new Player(id, name, surname, dateOfBirth));
        }
    }

    private void addPlayer(Player player){
        players[numPlayers] = player;
        numPlayers++;
    }

    @Override
    public void addOrganizingEntity(int id, String name, String description) {
        OrganizingEntity oE = getOrganizingEntity(id);
        if(oE != null){
            oE.setName(name);
            oE.setDescription(description);
        } else{
          addOrganizingEntity(new OrganizingEntity(id, name, description));
        }
    }

    private void addOrganizingEntity(OrganizingEntity oE){
        organizingEntities[numOrganizingEntities] = oE;
        numOrganizingEntities++;
    }

    public void addFile(String id, String eventId, int orgId, String description, Type type, byte resources, int max, LocalDate startDate, LocalDate endDate) throws OrganizingEntityNotFoundException {
    OrganizingEntity orgEntinty = getOrganizingEntity(orgId);
        if( orgEntinty == null){
            throw new OrganizingEntityNotFoundException();
        }else{
            files.add(new File(id, eventId, description, type, resources, max, startDate, endDate, orgEntinty));
            totalFiles++;
        }
    }

    @Override
    public File updateFile(Status status, LocalDate date, String description) throws NoFilesException {
        File file = files.poll();
        if(file == null){
            throw new NoFilesException();
        }
        file.update(status, date, description);
        if(file.isEnabled()){
            SportEvent sportEvent = file.newSportEvent();
            sportEvents.put(sportEvent.getEventId(), sportEvent);
        }else{
            numRejectedFiles++;
        }
        return file;
    }

    @Override
    public void signUpEvent(String playerId, String eventId) throws PlayerNotFoundException, SportEventNotFoundException, LimitExceededException {
        Player player = getPlayer(playerId);
        if (player == null) {
            throw new PlayerNotFoundException();
        }

        SportEvent sportEvent = getSportEvent(eventId);
        if (sportEvent  == null) {
            throw new SportEventNotFoundException();
        }

        sportEvent.inscribePlayer(player);
        player.addSportEvent(sportEvent);
        updateMostActivePlayer(player);

        if (!sportEvent.hasInscriptionSlots()) {
            throw new LimitExceededException();
        }
    }

    private void updateMostActivePlayer(Player player) {
        if(mostActivePlayer == null){
            mostActivePlayer = player;
        }else{
            if(player.numEvents() > mostActivePlayer.numEvents()){
                mostActivePlayer = player;
            }
        }
    }

    @Override
    public double getRejectedFiles() {
        return (double)numRejectedFiles/totalFiles;
    }

    @Override
    public Iterator<SportEvent> getSportEventsByOrganizingEntity(int organizationId) throws NoSportEventsException {
        OrganizingEntity oe = getOrganizingEntity(organizationId);
        if(oe == null){
            throw new NoSportEventsException();
        }
        if(!oe.hasSportEvents()){
            throw new NoSportEventsException();
        }
        return sportEvents.values();
    }

    @Override
    public Iterator<SportEvent> getAllEvents() throws NoSportEventsException {
        if(sportEvents.size() == 0){
            throw new NoSportEventsException();
        }
        return sportEvents.values();
    }

    @Override
    public Iterator<SportEvent> getEventsByPlayer(String playerId) throws NoSportEventsException {
        Player player = getPlayer(playerId);
        if(!player.hasSportEvents()){
            throw new NoSportEventsException();
        }
        return player.getSportEvents();
    }

    @Override
    public void addRating(String playerId, String eventId, Rating rating, String message) throws SportEventNotFoundException, PlayerNotFoundException, PlayerNotInSportEventException {
        Player player = getPlayer(playerId);
        SportEvent sportEvent = getSportEvent(eventId);
        if(player == null){
            throw new PlayerNotFoundException();
        }
        if(sportEvent == null){
            throw new SportEventNotFoundException();
        }
        if(!player.isInEvent(eventId)){
            throw new PlayerNotInSportEventException();
        }
        sportEvent.addRating(rating, message, player);
        sportEventsByRating.update(sportEvent);

    }

    @Override
    public Iterator<uoc.ds.pr.model.Rating> getRatingsByEvent(String eventId) throws SportEventNotFoundException, NoRatingsException {
        SportEvent sportEvent = getSportEvent(eventId);
        if(sportEvent == null){
            throw new SportEventNotFoundException();
        }
        if(!sportEvent.hasRatings()){
            throw new NoRatingsException();
        }
        return sportEvent.getRatings();
    }

    @Override
    public Player mostActivePlayer() throws PlayerNotFoundException {
        if(mostActivePlayer == null){
            throw new PlayerNotFoundException();
        }
        return mostActivePlayer;
    }

    @Override
    public SportEvent bestSportEvent() throws SportEventNotFoundException {
        if(sportEventsByRating.size() == 0){
            throw new SportEventNotFoundException();
        }
        return sportEventsByRating.elementAt(0);
    }

    @Override
    public int numPlayers() {
        return numPlayers;
    }

    @Override
    public int numOrganizingEntities() {
        return numOrganizingEntities;
    }

    @Override
    public int numFiles() {
        return totalFiles;
    }

    @Override
    public int numRejectedFiles() {
        return numRejectedFiles;
    }

    @Override
    public int numPendingFiles() {
        return files.size();
    }

    @Override
    public int numSportEvents() {
        return sportEvents.size();
    }

    @Override
    public int numSportEventsByPlayer(String playerId){
        Player player = getPlayer(playerId);
        return player.numEvents();
    }

    @Override
    public int numPlayersBySportEvent(String sportEventId) {
        SportEvent sportEvent = getSportEvent(sportEventId);
        return sportEvent.numPlayers();
    }

    @Override
    public int numSportEventsByOrganizingEntity(int orgId) {
        OrganizingEntity orgEntity = getOrganizingEntity(orgId);
        return orgEntity.numSportEvents();
    }

    @Override
    public int numSubstitutesBySportEvent(String sportEventId) {
        SportEvent sportEvent = getSportEvent(sportEventId);
        return sportEvent.numPlayers() - sportEvent.getMaxInscryptions();
    }

    @Override
    public Player getPlayer(String playerId) {
        Player player = null;
       for(Player p : players){
           if(p == null){
               return null;
           }
           if(p.getId().equals(playerId)){
               player = p;
               break;
           }
       }
       return player;
    }

    @Override
    public SportEvent getSportEvent(String eventId) {
        return sportEvents.get(eventId);

    }

    @Override
    public OrganizingEntity getOrganizingEntity(int orgEntityId) {
        OrganizingEntity organizingEntity = null;
        for(OrganizingEntity oe : organizingEntities){
            if(oe == null){
                return null;
            }
            if(oe.getId() == orgEntityId){
                organizingEntity = oe;
                break;
            }
        }
        return organizingEntity;
    }

    @Override
    public File currentFile() {
        if(files.size() > 0){
            return files.peek();
        }
        return null;
    }
}
