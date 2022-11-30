package uoc.ds.pr.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;

public class ResourceUtil {

    public static byte getFlag(byte... args) {
        byte flag = 0;
        for(byte arg: args)
            flag |= arg;

        return flag;
    }

    public static boolean hasPrivateSecurity(byte resource){
        return (resource & (1 << 0)) != 0;
    }
    public static boolean hasPublicSecurity(byte resource){
        return (resource & (1 <<1)) != 0;
    }

    public static boolean hasVolunteers(byte resource){
        return (resource & (1 <<2)) != 0;
    }
    public static boolean hasBasicLifeSupport(byte resource){
        return (resource & (1 <<3)) != 0;
    }
    public static boolean hasAllOpts(byte resource){
       return hasPrivateSecurity(resource) && hasPublicSecurity(resource) && hasVolunteers(resource) && hasBasicLifeSupport(resource);
    }


}
