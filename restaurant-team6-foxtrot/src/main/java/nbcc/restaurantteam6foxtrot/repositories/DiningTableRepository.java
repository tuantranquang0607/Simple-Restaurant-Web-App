package nbcc.restaurantteam6foxtrot.repositories;

import nbcc.restaurantteam6foxtrot.entities.DiningTable;
import nbcc.restaurantteam6foxtrot.entities.Layout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiningTableRepository extends JpaRepository<DiningTable, Long>
{
    List<DiningTable> findAllByLayout(Layout layout);

    List<DiningTable> findAllByLayout_ArchivedNotAndLayout(boolean layoutArchived, Layout layout);

    List<DiningTable> findAllByLayout_ArchivedAndLayout(boolean layoutArchived, Layout layout);

    List<DiningTable> findByArchivedNotAndLayout(boolean archived, Layout layout);
}
