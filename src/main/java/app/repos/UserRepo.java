package app.repos;

import app.container.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, UUID> {
    @Query(nativeQuery = true, value = "SELECT * from CUSTOMERS where PHONE =:phone")
    Optional<UserEntity> findByPhone(@Param("phone") String phone);

    @Query(nativeQuery = true, value = "SELECT * from testDb.CUSTOMERS")
    List<UserEntity> findAllCustomers();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT into testDb.CUSTOMERS (id,name,phone) values (:id, :name, :phone)")
    void saveCustomer(@Param("id") UUID id, @Param("name") String name, @Param("phone") String phone);

    @Query(nativeQuery = true, value = "SELECT * from testDb.CUSTOMERS where ID =:uuid")
    Optional<UserEntity> findCustomerById(@Param("uuid") UUID uuid);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE from testDb.CUSTOMERS where phone =:phone")
    void deleteCustomer(@Param("phone") String phone);
}
