package uoc.ds.pr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.model.*;
import uoc.ds.pr.util.OrderedVector;
import uoc.ds.pr.util.ResourceUtil;

import java.util.Comparator;

import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class OrderedVectorTest {
    private OrderedVector<SportEvent> orderedVector;
    private SportEvents4Club sportEvents4Club;
    private static final Comparator<SportEvent> CMP = new Comparator<SportEvent>() {
        @Override
        public int compare(SportEvent sportEvent1, SportEvent sportEvent2) {
            return Double.compare(sportEvent1.rating(), sportEvent2.rating());
        }
    };
    private static final int MAX_ELEMENTS = 3;

    @Before
    public void setUp() {

        orderedVector = new OrderedVector<>(MAX_ELEMENTS, CMP);
    }

    @Test
    public void isEmptyTest() {
        Assert.assertTrue(orderedVector.isEmpty());
    }

    @Test
    public void update() {
        OrganizingEntity organizingEntity1 = new OrganizingEntity(3, "ORG_AAA", "description AAA");
        byte resources1 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT, SportEvents4Club.FLAG_VOLUNTEERS);
        File f1 = new File("F-001", "ev01", "description ev3", SportEvents4Club.Type.MICRO, resources1, 20, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"), organizingEntity1);
        SportEvent sport1 = new SportEvent("ev01", "basketball event", SportEvents4Club.Type.MEDIUM, resources1, 20, createLocalDate("25-11-2022"), createLocalDate("25-12-2022"), f1);
        orderedVector.update(sport1);
        Assert.assertFalse(orderedVector.isEmpty());
        Assert.assertEquals(1, orderedVector.size());

        OrganizingEntity organizingEntity2 = new OrganizingEntity(4, "ORG_AAA", "description AAA");
        byte resources2 = ResourceUtil.getFlag(SportEvents4Club.FLAG_ALL_OPTS, SportEvents4Club.FLAG_VOLUNTEERS);
        File f2 = new File("F-002", "ev02", "description ev2", SportEvents4Club.Type.MICRO, resources2, 30, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"), organizingEntity1);
        SportEvent sport2 = new SportEvent("ev02", "basketball event", SportEvents4Club.Type.MEDIUM, resources2, 30, createLocalDate("25-11-2022"), createLocalDate("25-12-2022"), f2);
        orderedVector.update(sport2);
        Assert.assertEquals(2, orderedVector.size());
        Assert.assertEquals("ev02", orderedVector.last().getEventId());

        Player player1 = new Player("idPlayer1", "Maria", "Simo", createLocalDate("07-01-1934"));
        sport2.addRating(SportEvents4Club.Rating.FOUR, "my rating", player1);
        orderedVector.update(sport2);
        Assert.assertEquals("ev01", orderedVector.last().getEventId());

        SportEvent sport3 = new SportEvent("ev03", "basketball event", SportEvents4Club.Type.MEDIUM, resources2, 30, createLocalDate("25-11-2022"), createLocalDate("25-12-2022"), f2);
        orderedVector.delete(sport3);
        Assert.assertEquals(2, orderedVector.size());
        orderedVector.update(sport3);
        Assert.assertEquals(3, orderedVector.size());
        Assert.assertEquals("ev03", orderedVector.last().getEventId());

        sport3.addRating(SportEvents4Club.Rating.FIVE, "RATING", player1);
        orderedVector.update(sport3);
        Assert.assertEquals("ev01", orderedVector.last().getEventId());
        Assert.assertEquals("ev03", orderedVector.elementAt(0).getEventId());
    }

    @Test
    public void isFullTest(){
        OrganizingEntity organizingEntity1 = new OrganizingEntity(3, "ORG_AAA", "description AAA");
        byte resources1 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT, SportEvents4Club.FLAG_VOLUNTEERS);
        File f1 = new File("F-001", "ev01", "description ev3", SportEvents4Club.Type.MICRO, resources1, 20, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"), organizingEntity1);
        SportEvent sport1 = new SportEvent("ev01", "basketball event", SportEvents4Club.Type.MEDIUM, resources1, 20, createLocalDate("25-11-2022"), createLocalDate("25-12-2022"), f1);
        orderedVector.update(sport1);
        OrganizingEntity organizingEntity2 = new OrganizingEntity(2, "ORG_AAA", "description AAA");
        byte resources2 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT, SportEvents4Club.FLAG_VOLUNTEERS);
        File f2 = new File("F-002", "ev02", "description ev3", SportEvents4Club.Type.MICRO, resources1, 20, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"), organizingEntity1);
        SportEvent sport2 = new SportEvent("ev02", "basketball event", SportEvents4Club.Type.MEDIUM, resources1, 20, createLocalDate("25-11-2022"), createLocalDate("25-12-2022"), f2);
        orderedVector.update(sport2);
        OrganizingEntity organizingEntity3 = new OrganizingEntity(4, "ORG_AAA", "description AAA");
        byte resources3 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT, SportEvents4Club.FLAG_VOLUNTEERS);
        File f3 = new File("F-003", "ev03", "description ev3", SportEvents4Club.Type.MICRO, resources1, 20, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"), organizingEntity1);
        SportEvent sport3 = new SportEvent("ev03", "basketball event", SportEvents4Club.Type.MEDIUM, resources1, 20, createLocalDate("25-11-2022"), createLocalDate("25-12-2022"), f3);
        orderedVector.update(sport3);
        Assert.assertTrue(orderedVector.isFull());
    }

}




