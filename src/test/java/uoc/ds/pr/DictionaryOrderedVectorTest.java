package uoc.ds.pr;
import edu.uoc.ds.traversal.Iterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.model.File;
import uoc.ds.pr.model.OrganizingEntity;
import uoc.ds.pr.model.SportEvent;
import uoc.ds.pr.util.DictionaryOrderedVector;
import uoc.ds.pr.util.ResourceUtil;
import java.util.Comparator;
import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class DictionaryOrderedVectorTest {
    private DictionaryOrderedVector<String, SportEvent> dictionary;
    public static final Comparator<String> CMP = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };
    private static final int MAX_NUM_SPORT_EVENTS = 5;



    @Before
    public void setUp(){

        dictionary = new DictionaryOrderedVector<>(MAX_NUM_SPORT_EVENTS, CMP);

        OrganizingEntity organizingEntity1 = new OrganizingEntity(3, "ORG_AAA", "description AAA" );
        OrganizingEntity organizingEntity2 = new OrganizingEntity(8, "ORG_ABA", "description ABA" );
        OrganizingEntity organizingEntity3 = new OrganizingEntity(10, "ORG_ABB", "description ABB" );


        byte resources1 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT,  SportEvents4Club.FLAG_VOLUNTEERS);
        File f1 = new File("F-001", "Ev03", "description ev3" , SportEvents4Club.Type.MICRO, resources1, 20, createLocalDate("22-11-2022"), createLocalDate("15-12-2022"), organizingEntity1);

        byte resources2 = ResourceUtil.getFlag(SportEvents4Club.FLAG_BASIC_LIFE_SUPPORT,
                SportEvents4Club.FLAG_PRIVATE_SECURITY, SportEvents4Club.FLAG_PUBLIC_SECURITY);
        File f2 = new File("F-002", "Ev02","description ev2" ,
                SportEvents4Club.Type.MEDIUM, resources2,
                50, createLocalDate("22-11-2022"), createLocalDate("22-11-2022"), organizingEntity2);

        byte resources3 = ResourceUtil.getFlag(SportEvents4Club.FLAG_ALL_OPTS);
        File f3 = new File("F-003", "Ev04","description ev4",
                SportEvents4Club.Type.XLARGE, resources3,
                1500, createLocalDate("22-11-2022"),  createLocalDate("31-01-2023"), organizingEntity3);

        dictionary.put("ev03", new SportEvent("ev03", "basketball event", SportEvents4Club.Type.MEDIUM, resources1,20, createLocalDate("25-11-2022"),createLocalDate("25-12-2022"), f1));
        dictionary.put("ev02", new SportEvent("ev02", "football event", SportEvents4Club.Type.MEDIUM, resources2, 50, createLocalDate("25-11-2022"),createLocalDate("25-12-2022"), f2));
        dictionary.put("ev04", new SportEvent("ev04", "baseball event", SportEvents4Club.Type.MEDIUM, resources2, 1500, createLocalDate("25-11-2022"),createLocalDate("25-12-2022"), f3));
    }

    @Test
    public void getTest(){
        Assert.assertEquals(null, dictionary.get("ev05"));
        SportEvent sportEvent = dictionary.get("ev03");
        Assert.assertEquals("basketball event", sportEvent.getDescription());
    }
    @Test
    public void putTest(){

        OrganizingEntity organizingEntity4 = new OrganizingEntity(12, "ORG_BBA", "description BBA" );
        byte resources3 = ResourceUtil.getFlag(SportEvents4Club.FLAG_ALL_OPTS);
        File f4 = new File("F-003", "ev01","description ev1",
                SportEvents4Club.Type.XLARGE, resources3,
                1500, createLocalDate("22-11-2022"),  createLocalDate("31-01-2023"), organizingEntity4);
        Assert.assertEquals(3, dictionary.size());
        dictionary.put("ev01", new SportEvent("ev01","description ev1",
                SportEvents4Club.Type.XLARGE, resources3,
                1500, createLocalDate("22-11-2022"),  createLocalDate("31-01-2023"), f4));
        Assert.assertEquals(4, dictionary.size());

        Iterator<SportEvent> it = dictionary.values();

        SportEvent sportEvent1 = it.next();
        Assert.assertEquals("ev01", sportEvent1.getEventId());

        SportEvent sportEvent2 = it.next();
        Assert.assertEquals("ev02", sportEvent2.getEventId());

        SportEvent sportEvent3 = it.next();
        Assert.assertEquals("ev03", sportEvent3.getEventId());
    }


    @Test
    public void fullTest(){
        Assert.assertFalse(dictionary.isFull());

        OrganizingEntity organizingEntity5 = new OrganizingEntity(14, "ORG_BBA", "description BBA" );
        byte resources5 = ResourceUtil.getFlag(SportEvents4Club.FLAG_ALL_OPTS);
        File f5 = new File("F-003", "ev05","description ev5",
                SportEvents4Club.Type.XLARGE, resources5,
                200, createLocalDate("22-11-2022"),  createLocalDate("31-01-2023"), organizingEntity5);

        dictionary.put("ev05", new SportEvent("ev05","description ev5",
                SportEvents4Club.Type.XLARGE, resources5,
                1500, createLocalDate("22-11-2022"),  createLocalDate("31-01-2023"), f5));
        Assert.assertTrue(true);

    }



}
