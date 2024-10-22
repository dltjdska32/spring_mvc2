package hello.login.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>();
    private static Long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }


    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long ItemId, Item updatedItem) {
        Item findItem = store.get(ItemId);
        findItem.setItemName(updatedItem.getItemName());
        findItem.setPrice(updatedItem.getPrice());
        findItem.setQuantity(updatedItem.getQuantity());
    }

    public void clearStrore(){
        store.clear();
    }
}
