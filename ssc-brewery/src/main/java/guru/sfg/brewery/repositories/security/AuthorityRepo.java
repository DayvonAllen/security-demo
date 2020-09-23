package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorityRepo extends JpaRepository<PermissionsEntity, UUID> {
}
