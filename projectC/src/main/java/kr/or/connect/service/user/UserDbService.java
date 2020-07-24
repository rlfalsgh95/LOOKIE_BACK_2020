package kr.or.connect.service.user;
import kr.or.connect.dto.user.UserEntity;
import kr.or.connect.dto.user.UserRoleEntity;

import java.util.List;

// UserDbService는 스프링 시큐리티에서 필요로 하는 정보를 가지고 오는 메소드를 가지고 있다.
public interface UserDbService {
    public UserEntity getUser(String loginUserId);
    public List<UserRoleEntity> getUserRoles(String loginUserId);
}
