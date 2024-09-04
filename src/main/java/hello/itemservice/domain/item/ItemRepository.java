package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    // 실무에서는 HashMap으로 쓰면 안 된다. 멀티쓰레드 환경이면 잘못된 값이 나올 수 있다.
    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L; // static

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

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);

        /**
         * 원래는 update용 ItemParameterDto 이런 걸 id 필드 빼고 만들어서 설계상 명확성을 높이는 게 나음
         * 개발자가 "id는 사용을 안 하나? 어떻게 되지?" 하며 혼란스러울 수 있음
         * 중복이냐 명확성이냐 하면 명확성을 따르는 게 맞음
         */
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
