package vj.dao;

import vj.domain.User;

public interface IUserDao {

	User findByLoginNameAndPassword(User user);

}
