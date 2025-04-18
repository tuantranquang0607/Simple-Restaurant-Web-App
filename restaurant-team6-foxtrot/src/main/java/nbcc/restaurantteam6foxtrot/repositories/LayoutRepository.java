package nbcc.restaurantteam6foxtrot.repositories;

import nbcc.restaurantteam6foxtrot.entities.Layout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LayoutRepository extends JpaRepository<Layout, Long>{
    List<Object> findByArchived(boolean archived);
}
