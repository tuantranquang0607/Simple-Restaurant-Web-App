package nbcc.restaurantteam6foxtrot.repositories;

import nbcc.restaurantteam6foxtrot.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>
{

}
