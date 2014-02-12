package Main.DAL;

import Main.Entities.usage.UnitUser;

/**
 * Created by Jackson on 2/8/14.
 */
public interface IUserDAO {
    UnitUser Create(UnitUser newUnitUser);

    void Delete(int unitUserId);

    UnitUser Get(int user_id);

    UnitUser Update(UnitUser unitUser) throws Exception;

}
