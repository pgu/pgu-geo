package pgu.server.access;

import pgu.shared.model.UserAndLocations;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

public class DAO extends DAOBase {

    static {
        ObjectifyService.register(UserAndLocations.class);
    }

}
